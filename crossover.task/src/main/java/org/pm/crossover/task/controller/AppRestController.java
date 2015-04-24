package org.pm.crossover.task.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.pm.crossover.task.dto.AnswerDTO;
import org.pm.crossover.task.dto.ExamStateDTO;
import org.pm.crossover.task.model.Exam;
import org.pm.crossover.task.model.Question;
import org.pm.crossover.task.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

/**
 * The main REST API for exam application
 *
 */
@RestController
@RequestMapping("/rest")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AppRestController {

	@Autowired
	ExamService svc;

	/**
	 * 
	 * @return Current state of user session. It shows whether the user is
	 *         currently working on the exam or not.
	 */
	@RequestMapping("/state")
	public @ResponseBody ExamStateDTO getState() {
		return svc.getState();
	}

	/**
	 * 
	 * @return the time left for current exam
	 */
	@RequestMapping("/timeleft")
	public @ResponseBody long getTimeLeft() {
		return svc.getTimeLeft();
	}

	/**
	 * 
	 * @return the list of all exams in database
	 */
	@RequestMapping("/exams")
	public List<Exam> getExams() {
		return svc.getExams();
	}

	/**
	 * 
	 * @return the list of questions for the current exam with mark showing if
	 *         the question has been already answered or not
	 */
	@RequestMapping("/questionsmap")
	public @ResponseBody Map<Question, Boolean> getQuestionsMap() {
		return svc.getQuestionsWithAnswerMarks();
	}

	/**
	 * 
	 * @return the list of only unanswered questions
	 */
	@RequestMapping("/questions")
	public @ResponseBody List<Question> getUnansweredQuestions() {
		Map<Question, Boolean> map = svc.getQuestionsWithAnswerMarks();
		ArrayList<Question> list = new ArrayList<Question>();
		for (Question q : map.keySet()) {
			Boolean bool = map.get(q);
			if (bool == null || !bool) {
				list.add(q);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param questionId
	 *            current question id
	 * @return the list of answer options (without flag showing if the answer is
	 *         correct)
	 */
	@RequestMapping("/answers")
	public @ResponseBody List<AnswerDTO> getAnswers(
			@RequestParam("id") int questionId) {
		return svc.getAnswersFor(questionId);
	}

	/**
	 * 
	 * @param id
	 *            the selected exam id
	 * @return the list of questions for selected exam
	 */
	@RequestMapping("/startexam")
	public List<Question> startExam(@RequestParam("id") int id) {
		return svc.startExam(id);
	}

	/**
	 * 
	 * @param questionId
	 *            current question id
	 * @param answers
	 *            the list of answer options chosen by user as correct
	 * @return the next unanswered question id
	 */
	@RequestMapping(value = "/postanswer", method = RequestMethod.POST)
	public Integer putAnswers(@RequestParam("id") int questionId,
			@RequestBody int[] answers) {
		Question q = svc.addAnswers(questionId, answers);
		if (q != null) {
			return q.getId();
		} else {
			return -1;
		}
	}

}
