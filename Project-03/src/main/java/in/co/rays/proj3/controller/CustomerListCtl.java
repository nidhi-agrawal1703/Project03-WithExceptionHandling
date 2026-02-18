package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CustomerDTO;
import in.co.rays.proj3.model.CustomerModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 *Course list functionality controller.
 * Performs operations for list,search,delete operations of customer
 * 
 * @author Nidhi
 * @version 1.0
 *
 */
@WebServlet(name="CustomerListCtl",urlPatterns= {"/ctl/CustomerListCtl"})
public class CustomerListCtl extends BaseCtl {
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CustomerDTO dto = new CustomerDTO();

		//dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setLocation(DataUtility.getString(request.getParameter("location")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		dto.setImportance(DataUtility.getString(request.getParameter("importance")));

		return dto;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		CustomerDTO dto=(CustomerDTO)populateDTO(request);
		
		CustomerModelInt model=ModelFactory.getInstance().getCustomerModel();
		//CourseModel model=new CourseModel();
		try {
			List<CustomerDTO> list=model.search(dto, pageNo, pageSize);
			List<CustomerDTO> next=model.search(dto, pageNo+1, pageSize);
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
		
		CustomerDTO dto=(CustomerDTO)populateDTO(request);
		CustomerModelInt model=ModelFactory.getInstance().getCustomerModel();
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
				ServletUtility.redirect(ORSView.CUSTOMER_CTL, request, response);
				return;
			}else if(OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=1;
				if(ids!=null && ids.length>0) {
					CustomerDTO deletedto=new CustomerDTO();
					for(String id:ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Customer Deleted successfully", request);
					}
				}else {
					ServletUtility.setErrorMessage("Select atleast one record", request);
				}
			}else if(OP_RESET.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.CUSTOMER_LIST_CTL, request, response);
				return;
			}else if(OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.CUSTOMER_LIST_CTL, request, response);
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
		return ORSView.CUSTOMER_LIST_VIEW;
	}

}
