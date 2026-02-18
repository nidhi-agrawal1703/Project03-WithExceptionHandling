package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.CompanyDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface CompanyModelInt {
	
	public long add(CompanyDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(CompanyDTO dto)throws ApplicationException;
	public void update(CompanyDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(CompanyDTO dto)throws ApplicationException;
	public List search(CompanyDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public CompanyDTO findByPK(long pk)throws ApplicationException;
	public CompanyDTO findByName(String name)throws ApplicationException;
}
