package in.co.rays.proj3.model;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CollegeDTO;
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

	public Long add(T dto) throws DatabaseException,ApplicationException, DuplicateRecordException,DatabaseException{
		
		Session session=null;
		Transaction tx=null;
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
			handleException(e);
			//handleException()
		}finally {
			HibDataSource.closeSession(session);
		}
		return dto.getId();
	}
	
	public void delete(T dto) throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		
		Session session=null;
		Transaction tx=null;
		
		try {
			
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
			handleException(e);
		}finally {
			HibDataSource.closeSession(session);
		}
		
	}
	
	public void update(T dto) throws ApplicationException, DuplicateRecordException,DatabaseException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		
		try {
			
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
			handleException(e);
		}finally {
			HibDataSource.closeSession(session);
		}
		
	}
	
	public List list() throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		return list(0,0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(persistentClass);
			if(pageSize>0) {
				pageNo=((pageNo-1)*pageSize)+1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (Exception e) {
			// TODO: handle exception
			handleException(e);
		}finally {
			HibDataSource.closeSession(session);
		}
		return list;
	}
	
	public T findByPK(long pk) throws ApplicationException,DatabaseException,PrimaryKeyNotFoundException {
		// TODO Auto-generated method stub
		Session session=null;
		T dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(T)session.get(persistentClass, pk);
			if(dto==null) {
				throw new PrimaryKeyNotFoundException("REcord Not Found at primary key");
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			handleException(e);
			
		}finally {
			HibDataSource.closeSession(session);
		}
		return dto;
	}

	
	public static void handleException(Exception e){
		
			if(e instanceof HibernateException) {
				throw new DatabaseException("Exception in database connection"+e.getMessage(),e);
			}
			if(e instanceof HibernateException) {
				throw new DatabaseException("Exception in database connection"+e.getMessage(),e);
			}
			
	}
	
	
}
