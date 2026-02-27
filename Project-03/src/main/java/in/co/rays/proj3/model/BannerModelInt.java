package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.BannerDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.PrimaryKeyNotFoundException;

public interface BannerModelInt {
	
			
		public Long add(BannerDTO dto)throws ApplicationException,DuplicateRecordException,DatabaseException;
		public void delete(BannerDTO dto)throws ApplicationException,DatabaseException;
		public void update(BannerDTO dto)throws ApplicationException,DuplicateRecordException,DatabaseException;
		public List list()throws ApplicationException,DatabaseException;
		public List list(int pageNo,int pageSize)throws ApplicationException,DatabaseException;
		public List search(BannerDTO dto)throws ApplicationException,DatabaseException;
		public List search(BannerDTO dto,int pageNo,int pageSize)throws ApplicationException,DatabaseException;
		public BannerDTO findByPK(long pk)throws ApplicationException,DatabaseException,PrimaryKeyNotFoundException;
		public BannerDTO findByBannerCode(String bannerCode)throws ApplicationException,DatabaseException;

	}

