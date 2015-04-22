package org.pm.crossover.task.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Exam entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exam")
public class Exam {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String description;
	private Integer passScore;
	private Integer totalScore;
	/**
	 * The duration of test in minutes
	 */
	private Long duration;
	@JsonIgnore
	@OneToMany(mappedBy = "exam")
	Set<Question> questions;

}
