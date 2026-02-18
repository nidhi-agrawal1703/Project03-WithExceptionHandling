package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CouponDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CouponModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

@WebServlet(name="CouponCtl",urlPatterns= {"/ctl/CouponCtl"})
public class CouponCtl extends BaseCtl {
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean pass=true;
		
		if (DataValidator.isNull(request.getParameter("offerCode"))) {
			request.setAttribute("offerCode", PropertyReader.getValue("error.require", "Offer Code"));
			pass = false;
		} 
		
		if (DataValidator.isNull(request.getParameter("discountAmount"))) {
			request.setAttribute("discountAmount", PropertyReader.getValue("error.require", "Discount Amount"));
			pass = false;
		} 
		
		if (DataValidator.isNull(request.getParameter("expiryDate"))) {
			request.setAttribute("expiryDate", PropertyReader.getValue("error.require", "Expiry Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("expiryDate"))) {
			request.setAttribute("expiryDate", "Invalid Expiry Date");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("offerStatus"))) {
			request.setAttribute("offerStatus", PropertyReader.getValue("error.require", "Offer Status"));
			pass = false;
		} 
				
		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CouponDTO dto = new CouponDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setOfferCode(DataUtility.getString(request.getParameter("offerCode")));
		dto.setDiscountAmount(DataUtility.getBigDecimal(request.getParameter("discountAmount")));
		dto.setExpiryDate(DataUtility.getDate(request.getParameter("expiryDate")));
		System.out.println("Expiry date in populateDTO of CouponCtl: "+request.getParameter("expiryDate"));
		dto.setOfferStatus(DataUtility.getString(request.getParameter("offerStatus")));
		
		populateBean(dto, request);

		return dto;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long id = DataUtility.getLong(request.getParameter("id"));

		CouponModelInt model=ModelFactory.getInstance().getCouponModel();
		//CourseModel model = new CourseModel();

		if (id > 0) {
			try {
				CouponDTO dto=model.findByPK(id);
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
		
		CouponModelInt model=ModelFactory.getInstance().getCouponModel();
		//CourseModel model = new CourseModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			CouponDTO dto = (CouponDTO) populateDTO(request);
			try {
				long pk = model.add(dto);
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Coupon added successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Coupon already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}else if(OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COUPON_CTL, request, response);
			return;
		}else if(OP_UPDATE.equalsIgnoreCase(op)) {
			CouponDTO dto = (CouponDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
				}
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Coupon updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Coupon already exists", request);
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
		return ORSView.COUPON_VIEW;
	}

}
