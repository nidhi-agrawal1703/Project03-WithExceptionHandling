package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


/**
 * Subject List functionality controller.
 * Performs list,search operations for SubjectBean .
 * 
 * @author Nidhi
 * @version 1.0
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {
	@Override
	protected void preload(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		SubjectModelInt subjectModel=ModelFactory.getInstance().getSubjectModel();
		CourseModelInt courseModel=ModelFactory.getInstance().getCourseModel();
		//SubjectModel subjectModel=new SubjectModel();
		//CourseModel courseModel=new CourseModel();
		
		try {
			List subjectList=subjectModel.list();
			request.setAttribute("subjectList", subjectList);
			
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
		SubjectDTO dto = new SubjectDTO();

		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setId(DataUtility.getLong(request.getParameter("subjectId")));

		return dto;	
		}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		SubjectDTO dto = (SubjectDTO) populateDTO(request);
		SubjectModelInt model=ModelFactory.getInstance().getSubjectModel();
		//SubjectModel model=new SubjectModel();
		
		try {
			List<SubjectDTO> list=model.search(dto, pageNo, pageSize);
			List<SubjectDTO> next=model.search(dto, pageNo+1, pageSize);
			System.out.println("List in SubjectListCTL doGet"+list);
			if(list==null ||list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
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
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List list=null;
		List next=null;
		
		int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize=DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo=(pageNo==0)?1:pageNo;
		pageSize=(pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
		
		SubjectDTO dto=(SubjectDTO)populateDTO(request);
		SubjectModelInt model=ModelFactory.getInstance().getSubjectModel();
		//SubjectModel model=new SubjectModel();
		
		String op=DataUtility.getString(request.getParameter("opearation"));
		String[] ids=request.getParameterValues("ids");
		System.out.println("OPeration in doPost of SubjectListCTL "+op);
		System.out.println("PageNo in doPost of SubjectListCTL"+pageNo);
		try {
			if(OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
				if(OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo=1;
				}else if(OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				}else if(OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}
			}else if(OP_NEW.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.SUBJECT_CTL,request,response);
				return;
			}else if(OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=1;
				if(ids!=null && ids.length>0) {
					SubjectDTO deletedto=new SubjectDTO();
					for(String id:ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Data is deleted successfully", request);
					}
				}else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}else if(OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL,request,response);
				return;
			}else if(OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL,request,response);
				return;
			}
			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

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
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_LIST_VIEW;
	}

}
