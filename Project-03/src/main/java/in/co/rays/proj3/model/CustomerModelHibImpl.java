package in.co.rays.proj3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CustomerDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class CustomerModelHibImpl implements CustomerModelInt {

	@Override
	public long add(CustomerDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		CustomerDTO existDto=null;
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
			throw new ApplicationException("Exception in add customer"+e.getMessage());
		}finally {
			session.close();
		}
		return dto.getId();
	}

	@Override
	public void delete(CustomerDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Deleting Customer" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(CustomerDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		CustomerDTO existDto=findByName(dto.getName());
		
		//Check if login id already exists
		if(existDto!=null && existDto.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Customer name already exists");
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
			throw new ApplicationException("Exception in updating customer"+e.getMessage());
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
			Criteria criteria =session.createCriteria(CustomerDTO.class);
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting customer list"+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	@Override
	public List search(CustomerDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	@Override
	public List search(CustomerDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		ArrayList<CustomerDTO> list=null;
		
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(CustomerDTO.class);
			if(dto!=null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				if (dto.getLocation() != null && dto.getLocation().length() > 0) {
					criteria.add(Restrictions.like("location", dto.getLocation() + "%"));
				}
				if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
					criteria.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
				}
				if (dto.getLocation() != null && dto.getLocation().length() > 0) {
					criteria.add(Restrictions.like("location", dto.getLocation() + "%"));
				}
							
				if (pageSize > 0) {
					pageNo = (pageNo - 1) * pageSize;
					criteria.setFirstResult(pageNo);
					criteria.setMaxResults(pageSize);
				}
				list = (ArrayList<CustomerDTO>) criteria.list();
				//System.out.println("UserList: "+list);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in customer search"+e.getMessage());
		}finally {
			session.close();
		}
		return list;

	}

	@Override
	public CustomerDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		CustomerDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(CustomerDTO)session.get(CustomerDTO.class, pk);
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting customer by pk"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public CustomerDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		CustomerDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria =session.createCriteria(CustomerDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list=criteria.list();
			if(list.size()==1) {
				dto=(CustomerDTO)list.get(0);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in getting customer by name"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

}
