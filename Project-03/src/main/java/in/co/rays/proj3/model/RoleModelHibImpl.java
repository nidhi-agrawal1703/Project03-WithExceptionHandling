package in.co.rays.proj3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class RoleModelHibImpl implements RoleModelInt {

	public long add(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		long pk;
		
		RoleDTO existDto=findByName(dto.getName());
		if(existDto!=null) {
			throw new DuplicateRecordException("Role name already exists");
		}
		
		session=HibDataSource.getSession();
		try {
			tx=session.beginTransaction();
			session.save(dto);
			pk=dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in add() of RoleModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		
		return pk;
	}

	public void delete(RoleDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		long pk;
		
		session=HibDataSource.getSession();
		try {
			tx=session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in delete() of RoleModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		
	}

	public void update(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		long pk;
		
		RoleDTO existDto=findByName(dto.getName());
		if(existDto!=null && existDto.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Role name already exists");
		}
		
		session=HibDataSource.getSession();
		try {
			tx=session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in update() of RoleModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		
	}

	public List list() throws DatabaseException {
		// TODO Auto-generated method stub
		return list(0,0);
	}

	public List list(int pageNo, int pageSize) throws DatabaseException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(RoleDTO.class);
			if(pageSize>0) {
				pageNo=((pageNo-1)*pageSize)+1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
			 if (list == null) {
		            list = new ArrayList();
		        }

		} catch (HibernateException e) {
			// TODO: handle exception
			//throw new ApplicationException("Exception in list() of RoleMOdelHibImpl"+e.getMessage(),e);
			throw new DatabaseException("Exception in database connection"+e.getMessage(),e);
		}finally {
			session.close();
		}
		return list;
	}

	public List search(RoleDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	public List search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(RoleDTO.class);
			if(dto.getId()>0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getName()!=null && dto.getName().length()>0) {
				criteria.add(Restrictions.like("name", dto.getName()+"%"));
			}
			if(dto.getDescription()!=null && dto.getDescription().length()>0) {
				criteria.add(Restrictions.like("description", dto.getDescription()+"%"));
			}
			if(pageSize>0) {
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in search() of RoleModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	public RoleDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=HibDataSource.getSession();
		try {
			RoleDTO dto=(RoleDTO)session.get(RoleDTO.class, pk);
			return dto;
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in findByPk method"+e.getMessage());
		}finally {
			session.close();
		}
		
	}

	public RoleDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		RoleDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(RoleDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list=criteria.list();
			
			if(list.size()>0) {
				dto=(RoleDTO)list.get(0);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting role by login"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

}
