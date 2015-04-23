/**
 * 
 */
package org.pm.crossover.task.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.pm.crossover.task.dto.ExamState;
import org.pm.crossover.task.model.Answer;
import org.pm.crossover.task.model.Exam;
import org.pm.crossover.task.model.ExamUser;
import org.pm.crossover.task.model.Question;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 *
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
	/**
	 * user in session
	 */
	private ExamUser user;
	/**
	 * selected exam
	 */
	private Exam exam;
	/**
	 * current question
	 */
	private Question question;
	/**
	 * We log the time the user has begun examining
	 */
	private Date startTime;
	private Date finishTime;

	private final Map<Question, Set<Answer>> answeredQuestions = new Hashtable<Question, Set<Answer>>();

	public boolean addAnswers(Answer... answers) {
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

	public ExamState checkAndFinishExam() {
		if (isExamFinished()) {
			finishTime = new Date();
			question = null;
			ExamState state = getState();
			state.setUserAnswers(answeredQuestions);
			Map<Question, Set<Answer>> correctAnswers = new Hashtable<Question, Set<Answer>>();
			for (Question q : exam.getQuestions()) {
				HashSet<Answer> set = new HashSet<Answer>();
				for (Answer a : q.getAnswers()) {
					if (a.getCorrect()) {
						set.add(a);
					}
				}
				correctAnswers.put(q, set);
			}
			state.setCorrectAnswers(correctAnswers);
			clearResults();
			return state;
		}
		return getState();
	}

	public void clearResults() {
		exam = null;
		question = null;
		startTime = null;
		finishTime = null;
		answeredQuestions.clear();
	}

	public ExamState getState() {
		ExamState state = new ExamState();
		state.setExamActive(isExamActive());
		state.setExamName(exam == null ? null : exam.getName());
		state.setExamStarted(isExamStarted());
		state.setExamTimedOut(isExamTimedOut());
		state.setExamFullyAnswered(isExamFullyAnswered());
		state.setQuestionsAnswered(answeredQuestions.size());
		state.setQuestionsCount(exam == null ? 0 : exam.getQuestions().size());
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

	public boolean isExamActive() {
		return isExamStarted() && !isExamFullyAnswered() && !isExamTimedOut();
	}

	public boolean isExamFinished() {
		return isExamStarted() && (isExamFullyAnswered() || isExamTimedOut());
	}

	public boolean isExamFullyAnswered() {
		return isExamStarted()
				&& answeredQuestions.keySet().containsAll(exam.getQuestions());
	}

	public boolean isExamStarted() {
		return isUserAuthenticated() && exam != null;
	}

	public boolean isExamTimedOut() {
		return isExamStarted()
				&& startTime != null
				&& (new Date().getTime() - startTime.getTime() <= exam
						.getDuration() * 60 * 1000);
	}

	public boolean isUserAuthenticated() {
		return user != null;
	}

	public boolean startExam(Exam e) {
		if (!isExamStarted()) {
			exam = e;
			question = null;
			startTime = new Date();
			return true;
		}
		return false;
	}

}
