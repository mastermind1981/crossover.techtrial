/**
 * 
 */
package org.pm.crossover.task.service;

import java.util.Date;

import javax.annotation.PostConstruct;

import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ExamInfo {
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
	/**
	 * <code>false</code> if either the exam if <code>null</code>, or exam has
	 * been timed out, or it has unanswered questions
	 */
	private boolean examiningActive;

	@PostConstruct
	public void init() {
		System.out.println("Init exam info");
	}
}
