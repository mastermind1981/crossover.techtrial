/**
 * 
 */
package org.pm.crossover.task.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Question choice answer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answer")
public class Answer {

	@Id
	@GeneratedValue
	private Integer id;
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="question")
	private Question question;
	private String text;
	private Integer answerOrder;
//	@JsonIgnore
	private Boolean correct;
	
}
