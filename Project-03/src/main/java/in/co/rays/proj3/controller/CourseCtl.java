package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CourseModelInt;
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
@WebServlet(name="CourseCtl",urlPatterns= {"/ctl/CourseCtl"})
public class CourseCtl extends BaseCtl{
	
	
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
		
		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
			pass = false;
		} 
		
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		} 
		return pass;
	}
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CourseDTO dto = new CourseDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCourseName(DataUtility.getString(request.getParameter("name")));
		dto.setDuration(DataUtility.getString(request.getParameter("duration")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));

		populateBean(dto, request);

		return dto;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long id = DataUtility.getLong(request.getParameter("id"));

		CourseModelInt model=ModelFactory.getInstance().getCourseModel();
		//CourseModel model = new CourseModel();

		if (id > 0) {
			try {
				CourseDTO dto=model.findByPK(id);
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
		
		CourseModelInt model=ModelFactory.getInstance().getCourseModel();
		//CourseModel model = new CourseModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			CourseDTO dto = (CourseDTO) populateDTO(request);
			try {
				long pk = model.add(dto);
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Course added successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Course already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}else if(OP_UPDATE.equalsIgnoreCase(op)) {
			CourseDTO dto = (CourseDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
				}
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Course updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Course already exists", request);
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
		return ORSView.COURSE_VIEW;
	}
	
	
}
