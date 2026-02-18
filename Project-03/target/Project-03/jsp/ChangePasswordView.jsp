<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.ChangePasswordCtl"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Change Password</title>
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
<!-- Header -->
<%@ include file="Header.jsp" %>

 <!--Bean  -->
 <jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO" scope="request"></jsp:useBean>
 
 <!-- Main Content -->
 <div class="container pt-4">
 	<div class="row">
 		<div class="col-md-3"></div>
 		<div class="col-md-6">
 			<div class="card input-group-addon">
				<div class="card-body">
					<h3 class="text-center text-primary pb-3">Change Password</h3>
 					
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
 						
 					<!-- Form Start -->
 						<form action="<%=ORSView.CHANGE_PASSWORD_CTL %>" method="post">
 							
 							<input type="hidden" name="id" value="<%=dto.getId()%>">
            				<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
            				<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
            				<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
            				<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
            
 							
 							<!-- Old Password -->
 							<label><b>Old Password</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<div class="input-group-text"><i class="fa fa-key" style="font-size: 25px;"></i></div>
 								</div>
 								<input type="text" class="form-control" name="oldPassword" placeholder="Old Password"
 								value="<%=DataUtility.getString(request.getParameter("oldPassword") == null ? "" : DataUtility.getString(request.getParameter("oldPassword")))%>">
 								<!--  <input type="password" class="form-control" name="oldPassword"
 								placeholder="Old Password" value="<%=DataUtility.getString(request.getParameter("oldPassword") == null ? "" : DataUtility.getString(request.getParameter("oldPassword")))%>">-->
 								<%=ServletUtility.getErrorMessage("oldPassword", request)%>	 
 							</div>
 							
 							<!-- New Password -->
 							<label><b>New Password</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<div class="input-group-text"><i class="fa fa-key" style="font-size: 25px;"></i></div>
 								</div>
 								<input type="text" class="form-control" name="newPassword"
 								placeholder="New Password" value="<%=DataUtility.getString(request.getParameter("newPassword") == null ? "" : DataUtility.getString(request.getParameter("newPassword")))%>">
 								<%=ServletUtility.getErrorMessage("newPassword", request)%>	 
 							</div>
 							
 							<!-- Confirm Password -->
 							<label><b>Confirm Password</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<div class="input-group-text"><i class="fa fa-key" style="font-size: 25px;"></i></div>
 								</div>
 								<input type="text" class="form-control" name="confirmPassword"
 								placeholder="Confirm Password" value="<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? "" : DataUtility.getString(request.getParameter("confirmPassword")))%>">
 								<%=ServletUtility.getErrorMessage("confirmPassword", request)%>	 
 							</div>
 							
 							
 							<!-- BUTTONS -->
                            <div class="text-center">
                            
                                <input type="submit" class="btn btn-success" name="operation" value="<%=ChangePasswordCtl.OP_SAVE%>" >
                                <input type="submit" class="btn btn-secondary" name="operation"value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>">
  								                          
                            </div> 							
 							
 						</form>				
 		
				</div> 			
 			</div>
 		</div>
 		<div class="col-md-3"></div>
 	</div>
 </div>
<!-- Footer -->
<%@ include file="Footer.jsp" %>
</body>
</html>