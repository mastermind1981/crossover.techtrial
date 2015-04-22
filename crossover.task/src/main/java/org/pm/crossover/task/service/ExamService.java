package org.pm.crossover.task.service;

import javax.annotation.PostConstruct;

import org.pm.crossover.task.dao.ExamDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ExamService {
	@Autowired
	ExamDAO dao;
	@Autowired
	ExamInfo info;

	@PostConstruct
	public void init() {
		System.out.println("Init exam service");
	}
}
