package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.StaffDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.StaffModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * Staff Member functionality controller.Performs CRUD operations.
 *
 * @author Nidhi
 * @version 1.0
 */
@WebServlet(name="StaffCtl",urlPatterns={"/ctl/StaffCtl"})
public class StaffCtl extends BaseCtl {
	
	
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
		
		if (DataValidator.isNull(request.getParameter("joiningDate"))) {
			request.setAttribute("joiningDate", PropertyReader.getValue("error.require", "Joining Date"));
			pass = false;
		}else if (!DataValidator.isDate(request.getParameter("joiningDate"))) {
			request.setAttribute("name", "Invalid Date");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("division"))) {
			request.setAttribute("division", PropertyReader.getValue("error.require", "Division"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("previousEmployer"))) {
			request.setAttribute("previousEmployer", PropertyReader.getValue("error.require", "Previous Employer"));
			pass = false;
		}
		
		return pass;

		}
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StaffDTO dto = new StaffDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setJoiningDate(DataUtility.getDate(request.getParameter("joiningDate")));
		dto.setDivision(DataUtility.getString(request.getParameter("division")));
		dto.setPreviousEmployer(DataUtility.getString(request.getParameter("previousEmployer")));

		populateBean(dto, request);

		return dto;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long id = DataUtility.getLong(request.getParameter("id"));

		StaffModelInt model=ModelFactory.getInstance().getStaffModel();
		
		if (id > 0) {
			try {
				StaffDTO dto=model.findByPK(id);
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
		
		StaffModelInt model=ModelFactory.getInstance().getStaffModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			StaffDTO dto = (StaffDTO) populateDTO(request);
			try {
				long pk = model.add(dto);
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Staff added successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Staff already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STAFF_CTL, request, response);
			return;
		}else if(OP_UPDATE.equalsIgnoreCase(op)) {
			StaffDTO dto = (StaffDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
				}
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Staff updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Staff already exists", request);
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
		return ORSView.STAFF_VIEW;
	}

}
