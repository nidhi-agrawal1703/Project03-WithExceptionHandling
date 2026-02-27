<%@page import="in.co.rays.proj3.controller.ORSView"%>	
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.UserListCtl"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="in.co.rays.proj3.model.RoleModelInt" %>
<%@page import="in.co.rays.proj3.model.ModelFactory" %>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.StudentDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList" %>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>User List</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
 <style>
        body {
            
            padding-top: 90px;
        }

        .search-row {
            width: 90%;
            /* SEARCH WIDTH BADHA DIYA */
            margin: auto;
            margin-bottom: 20px;
        }
    </style>


</head>
<body>
<!-- Header File -->
<%@include file="Header.jsp"%>

<!-- JSP UseBean Tag -->
<jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO" ></jsp:useBean>


<!-- Main Content -->

<div align="center" class="container-fluid">
<h1 class="text-dark font-weight-bold pb-3"><u>User List</u></h1>

						<!-- Static Success Message -->
 						<%if(ServletUtility.getSuccessMessage(request).length()>0){ %>
 						<div class="alert alert-success alert-dismissable">
 						<button type="button" class="close" data-dismiss="alert">&times;</button>
                            <h4><%=ServletUtility.getSuccessMessage(request)%></h4>
 						</div>
 						<%} %>
 						
 						
 						<!-- Static Error Message -->
 						<%if(ServletUtility.getErrorMessage(request).length()>0) {%>
 						<div class="alert alert-danger alert-dismissable">
 						<button type="button" class="close" data-dismiss="alert">&times;</button>
                            <h4><%=ServletUtility.getErrorMessage(request)%></h4>
 						</div>
 						<% }%>
 <!-- Form Tag -->
 <form action="<%=ORSView.USER_LIST_CTL %>" method="post">
 
 <%
				List list1 = (List) request.getAttribute("roleList");
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int nextPageSize=0;
				int index = ((pageNo - 1) * pageSize) + 1;
				if(request.getAttribute("nextListSize")!=null){					
				
				nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				}
				RoleDTO rbean1 = new RoleDTO();
				RoleModelInt rmodel = ModelFactory.getInstance().getRoleModel();

				List list = ServletUtility.getList(request);
				 //if (list == null) {list = new ArrayList<>();}
				Iterator<UserDTO> it = list.iterator();
				if (list.size() != 0) {
			%>					
<!-- Search Form -->
<div class="row search-row">
	<div class="col-md-3">
		<input type="text" class="form-control" name="firstName" placeholder="First Name" value="<%=ServletUtility.getParameter("firstName", request)%>">
	</div>
	<div class="col-md-3">
		<input type="text" class="form-control" name="login" placeholder="Login Id" value="<%=ServletUtility.getParameter("login", request)%>">
	</div>
	<%if (list1!=null){ %>
	<div class="col-md-3"><%=HTMLUtility.getList("roleId", String.valueOf(dto.getRoleId()), list1)%></div>
	<%} %>
	<div class="col-md-3">
		<input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=UserListCtl.OP_SEARCH%>">
        <input type="submit" class="btn btn-secondary btn-md" name="operation" value="<%=UserListCtl.OP_RESET%>">
	</div>
</div>
<!-- Hidden Fields -->
		<input type="hidden" name="pageNo" value="<%=pageNo%>"> 
		<input type="hidden" name="pageSize" value="<%=pageSize%>">
	
<!-- Table -->
<div>
	<table class="table table-bordered table-light table-hover">
		<thead>
                    <tr align="center" style="background:#8C8C8C;">
                        <th>Select<br><input type="checkbox" id="selectall" /></th>
                        <th>S.No</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Login ID</th>
                        <th>Gender</th>
                        <th>Role</th>
                        <th>DOB</th>
                        <th>Edit</th>
                    </tr>
                </thead>
	<%
						while (it.hasNext()) {
								dto = it.next();

								RoleDTO rbean = rmodel.findByPK(dto.getRoleId());
					%>
		<tbody align="center">
                    <tr>
                        <td><input type="checkbox" class="case" name="ids" value="<%=dto.getId()%>" <%if(dto.getRoleId()==RoleDTO.ADMIN){ %><%="disabled" %><%} %>></td>
                        <td><%=index++ %></td>
                        <td><%=dto.getFirstName() %></td>
                        <td><%=dto.getLastName() %></td>
                        <td><%=dto.getLogin() %></td>
                        <td><%=dto.getGender() %></td>
                        <td><%=rbean.getName() %></td>
                        <td><%=DataUtility.getDateString(dto.getDob()) %></td>
                        <td><a href="UserCtl?id=<%=dto.getId()%>" <%if(dto.getRoleId()==RoleDTO.ADMIN){ %>onclick="return false;"<%} %>>Edit</a></td>
                    </tr>

                    
                </tbody>
			<%
						}//Closing iterator while loop
					%>
	
	</table>

</div>

<!-- Search Button -->
<div class="row mt-3" style="width: 95%; margin:auto;">
            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=UserListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
            </div>

            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=UserListCtl.OP_NEW%>">
            </div>

            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=UserListCtl.OP_DELETE%>">
            </div>

            <div class="col-md-3 text-right">
                <input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=UserListCtl.OP_NEXT%>" <%=nextPageSize != 0 ? "" : "disabled"%>>
            </div>
        </div>
	
</div>
<%
				}
				if (list.size() == 0) {
			%>
			<div style="padding-left: 48%;">
				<input type="submit" name="operation" class="btn btn-primary btn-md"
					style="font-size: 17px" value="<%=UserListCtl.OP_BACK%>">
			</div>
			<%} %>
<!-- End of Main Content -->
<!-- End of Form Tag -->
	</form>	
<!-- Footer File -->
<%@include file="Footer.jsp"%>
</body>
</html>