package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.BannerDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.model.BannerModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


 /**
 * Banner list functionality controller.
 * Performs operations for list,search,delete operations of Banner
 * 
 * @author Nidhi
 * @version 1.0
 */
@WebServlet(name = "BannerListCtl", urlPatterns = { "/ctl/BannerListCtl" })
public class BannerListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(BannerListCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request) {
		//BannerModel BannerModel = new BannerModel();
		//BannerModelInt BannerModel=ModelFactory.getInstance().getBannerModel();
		//try {
		///BannerList = BannerModel.list();
			//request.setAttribute("BannerList", BannerList);
		//} catch (ApplicationException e) {
			//e.printStackTrace();
		//}
		super.preload(request);
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		BannerDTO dto = new BannerDTO();

		dto.setBannerCode(DataUtility.getString(request.getParameter("bannerCode")));
		dto.setBannerTitle(DataUtility.getString(request.getParameter("bannerTitle")));
		dto.setImagePath(DataUtility.getString(request.getParameter("imagePath")));
		dto.setBannerStatus(DataUtility.getString(request.getParameter("bannerStatus")));

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,DatabaseException {
		
		log.debug("Banner list do get start");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		BannerDTO dto = (BannerDTO) populateDTO(request);
		//BannerModel model = new BannerModel();
		BannerModelInt model=ModelFactory.getInstance().getBannerModel();
		
		try {
			List<BannerDTO> list = model.search(dto, pageNo, pageSize);
			List<BannerDTO> next = model.search(dto, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			//ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize",
			        (next != null) ? next.size() : 0);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DatabaseException {
		log.debug("Banner list do post start");
		
		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		BannerDTO dto = (BannerDTO) populateDTO(request);
		//BannerModel model = new BannerModel();
		BannerModelInt model=ModelFactory.getInstance().getBannerModel();
		
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.BANNER_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					BannerDTO deletedto = new BannerDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Data is deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.BANNER_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.BANNER_LIST_CTL, request, response);
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
			//ServletUtility.setBean(dto, request);
			ServletUtility.setDto(dto, request);
			 request.setAttribute("nextListSize", (next != null) ? next.size() : 0);

			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		return ORSView.BANNER_LIST_VIEW;
	}
}