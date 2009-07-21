package org.mix3.gae_wicket.logic;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.mix3.gae_wicket.PMF;

public class AbstractLogic<T> {
	PersistenceManager pm;

	Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public AbstractLogic(T... args) {
		try {
			entityClass = (Class<T>) args.getClass().getComponentType();
			pm = PMF.get().getPersistenceManager();
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	public Query newQuery() {
		return pm.newQuery(entityClass);
	}

	public List<T> list(Query query) {
		try {
			@SuppressWarnings("unchecked")
			List<T> list = (List<T>) query.execute();
			return new ArrayList<T>(list);
		} finally {
			query.closeAll();
		}
	}
	
	public List<T> listWithParam(Query query, String param) {
		try {
			@SuppressWarnings("unchecked")
			List<T> list = (List<T>) query.execute(param);
			return new ArrayList<T>(list);
		} finally {
			query.closeAll();
		}
	}
	
	public T delete(T entity, Object key) {
		Transaction transaction = pm.currentTransaction();
		transaction.begin();
		try {
			T b = pm.getObjectById(entityClass, key);
			pm.deletePersistent(b);
			transaction.commit();
			return b;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	public T save(T entity) {
		Transaction transaction = pm.currentTransaction();
		transaction.begin();
		try {
			T b = pm.makePersistent(entity);
			transaction.commit();
			return b;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
}
