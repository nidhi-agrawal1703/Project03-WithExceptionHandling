<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.CompanyCtl"%>
<%@page import="in.co.rays.proj3.dto.CompanyDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>Add Company</title>
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
	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.CompanyDTO" scope="request"></jsp:useBean>
	
	
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
						<h3 class="text-center text-primary pb-3">Update Company</h3>
						<%}else { %>
						<h3 class="text-center text-primary pb-3">Add Company</h3>
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
						<form action="<%=ORSView.COMPANY_CTL %>" method="post">
						
						<!-- Name -->
 							<label><b>Name</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-user-alt" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="name"
 									 placeholder="name" value="<%=DataUtility.getStringData(dto.getName())%>">
 								</div>
 								<font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font><br>	 
 							
 							
						
 							<!-- Industry Type -->
 							<label><b>Industry Type</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-clock" style="font-size: 25px;"></i></span>
 									
 								</div>
 								
 								<input type="text" class="form-control" name="industryType" placeholder="Industry Type" 
 								value="<%=DataUtility.getStringData(dto.getIndustryType())%>">
 								</div>
 								<font color="red"><%=ServletUtility.getErrorMessage("industryType", request)%></font><br>	 
 							
							<!-- Employee Count -->
 							<label><b>Employee Count</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-list" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="employeeCount" placeholder="Employee Count"
 								value="<%=DataUtility.getStringData(dto.getEmployeeCount())%>">
 								<%=ServletUtility.getErrorMessage("employeeCount", request)%>	 
 							</div>
 					
							
							<!-- Opening Date -->
 							<label><b>Opening Date</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-clock" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" id="udatee" readonly="readonly" class="form-control" name="openingDate" placeholder="Opening Date" 
 								value="<%=DataUtility.getStringData(dto.getOpeningDate())%>">
 								<%=ServletUtility.getErrorMessage("openingDate", request)%>	 
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
                                <input type="submit" class="btn btn-success" name="operation" value="<%=CompanyCtl.OP_UPDATE%>" >
                                <input type="submit" class="btn btn-secondary" name="operation"value="<%=CompanyCtl.OP_CANCEL%>"><br>  								                          
                            </div> 							
						
                    <%
                        } else {
                    %>
                    <div class="text-center">                            
                                <input type="submit" class="btn btn-success" name="operation" value="<%=CompanyCtl.OP_SAVE%>" >
                                <input type="submit" class="btn btn-secondary" name="operation"value="<%=CompanyCtl.OP_RESET%>"><br>  								                          
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