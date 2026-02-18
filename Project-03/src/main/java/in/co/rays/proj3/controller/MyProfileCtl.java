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
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.UserModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


/**
 * My profile functionality controller.
 * Performs operations to update your profile.
 * 
 * @author Nidhi
 * @version 1.0
 *
 */
@WebServlet(name="MyProfileCtl",urlPatterns= {"/ctl/MyProfileCtl"})
public class MyProfileCtl extends BaseCtl {
	public static final String OP_CHANGE_MY_PASSWORD = "Change Password";
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean pass=true;
		String op=DataUtility.getString(request.getParameter("operation"));
		
		if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {
			return pass;
		}
		
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

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}

		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setLogin(DataUtility.getString(request.getParameter("login")));

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateBean(dto, request);

		return dto;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(true);
		UserDTO dto=(UserDTO)session.getAttribute("user");
		UserModelInt model=ModelFactory.getInstance().getUserModel();		
		long id=dto.getId();
		if(id>0) {
			try {
				UserDTO dto1=model.findByPK(id);
				ServletUtility.setDto(dto1, request);;
			} catch (ApplicationException e) {
				// TODO: handle exception
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;

			}
		}
		ServletUtility.forward(getView(), request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(true);
		UserDTO dto=(UserDTO)session.getAttribute("user");
		long id=dto.getId();
		String op=DataUtility.getString(request.getParameter("operation"));
		
		UserModelInt model=ModelFactory.getInstance().getUserModel();
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			UserDTO dto1=(UserDTO)populateDTO(request);
			try {
				if(id>0) {
					dto.setFirstName(dto1.getFirstName());
					dto.setLastName(dto1.getLastName());
					dto.setGender(dto1.getGender());
					dto.setMobileNo(dto1.getMobileNo());
					dto.setDob(dto1.getDob());
					model.update(dto);
				}
				ServletUtility.setDto(dto, request);;
				ServletUtility.setSuccessMessage("Profile has been updated Successfully. ", request);
			} catch (DuplicateRecordException e) {
				// TODO: handle exception
				ServletUtility.setDto(dto, request);;
				ServletUtility.setErrorMessage("Login Id already exists", request);
			}catch(ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if(OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MY_PROFILE_VIEW;
	}

}
