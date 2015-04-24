/**
 * 
 */
package org.pm.crossover.task.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.pm.crossover.task.model.Answer;

/**
 * Question choice answer DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4166125454826805927L;
	private int id;
	private String text;
	private int order;
	private QuestionDTO question;

	public AnswerDTO(Answer a) {
		id = a.getId();
		question = new QuestionDTO(a.getQuestion());
		text = a.getText();
		order = a.getAnswerOrder(); 
	}
}
