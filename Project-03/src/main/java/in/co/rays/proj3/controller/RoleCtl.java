package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.RoleModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 *Role functionality controller.
 *Performs CRUD operations for RoleBean
 * 
 * @author Nidhi
 * @version 1.0
 */

@WebServlet(name="RoleCtl",urlPatterns = {"/ctl/RoleCtl"} )
public class RoleCtl extends BaseCtl {
		
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass=true;
		if(DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass=false;
		}else if(!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid name");
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass=false;
		}
//		System.out.println("Value of "+pass);
		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		
		RoleDTO dto=new RoleDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		
		populateBean(dto, request);
		return dto;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long id = DataUtility.getLong(request.getParameter("id"));
		
		RoleModelInt model=ModelFactory.getInstance().getRoleModel();
		//RoleModel model = new RoleModel();

		if (id > 0) {
			try {
				RoleDTO dto = model.findByPK(id);
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
		String op=DataUtility.getString(request.getParameter("operation"));
		
	
		RoleModelInt model=ModelFactory.getInstance().getRoleModel();
		long id=DataUtility.getLong(request.getParameter("id"));
		
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			RoleDTO dto=(RoleDTO)populateDTO(request);
			try {
				model.add(dto);
				ServletUtility.setDto(dto, request);
				ServletUtility.setSuccessMessage("Data is successfully saved",request);
			} catch (DuplicateRecordException e) {
				// TODO: handle exception
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Role already Exists", request);
			}catch(ApplicationException e2) {
				e2.printStackTrace();
				ServletUtility.handleException(e2, request, response);
				return;
			}
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.forward(ORSView.ROLE_CTL, request, response);
			return;
		}else if(OP_UPDATE.equalsIgnoreCase(op)) {
			RoleDTO dto = (RoleDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
				}
				ServletUtility.setDto(dto, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Role already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;

		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
		ServletUtility.forward(getView(), request, response);
		}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_VIEW;
	}

}
