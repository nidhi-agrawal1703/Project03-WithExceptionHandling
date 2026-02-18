package in.co.rays.proj3.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.mapping.Set;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.controller.BaseCtl;
import in.co.rays.proj3.controller.ORSView;
import in.co.rays.proj3.dto.BaseDTO;


/**
 * 
 * ServletUtility class contains utility functions like
 * requestDispatcher,sendRedirect.
 *It also contains functions to implement business validations
 *like setErrorMessage,getErrorMessage
 * It also contains utility functions for 
 * carrying data in request like setBean,setList,setPageNo,setPageSize.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class ServletUtility {

	public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.sendRedirect(page);
	}

	public static String getErrorMessage(String property, HttpServletRequest request) {

		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}
	
	public static String getViewName(String uri) {
		
		String path1=uri;
		String className=path1.substring(path1.lastIndexOf("/")+1);
		String shortName = className.replace("Ctl", "");
		String finalView = "/jsp/" + shortName + "View.jsp";
		return finalView;
	}
	
	public static String getMessage(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static void setErrorMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_ERROR, msg);
	}

	public static String getErrorMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
		//System.out.println("mmmmmmmmsssssssssssssssggggggggggg = " + val);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
		//System.out.println("Success message in setSuccess="+msg);
	}

	public static String getSuccessMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
		System.out.println("mmmmmmmmsssssssssssssssggggggggggg = " + val);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	
	public static void setList(List list,HttpServletRequest request) {
		request.setAttribute("list", list);
	}

	public static List getList(HttpServletRequest request) {
		List list=(List) request.getAttribute("list");
		if(list==null) {
			list=new ArrayList<>();
		}
		return list;
	}

	public static String getParameter(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}

	}

	public static void setPageNo(int pageNo, HttpServletRequest request) {
		
		request.setAttribute("pageNo", pageNo);
	}

	public static int getPageNo(HttpServletRequest request) {
		Integer pageNo;
		pageNo=(Integer) request.getAttribute("pageNo");
		if(pageNo==null) {
			pageNo=1;
		}
		return pageNo;
	}

	public static void setPageSize(int pageSize, HttpServletRequest request) {
		request.setAttribute("pageSize", pageSize);
	}

	public static int getPageSize(HttpServletRequest request) {
		Integer pageSize;
		pageSize=(Integer) request.getAttribute("pageSize");
		if(pageSize==null) {
			pageSize=5;
		}
		return pageSize;
	}

	public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("exception", e);
		response.sendRedirect(ORSView.ERROR_CTL);
	}
	
	public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response,String className,BaseDTO dto)
			throws IOException, ServletException {
		
		
		String recordErrorMessage="Not Found";
		String duplicateErrorMessage="Already Exists";
				
		if(e instanceof DuplicateRecordException) {
			duplicateErrorMessage=className+" "+duplicateErrorMessage;
			setErrorMessage(duplicateErrorMessage, request);
			if(dto!=null) {
				setDto(dto, request);
			}
		}
		if(e instanceof RecordNotFoundException) {
			recordErrorMessage=className+" "+recordErrorMessage;
			if(dto!=null) {
				setDto(dto, request);
			}
			setErrorMessage(recordErrorMessage,request);
		}
		
		request.setAttribute("exception", e);
		//forward(viewName, request, response);
	}
	/**
     * Sets default DTO to request
     *
     * @param dto
     * @param request
     */
    public static void setDto(BaseDTO dto, HttpServletRequest request) {
        request.setAttribute("dto", dto);
    }

    /**
     * Gets default DTO from request
     *
     * @param request
     * @return
     */

    public static BaseDTO getDto(HttpServletRequest request) {
        return (BaseDTO) request.getAttribute("dto");
    }
}
