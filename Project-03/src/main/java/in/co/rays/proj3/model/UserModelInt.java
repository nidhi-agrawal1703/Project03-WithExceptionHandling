package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;

public interface UserModelInt {
	
	public long add(UserDTO dto)throws ApplicationException,DuplicateRecordException,DatabaseException;
	public void delete(UserDTO dto)throws ApplicationException,DatabaseException;
	public void update(UserDTO dto)throws ApplicationException,DuplicateRecordException,DatabaseException;
	public UserDTO findByPK(long pk)throws ApplicationException;
	public UserDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException,DatabaseException;
	public List list(int pageNo,int pageSize)throws ApplicationException,DatabaseException;
	public List search(UserDTO dto,int pageNo,int pageSize)throws ApplicationException,DatabaseException;
	public List search(UserDTO dto)throws ApplicationException,DatabaseException;
	public boolean changePassword(long id,String newPassword,String oldPassword)throws ApplicationException, RecordNotFoundException;
	public UserDTO authenticate(String login,String password)throws ApplicationException,RecordNotFoundException,DatabaseException;
	public boolean forgetPassword(String login)throws ApplicationException, RecordNotFoundException;
	public boolean resetPassword(UserDTO dto)throws ApplicationException,RecordNotFoundException;
	public long registerUser(UserDTO dto)throws ApplicationException,DuplicateRecordException;
	public List getRoles(UserDTO dto)throws ApplicationException;
}
