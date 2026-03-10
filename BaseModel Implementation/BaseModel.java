package in.co.rays.proj3.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.BannerDTO;
import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.PrimaryKeyNotFoundException;
import in.co.rays.proj3.util.HibDataSource;

public abstract class BaseModel<T extends BaseDTO> {

	private Class<T> persistentClass;

	public BaseModel(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public Long add(T dto) throws DatabaseException, ApplicationException, DuplicateRecordException, DatabaseException {

		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (Exception e) {
			
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			handleException(e);
			// handleException()
		} finally {
			HibDataSource.closeSession(session);
		}
		return dto.getId();
	}

	public void delete(T dto) throws ApplicationException, DatabaseException {
	
		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			handleException(e);
		} finally {
			HibDataSource.closeSession(session);
		}

	}

	public void update(T dto) throws ApplicationException, DuplicateRecordException, DatabaseException {
		
		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (Exception e) {
			
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			handleException(e);
		} finally {
			HibDataSource.closeSession(session);
		}

	}

	public List list() throws ApplicationException, DatabaseException {
		
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException, DatabaseException {
		
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(persistentClass);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (Exception e) {
			
			handleException(e);
		} finally {
			HibDataSource.closeSession(session);
		}
		return list;
	}

	public T findByPK(long pk) throws ApplicationException, DatabaseException, PrimaryKeyNotFoundException {
		
		Session session = null;
		T dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (T) session.get(persistentClass, pk);
			if (dto == null) {
				throw new PrimaryKeyNotFoundException("REcord Not Found at primary key");
			}
		} catch (HibernateException e) {
		
			handleException(e);

		} finally {
			HibDataSource.closeSession(session);
		}
		return dto;
	}

	public void whereCondition(T dto, Criteria criteria) {
	}

	public List<T> search(T dto, int pageNo, int pageSize) {

		Session session = null;
		ArrayList<T> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(persistentClass);

			whereCondition(dto, criteria);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<T>) criteria.list();
			// System.out.println("UserList: "+list);

		} catch (HibernateException e) {
			
			BaseModel.handleException(e);
		} finally {
			HibDataSource.closeSession(session);
		}
		return list;

	}

	
	public T findByUniqueKey(String property, String value) throws ApplicationException, DatabaseException {
		
		Session session = null;
		T dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.add(Restrictions.eq(property, value));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (T) list.get(0);
			}
		} catch (HibernateException e) {
			
			e.printStackTrace();
			throw new DatabaseException("Database SErver Down" + e.getMessage(), e);
		} finally {
			HibDataSource.closeSession(session);
		}
		return dto;

	}

	public static void handleException(Exception e) {

		if (e instanceof HibernateException) {
			throw new DatabaseException("Exception in database connection" + e.getMessage(), e);
		}
		

	}

}
