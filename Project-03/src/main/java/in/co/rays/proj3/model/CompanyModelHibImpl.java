package in.co.rays.proj3.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CompanyDTO;
import in.co.rays.proj3.dto.CustomerDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class CompanyModelHibImpl implements CompanyModelInt {

	@Override
	public long add(CompanyDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		CompanyDTO existDto=null;
		existDto=findByName(dto.getName());
		if(existDto!=null) {
			throw new DuplicateRecordException("Duplicate Company Code found");
			
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
			throw new ApplicationException("Exception in add Company"+e.getMessage());
		}finally {
			session.close();
		}
		return dto.getId();
	}

	@Override
	public void delete(CompanyDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Deleting Company" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(CompanyDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		CompanyDTO existDto=findByName(dto.getName());
		
		//Check if login id already exists
		if(existDto!=null && existDto.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Company name already exists");
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
			throw new ApplicationException("Exception in updating Company"+e.getMessage());
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
			Criteria criteria =session.createCriteria(CompanyDTO.class);
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting Company list"+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	@Override
	public List search(CompanyDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	@Override
	public List search(CompanyDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		ArrayList<CompanyDTO> list=null;
		
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(CompanyDTO.class);
			if(dto!=null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				if (dto.getEmployeeCount() != null && dto.getEmployeeCount() > 0) {
					criteria.add(Restrictions.eq("employeeCount", dto.getEmployeeCount()));
				}
				if (dto.getOpeningDate() != null && dto.getOpeningDate().getYear() > 0) {
					criteria.add(Restrictions.eq("openingDate", dto.getOpeningDate() ));
				}
				if (dto.getIndustryType() != null && dto.getIndustryType().length() > 0) {
					criteria.add(Restrictions.like("industryType", dto.getIndustryType() + "%"));
				}
							
				if (pageSize > 0) {
					pageNo = (pageNo - 1) * pageSize;
					criteria.setFirstResult(pageNo);
					criteria.setMaxResults(pageSize);
				}
				list = (ArrayList<CompanyDTO>) criteria.list();
				//System.out.println("UserList: "+list);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in Company search"+e.getMessage());
		}finally {
			session.close();
		}
		return list;

	}

	@Override
	public CompanyDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		CompanyDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(CompanyDTO)session.get(CompanyDTO.class, pk);
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting Company by pk"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	

	@Override
	public CompanyDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		CompanyDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria =session.createCriteria(CompanyDTO.class);
			
			criteria.add(Restrictions.eq("name", name));
			List list=criteria.list();
			if(list.size()==1) {
				dto=(CompanyDTO)list.get(0);
				System.out.println("Company name in CompanyModelHibImpl findByName "+dto.getName());
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Company by name"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;

	}

}
