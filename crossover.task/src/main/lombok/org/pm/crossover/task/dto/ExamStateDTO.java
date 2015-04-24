package org.pm.crossover.task.dto;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.pm.crossover.task.model.Answer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamStateDTO {
	private String userName;
	private ExamDTO exam;
	private boolean examStarted;
	private boolean examActive;
	private boolean examTimedOut;
	private boolean examFullyAnswered;
	private int questionsCount;
	private int questionsAnswered;
	private long timeLeft;
	private Date startTime;
	private Date finishTime;
	private Map<Integer, Set<Answer>> userAnswers;
	private int score;
}
