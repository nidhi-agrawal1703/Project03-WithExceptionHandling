package in.co.rays.proj3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class CourseModelHibImpl implements CourseModelInt {

	public long add(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		long pk=0;
		CourseDTO existDto=findByName(dto.getCourseName());
		if(existDto!=null) {
			throw new DuplicateRecordException("Course already exists");
		}
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.save(dto);
			pk=dto.getId();
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in course Add " + e.getMessage());
		}finally {
			session.close();
		}
		return pk;
	}

	public void delete(CourseDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in delete of CourseModelHibImpl");
		}finally {
			session.close();
		}
		
	}

	public void update(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
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
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in update of CourseModelHimImpl " + e.getMessage());
		}finally {
			session.close();
		}
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(CourseDTO.class);
			if(pageSize>0) {
				pageNo=((pageNo-1)*pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in list in CourseModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	public List search(CourseDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	public List search(CourseDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(CourseDTO.class);
			
			if(dto.getId()>0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getCourseName()!=null && dto.getCourseName().length()>0) {
				criteria.add(Restrictions.like("courseName", dto.getCourseName()+"%"));
			}
			if(dto.getDescription()!=null && dto.getDescription().length()>0) {
				criteria.add(Restrictions.like("description", dto.getDescription()+"%"));
			}
			if(dto.getDuration()!=null && dto.getDuration().length()>0) {
				criteria.add(Restrictions.like("duration", dto.getDuration()+"%"));
			}
			
			if(pageSize>0) {
				criteria.setFirstResult(((pageNo-1)*pageSize));
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
			//System.out.println("List in search of CourseModelHibImpl: "+list);
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in search() of CourseModelHibSearch"+e.getMessage());
		}finally {
			session.close();
		}
		
		return list;
	}

	public CourseDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		CourseDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(CourseDTO)session.get(CourseDTO.class, pk);
			
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in findByPk() by CourseModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	public CourseDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		CourseDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(CourseDTO.class);
			criteria.add(Restrictions.eq("courseName", name));
			List list=criteria.list();
			if(list.size()>0) {
				dto=(CourseDTO)list.get(0);
			}
			
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in findByName in CourseModelHibImpl "+e.getMessage());
		}finally {
			session.close();
		}
		
		return dto;
	}

}
