package in.ro.rays.proj3.demo;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestUserDTO {
	
	public static void main(String[] args) {
		//UserDTO dto=new UserDTO();
		
		SessionFactory sf=new Configuration().configure().buildSessionFactory();
		Session session=sf.openSession();
		
		//UserDTO dto = (UserDTO)session.get(UserDTO.class, 1); 
		String login="ananya@gmail.com";
		String password="123";
		
		//dto.setId(1);
		//dto.setFirstName("Ananya");
		//dto.setLastName("Gupta");
		//dto.setLoginId("ananya@gmail.com");
		//dto.setPassword("123");
		//dto.setDob(new Date());		
		//dto.setAddress("Vellore");
		

		Query q=session.createQuery("from UserDTO where loginId=? and password=?");
		
		q.setString(0, login);
		q.setString(1, password);
		
		List list=q.list();
		
		UserDTO dto=null;
		
		if(list.size()>0) {
			dto=(UserDTO)list.get(0);
		}
		
		if(dto!=null) {
			System.out.println(dto.getId());
			System.out.println(dto.getFirstName());
			System.out.println(dto.getLastName());
			System.out.println(dto.getLoginId());
			System.out.println(dto.getPassword());
			System.out.println(dto.getDob());
			System.out.println(dto.getAddress());	
		}else {
			System.out.println("Authentication failed");
		}
		
		
		
		session.close();
	}
}
