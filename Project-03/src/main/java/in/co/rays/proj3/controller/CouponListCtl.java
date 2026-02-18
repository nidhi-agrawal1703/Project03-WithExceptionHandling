package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CouponDTO;
import in.co.rays.proj3.model.CouponModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 *Coupon list functionality controller.
 * Performs operations for list,search,delete operations of Coupon
 * 
 * @author Nidhi
 * @version 1.0
 *
 */

@WebServlet(name="CouponListCtl",urlPatterns= {"/ctl/CouponListCtl"})
public class CouponListCtl extends BaseCtl {
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CouponDTO dto = new CouponDTO();

		//dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setOfferCode(DataUtility.getString(request.getParameter("offerCode")));
		dto.setDiscountAmount(DataUtility.getBigDecimal(request.getParameter("discountAmount")));
		dto.setExpiryDate(DataUtility.getDate(request.getParameter("expiryDate")));
		dto.setOfferStatus(DataUtility.getString(request.getParameter("offerStatus")));

		return dto;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		CouponDTO dto=(CouponDTO)populateDTO(request);
		
		CouponModelInt model=ModelFactory.getInstance().getCouponModel();
		//CourseModel model=new CourseModel();
		try {
			List<CouponDTO> list=model.search(dto, pageNo, pageSize);
			List<CouponDTO> next=model.search(dto, pageNo+1, pageSize);
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
		
		CouponDTO dto=(CouponDTO)populateDTO(request);
		CouponModelInt model=ModelFactory.getInstance().getCouponModel();
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
				ServletUtility.redirect(ORSView.COUPON_CTL, request, response);
				return;
			}else if(OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=1;
				if(ids!=null && ids.length>0) {
					CouponDTO deletedto=new CouponDTO();
					for(String id:ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Coupon Deleted successfully", request);
					}
				}else {
					ServletUtility.setErrorMessage("Select atleast one record", request);
				}
			}else if(OP_RESET.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.COUPON_LIST_CTL, request, response);
				return;
			}else if(OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COUPON_LIST_CTL, request, response);
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
		return ORSView.COUPON_LIST_VIEW;
	}

}
