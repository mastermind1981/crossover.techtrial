package org.pm.crossover.task.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.pm.crossover.task.dao.ExamDAO;
import org.pm.crossover.task.dto.ExamState;
import org.pm.crossover.task.model.Answer;
import org.pm.crossover.task.model.Exam;
import org.pm.crossover.task.model.ExamUser;
import org.pm.crossover.task.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ExamService {
	@Autowired
	private ExamDAO dao;
	@Autowired
	private ExamInfo info;

	@PostConstruct
	public void init() {
		System.out.println("Init exam service");
	}

	/*
	 * get current state
	 */
	public void getCurrentState() {

	}

	public List<Exam> getExams() {
		return dao.list(Exam.class, null, "name");
	}

	public List<Answer> getAnswersFor(int questionId) {
		Question question = dao.findById(Question.class, questionId);
		return getAnswersFor(question);
	}

	public List<Answer> getAnswersFor(Question question) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("question", question);
		return dao.list(Answer.class, map, "answerOrder");
	}

	public Question addAnswers(int[] answerIds) {
		if (answerIds != null) {
			Answer[] answers = new Answer[answerIds.length];
			for (int i = 0; i < answerIds.length - 1; i++) {
				answers[i] = dao.findById(Answer.class, answerIds[i]);
			}
			if (info.addAnswers(answers)) {
				info.setQuestion(getNextQuestion(info.getQuestion()));
			}
		}
		return info.getQuestion();
	}

	public Question startExam(int examId) {
		Exam exam = dao.findById(Exam.class, examId);
		if (info.startExam(exam)) {
			info.setQuestion(getFirstQuestion(exam));
		}
		return info.getQuestion();
	}

	public ExamState getState() {
		return info.checkAndFinishExam();
	}

	/*
	 * get next question
	 * 
	 * start exam
	 * 
	 * set answer
	 */

	/* package-visible methods to be used only by ExamInfo class */

	private Question getNextQuestion(Question q) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exam", q.getExam());
		map.put("questionOrder", new Integer(q.getQuestionOrder() + 1));
		List<Question> list = dao.list(Question.class, map);
		if (!list.isEmpty()) {
			return list.iterator().next();
		}
		info.checkAndFinishExam();
		return null;
	}

	private Question getFirstQuestion(Exam e) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exam", e);
		map.put("questionOrder", new Integer(0));
		List<Question> list = dao.list(Question.class, map);
		if (!list.isEmpty()) {
			return list.iterator().next();
		}
		info.checkAndFinishExam();
		return null;
	}

	public void setUser(Authentication authentication) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", authentication.getName());
//		map.put("password", authentication.getCredentials());
		List<ExamUser> list = dao.list(ExamUser.class, map);
		if (!list.isEmpty()) {
			info.setUser(list.iterator().next());
		} else {
			info.setUser(null);
		}
	}
}
