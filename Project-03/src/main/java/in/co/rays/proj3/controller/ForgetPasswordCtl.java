package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.UserModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


/**
 * Forget password functionality controller.
 * It is executed when user forgets password
 * Performs input and business validations 
 * Sends email containing password 
 * 
 * @author Nidhi
 * @version1.0
 */
@WebServlet(name="ForgetPasswordCtl",urlPatterns= {"/ForgetPasswordCtl"})
public class ForgetPasswordCtl extends BaseCtl {
	public static final String OP_GO="Go";
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean pass=true;
		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		return pass;
	}
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		UserDTO dto=new UserDTO();
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		return dto;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletUtility.forward(getView(), request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String op=DataUtility.getString(request.getParameter("operation"));
		
		UserDTO dto=(UserDTO)populateDTO(request);
		
		UserModelInt model=ModelFactory.getInstance().getUserModel();
		
		
		if(OP_GO.equalsIgnoreCase(op)) {
			try {
			boolean flag=model.forgetPassword(dto.getLogin());
			if(flag) {
				ServletUtility.setSuccessMessage("Password has been sent to your email id",request);
			}
			} catch (RecordNotFoundException e) {
				// TODO: handle exception
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}catch(ApplicationException e) {
				e.printStackTrace();
				ServletUtility.setErrorMessage("Please check your internet connection..!!", request);
			}
			ServletUtility.forward(getView(), request, response);
		}
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}
