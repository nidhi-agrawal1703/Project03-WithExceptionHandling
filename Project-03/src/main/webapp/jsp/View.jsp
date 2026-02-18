<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.LoginCtl"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.UserDTO"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
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
					<h3 class="text-center text-primary pb-3">Login</h3>
 					
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
 						<form action="<%=ORSView.LOGIN_CTL%>" method="post">
 							
 							<!-- Login Id -->
 							<label><b>Login Id</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-user-alt" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="login"
 									 placeholder="Login Id" value="<%=DataUtility.getStringData(dto.getLogin())%>">
 								<%=ServletUtility.getErrorMessage("login", request)%>	 
 							</div>
 							
 							<!-- Password -->
 							<label><b>Password</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<div class="input-group-text"><i class="fa fa-key" style="font-size: 25px;"></i></div>
 								</div>
 								<input type="password" class="form-control" name="password"
 								placeholder="Password" value="<%=DataUtility.getStringData(dto.getPassword())%>">
 								<%=ServletUtility.getErrorMessage("password", request)%>	 
 							</div>
 							
 							<!-- BUTTONS -->
                            <div class="text-center">
                            
                                <button class="btn btn-success" name="operation" value="<%=LoginCtl.OP_SIGN_IN%>" >Login</button>
                                <button class="btn btn-secondary" name="operation"value="<%=LoginCtl.OP_SIGN_UP%>">Sign Up</button><br>
  								<a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forgot password?</b></a>                          
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