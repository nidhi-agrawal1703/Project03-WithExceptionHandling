package in.co.rays.proj3.controller;

import java.io.IOException; 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * Change Password functionality controller.Performs operation 
 * for change password.
 * 
 * @author Nidhi
 * @version 1.0
 *
 */
@WebServlet(name="ChangePasswordCtl",urlPatterns= {"/ctl/ChangePasswordCtl"})
public class ChangePasswordCtl extends BaseCtl {
	
	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean pass=true;
		String op=request.getParameter("operation");
		
		if(OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			return pass;
		}
		
		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		} else if (request.getParameter("oldPassword").equals(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Old and New passwords should be different");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass=false;
		}else if(!DataValidator.isPasswordLength(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Password length must be between 8 to 12 characters");
			pass=false;
		}else if(!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Must contain uppercase, lowercase, digit & special character");
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass=false;
		}
		
		if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "New and confirm passwords not matched");
			pass = false;
		}

		return pass;
	}
	
		
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		
		String op=DataUtility.getString(request.getParameter("operation"));
		
		UserModelInt model=ModelFactory.getInstance().getUserModel();
		UserDTO dto=(UserDTO)session.getAttribute("user");
		String newPassword = request.getParameter("newPassword");
		String oldPassword = request.getParameter("oldPassword");
		long id=dto.getId();
		//System.out.println("ID in doPost of change password ctl: "+dto.getId());
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			try {
				boolean flag = model.changePassword(id,newPassword, dto.getPassword());
				if (flag == true) {
					model.findByLogin(dto.getLogin());
					ServletUtility.setSuccessMessage("Password has been changed Successfully", request);
				}
			} catch (RecordNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
				ServletUtility.setErrorMessage("Old Password is Invalid Not Found", request);
			}catch (ApplicationException e1) {
				// TODO: handle exception
				e1.printStackTrace();
				ServletUtility.handleException(e1, request, response);
				return;
			}catch (Exception e2) {
				// TODO: handle exception
			}
		}else if(OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;
		}
		ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		UserDTO dto=(UserDTO)session.getAttribute("user");
		ServletUtility.setDto(dto, request);
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}
