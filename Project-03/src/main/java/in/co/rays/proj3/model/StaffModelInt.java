package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.StaffDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface StaffModelInt {
	
	public long add(StaffDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(StaffDTO dto)throws ApplicationException;
	public void update(StaffDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(StaffDTO dto)throws ApplicationException;
	public List search(StaffDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public StaffDTO findByPK(long pk)throws ApplicationException;
	public StaffDTO findByName(String name)throws ApplicationException;
}
