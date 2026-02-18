package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CollegeModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


/**
 * College functionality controller.Performs crud operations.
 *
 * @author Nidhi
 * @version 1.0
 *
 */
@WebServlet(name = "CollegeCtl", urlPatterns = "/ctl/CollegeCtl")
public class CollegeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CollegeCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.preload(request);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "Phone No"));
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", "Phone No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", "Invalid Phone No");
			pass = false;
		}

		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		CollegeDTO dto = new CollegeDTO();
		System.out.println(request.getParameter("mobileNo"));
		
		dto.setName(request.getParameter("name"));
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("city"));
		System.out.println(request.getParameter("address"));
		System.out.println(request.getParameter("state"));
		System.out.println(request.getParameter("mobileNo"));
		dto.setCity(request.getParameter("city"));
		dto.setAddress(request.getParameter("address"));
		dto.setState(request.getParameter("state"));
		dto.setPhoneNo(request.getParameter("phoneNo"));
		
		populateBean(dto,request);
		return dto;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		
		CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
		
		if (id > 0 || op!=null) {
			CollegeDTO dto;
			try {
				dto=model.findByPK(id);
				ServletUtility.setDto(dto, request);;
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String op = DataUtility.getString(request.getParameter("operation"));

		CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
			CollegeDTO dto = (CollegeDTO) populateDTO(request);
			try {
				if(id>0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Record Successfully Updated", request);
				}else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Record Successfully Saved", request);
				}
				ServletUtility.setDto(dto, request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("College Already Exists", request);
			} catch (ApplicationException e2) {
				e2.printStackTrace();
				log.error(e2);
				ServletUtility.handleException(e2, request, response);
			} 
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(getView(), request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COLLEGE_VIEW;
	}

}
