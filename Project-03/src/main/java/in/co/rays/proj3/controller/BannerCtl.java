package in.co.rays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.BannerDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.PrimaryKeyNotFoundException;
import in.co.rays.proj3.model.BannerModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


/**
 * Banner functionality controller.Performs crud operations.
 *
 * @author Nidhi
 * @version 1.0
 *
 */
@WebServlet(name = "BannerCtl", urlPatterns = "/ctl/BannerCtl")
public class BannerCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(BannerCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.preload(request);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("bannerCode"))) {
			request.setAttribute("bannerCode", PropertyReader.getValue("error.require", "Banner Code"));
			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("bannerTitle"))) {
			request.setAttribute("bannerTitle", PropertyReader.getValue("error.require", "Banner Title"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("imagePath"))) {
			request.setAttribute("imagePath", PropertyReader.getValue("error.require", "Image Path"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("bannerStatus"))) {
			request.setAttribute("bannerStatus", PropertyReader.getValue("error.require", "Banner Status"));
			pass = false;
		}
		
		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		BannerDTO dto = new BannerDTO();
		//System.out.println(request.getParameter("mobileNo"));
		
		dto.setBannerCode(request.getParameter("bannerCode"));
		//System.out.println(request.getParameter("name"));
		//System.out.println(request.getParameter("city"));
		//System.out.println(request.getParameter("address"));
		//System.out.println(request.getParameter("state"));
		//System.out.println(request.getParameter("mobileNo"));
		dto.setBannerTitle(request.getParameter("bannerTitle"));
		dto.setImagePath(request.getParameter("imagePath"));
		dto.setBannerStatus(request.getParameter("bannerStatus"));
		
		populateBean(dto,request);
		return dto;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,DatabaseException {
		// TODO Auto-generated method stub
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		
		BannerModelInt model=ModelFactory.getInstance().getBannerModel();
		BannerDTO dto=null;
		if (id > 0) {
			
			try {
				dto=model.findByPK(id);
				ServletUtility.setDto(dto, request);;
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}catch(PrimaryKeyNotFoundException e) {
				ServletUtility.handleException(e, request, response, "Banner", dto);
				
			}
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,DatabaseException {
		// TODO Auto-generated method stub
		String op = DataUtility.getString(request.getParameter("operation"));

		BannerModelInt model=ModelFactory.getInstance().getBannerModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
			BannerDTO dto = (BannerDTO) populateDTO(request);
			try {
				if(id>0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Record Successfully Updated", request);
				}else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Record Successfully Saved", request);
				}
				ServletUtility.setDto(dto, request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Banner Already Exists", request);
			} catch (ApplicationException e2) {
				e2.printStackTrace();
				log.error(e2);
				ServletUtility.handleException(e2, request, response);
			} 
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(getView(), request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.BANNER_VIEW;
	}

}
