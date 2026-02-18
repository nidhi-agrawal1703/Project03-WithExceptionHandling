package in.co.rays.proj3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.ProductDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class ProductModelHibImpl implements ProductModelInt {

	@Override
	public long add(ProductDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		
		ProductDTO duplicatedto=findByName(dto.getProductName());
		if(duplicatedto!=null) {
			throw new DuplicateRecordException("Product already exists");
		}
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Add of ProductModelHibImpl" + e.getMessage());
		}finally {
			session.close();
		}
		return dto.getId();
	}

	@Override
	public void delete(ProductDTO dto) throws ApplicationException {
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
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in delete of ProductModelHibImpl" + e.getMessage());
		}finally {
			session.close();
		}
	}

	@Override
	public void update(ProductDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
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
			throw new ApplicationException("Exception in update of ProductModelHibImpl" + e.getMessage());
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
			Criteria criteria=session.createCriteria(ProductDTO.class);
			if(pageSize>0) {
				pageNo=((pageNo-1)*pageSize)+1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
				
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in list in ProductModelHibImpl"+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	@Override
	public List search(ProductDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	@Override
	public List search(ProductDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(ProductDTO.class);
			if(dto!=null) {
				if(dto.getId()!=null && dto.getId()>0) {
					criteria.add(Restrictions.eq("id",dto.getId()));
				}
				if(dto.getProductName()!=null && dto.getProductName().length()>0) {
					criteria.add(Restrictions.like("productName", dto.getProductName()+"%"));
				}
				if(dto.getProductCategory()!=null && dto.getProductCategory().length()>0) {
					criteria.add(Restrictions.like("productCategory", dto.getProductCategory()+"%"));
				}
				if(dto.getProductAmount()!=null && dto.getProductAmount().length()>0) {
					criteria.add(Restrictions.like("productAmount", dto.getProductAmount()+"%"));
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
			throw new ApplicationException("Exception in search in ProductModelHibImpl"+e.getMessage());

		}finally {
			session.close();
		}
		return list;
	}

	@Override
	public ProductDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		ProductDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto = (ProductDTO) session.get(ProductDTO.class, pk);
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in findByPk in ProductModelInt"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public ProductDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		ProductDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(ProductDTO.class);
			criteria.add(Restrictions.eq("productName", name));
			List list=criteria.list();
			if(list.size()==1) {
				dto=(ProductDTO)list.get(0);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in getting User by Login " + e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

}
