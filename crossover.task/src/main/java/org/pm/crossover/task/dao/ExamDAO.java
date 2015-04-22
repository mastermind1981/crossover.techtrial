package org.pm.crossover.task.dao;

import java.util.List;
import java.util.Map;

public interface ExamDAO {

	public abstract void insert(Object entity);

	public abstract <T> List<T> list(Class<T> entityClass,
			Map<String, ?> restrictions);

	public abstract <T> List<T> list(Class<T> entityClass);

}