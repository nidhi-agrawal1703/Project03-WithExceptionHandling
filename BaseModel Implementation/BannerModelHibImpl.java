package in.co.rays.proj3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.BannerDTO;
import in.co.rays.proj3.dto.BannerDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.PrimaryKeyNotFoundException;
import in.co.rays.proj3.util.HibDataSource;

public class BannerModelHibImpl extends BaseModel<BannerDTO> implements BannerModelInt {
	
	public BannerModelHibImpl() {
        super(BannerDTO.class);
    }
	@Override
	public Long add(BannerDTO dto) throws ApplicationException, DuplicateRecordException,DatabaseException {
		// TODO Auto-generated method stub
		BannerDTO existDto=null;
		existDto=findByBannerCode(dto.getBannerCode());
		if(existDto!=null) {
			throw new DuplicateRecordException("Duplicate Banner Code found");			
		}
		
		return super.add(dto);
	}

	@Override
	public void delete(BannerDTO dto) throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		super.delete(dto);
	}

	@Override
	public void update(BannerDTO dto) throws ApplicationException, DuplicateRecordException,DatabaseException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		BannerDTO existDto=findByBannerCode(dto.getBannerCode());
		
		//Check if login id already exists
		if(existDto!=null && existDto.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Banner code already exists");
		}
		super.update(dto);
	}

	@Override
	public List list() throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		return list(0,0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException,DatabaseException {
		
		return super.list(pageNo, pageSize);
	}

	@Override
	public List search(BannerDTO dto) throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	@Override
	public List search(BannerDTO dto, int pageNo, int pageSize) throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		Session session=null;
		ArrayList<BannerDTO> list=null;
		
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(BannerDTO.class);
			if(dto!=null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getBannerCode() != null && dto.getBannerCode().length() > 0) {
					criteria.add(Restrictions.like("bannerCode", dto.getBannerCode() + "%"));
				}
				if (dto.getBannerTitle() != null && dto.getBannerTitle().length() > 0) {
					criteria.add(Restrictions.like("bannerTitle", dto.getBannerTitle() + "%"));
				}
				if (dto.getImagePath() != null && dto.getImagePath().length() > 0) {
					criteria.add(Restrictions.like("imagePath", dto.getImagePath() + "%"));
				}
				if (dto.getBannerStatus() != null && dto.getBannerStatus().length() > 0) {
					criteria.add(Restrictions.like("bannerStatus", dto.getBannerStatus() + "%"));
				}
											
				if (pageSize > 0) {
					pageNo = (pageNo - 1) * pageSize;
					criteria.setFirstResult(pageNo);
					criteria.setMaxResults(pageSize);
				}
				list = (ArrayList<BannerDTO>) criteria.list();
				//System.out.println("UserList: "+list);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			BaseModel.handleException(e);
		}finally {
			HibDataSource.closeSession(session);
		}
		return list;

	}

	@Override
	public BannerDTO findByPK(long pk) throws ApplicationException,DatabaseException,PrimaryKeyNotFoundException {
		// TODO Auto-generated method stub
		
			BannerDTO dto=super.findByPK(pk);
			return dto;
		
	}

	
	@Override
	public BannerDTO findByBannerCode(String bannerCode) throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		Session session=null;
		BannerDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria =session.createCriteria(BannerDTO.class);
			criteria.add(Restrictions.eq("bannerCode", bannerCode));
			List list=criteria.list();
			if(list.size()==1) {
				dto=(BannerDTO)list.get(0);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new DatabaseException("Database SErver Down"+e.getMessage(),e);
		}finally {
			HibDataSource.closeSession(session);
		}
		return dto;

	}

}
