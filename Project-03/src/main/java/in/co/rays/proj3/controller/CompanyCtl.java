package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CompanyDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CompanyModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

@WebServlet(name="CompanyCtl",urlPatterns= {"/ctl/CompanyCtl"})
public class CompanyCtl extends BaseCtl {
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean pass=true;
		
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if(!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("industryType"))) {
			request.setAttribute("industryType", PropertyReader.getValue("error.require", "Industry Type"));
			pass = false;
		} 
		
		if (DataValidator.isNull(request.getParameter("employeeCount"))) {
			request.setAttribute("employeeCount", PropertyReader.getValue("error.require", "Employee Count"));
			pass = false;
		} 
				
		
		if (DataValidator.isNull(request.getParameter("openingDate"))) {
			request.setAttribute("openingDate", PropertyReader.getValue("error.require", "Opening Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("openingDate"))) {
			request.setAttribute("openingDate", "Opening Date");
			pass = false;
		}
		
		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CompanyDTO dto = new CompanyDTO();

		//dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setIndustryType(DataUtility.getString(request.getParameter("industryType")));
		if (request.getParameter("employeeCount") != null && request.getParameter("employeeCount").length() > 0) {
			dto.setEmployeeCount(DataUtility.getInt(request.getParameter("employeeCount")));
		}
		dto.setOpeningDate(DataUtility.getDate(request.getParameter("openingDate")));
				
		populateBean(dto, request);

		return dto;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long id = DataUtility.getLong(request.getParameter("id"));

		CompanyModelInt model=ModelFactory.getInstance().getCompanyModel();
		//CourseModel model = new CourseModel();

		if (id > 0) {
			try {
				CompanyDTO dto=model.findByPK(id);
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
		
		CompanyModelInt model=ModelFactory.getInstance().getCompanyModel();
		//CourseModel model = new CourseModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			CompanyDTO dto = (CompanyDTO) populateDTO(request);
			try {
				long pk = model.add(dto);
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Company added successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Company already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COMPANY_CTL, request, response);
			return;
		}else if(OP_UPDATE.equalsIgnoreCase(op)) {
			CompanyDTO dto = (CompanyDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
				}
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Company updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Company already exists", request);
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
		return ORSView.COMPANY_VIEW;
	}

}
