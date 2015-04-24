package org.pm.crossover.task.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pm.crossover.task.model.Answer;
import org.pm.crossover.task.model.Exam;
import org.pm.crossover.task.model.Question;

public class DTOUtil {
	public static List<ExamDTO> getExamList(Collection<Exam> list) {
		List<ExamDTO> r = new ArrayList<ExamDTO>();
		for (Exam e : list) {
			r.add(new ExamDTO(e));
		}
		return r;
	}

	public static List<QuestionDTO> getQuestionList(Collection<Question> list) {
		List<QuestionDTO> r = new ArrayList<QuestionDTO>();
		for (Question q : list) {
			r.add(new QuestionDTO(q));
		}
		return r;
	}

	public static List<AnswerDTO> getAnswerList(Collection<Answer> list) {
		List<AnswerDTO> r = new ArrayList<AnswerDTO>();
		for (Answer a : list) {
			r.add(new AnswerDTO(a));
		}
		return r;
	}
}
