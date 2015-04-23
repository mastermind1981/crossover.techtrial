package org.pm.crossover.task.controller;

import java.util.List;

import org.pm.crossover.task.dto.ExamState;
import org.pm.crossover.task.model.Answer;
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
	public @ResponseBody ExamState getState() {
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

	@RequestMapping("/question")
	public @ResponseBody Question getCurrentQuestion() {
		return svc.getCurrentQuestion();
	}

	@RequestMapping("/answers")
	public @ResponseBody List<Answer> getAnswers() {
		return svc.getCurrentAnswers();
	}
	
	@RequestMapping("/startexam")
	public Question startExam(@RequestParam("id") int id) {
		return svc.startExam(id);
	}
	
	@RequestMapping(value="/postanswer", method=RequestMethod.POST)
	public Question putAnswers(@RequestBody int[] answers) {
		return svc.addAnswers(answers);
	}

}
