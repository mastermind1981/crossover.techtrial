package org.pm.crossover.task.controller;

import org.pm.crossover.task.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController
@RequestMapping("/rest")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AppRestController {

	@Autowired
	ExamService svc;

	@RequestMapping("/test")
	public void ok() {
		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		svc.setUser(a);
	}

}
