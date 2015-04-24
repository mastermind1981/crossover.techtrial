package org.pm.crossover.task.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.pm.crossover.task.model.Exam;

/**
 * Exam entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7308161853785123558L;
	private int id;
	private String name;
	private String description;
	private int passScore;
	private int totalScore;
	private long duration;
	private List<QuestionDTO> questions;

	public ExamDTO(Exam e) {
		id = e.getId();
		name = e.getName();
		description = e.getDescription();
		passScore = e.getPassScore();
		totalScore = e.getTotalScore();
		duration = e.getDuration();
	}

}
