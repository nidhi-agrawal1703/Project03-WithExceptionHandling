package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
	
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.MarksheetDTO;
import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.model.MarksheetModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


/**
 *
 *Marksheet merit list functionality controller.
 *Performance operation of Marksheet merit list
 *
 * @author Nidhi
 * @version 1.0
 */
@WebServlet(name="MarksheetMeritListCtl",urlPatterns= {"/ctl/MarksheetMeritListCtl"})
public class MarksheetMeritListCtl extends BaseCtl {
	
	protected BaseDTO populateDTO(HttpServletRequest request) {
		//log.debug("Marksheet merit list populate bean start");
		MarksheetDTO dto = new MarksheetDTO();
		return dto;
		}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List list;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
		MarksheetModelInt model =ModelFactory.getInstance().getMarksheetModel();
		try {
			list = model.getMeritList(pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) 
		{
			//log.error(e);
			ServletUtility.handleException(e, request, response);
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String op=DataUtility.getString(request.getParameter("operation"));
		if(OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.WELCOME_CTL,request,response);
			return;
		}
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}

}
