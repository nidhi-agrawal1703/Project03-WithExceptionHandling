<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.CustomerCtl"%>
<%@page import="in.co.rays.proj3.dto.CustomerDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Customer</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
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
	
<!-- Bean Tag -->
	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.CustomerDTO" scope="request"></jsp:useBean>
	
	
<!-- Main Content -->
	<div class="container pt-4">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="card input-group-addon">
					<div class="card-body">
						<%
							long id = DataUtility.getLong(request.getParameter("id"));
						
							if(dto.getId()!=null){						
						%>				
						<h3 class="text-center text-primary pb-3">Update Customer</h3>
						<%}else { %>
						<h3 class="text-center text-primary pb-3">Add Customer</h3>
						<%} %>
						
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
						<form action="<%=ORSView.CUSTOMER_CTL %>" method="post">
						
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
 							
						
 						<!-- Location -->
 							<label><b>Location</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa-location-arrow" style="font-size: 25px;"></i></span>
 									
 								</div>
 								
 								<input type="text" class="form-control" name="location" placeholder="Location" 
 								value="<%=DataUtility.getStringData(dto.getLocation())%>">
 								<%=ServletUtility.getErrorMessage("location", request)%>	 
 							</div>
							
							<!-- Mobile No -->
 							<label><b>Mobile No</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-list" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="mobileNo" placeholder="Mobile No"
 								value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
 								<%=ServletUtility.getErrorMessage("mobileNo", request)%>	 
 							</div>
 							
 					
 							<!-- Importance -->
 							<label><b>Importance</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-clock" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<%
											HashMap map1 = new HashMap();
											map1.put("High", "High");
											map1.put("Medium", "Medium");
											map1.put("Low", "Low");
											String htmlList1 = HTMLUtility.getList("importance", dto.getImportance(), map1);
										%>
										<%=htmlList1%>

 								<%=ServletUtility.getErrorMessage("importance", request)%>	 
 							</div>
 							
 												
								
 							
							<input type="hidden" name="id" value="<%=dto.getId()%>">
            				<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
            				<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
            				<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
           					 <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">					
						
						<!-- Buttons -->
						<%
                        if (dto.getId() !=null) {
                    %>
                    <div class="text-center">                            
                                <input type="submit" class="btn btn-success" name="operation" value="<%=CustomerCtl.OP_UPDATE%>" >
                                <input type="submit" class="btn btn-secondary" name="operation"value="<%=CustomerCtl.OP_CANCEL%>"><br>  								                          
                            </div> 							
						
                    <%
                        } else {
                    %>
                    <div class="text-center">                            
                                <input type="submit" class="btn btn-success" name="operation" value="<%=CustomerCtl.OP_SAVE%>" >
                                <input type="submit" class="btn btn-secondary" name="operation"value="<%=CustomerCtl.OP_RESET%>"><br>  								                          
                            </div> 							
						
                    <%
                        }
                    %>						
						</form>							
					</div>
				</div>
				
			</div>
			<div class="col-md-3"></div>	
		</div>
	
	</div>
	
	
	<!-- Footer -->
	<%@ include file="Footer.jsp"%>
</body>
</html>