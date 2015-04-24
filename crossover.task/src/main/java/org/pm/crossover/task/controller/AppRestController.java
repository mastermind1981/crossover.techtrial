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

@RestController
@RequestMapping("/rest")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AppRestController {

	@Autowired
	ExamService svc;

	@RequestMapping("/state")
	public @ResponseBody ExamStateDTO getState() {
		return svc.getState();
	}

	@RequestMapping("/timeleft")
	public @ResponseBody long getTimeLeft() {
		return svc.getTimeLeft();
	}

	@RequestMapping("/exams")
	public List<Exam> getExams() {
		return svc.getExams();
	}

	@RequestMapping("/questionsmap")
	public @ResponseBody Map<Question, Boolean> getQuestionsMap() {
		return svc.getQuestionsWithAnswerMarks();
	}

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

	@RequestMapping("/answers")
	public @ResponseBody List<AnswerDTO> getAnswers(
			@RequestParam("id") int questionId) {
		return svc.getAnswersFor(questionId);
	}

	@RequestMapping("/startexam")
	public List<Question> startExam(@RequestParam("id") int id) {
		return svc.startExam(id);
	}

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
