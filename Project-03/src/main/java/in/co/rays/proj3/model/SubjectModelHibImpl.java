package in.co.rays.proj3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class SubjectModelHibImpl implements SubjectModelInt {

	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		
		CourseModelInt cModel=ModelFactory.getInstance().getCourseModel();
		CourseDTO coursedto=cModel.findByPK(dto.getCourseId());
		dto.setCourseName(coursedto.getCourseName());
		
		SubjectDTO duplicatedto=findByName(dto.getName());
		if(duplicatedto!=null) {
			throw new DuplicateRecordException("Subject already exists");
		}
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			e.printStackTrace();
			throw new ApplicationException("Exception in subject Add " + e.getMessage());
		}finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(SubjectDTO dto) throws ApplicationException {
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
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in subject Delete" + e.getMessage());
		}finally {
			session.close();
		}
	}

	public void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		
		SubjectDTO duplicatedto=findByName(dto.getName());
		if(duplicatedto!=null && dto.getId()!=duplicatedto.getId())  {
			throw new DuplicateRecordException("Subject already exists");
		}
		
		CourseModelInt cModel=ModelFactory.getInstance().getCourseModel();
		CourseDTO coursedto=cModel.findByPK(dto.getCourseId());
		dto.setCourseName(coursedto.getCourseName());
				
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in subject update" + e.getMessage());
		}finally {
			session.close();
		}
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0,0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(SubjectDTO.class);
			if(pageSize>0) {
				pageNo=((pageNo-1)*pageSize)+1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in list of SubjectModelHibImpl "+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	public List search(SubjectDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	public List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(SubjectDTO.class);
			
			if(dto!=null) {
				if(dto.getId()>0){
					criteria.add(Restrictions.eq("id", dto.getId()));					
				}
				if(dto.getCourseId()>0){
					criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
					//System.out.println("courseId in Search of SubjectModelHibImpl"+dto.getCourseId());
				}
				if(dto.getCourseName()!=null&& dto.getCourseName().length()>0){
					criteria.add(Restrictions.like("courseName", dto.getCourseName()+"%"));
				}
				if(dto.getName()!=null&& dto.getName().length()>0){
					criteria.add(Restrictions.like("subjectName", dto.getName()+"%"));
				}
			}

			if(pageSize>0) {
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search in SubjectModelInt"+e.getMessage());
		}finally {
			session.close();
		}
		//System.out.println("List in search of SubjectModelInt"+list);
		return list;
	}

	public SubjectDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		SubjectDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(SubjectDTO)session.get(SubjectDTO.class, pk);
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in findByPK in SubjectModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	public SubjectDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		SubjectDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(SubjectDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list=criteria.list();
			if(list.size()==1) {
				dto=(SubjectDTO)list.get(0);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in findByName() in SubjectModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

}
