<%@page import="in.co.rays.proj3.dto.CollegeDTO"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.RoleCtl"%>
<%@page import="in.co.rays.proj3.dto.RoleDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="in.co.rays.proj3.controller.StudentCtl"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Role</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
<style>
	.bgColor {
            background-color: white;
            padding-top: 120px;
            /* Because header is fixed */
            padding-bottom: 80px;
            /* Because footer is fixed */
        }

        .input-group-addon {
            box-shadow: 9px 8px 7px #001a33;
        }
	
h1,h2,h3{
		color:#004e92;
	}
	
</style>

</head>
<body class="bgColor">

<!-- Header File -->
<%@ include file="Header.jsp" %>

<!-- JSP UseBean Tag -->
<jsp:useBean id="dto" class="in.co.rays.proj3.dto.RoleDTO" scope="request"></jsp:useBean>	

<!-- Main content -->
<div class="container pt-4">
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<div class="card input-group-addon">
				<div class="card-body">
					<%
							Long id=DataUtility.getLong(request.getParameter("id"));
							if(dto.getId()!=null){						
						%>				
						<h3 class="text-center text-primary pb-3">Update Role</h3>
						<%}else { %>
						<h3 class="text-center text-primary pb-3">Add Role</h3>
						<%} %>
						
						<!-- Static Success Message -->
 						<%if(!ServletUtility.getSuccessMessage(request).equals("")){ %>
 						<div class="alert alert-success alert-dismissable">
 						<button type="button" class="close" data-dismiss="alert">&times;</button>
                            <h4><%=ServletUtility.getSuccessMessage(request)%></h4>
 						</div>
 						<%} %>
 						
 						
 						<!-- Static Error Message -->
 						<%if(!ServletUtility.getErrorMessage(request).equals("")) {%>
 						<div class="alert alert-danger alert-dismissable">
 						<button type="button" class="close" data-dismiss="alert">&times;</button>
                            <h4><%=ServletUtility.getErrorMessage(request)%></h4>
 						</div>	
 						<% }%>
						
						<!-- Form Start -->
						<form action="<%=ORSView.ROLE_CTL %>" method="post">
						
						<!-- Name -->
 							<label><b>Name</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-user-alt" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="name"
 									 placeholder="Name" value="<%=DataUtility.getStringData(dto.getName())%>">
 								<%=ServletUtility.getErrorMessage("name", request)%>	 
 							</div>
 							<!-- Description -->
 							<label><b>Description</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-user-alt" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="description"
 									 placeholder="Description" value="<%=DataUtility.getStringData(dto.getDescription())%>">
 								<%=ServletUtility.getErrorMessage("description", request)%>	 
 							</div>
 								
						<!-- Hidden Fields -->
							<input type="hidden" name="id" value="<%=dto.getId()%>">
            				<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
            				<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
            				<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
           					 <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">					
						
						<!-- Buttons -->
						<%
						if(id>0){
						%>
						<div class="text-center">
							<input type="submit" class="btn btn-success" name="operation" value="<%=RoleCtl.OP_UPDATE%>">
							<input type="submit" class="btn btn-secondary" name="operation" value="<%=RoleCtl.OP_CANCEL%>">
						</div>
						<% }else{%>
						<div class="text-center">
							<input type="submit" class="btn btn-success" name="operation" value="<%=RoleCtl.OP_SAVE%>">
							<input type="submit" class="btn btn-secondary" name="operation" value="<%=RoleCtl.OP_RESET%>">
						</div>
						<%} %>
						
						</form>
					
				</div>
			</div>
		
		</div>
		<div class="col-md-3"></div>
	</div>
</div>
	
<!-- Footer File -->
<%@ include file="Footer.jsp" %>	
	
</body>
</html>