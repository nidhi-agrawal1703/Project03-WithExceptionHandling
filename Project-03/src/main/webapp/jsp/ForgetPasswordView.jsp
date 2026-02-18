<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.ForgetPasswordCtl"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>Forget Password</title>
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
					<h3 class="text-center text-primary pb-3">Forget Password</h3>
 					
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
 						<form action="<%=ORSView.FORGET_PASSWORD_CTL %>" method="post">
 							
 							<input type="hidden" name="id" value="<%=dto.getId()%>">
            
           					 <h3 style="margin-bottom: -10;">
                				<label>Submit your email address and we'll send you password.</label>
           					 </h3>
             							
 								
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
 							
 							
 							<!-- BUTTONS -->
                            <div class="text-center">
                            
                                <input type="submit" class="btn btn-success" name="operation" value="<%=ForgetPasswordCtl.OP_GO%>" >
                                                          
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