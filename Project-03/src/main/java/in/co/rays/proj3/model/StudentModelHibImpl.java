package in.co.rays.proj3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class StudentModelHibImpl implements StudentModelInt{

	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=HibDataSource.getSession();
		Transaction tx=null;
		
		CollegeModelInt collegeModel=ModelFactory.getInstance().getCollegeModel();
		StudentModelInt studentModel=ModelFactory.getInstance().getStudentModel();
		long collegeId=dto.getCollegeId();
		System.out.println("College Id"+collegeId);
		CollegeDTO collegeDto=collegeModel.findByPK(collegeId);
		//System.out.println("College Id:"+collegeDto.getId());
		dto.setCollegeId(collegeId);
		dto.setCollegeName(collegeDto.getName());
		
		System.out.println("nme ===>>>>>>>>>>>>"+ dto.getCollegeName());
		long pk=0;
		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in add() of StudentModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return pk;
		
	}

	public void delete(StudentDTO dto) throws ApplicationException {
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
			if(tx!=null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Delete of StudentModelHibImpl" + e.getMessage());
		}finally {
			session.close();
		}	
		
	}

	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session =null;
		Transaction tx=null;
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.update(dto);
			tx.commit();
			
		} catch (HibernateException e) {
			// TODO: handle exception
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
			Criteria criteria=session.createCriteria(StudentDTO.class);
			if(pageSize>0) {
				pageNo=((pageNo-1)*pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception occured in list() of StudentModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	public List search(StudentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	public List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(StudentDTO.class);
			if(dto!=null) {
				if(dto.getId()!=null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
					}				
				if(dto.getFirstName()!=null && dto.getFirstName().length()>0) {
					criteria.add(Restrictions.like("firstName", dto.getFirstName()+"%"));
				}
				if(dto.getLastName()!=null && dto.getLastName().length()>0) {
					criteria.add(Restrictions.like("lastName", dto.getLastName()+"%"));
				}
				if(dto.getEmail()!=null && dto.getEmail().length()>0) {
					criteria.add(Restrictions.like("email", dto.getEmail()+"%"));
				}
				if(dto.getDob()!=null && dto.getDob().getDate()>0) {
					criteria.add(Restrictions.eq("dob", dto.getDob()));
				}
				if(dto.getCollegeId()>0) {
					criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
				}
				if(dto.getMobileNo()!=null && dto.getMobileNo().length()>0) {
					criteria.add(Restrictions.eq("mobileNo", dto.getMobileNo()));
					}
				}
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in search of StudentModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	public StudentDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=HibDataSource.getSession();
		StudentDTO dto=null;
		try {
			dto=(StudentDTO)session.get(StudentDTO.class, pk);
			
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in findByPK() of StudentModelHibImpl");
		}finally {
			session.close();
		}
		return dto;
	}

	public StudentDTO findByEmail(String emailId) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=HibDataSource.getSession();
		StudentDTO dto=null;
		try {
			Criteria criteria=session.createCriteria(StudentDTO.class);
			criteria.add(Restrictions.eq("email", emailId));
			List list=criteria.list();
			if(list.size()==1) {
				dto=(StudentDTO)list.get(0);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in findByEmail() of StudentModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

}
