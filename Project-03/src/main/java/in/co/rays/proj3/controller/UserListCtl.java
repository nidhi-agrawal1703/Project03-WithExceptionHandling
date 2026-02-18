package in.co.rays.proj3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.RoleModelInt;
import in.co.rays.proj3.model.UserModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;


/**
 * User List functionality controller.
 * Performs list,search operations for UserBean .
 * 
 * @author Nidhi
 * @version 1.0
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })

public class UserListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request)throws DatabaseException {
		RoleModelInt roleModel=ModelFactory.getInstance().getRoleModel();		
		try {
			List roleList = roleModel.list();
			if(roleList==null) {
				roleList=new ArrayList<>();
			}
			request.setAttribute("roleList", roleList);
		}catch(HibernateException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to load roles", e); 
		}
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		//System.out.println("login3 : " + request.getParameter("login"));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		//System.out.println("login : " + bean.getLogin());
		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,DatabaseException {
		// TODO Auto-generated method stub
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		UserDTO dto = (UserDTO) populateDTO(request);
		UserModelInt model=ModelFactory.getInstance().getUserModel();
		
		try {
			List<UserDTO> list = model.search(dto, pageNo, pageSize);
			List<UserDTO> next = model.search(dto, pageNo + 1, pageSize);

			if (list == null) {
			    list = new ArrayList<UserDTO>();
			}

			if (list.isEmpty()) {
			    ServletUtility.setErrorMessage("No record found", request);
			}
			//if (list == null || list.isEmpty()) {
				//ServletUtility.setErrorMessage("No record found", request);
			//}
			ServletUtility.setList(list, request);
			ServletUtility.setDto(dto, request);
			//ServletUtility.setBean(bean, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			
			request.setAttribute("nextListSize",
			        (next != null) ? next.size() : 0);

			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,DatabaseException {
		// TODO Auto-generated method stub
		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		UserDTO dto = (UserDTO) populateDTO(request);
		UserModelInt model=ModelFactory.getInstance().getUserModel();
		

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if ("Next".equalsIgnoreCase(op)) {
					pageNo++;
				} else if ("Previous".equalsIgnoreCase(op)) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					UserDTO dtodelete=new UserDTO();
					//UserBean deleteBean = new UserBean();
					for (String id : ids) {
						dtodelete.setId(DataUtility.getLong(id));
						model.delete(dtodelete);
						ServletUtility.setSuccessMessage("User Deleted Successfully", request);
					}
				} else {

					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			}
			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record found", request);
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
			//if(e instanceof JDBCConnectionException) {
				//ServletUtility.setErrorMessage("Database Server Down", request);
				//ServletUtility.setList(new ArrayList<>(), request);
		        //ServletUtility.setDto(dto, request);
		        //request.setAttribute("pageNo", pageNo);
				//request.setAttribute("pageSize", pageSize);
				//request.setAttribute("nextListSize", 0);
		        //forward(page, request, response);
				//ServletUtility.forward(getView(), request, response);
			//}
			
			//ServletUtility.handleException(e, request, response);
			//return;
		}

//		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_LIST_VIEW;
	}

}
