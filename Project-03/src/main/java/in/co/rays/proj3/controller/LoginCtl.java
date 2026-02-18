package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.RoleModelInt;
import in.co.rays.proj3.model.UserModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


/**
 *Login functionality controller.
 *Used to perform login,session activation and logout
 *
 * @author Nidhi
 * @version 1.0
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {

	public static final String OP_SIGN_IN = "Sign In";
	public static final String OP_SIGN_UP = "Sign Up";
	public static final String OP_LOG_OUT = "Logout";

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			//System.out.println("inside valid");
			return pass;
		}
		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login Id"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "password"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();
		System.out.println(request.getParameter("login"));
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		dto.setPassword(DataUtility.getString(request.getParameter("password")));
		return dto;

	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String op = DataUtility.getString(request.getParameter("operation"));
		UserModelInt model=ModelFactory.getInstance().getUserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_LOG_OUT.equalsIgnoreCase(op)) {
			session.invalidate();
			ServletUtility.setSuccessMessage("Logout Successful", request);
		}
		
	ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,DatabaseException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		String op = DataUtility.getString(req.getParameter("operation"));
		
		UserModelInt userModel=ModelFactory.getInstance().getUserModel();
		RoleModelInt roleModel=ModelFactory.getInstance().getRoleModel();

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			
			//System.out.println("1111");

			UserDTO dto = (UserDTO) populateDTO(req);
			//System.out.println("LoginId:"+dto.getLogin()+"Password:"+dto.getPassword());
			try {
				dto=userModel.authenticate(dto.getLogin(), dto.getPassword());
				//System.out.println("LoginId:"+dto.getFirstName()+"Password:"+dto.getPassword());
				if(dto!=null) {
					session.setAttribute("user", dto);
					System.out.println("22222");
					System.out.println(dto.getFirstName());
					long roleId=dto.getRoleId();
					RoleDTO roleDto=roleModel.findByPK(roleId);
					if(roleDto!=null) {
						session.setAttribute("role", roleDto.getName());
					}
					ServletUtility.redirect(ORSView.WELCOME_CTL, req, resp);
					return;

					
				}//else {
					//dto=(UserDTO)populateDTO(req);
					//ServletUtility.setDto(dto, req);
					//ServletUtility.setErrorMessage("Invalid username password", req);
					//}
				}
			 catch (ApplicationException e) {
				//System.out.println("3333");
				// TODO: handle exception
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp);
				return;
			}//catch(JDBCConnectionException e) {
				//ServletUtility.setErrorMessage("Database Server Down",req);
				//ServletUtility.forward(getView(), req, resp);				
			//}
				catch(RecordNotFoundException e) {
				dto=(UserDTO)populateDTO(req);
				if(dto==null) {
					dto=new UserDTO();
				}
				ServletUtility.handleException(e, req, resp, "User",dto);
				//ServletUtility.setDto(dto, req);
				//ServletUtility.setErrorMessage("Invalid username password", req);
				//ServletUtility.forward(getView(), req, resp);
			}		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, req, resp);
			return;
		}
		//System.out.println("4444");
		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected String getView() {

		return ORSView.LOGIN_VIEW;
	}

}
