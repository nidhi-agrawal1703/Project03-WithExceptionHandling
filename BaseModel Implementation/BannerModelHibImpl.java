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
	public Long add(BannerDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException {
		
		BannerDTO existDto = null;
		existDto = findByUniqueKey("bannerCode",dto.getBannerCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Duplicate Banner Code found");
		}

		return super.add(dto);
	}

	@Override
	public void delete(BannerDTO dto) throws ApplicationException, DatabaseException {
		
		super.delete(dto);
	}

	@Override
	public void update(BannerDTO dto) throws ApplicationException, DuplicateRecordException, DatabaseException {
		
		Session session = null;
		Transaction tx = null;
		BannerDTO existDto = findByUniqueKey("bannerCode",dto.getBannerCode());

		// Check if login id already exists
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Banner code already exists");
		}
		super.update(dto);
	}

	@Override
	public List list() throws ApplicationException, DatabaseException {
		
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException, DatabaseException {

		return super.list(pageNo, pageSize);
	}

	@Override
	public List search(BannerDTO dto) throws ApplicationException, DatabaseException {
		
		return search(dto, 0, 0);
	}

	@Override
	public void whereCondition(BannerDTO dto,Criteria criteria) {
		
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
		}
			
	}

	@Override
	public BannerDTO findByPK(long pk) throws ApplicationException, DatabaseException, PrimaryKeyNotFoundException {
		

		BannerDTO dto = super.findByPK(pk);
		return dto;

	}

	
	
	
}
