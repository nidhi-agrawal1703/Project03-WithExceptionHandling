package in.co.rays.proj3.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.util.EmailBuilder;
import in.co.rays.proj3.util.EmailMessage;
import in.co.rays.proj3.util.EmailUtility;
import in.co.rays.proj3.util.HibDataSource;
import in.co.rays.proj3.util.ServletUtility;

public class UserModelHibImpl implements UserModelInt {

	public long add(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		UserDTO existDto=null;
		existDto=findByLogin(dto.getLogin());
		if(existDto!=null) {
			throw new DuplicateRecordException("Duplicate Record found");
			
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
			//throw new ApplicationException("Exception in add user"+e.getMessage());
			throw new DatabaseException("Database Server Down Exception",e);
		}finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(UserDTO dto) throws ApplicationException {
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
			//throw new ApplicationException("Exception in User Delete" + e.getMessage());
			throw new DatabaseException("Database Server Down Exception",e);
		}finally {
			session.close();
		}
	}

	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction tx=null;
		UserDTO existDto=findByLogin(dto.getLogin());
		
		//Check if login id already exists
		if(existDto!=null && existDto.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Login id already exists");
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
			//throw new ApplicationException("Exception in updating user"+e.getMessage());
			throw new DatabaseException("Database Server Down Exception",e);
		}finally {
			session.close();
		}
	}

	public UserDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		UserDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(UserDTO)session.get(UserDTO.class, pk);
		} catch (HibernateException e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting user by pk"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	public UserDTO findByLogin(String login) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		UserDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria =session.createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list=criteria.list();
			if(list.size()==1) {
				dto=(UserDTO)list.get(0);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in getting user by loginid"+e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	public List list() throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		return list(0,0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria =session.createCriteria(UserDTO.class);
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		} catch (HibernateException e) {
			// TODO: handle exception
			//throw new ApplicationException("Exception in getting user list"+e.getMessage());
			throw new DatabaseException("Database server down exception",e);
		}finally {
			session.close();
		}
		return list;
	}

	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session=null;
		ArrayList<UserDTO> list=null;
		
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(UserDTO.class);
			if(dto!=null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
					criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
				}

				if (dto.getLastName() != null && dto.getLastName().length() > 0) {
					criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
				}
				if (dto.getLogin() != null && dto.getLogin().length() > 0) {
					criteria.add(Restrictions.like("login", dto.getLogin() + "%"));
				}
				if (dto.getPassword() != null && dto.getPassword().length() > 0) {
					criteria.add(Restrictions.like("password", dto.getPassword() + "%"));
				}
				if (dto.getGender() != null && dto.getGender().length() > 0) {
					criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
				}
				if (dto.getDob() != null && dto.getDob().getDate() > 0) {
					criteria.add(Restrictions.eq("dob", dto.getDob()));
				}
				if (dto.getLastLogin() != null && dto.getLastLogin().getTime() > 0) {
					criteria.add(Restrictions.eq("lastLogin", dto.getLastLogin()));
				}
				if (dto.getRoleId() > 0) {
					criteria.add(Restrictions.eq("roleId", dto.getRoleId()));
				}
				if (dto.getUnSuccessfullLogin() > 0) {
					criteria.add(Restrictions.eq("unSuccessfulLogin", dto.getUnSuccessfullLogin()));
				}
			
				if (pageSize > 0) {
					pageNo = (pageNo - 1) * pageSize;
					criteria.setFirstResult(pageNo);
					criteria.setMaxResults(pageSize);
				}
				list = (ArrayList<UserDTO>) criteria.list();
				//System.out.println("UserList: "+list);
			}
		} catch(HibernateException e) {
			e.printStackTrace();
			throw new DatabaseException("Database server down",e);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//if(e instanceof JDBCException) {
				
			//}else {
			//throw new ApplicationException("Exception in user search"+e.getMessage());
			//}
		}finally {
			session.close();
		}
		return list;
	}

	public List search(UserDTO dto) throws ApplicationException,DatabaseException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	public boolean changePassword(long id, String newPassword, String oldPassword)
			throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		boolean flag=false;
		UserDTO dtoExist=null;
		
		dtoExist=findByPK(id);
		//System.out.println("Password in database from changePassword() of UserModelHibImpl= "+ dtoExist.getPassword());
		//System.out.println("Password in database field changePassword() of UserModelHibImpl= "+oldPassword);
		if(dtoExist!=null && dtoExist.getPassword().equals(oldPassword)) {
			dtoExist.setPassword(newPassword);
			dtoExist.setConfirmPassword(newPassword);
			try {
				update(dtoExist);	
			} catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flag=true;
		}else {
			throw new RecordNotFoundException("Login id does not exist");
		}
		
		//Code for sending email
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", dtoExist.getLogin());
		map.put("password", dtoExist.getPassword());
		map.put("firstName", dtoExist.getFirstName());
		map.put("lastName", dtoExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dtoExist.getLogin());
		msg.setSubject("Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return flag;
	}

	public UserDTO authenticate(String login, String password) throws ApplicationException,RecordNotFoundException,DatabaseException {
		// TODO Auto-generated method stub
		UserDTO dto=null;
		try {
		Session session=null;		
		session=HibDataSource.getSession();
		Query q=session.createQuery("from UserDTO where login=? and password=?");
		q.setString(0, login);
		q.setString(1, password);
		List list=q.list();
		if(list.size()>0) {
			dto=(UserDTO)list.get(0);
			//System.out.println("Inside authenticate user model Firstname: "+dto.getLogin());
		}else {
			dto=null;
			throw new RecordNotFoundException("User not found");
		}
		
		//System.out.println("Inside authenticate");
		//System.out.println(dto.getFirstName());
		
		}catch(HibernateException e) {
			e.printStackTrace();
			throw new DatabaseException("Database server Down",e);
		}
		return dto;		
	}

	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		UserDTO userData=findByLogin(login);
		boolean flag=false;
		if(userData==null) {
			throw new RecordNotFoundException("Login Id not found");
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", login);
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastName", userData.getLastName());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		System.out.println(flag);
		flag = true;

		return flag;
	}

	public boolean resetPassword(UserDTO dto) throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
		UserDTO userData = findByPK(dto.getId());
		userData.setPassword(newPassword);

		try {
			update(userData);
		} catch (DuplicateRecordException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return true;
	}

	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		long pk=add(dto);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Registration is successful for ORS Project SUNRAYS Technologies");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}

	public List getRoles(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
