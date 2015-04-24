/**
 * 
 */
package org.pm.crossover.task.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.pm.crossover.task.dao.ExamDAO;
import org.pm.crossover.task.dto.DTOUtil;
import org.pm.crossover.task.dto.ExamDTO;
import org.pm.crossover.task.dto.ExamStateDTO;
import org.pm.crossover.task.model.Answer;
import org.pm.crossover.task.model.Exam;
import org.pm.crossover.task.model.ExamResult;
import org.pm.crossover.task.model.ExamUser;
import org.pm.crossover.task.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * the session object for user. Stores the info about current state
 */
@Data
@NoArgsConstructor
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ExamInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4258463847164317127L;

	@Autowired
	private ExamDAO dao;
	/**
	 * user in session
	 */
	private ExamUser user;
	/**
	 * selected exam
	 */
	private Exam exam;
	private final List<Question> questions = new ArrayList<Question>();
	/**
	 * We log the time the user has begun examining
	 */
	private Date startTime;
	private Date finishTime;

	private final Map<Question, Set<Answer>> answeredQuestions = new Hashtable<Question, Set<Answer>>();

	/**
	 * 
	 * @param question
	 *            question
	 * @param answers
	 *            list of answers
	 * @return <code>true</code> if the answers were put successfully and the
	 *         question now considered as answered, <code>false</code> otherwise
	 */
	public boolean addAnswers(Question question, Answer... answers) {
		Set<Answer> set = answeredQuestions.get(question);
		for (Answer a : answers) {
			if (question != null && a != null
					&& question.equals(a.getQuestion())) {
				if (set == null) {
					set = new HashSet<Answer>();
					answeredQuestions.put(question, set);
				}
				set.add(a);
			}
		}
		return set != null && !set.isEmpty();
	}

	/**
	 * 
	 * @param question
	 *            question
	 * @return <code>true</code> if question has answer(s) chosen by user,
	 *         <code>false</code> otherwise
	 */
	public boolean isAnswered(Question question) {
		Set<Answer> set = answeredQuestions.get(question);
		return set != null && !set.isEmpty();
	}

	/**
	 * checks if the current exam can be considered as finished and generates
	 * current state object
	 * 
	 * @return current state
	 */
	public ExamStateDTO checkAndFinishExam() {
		if (isExamFinished()) {
			finishTime = new Date();
			ExamStateDTO state = getState();
			int score = 0;
			for (Set<Answer> s : answeredQuestions.values()) {
				for (Answer a : s) {
					if (a.getCorrect()) {
						score++;
					}
				}
			}
			state.setScore(score);
			Map<Integer, Set<Answer>> answers = new HashMap<Integer, Set<Answer>>();
			for (Question q : answeredQuestions.keySet()) {
				answers.put(q.getId(), answeredQuestions.get(q));
			}
			state.setUserAnswers(answers);
			ExamResult result = new ExamResult();
			result.setExam(exam);
			result.setExamuser(user);
			result.setExamFullyAnswered(isExamFullyAnswered());
			result.setExamTimedOut(isExamTimedOut());
			result.setFinishTime(finishTime);
			result.setStartTime(startTime);
			result.setQuestionsAnswered(answeredQuestions.size());
			result.setScore(score);
			dao.insert(result);
			clearResults();
			return state;
		}
		return getState();
	}

	/**
	 * removes all information about the currently running exam
	 */
	public void clearResults() {
		exam = null;
		questions.clear();
		startTime = null;
		finishTime = null;
		answeredQuestions.clear();
	}

	/**
	 * 
	 * @return current state (considering the exam is either still running or
	 *         not started at all)
	 */
	public ExamStateDTO getState() {
		ExamStateDTO state = new ExamStateDTO();
		state.setExamActive(isExamActive());
		if (exam != null) {
			state.setExam(new ExamDTO(exam));
			state.getExam().setQuestions(DTOUtil.getQuestionList(questions));
		}
		state.setExamStarted(isExamStarted());
		state.setExamTimedOut(isExamTimedOut());
		state.setExamFullyAnswered(isExamFullyAnswered());
		state.setQuestionsAnswered(answeredQuestions.size());
		state.setQuestionsCount(exam == null ? 0 : questions.size());
		state.setTimeLeft(startTime == null || exam == null ? 0 : (exam
				.getDuration() * 60 * 1000 + startTime.getTime() - new Date()
				.getTime()));
		state.setUserName(user == null ? null : user.getFullName());
		state.setStartTime(startTime);
		state.setFinishTime(finishTime);
		return state;
	}

	@PostConstruct
	public void init() {
	}

	/**
	 * 
	 * @return <code>true</code> if the exam is currently running,
	 *         <code>false</code> otherwise
	 */
	public boolean isExamActive() {
		return isExamStarted() && !isExamFullyAnswered() && !isExamTimedOut();
	}

	/**
	 * 
	 * @return <code>true</code> if the exam was running ans now is finished,
	 *         <code>false</code> otherwise
	 */
	public boolean isExamFinished() {
		return isExamStarted() && (isExamFullyAnswered() || isExamTimedOut());
	}

	/**
	 * 
	 * @return <code>true</code> if all the questions from the exam are unswered
	 *         by user, <code>false</code> otherwise
	 */
	public boolean isExamFullyAnswered() {
		return isExamStarted()
				&& answeredQuestions.keySet().containsAll(questions);
	}

	/**
	 * 
	 * @return <code>true</code> if the user has selected the exam,
	 *         <code>false</code> otherwise
	 */
	public boolean isExamStarted() {
		return isUserAuthenticated() && exam != null;
	}

	/**
	 * 
	 * @return <code>true</code> if the time for the exam has been out,
	 *         <code>false</code> otherwise
	 */
	public boolean isExamTimedOut() {
		return isExamStarted()
				&& startTime != null
				&& (new Date().getTime() - startTime.getTime() > exam
						.getDuration() * 60 * 1000);
	}

	/**
	 * 
	 * @return <code>true</code> if the user has been authenticated,
	 *         <code>false</code> otherwise
	 */
	public boolean isUserAuthenticated() {
		return user != null;
	}

	/**
	 * 
	 * @param e
	 *            exam user selected
	 * @param questions
	 *            list of questions for the exam
	 * @return <code>true</code> if the progress for given exam has been started
	 *         right now, <code>false</code> otherwise
	 */
	public boolean startExam(Exam e, List<Question> questions) {
		if (e != null && !isExamStarted()) {
			exam = e;
			this.questions.clear();
			this.questions.addAll(questions);
			startTime = new Date();
			return true;
		}
		return false;
	}

}
