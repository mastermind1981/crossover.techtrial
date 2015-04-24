package org.pm.crossover.task.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.pm.crossover.task.dao.ExamDAO;
import org.pm.crossover.task.dto.AnswerDTO;
import org.pm.crossover.task.dto.DTOUtil;
import org.pm.crossover.task.dto.ExamStateDTO;
import org.pm.crossover.task.model.Answer;
import org.pm.crossover.task.model.Exam;
import org.pm.crossover.task.model.ExamUser;
import org.pm.crossover.task.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(WebApplicationContext.SCOPE_SESSION)
@Transactional
public class ExamService {
	@Autowired
	private ExamDAO dao;
	@Autowired
	private ExamInfo info;

	@PostConstruct
	public void init() {
		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		setUser(a);

	}

	/**
	 * 
	 * @return list of exams
	 */
	public List<Exam> getExams() {
		return dao.list(Exam.class, null, "name");
	}

	/**
	 * list of questions for the given exam
	 */
	List<Question> getQuestionsFor(Exam exam) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exam", exam);
		return dao.list(Question.class, map, "questionOrder");
	}

	/**
	 * 
	 * @param questionId
	 *            current question id
	 * @return list of answers for the given question (without
	 *         <code>correct</code> field)
	 */
	public List<AnswerDTO> getAnswersFor(int questionId) {
		Question question = dao.findById(Question.class, questionId);
		return DTOUtil.getAnswerList(getAnswersFor(question));
	}

	/**
	 * 
	 * @param question
	 *            current question
	 * @return list of answers for the given question (without
	 *         <code>correct</code> field)
	 */
	List<Answer> getAnswersFor(Question question) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("question", question);
		return dao.list(Answer.class, map, "answerOrder");
	}

	/**
	 * put answers for the given question
	 * 
	 * @param questionId
	 *            current question id
	 * @param answerIds
	 *            user answers ids
	 * @return next unanswered question (null if there are no unanswered
	 *         questions)
	 */
	public Question addAnswers(int questionId, int[] answerIds) {
		Question q = dao.findById(Question.class, questionId);
		if (answerIds != null) {
			Answer[] answers = new Answer[answerIds.length];
			for (int i = 0; i < answerIds.length; i++) {
				answers[i] = dao.findById(Answer.class, answerIds[i]);
			}
			if (info.addAnswers(q, answers)) {
				if (info.isExamFullyAnswered()) {
					return null;
				}
				q = getNextQuestion(q);
				while (q == null || info.isAnswered(q)) {
					q = getNextQuestion(q);
				}
				return q;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param examId
	 *            selected exam id
	 * @return list of exam questions
	 */
	public List<Question> startExam(int examId) {
		Exam exam = dao.findById(Exam.class, examId);
		List<Question> questions = getQuestionsFor(exam);
		info.startExam(exam, questions);
		return info.getQuestions();
	}

	/**
	 * 
	 * @return current state
	 */
	public ExamStateDTO getState() {
		return info.checkAndFinishExam();
	}

	private Question getFirstQuestion(Exam e) {
		List<Question> qq = getQuestionsFor(e);
		if (qq == null || qq.isEmpty()) {
			return null;
		}
		return qq.iterator().next();
	}

	private Question getNextQuestion(Question q) {
		if (q == null) {
			return q;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exam", q.getExam());
		map.put("questionOrder", new Integer(q.getQuestionOrder() + 1));
		List<Question> list = dao.list(Question.class, map);
		if (!list.isEmpty()) {
			return list.iterator().next();
		}
		return getFirstQuestion(q.getExam());
	}

	/**
	 * finds user with given username
	 * 
	 * @param authentication
	 *            authentication
	 */
	public void setUser(Authentication authentication) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", authentication.getName());
		// map.put("password", authentication.getCredentials());
		List<ExamUser> list = dao.list(ExamUser.class, map);
		if (!list.isEmpty()) {
			info.setUser(list.iterator().next());
		} else {
			info.setUser(null);
		}
	}

	/**
	 * 
	 * @return the time left in milliseconds
	 */
	public long getTimeLeft() {
		return info.getStartTime() == null || info.getExam() == null ? 0
				: (info.getExam().getDuration() * 60 * 1000
						+ info.getStartTime().getTime() - new Date().getTime());
	}

	/**
	 * 
	 * @return the list of questions for the current exam with mark showing if
	 *         the question has been already answered or not
	 */
	public Map<Question, Boolean> getQuestionsWithAnswerMarks() {
		Map<Question, Set<Answer>> aa = info.getAnsweredQuestions();
		List<Question> qq = info.getQuestions();
		HashMap<Question, Boolean> r = new HashMap<Question, Boolean>();
		for (Question q : qq) {
			Set<Answer> a = aa.get(q);
			r.put(q, a != null && !a.isEmpty());
		}
		return r;
	}
}
