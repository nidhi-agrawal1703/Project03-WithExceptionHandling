package in.co.rays.proj3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CustomerDTO;
import in.co.rays.proj3.dto.StaffDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class StaffModelHibImpl implements StaffModelInt {

	@Override
	public long add(StaffDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		StaffDTO existDto=null;
		existDto=findByName(dto.getName());
		if(existDto!=null) {
			throw new DuplicateRecordException("Duplicate Record found");
			
		}
		Session session=HibDataSource.getSession();
		Transaction tx=null;
		try {
			int pk=0;
			tx=session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in add staff"+e.getMessage());
		}finally {
			session.close();
		}
		return dto.getId();
	}

	@Override
	public void delete(StaffDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Deleting Staff" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(StaffDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		StaffDTO existDto=findByName(dto.getName());
		
		//Check if login id already exists
		if(existDto!=null && existDto.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Staff name already exists");
		}
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			if(tx!=null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in updating staff"+e.getMessage());
		}finally {
			session.close();
		}
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0,0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria =session.createCriteria(StaffDTO.class);
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting staff list"+e.getMessage());
		}finally {
			session.close();
		}
		
		return list;
		
	}

	@Override
	public List search(StaffDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	@Override
	public List search(StaffDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		ArrayList<StaffDTO> list=null;
		
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(StaffDTO.class);
			if(dto!=null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				if (dto.getDivision() != null && dto.getDivision().length() > 0) {
					criteria.add(Restrictions.like("division", dto.getDivision() + "%"));
				}
				if (dto.getPreviousEmployer() != null && dto.getPreviousEmployer().length() > 0) {
					criteria.add(Restrictions.like("previousEmployer", dto.getPreviousEmployer() + "%"));
				}
							
				if (pageSize > 0) {
					pageNo = (pageNo - 1) * pageSize;
					criteria.setFirstResult(pageNo);
					criteria.setMaxResults(pageSize);
				}
				list = (ArrayList<StaffDTO>) criteria.list();
				//System.out.println("UserList: "+list);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in staff search"+e.getMessage());
		}finally {
			session.close();
		}
		return list;

	}

	@Override
	public StaffDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		StaffDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(StaffDTO)session.get(StaffDTO.class, pk);
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting staff by pk"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public StaffDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		StaffDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria =session.createCriteria(StaffDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list=criteria.list();
			if(list.size()==1) {
				dto=(StaffDTO)list.get(0);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in getting staff by name"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

}
