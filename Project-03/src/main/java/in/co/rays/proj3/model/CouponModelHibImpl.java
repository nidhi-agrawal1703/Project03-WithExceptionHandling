package in.co.rays.proj3.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CouponDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibDataSource;

public class CouponModelHibImpl implements CouponModelInt {

	@Override
	public long add(CouponDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		CouponDTO existDto=null;
		existDto=findByOfferCode(dto.getOfferCode());
		if(existDto!=null) {
			throw new DuplicateRecordException("Duplicate Coupon Code found");
			
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
			throw new ApplicationException("Exception in add Coupon"+e.getMessage());
		}finally {
			session.close();
		}
		return dto.getId();
	}

	@Override
	public void delete(CouponDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Deleting Coupon" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(CouponDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		CouponDTO existDto=findByOfferCode(dto.getOfferCode());
		
		//Check if login id already exists
		if(existDto!=null && existDto.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Coupon code already exists");
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
			throw new ApplicationException("Exception in updating Coupon"+e.getMessage());
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
			Criteria criteria =session.createCriteria(CouponDTO.class);
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting Coupon list"+e.getMessage());
		}finally {
			session.close();
		}
		return list;
	}

	@Override
	public List search(CouponDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	@Override
	public List search(CouponDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		ArrayList<CouponDTO> list=null;
		
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(CouponDTO.class);
			if(dto!=null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getOfferCode() != null && dto.getOfferCode().length() > 0) {
					criteria.add(Restrictions.like("offerCode", dto.getOfferCode() + "%"));
				}
				if (dto.getDiscountAmount() != null ) {
					criteria.add(Restrictions.eq("discountAmount", dto.getDiscountAmount() + "%"));
				}
				if (dto.getExpiryDate() != null && dto.getExpiryDate().getYear() > 0) {
					criteria.add(Restrictions.eq("expiryDate", dto.getExpiryDate() ));
				}
				if (dto.getOfferStatus() != null && dto.getOfferStatus().length() > 0) {
					criteria.add(Restrictions.like("offerStatus", dto.getOfferStatus() + "%"));
				}
							
				if (pageSize > 0) {
					pageNo = (pageNo - 1) * pageSize;
					criteria.setFirstResult(pageNo);
					criteria.setMaxResults(pageSize);
				}
				list = (ArrayList<CouponDTO>) criteria.list();
				//System.out.println("UserList: "+list);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in Coupon search"+e.getMessage());
		}finally {
			session.close();
		}
		return list;

	}

	@Override
	public CouponDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		CouponDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(CouponDTO)session.get(CouponDTO.class, pk);
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting Coupon by pk"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	
	@Override
	public CouponDTO findByOfferCode(String offerCode) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		CouponDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria =session.createCriteria(CouponDTO.class);
			criteria.add(Restrictions.eq("offerCode", offerCode));
			List list=criteria.list();
			if(list.size()==1) {
				dto=(CouponDTO)list.get(0);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Coupon by name"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;

	}

}
