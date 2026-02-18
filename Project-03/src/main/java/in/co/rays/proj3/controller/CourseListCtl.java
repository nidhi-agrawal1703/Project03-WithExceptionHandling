package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 *Course list functionality controller.
 * Performs operations for list,search,delete operations of course
 * 
 * @author Nidhi
 * @version 1.0
 *
 */
@WebServlet(name="CourseListCtl",urlPatterns= {"/ctl/CourseListCtl"})
public class CourseListCtl extends BaseCtl {
	@Override
	protected void preload(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CourseModelInt courseModel=ModelFactory.getInstance().getCourseModel();
		//CourseModel courseModel=new CourseModel();
		try {
			List courseList=courseModel.list();
			request.setAttribute("courseList", courseList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CourseDTO dto = new CourseDTO();

		dto.setCourseName(DataUtility.getString(request.getParameter("name")));
		dto.setId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setDuration(DataUtility.getString(request.getParameter("duration")));

		return dto;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		CourseDTO dto=(CourseDTO)populateDTO(request);
		
		CourseModelInt model=ModelFactory.getInstance().getCourseModel();
		//CourseModel model=new CourseModel();
		try {
			List<CourseDTO> list=model.search(dto, pageNo, pageSize);
			List<CourseDTO> next=model.search(dto, pageNo+1, pageSize);
			//System.out.println("List in doGEt of controller"+ list);
			if(list==null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			ServletUtility.setList(list,request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setDto(dto, request);
			//ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());
			
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List list=null;
		List next=null;
		
		int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize=DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo=(pageNo==0)?1:pageNo	;
		pageSize=(pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
		
		CourseDTO dto=(CourseDTO)populateDTO(request);
		CourseModelInt model=ModelFactory.getInstance().getCourseModel();
		//CourseModel model=new CourseModel();
		
		String op=DataUtility.getString(request.getParameter("operation"));
		String[]ids=request.getParameterValues("ids");
		
		try {
			if(OP_SEARCH.equalsIgnoreCase(op)||"Next".equalsIgnoreCase(op)||"Previous".equalsIgnoreCase(op)	) {
				if(OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo=1;
				}
				if("Next".equalsIgnoreCase(op)) {
					pageNo++;
				}
				if("Previous".equalsIgnoreCase(op)) {
					pageNo--;
				}
			}else if(OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
				return;
			}else if(OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=1;
				if(ids!=null && ids.length>0) {
					CourseDTO deletedto=new CourseDTO();
					for(String id:ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Course Deleted successfully", request);
					}
				}else {
					ServletUtility.setErrorMessage("Select atleast one record", request);
				}
			}else if(OP_RESET.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;
			}else if(OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;
			}
			
			list=model.search(dto, pageNo, pageSize);
			next=model.search(dto, pageNo+1, pageSize);
			
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setDto(dto, request);
			//ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
		
		//ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_LIST_VIEW;
	}

}
