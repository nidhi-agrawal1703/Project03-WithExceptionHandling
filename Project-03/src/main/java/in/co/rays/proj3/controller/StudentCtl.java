package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CollegeModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.StudentModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


/**
 * Student functionality controller.
 * Performs CRUD operation for StudentBean.
 * 
 * @author Nidhi
 * @version 1.0
 */
@WebServlet(name="StudentCtl",urlPatterns= {"/ctl/StudentCtl"})
public class StudentCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(StudentCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CollegeModelInt collegeModel=ModelFactory.getInstance().getCollegeModel();
		
		try {
			List collegeList=collegeModel.list();
			request.setAttribute("collegeList", collegeList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e);
		}
	}
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		// TODO Auto-generated method stub
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
		
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email Id"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}

		
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College"));
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

		return pass;
	}
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		log.debug("StudentCtl Method populatebean Started");

		StudentDTO dto=new StudentDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setEmail(DataUtility.getString(request.getParameter("email")));
		
		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
		
		populateBean(dto, request);
		
		log.debug("StudentCtl Method populatebean Ended");
		
		return dto;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		log.debug("StudentCtl Method doGet Started");
		
		String op=DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		
		StudentModelInt model=ModelFactory.getInstance().getStudentModel();
		System.out.println("in do get ");
		if (id > 0 || op!=null) {
			try {
				StudentDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("out	 do get ");
		ServletUtility.forward(getView(), request, response);
		log.debug("StudentCtl Method doGett Ended");

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.debug("StudentCtl Method doPost Started");
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		StudentModelInt model=ModelFactory.getInstance().getStudentModel();
		//StudentModel model = new StudentModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if(OP_SAVE.equalsIgnoreCase(op)) {
			StudentDTO dto = (StudentDTO) populateDTO(request);
			try {
				long pk=model.add(dto);
				ServletUtility.setDto(dto, request);
				ServletUtility.setSuccessMessage("Student added successfully", request);
			} catch (DuplicateRecordException e) {
				// TODO: handle exception
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Email Id already exists", request);
			}catch (ApplicationException e1) {
				// TODO: handle exception
				e1.printStackTrace();
				ServletUtility.handleException(e1, request, response);
				return;
			}
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		}else if(OP_UPDATE.equalsIgnoreCase(op)) {
			StudentDTO dto = (StudentDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
				}
				ServletUtility.setDto(dto, request);
				ServletUtility.setSuccessMessage("Student updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Email already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("StudentCtl Method doPost Ended");
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_VIEW;
	}

}
