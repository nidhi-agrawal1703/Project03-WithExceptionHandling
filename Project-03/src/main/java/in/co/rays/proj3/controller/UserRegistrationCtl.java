package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.UserModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * User Registration functionality.
 * Used to perform input and business validations
 * Add user and send confirmation email.
 * 
 * @author Nidhi
 * @version 1.0
 */
@WebServlet(name="UserRegistrationCtl",urlPatterns={"/UserRegistrationCtl"})
public class UserRegistrationCtl extends BaseCtl {

	public static final String OP_SIGNUP ="Sign Up";

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass=true;
		
		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "Invalid First Name");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Invalid Last Name");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login",PropertyReader.getValue("error.require","Login Id"));
			pass=false;
		}else if(!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login","Invalid Login id");
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "password"));
			pass=false;
		}else if(!DataValidator.isPasswordLength(request.getParameter("password"))) {
			request.setAttribute("password", "Password should be 8 to 12 characters");
			pass=false;
		}else if(!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", "Must contain uppercase, lowercase, digit & special character");
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "confirm password"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "date of birth"));
			pass=false;
		}else if(!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "DAte of Birth"));
			pass=false;
		}
		
		if(!request.getParameter("password").equals(request.getParameter("confirmPassword")) && 
			!"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Password and Confirm Password must be same");
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender",PropertyReader.getValue("error.require","Gender"));
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo",PropertyReader.getValue("error.require", "Mobile No"));
			pass=false;
		}else if(!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass=false;
		}else if(!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass=false;
		}
		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		UserDTO dto=new UserDTO();
		
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		dto.setPassword(DataUtility.getString(request.getParameter("password")));
		dto.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		dto.setRoleId(RoleDTO.STUDENT);

		populateBean(dto,request);
		return dto;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String op=DataUtility.getString(req.getParameter("operation"));
		
		UserModelInt model=ModelFactory.getInstance().getUserModel();
		if(OP_SIGNUP.equalsIgnoreCase(op)) {
			UserDTO dto=(UserDTO)populateDTO(req);
			//System.out.println(bean.toString());
			System.out.println("Role Id: "+dto.getRoleId());
			try {
				model.registerUser(dto);
				ServletUtility.setDto(dto, req);
				ServletUtility.setSuccessMessage("Registration Successful",req);
			} catch (DuplicateRecordException e) {
				// TODO: handle exception
				ServletUtility.setDto(dto, req);
				ServletUtility.setErrorMessage("Login id already exists", req);
			}catch (ApplicationException e) {
				// TODO: handle exception
				e.printStackTrace();
				ServletUtility.handleException(e, req, resp);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.forward(getView(), req, resp);
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, req, resp);
			return;	
		}
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_REGISTRATION_VIEW;
	}
	
	
}
