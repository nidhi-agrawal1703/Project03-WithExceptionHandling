package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CustomerDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CustomerModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


/**
 * Course functionality controller.Performs CRUD operations.
 *
 * @author Nidhi
 * @version 1.0
 */
@WebServlet(name="CustomerCtl",urlPatterns= {"/ctl/CustomerCtl"})
public class CustomerCtl extends BaseCtl{
	
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean pass=true;
		
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("location"))) {
			request.setAttribute("location", PropertyReader.getValue("error.require", "Location"));
			pass = false;
		} 
		
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "mobileNo"));
			pass = false;
		}else if(!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		} 
		
		if (DataValidator.isNull(request.getParameter("importance"))) {
			request.setAttribute("importance", PropertyReader.getValue("error.require", "Importance"));
			pass = false;
		} 
		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CustomerDTO dto = new CustomerDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setLocation(DataUtility.getString(request.getParameter("location")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		dto.setImportance(DataUtility.getString(request.getParameter("importance")));

		populateBean(dto, request);

		return dto;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long id = DataUtility.getLong(request.getParameter("id"));

		CustomerModelInt model=ModelFactory.getInstance().getCustomerModel();
		//CourseModel model = new CourseModel();

		if (id > 0) {
			try {
				CustomerDTO dto=model.findByPK(id);
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
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
		String op = DataUtility.getString(request.getParameter("operation"));
		
		CustomerModelInt model=ModelFactory.getInstance().getCustomerModel();
		//CourseModel model = new CourseModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			CustomerDTO dto = (CustomerDTO) populateDTO(request);
			try {
				long pk = model.add(dto);
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Customer added successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Customer already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CUSTOMER_CTL, request, response);
			return;
		}else if(OP_UPDATE.equalsIgnoreCase(op)) {
			CustomerDTO dto = (CustomerDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
				}
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Customer updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Customer already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		
		ServletUtility.forward(getView(), request, response);
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CUSTOMER_VIEW;
	}
	
	
}
