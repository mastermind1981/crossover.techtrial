package org.pm.crossover.task.model;

import java.io.Serializable;
import java.util.Date;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "examresult")
public class ExamResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4220013591215362813L;
	@JsonIgnore
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examuser")
	private ExamUser examuser;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam")
	private Exam exam;
	private boolean examTimedOut;
	private boolean examFullyAnswered;
	private int questionsAnswered;
	private Date startTime;
	private Date finishTime;
	private int score;
	
}
