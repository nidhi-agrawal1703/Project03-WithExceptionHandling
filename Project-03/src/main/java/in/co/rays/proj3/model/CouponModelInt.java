package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.CouponDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface CouponModelInt {
	
	public long add(CouponDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(CouponDTO dto)throws ApplicationException;
	public void update(CouponDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(CouponDTO dto)throws ApplicationException;
	public List search(CouponDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public CouponDTO findByPK(long pk)throws ApplicationException;
	public CouponDTO findByOfferCode(String offerCode)throws ApplicationException;
}
