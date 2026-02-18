<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.CollegeCtl"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>Add College</title>
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
	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.CollegeDTO" scope="request"></jsp:useBean>
	
<!-- Main Content -->
	<div class="container pt-4">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="card input-group-addon">
					<div class="card-body">
						<%
							if(dto!=null && dto.getId()>0){						
						%>				
						<h3 class="text-center text-primary pb-3">Update College</h3>
						<%}else { %>
						<h3 class="text-center text-primary pb-3">Add College</h3>
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
						<form action="<%=ORSView.COLLEGE_CTL %>" method="post">
						<!-- Name -->
 							<label><b>Name</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-user-alt" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="name"
 									 placeholder="College Name" value="<%=DataUtility.getStringData(dto.getName())%>">
 								<%=ServletUtility.getErrorMessage("name", request)%>	 
 							</div>
						<!-- Address -->
 							<label><b>Address</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fas fa-map-marker-alt" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="address"
 									 placeholder="College Address" value="<%=DataUtility.getStringData(dto.getAddress())%>">
 								<%=ServletUtility.getErrorMessage("address", request)%>	 
 							</div>
 							<!-- State -->
 							<label><b>State</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fas fa-street-view" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="state"
 									 placeholder="College State" value="<%=DataUtility.getStringData(dto.getState())%>">
 								<%=ServletUtility.getErrorMessage("state", request)%>	 
 							</div>
						
							<!-- City -->
 							<label><b>City</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fas fa-street-view" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="city"
 									 placeholder="College City" value="<%=DataUtility.getStringData(dto.getCity())%>">
 								<%=ServletUtility.getErrorMessage("city", request)%>	 
 							</div>
 							
 							<!-- Phone No -->
 							<label><b>Phone No</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fas fa-phone" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="phoneNo"
 									 placeholder="Phone No" value="<%=DataUtility.getStringData(dto.getPhoneNo())%>">
 								<%=ServletUtility.getErrorMessage("phoneNo", request)%>	 
 							</div>
						
							<input type="hidden" name="id" value="<%=dto.getId()%>">
            				<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
            				<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
            				<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
           					 <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">					
						
						<!-- Buttons -->
						<%
                        if (dto != null && dto.getId() > 0) {
                    %>
                    <div class="text-center">                            
                                <button class="btn btn-success" name="operation" value="<%=CollegeCtl.OP_UPDATE%>" >Update</button>
                                <button class="btn btn-secondary" name="operation"value="<%=CollegeCtl.OP_CANCEL%>">Cancel</button><br>  								                          
                            </div> 							
						
                    <%
                        } else {
                    %>
                    <div class="text-center">                            
                                <button class="btn btn-success" name="operation" value="<%=CollegeCtl.OP_SAVE%>" >Save</button>
                                <button class="btn btn-secondary" name="operation"value="<%=CollegeCtl.OP_RESET%>">Reset</button><br>  								                          
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