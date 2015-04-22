package org.pm.crossover.task.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.pm.crossover.task.model.Answer;
import org.pm.crossover.task.model.Exam;
import org.pm.crossover.task.model.ExamUser;
import org.pm.crossover.task.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExamDAOImpl implements ExamDAO {

	@Autowired
	SessionFactory sessionFactory;

	/**
	 * Test data creation
	 */
//	@PostConstruct
	public void init() {
		insert(new ExamUser(null, "Test User", "test", "123", true));
		insert(new ExamUser(null, "User for test", "user", "321", true));
		Answer a;
		Question q;
		int ordQ; // order of question
		int ordA; // order of answer

		Exam e = new Exam();
		e.setName("My first exam");
		e.setDescription("The dummy test exam with only fake questions");
		e.setPassScore(3);
		e.setTotalScore(5);
		e.setDuration(10L);
		insert(e);
		ordQ = 0;

		q = new Question();
		q.setExam(e);
		q.setTitle("The title of the question");
		q.setDescription("What if the capital of Great Britain?");
		q.setMultiAnswer(false);
		q.setQuestionOrder(ordQ++);
		insert(q);

		ordA = 0;
		a = new Answer(null, q, "London", ordA++, true);
		insert(a);
		a = new Answer(null, q, "Paris", ordA++, false);
		insert(a);
		a = new Answer(null, q, "Moscow", ordA++, false);
		insert(a);
		a = new Answer(null, q, "Berlin", ordA++, false);
		insert(a);

		q = new Question();
		q.setExam(e);
		q.setTitle("Another title of the question");
		q.setDescription("Which cities are not in Europe?");
		q.setMultiAnswer(true);
		q.setQuestionOrder(ordQ++);
		insert(q);

		ordA = 0;
		a = new Answer(null, q, "Buenos Aires", ordA++, true);
		insert(a);
		a = new Answer(null, q, "Paris", ordA++, false);
		insert(a);
		a = new Answer(null, q, "Moscow", ordA++, true);
		insert(a);
		a = new Answer(null, q, "Capetown", ordA++, false);
		insert(a);

		q = new Question();
		q.setExam(e);
		q.setTitle("Question #3");
		q.setDescription("Which of the following is the name of island in Indonesia?");
		q.setMultiAnswer(false);
		q.setQuestionOrder(ordQ++);
		insert(q);

		ordA = 0;
		a = new Answer(null, q, "Groovy", ordA++, false);
		insert(a);
		a = new Answer(null, q, "Java", ordA++, true);
		insert(a);
		a = new Answer(null, q, "Madagascar", ordA++, false);
		insert(a);
		a = new Answer(null, q, "Scala", ordA++, false);
		insert(a);

		q = new Question();
		q.setExam(e);
		q.setTitle("The last question");
		q.setDescription("What religion has a school named Zen?");
		q.setMultiAnswer(false);
		q.setQuestionOrder(ordQ++);
		insert(q);

		ordA = 0;// order of answer to avoid messing with same order positions
		a = new Answer(null, q, "Taoism", ordA++, false);
		insert(a);
		a = new Answer(null, q, "Hinduism", ordA++, false);
		insert(a);
		a = new Answer(null, q, "Judaism", ordA++, false);
		insert(a);
		a = new Answer(null, q, "Buddhism", ordA++, true);
		insert(a);
	}

	/* (non-Javadoc)
	 * @see org.pm.crossover.task.dao.IExamDAO#insert(java.lang.Object)
	 */
	@Override
	public void insert(Object entity) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(entity);
		tx.commit();
		session.close();
	}

	/* (non-Javadoc)
	 * @see org.pm.crossover.task.dao.IExamDAO#list(java.lang.Class, java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> list(Class<T> entityClass, Map<String,?> restrictions) {
		Session session = this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(entityClass);
		if (restrictions != null) {
			criteria.add(Restrictions.allEq(restrictions));
		}
		List<T> personList = criteria.list();
		session.close();
		return personList;
	}

	/* (non-Javadoc)
	 * @see org.pm.crossover.task.dao.IExamDAO#list(java.lang.Class)
	 */
	@Override
	public <T> List<T> list(Class<T> entityClass) {
		return list(entityClass, null);
	}
	
	

}
