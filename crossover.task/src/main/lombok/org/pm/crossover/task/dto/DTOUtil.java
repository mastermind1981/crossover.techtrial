package org.pm.crossover.task.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pm.crossover.task.model.Answer;
import org.pm.crossover.task.model.Question;

/**
 * Util class containig methods to convert collection of entities to the list of
 * transferable objects
 */
public class DTOUtil {

	/**
	 * 
	 * @param list
	 *            collection of questions
	 * @return list of transferable question objects
	 */
	public static List<QuestionDTO> getQuestionList(Collection<Question> list) {
		List<QuestionDTO> r = new ArrayList<QuestionDTO>();
		for (Question q : list) {
			r.add(new QuestionDTO(q));
		}
		return r;
	}

	/**
	 * 
	 * @param list
	 *            collection of answers
	 * @return list of transferable answer objects
	 */
	public static List<AnswerDTO> getAnswerList(Collection<Answer> list) {
		List<AnswerDTO> r = new ArrayList<AnswerDTO>();
		for (Answer a : list) {
			r.add(new AnswerDTO(a));
		}
		return r;
	}
}
