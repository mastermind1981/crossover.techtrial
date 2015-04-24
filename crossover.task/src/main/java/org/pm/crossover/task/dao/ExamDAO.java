package org.pm.crossover.task.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * Simple universal DAO interface
 *
 */
public interface ExamDAO {

	/**
	 * Persists given entity in database
	 * 
	 * @param entity
	 */
	public abstract void insert(Object entity);

	/**
	 * 
	 * @param entityClass
	 *            the class of entity
	 * @param restrictions
	 *            the restrictions map which should be allied as "equals"
	 *            operation (property-value)
	 * @return the list of entities of given class filtered by given
	 *         restrictions
	 */
	public abstract <T> List<T> list(Class<T> entityClass,
			Map<String, ?> restrictions);

	/**
	 * 
	 * @param entityClass
	 *            the class of entity
	 * @return the list of entities of given class
	 */
	public abstract <T> List<T> list(Class<T> entityClass);

	/**
	 * 
	 * @param entityClass
	 *            the class of entity
	 * @param restrictions
	 *            the restrictions map which should be allied as "equals"
	 *            operation (property-value)
	 * @param orderBy
	 *            name of property for the list to be ordered by
	 * @return the list of entities of given class filtered by given
	 *         restrictions and ordered by given field
	 */
	public abstract <T> List<T> list(Class<T> entityClass,
			Map<String, ?> restrictions, String orderBy);

	/**
	 * 
	 * @param entityClass
	 *            the class of entity
	 * @param id
	 *            the id of entity
	 * @return the entity by given id or <code>null</code>
	 */
	public abstract <T> T findById(Class<T> entityClass, int id);

}