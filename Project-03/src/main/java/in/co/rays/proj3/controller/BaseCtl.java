package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.ServletUtility;


/**
 * BaseCtl is parent of all controllers.It contains
 * Generic workflow like service method()
 * Generic operations like preload(),validate(),populateBean()
 * Generic attributes
 * 
 * @author Nidhi
 * @version 1.0
 */
public abstract class BaseCtl extends HttpServlet {
	
	public static final String OP_SAVE="Save";
	public static final String OP_UPDATE="Update";
	public static final String OP_CANCEL="Cancel";
	public static final String OP_DELETE="Delete";
	public static final String OP_LIST="List";
	public static final String OP_SEARCH="Search";
	public static final String OP_VIEW="View";
	public static final String OP_NEXT="Next";
	public static final String OP_PREVIOUS="Previous";
	public static final String OP_NEW="New";
	public static final String OP_GO="Go";
	public static final String OP_BACK="Back";
	public static final String OP_RESET="Reset";
	public static final String OP_LOGOUT="Logout";
	
	public static final String MSG_SUCCESS = "success";
	public static final String MSG_ERROR = "error";
	
	
	/*
	 * Validate input data entered by user
	 */
	protected boolean validate(HttpServletRequest request) {
		return true;
	}
	
	protected BaseDTO populateBean(BaseDTO dto,HttpServletRequest request) {
		System.out.println("populateDTO method in BaseCtl");

    	String createdBy=request.getParameter("createdBy");
    	String modifiedBy=null;

    	// UserDTO userDto=(UserDTO)request.getSession().getAttribute("user");

    	HttpSession session=request.getSession();

    	UserDTO userDto=(UserDTO) session.getAttribute("user");

    	if(userDto==null){
    	createdBy="root";
    	modifiedBy="root";

    	}else{
    	modifiedBy=userDto.getFirstName();
    	if("null".equalsIgnoreCase(createdBy)||DataValidator.isNull(createdBy)){
    	createdBy=modifiedBy;
    	}
    	}
    	dto.setCreatedBy(createdBy);
    	dto.setModifiedBy(modifiedBy);

    	long cdt=DataUtility.getLong(request.getParameter("createdDateTime"));

    	if(cdt>0){
    	dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
    	}else{
    	dto.setCreatedDatetime(DataUtility.getCurrentTimeStamp());
    	}
    	dto.setModifiedDatetime(DataUtility.getCurrentTimeStamp());
    	return dto;
	}
	
	/*Loads the preloaded data at time of HTML Preloading
	 * 
	 */
	protected void preload(HttpServletRequest request){
		
	}
	
	/*
	 * Populates DTO object from request parameters
	 */

	protected BaseDTO populateDTO(HttpServletRequest request) {
		        return null;
		    }
		
	protected void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		try {
			preload(request);
			
			String op=DataUtility.getString(request.getParameter("operation"));
			
			if(DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op) && !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {
				if(!validate(request)) {
					BaseDTO dto=(BaseDTO)populateDTO(request);
					ServletUtility.setDto(dto, request);
					ServletUtility.forward(getView(),request,response);
					return;
				}
			}
			
			 super.service(request, response);
		}catch(DatabaseException e) {
			e.printStackTrace();
			String uri=request.getRequestURI();			
			String finalView = ServletUtility.getViewName(uri);			
			//System.out.println("ViewName in basectl service"+finalView);
			//List list=new ArrayList<>();
			//ServletUtility.setList(list, request);
			//ServletUtility.setPageNo(0, request);
			//ServletUtility.setPageSize(5, request);
	        ServletUtility.setErrorMessage("Database Server Not Working", request);
	        //System.out.println("In catch block of basectl");
	        //System.out.println("URI in basectl service() method: "+result);
	        //ServletUtility.forward(ORSView.LOGIN_VIEW,request, response);
	        ServletUtility.forward(finalView, request, response);
	        return;
		}
		//super.service(request,response);
		
	}
	protected abstract String getView();
}
