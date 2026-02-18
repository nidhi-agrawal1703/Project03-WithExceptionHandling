package in.co.rays.proj3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class FacultyModelHibImpl implements FacultyModelInt {

	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		long pk=0;
		
		//College Model
		CollegeModelInt collegemodel=ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegedto=collegemodel.findByPK(dto.getCollegeId());
		String collegeName=collegedto.getName();
		dto.setCollegeName(collegeName);
		System.out.println("College Name in add of FacultyModelHibImpl:"+collegeName);
		
		//Course Model
		CourseModelInt courseModel=ModelFactory.getInstance().getCourseModel();
		CourseDTO coursedto=courseModel.findByPK(dto.getCourseId());
		String courseName=coursedto.getCourseName();
		dto.setCourseName(courseName);
		System.out.println("Course name in add of FacultyModelHibImpl:"+courseName);
		
		//Subject Model
		SubjectModelInt subjectModel=ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectdto=subjectModel.findByPK(dto.getSubjectId());
		String subjectName=subjectdto.getName();
		dto.setSubjectName(subjectName);
		System.out.println("Subject name in add of FacultyModelHibImpl:"+subjectName);
		
		FacultyDTO duplicateDTO=findByEmailId(dto.getEmail());
		if(duplicateDTO!=null) {
			throw new DuplicateRecordException("Faculty already exists");
		}
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.save(dto);
			pk=dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in faculty Add in FacultyModelHibImpl" + e.getMessage());
		}finally {
			session.close();
		}
		
		return pk;
	}

	public void delete(FacultyDTO dto) throws ApplicationException {
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
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in faculty delete " + e.getMessage());
		}finally {
			session.close();
		}
	}

	public void update(FacultyDTO dto) throws ApplicationException, DatabaseException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		
		FacultyDTO duplicateDTO=findByEmailId(dto.getEmail());
		if(duplicateDTO!=null && duplicateDTO.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Faculty already exists");
		}

				//College Model
				CollegeModelInt collegemodel=ModelFactory.getInstance().getCollegeModel();
				CollegeDTO collegedto=collegemodel.findByPK(dto.getCollegeId());
				String collegeName=collegedto.getName();
				System.out.println("College Name in add of FacultyModelHibImpl:"+collegeName);
				
				//Course Model
				CourseModelInt courseModel=ModelFactory.getInstance().getCourseModel();
				CourseDTO coursedto=courseModel.findByPK(dto.getCourseId());
				String courseName=coursedto.getCourseName();	
				System.out.println("Course name in add of FacultyModelHibImpl:"+courseName);
				
				//Subject Model
				SubjectModelInt subjectModel=ModelFactory.getInstance().getSubjectModel();
				SubjectDTO subjectdto=subjectModel.findByPK(dto.getSubjectId());
				String subjectName=subjectdto.getName();
				System.out.println("Subject name in add of FacultyModelHibImpl:"+subjectName);
				
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in faculty update " + e.getMessage());
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
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in  faculty list"+e.getMessage());
		}finally {
			session.close();
		}
		
		return list;
	}

	public List search(FacultyDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		
		return search(dto,0,0);
	}

	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(FacultyDTO.class);
          if(dto!=null){
            if (dto.getId()!=null) {
                criteria.add(Restrictions.eq("id", dto.getId()));
            }
            if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
                criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
            }
            if (dto.getEmail() != null && dto.getEmail().length() > 0) {
                criteria.add(Restrictions.like("email", dto.getEmail()+ "%"));
            }
            if (dto.getLastName() != null && dto.getLastName().length() > 0) {
                criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
            }
            if (dto.getCollegeId() > 0) {
                criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
            }
            if (dto.getCourseId() > 0) {
                criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
            }
            if (dto.getSubjectId() > 0) {
                criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
            }}

            // if page size is greater than zero the apply pagination
            if (pageSize > 0) {
                criteria.setFirstResult(((pageNo - 1) * pageSize));
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search() in FacultyModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	public FacultyDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		FacultyDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(FacultyDTO)session.get(FacultyDTO.class,pk);
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in findByPK() in FacultyModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	public FacultyDTO findByEmailId(String emailId) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		FacultyDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(FacultyDTO.class);
			criteria.add(Restrictions.eq("email", emailId));
			List list=criteria.list();
			if(list.size()>0) {
				dto=(FacultyDTO)list.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in findByEmail() in FacultyModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

}
