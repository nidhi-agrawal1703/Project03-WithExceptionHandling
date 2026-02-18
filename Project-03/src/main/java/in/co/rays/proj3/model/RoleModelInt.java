package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;

public interface RoleModelInt {
	
	public long add(RoleDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(RoleDTO dto)throws ApplicationException;
	public void update(RoleDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws DatabaseException;
	public List list(int pageNo,int pageSize)throws DatabaseException;
	public List search(RoleDTO dto)throws ApplicationException;
	public List search(RoleDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public RoleDTO findByPK(long pk)throws ApplicationException;
	public RoleDTO findByName(String name)throws ApplicationException;
}
