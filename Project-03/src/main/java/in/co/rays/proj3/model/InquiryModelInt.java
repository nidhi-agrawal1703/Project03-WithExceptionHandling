package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.InquiryDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface InquiryModelInt {
	
	public long add(InquiryDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(InquiryDTO dto)throws ApplicationException;
	public void update(InquiryDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(InquiryDTO dto)throws ApplicationException;
	public List search(InquiryDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public InquiryDTO findByPK(long pk)throws ApplicationException;
	public InquiryDTO findByName(String name)throws ApplicationException;
}
