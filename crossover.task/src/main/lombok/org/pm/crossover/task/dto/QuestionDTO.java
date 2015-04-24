package org.pm.crossover.task.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.pm.crossover.task.model.Question;

/**
 * Question
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4866548551837033530L;
	private int id;
	private ExamDTO exam;
	private String title;
	private String description;
	private boolean multiAnswer;
	private int order;

	public QuestionDTO(Question q) {
		id = q.getId();
		exam = new ExamDTO(q.getExam());
		title = q.getTitle();
		description = q.getDescription();
		multiAnswer = q.getMultiAnswer();
		order = q.getQuestionOrder();
	}
}
