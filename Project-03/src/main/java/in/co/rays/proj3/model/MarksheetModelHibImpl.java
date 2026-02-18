package in.co.rays.proj3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.MarksheetDTO;
import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class MarksheetModelHibImpl implements MarksheetModelInt{

	public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=HibDataSource.getSession();
		Transaction tx=null;
		
		//get Student name
		StudentModelInt sModel=ModelFactory.getInstance().getStudentModel();
		StudentDTO studentdto=sModel.findByPK(dto.getStudentId());
		System.out.println("Student id in marksheet"+dto.getStudentId());
		//System.out.println("Student name in marksheet:"+studentdto.getFirstName()+" "+studentdto.getLastName());
		dto.setName(studentdto.getFirstName()+" "+studentdto.getLastName());
		
		
		MarksheetDTO duplicateMarksheet=findByRollNo(dto.getRollNo());
		
		if(duplicateMarksheet!=null) {
			throw new DuplicateRecordException("Roll Number already exists");
		}
		
		long pk=0;
		try {
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
			throw new ApplicationException("Exception in add() of MarksheetModelHibImplementation"+e.getMessage());
		}finally {
			session.close();
		}
		return pk;
	}

	public void delete(MarksheetDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in delete() of MaksheetModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
	}

	public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		
		MarksheetDTO dtoExist=findByRollNo(dto.getRollNo());
		//Check if roll no already exists
		if(dtoExist!=null && dtoExist.getId()==dto.getId()) {
			throw new DuplicateRecordException("Roll Number in maksheet exists");
		}
		
		//Get Student NAme
		StudentModelInt sModel=ModelFactory.getInstance().getStudentModel();
		StudentDTO studentdto=sModel.findByPK(dto.getStudentId());
		dto.setName(studentdto.getFirstName()+" "+studentdto.getLastName());
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
				throw new ApplicationException("Exception in update() of MarksheetModelHibImpl"+e.getMessage());
			}
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
			Criteria criteria=session.createCriteria(MarksheetDTO.class);
			if(pageSize>0) {
				pageNo=((pageNo-1)*pageSize)+1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in list in MaksheetModelHibImpl "+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	public List search(MarksheetDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	public List search(MarksheetDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(MarksheetDTO.class);
			if(dto!=null) {
				if(dto.getId()!=null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if(dto.getRollNo()!=null && dto.getRollNo().length()>0) {
					criteria.add(Restrictions.like("rollNo", dto.getRollNo()+"%"));
				}
				if(dto.getName()!=null && dto.getName().length()>0) {
					criteria.add(Restrictions.like("name", dto.getName()+"%"));
				}
				if(dto.getPhysics()!=null && dto.getPhysics()>0) {
					criteria.add(Restrictions.eq("physics", dto.getPhysics()));
				}
				if(dto.getChemistry()!=null && dto.getChemistry()>0) {
					criteria.add(Restrictions.eq("chemistry", dto.getChemistry()));
				}
				if(dto.getMaths()!=null && dto.getMaths()>0) {
					criteria.add(Restrictions.eq("maths", dto.getMaths()));
				}
				
			}
			if(pageSize>0) {
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in list() in MarksheetModelHibImpl "+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	public MarksheetDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		MarksheetDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(MarksheetDTO)session.get(MarksheetDTO.class, pk);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in findByPk() of MarksheetModelHibImpl "+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		MarksheetDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(MarksheetDTO.class);
			criteria.add(Restrictions.eq("rollNo", rollNo));
			List list=criteria.list();
			if(list.size()>0) {
				dto=(MarksheetDTO)list.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in findByRollNo of MArksheetModelHibImpl :"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			StringBuffer hql=new StringBuffer("from MarksheetDTO where physics>=33 and chemistry>=33 and maths>=33 order by (physics+chemistry+maths) desc");
			Query query=session.createQuery(hql.toString()).setMaxResults(pageSize);
			list=query.list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in getMeritList of MArksheetModelHibImpl "+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

}
