<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.MarksheetCtl"%>
<%@page import="in.co.rays.proj3.dto.StudentDTO" %>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>Add Marksheet</title>
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
	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.MarksheetDTO" scope="request"></jsp:useBean>

<!-- Main Content -->
	<div class="container pt-4">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="card input-group-addon">
					<div class="card-body">
					
						<!-- Preload Data -->
	<%
				List l = (List) request.getAttribute("studentList");
				System.out.println("Student list in view"+l);			
	%>
						
						<%
							long id = DataUtility.getLong(request.getParameter("id"));
						
							if(dto.getId()!=null){						
						%>				
						<h3 class="text-center text-primary pb-3">Update Marksheet</h3>
						<%}else { %>
						<h3 class="text-center text-primary pb-3">Add Marksheet</h3>
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
						<form action="<%=ORSView.MARKSHEET_CTL %>" method="post">
							
						
	
							
							
													
						<!-- RollNo -->
 							<label><b>RollNo</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-id-card" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="rollNo"
 									 placeholder="RollNo" value="<%=DataUtility.getStringData(dto.getRollNo())%>" 
 									 <%=(dto.getId() !=null) ? "readonly" : ""%>>
 								<%=ServletUtility.getErrorMessage("rollNo", request)%>	 
 							</div>
 							
						<!-- Name -->
 							<label><b>Name</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fas fa-user-alt" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<%=HTMLUtility.getList("studentId", String.valueOf(dto.getStudentId()), l)%>
 								<%=ServletUtility.getErrorMessage("studentId", request)%>	 
 							</div>
 						
 						<!-- Physics -->
 							<label><b>Physics</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-percent" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="physics"
 									 placeholder="Physics" value="<%=DataUtility.getStringData(dto.getPhysics())%>">
 								<%=ServletUtility.getErrorMessage("physics", request)%>	 
 							</div>
							
							<!-- Chemistry -->
 							<label><b>Chemistry</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-percent" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="chemistry"
 									 placeholder="Chemistry" value="<%=DataUtility.getStringData(dto.getChemistry())%>">
 								<%=ServletUtility.getErrorMessage("chemistry", request)%>	 
 							</div>
							
							<!-- Maths -->
 							<label><b>Maths</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-percent" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" class="form-control" name="maths"
 									 placeholder="Maths" value="<%=DataUtility.getStringData(dto.getMaths())%>">
 								<%=ServletUtility.getErrorMessage("maths", request)%>	 
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
                                <input type="submit" class="btn btn-success" name="operation" value="<%=MarksheetCtl.OP_UPDATE%>" >
                                <input type="submit" class="btn btn-secondary" name="operation"value="<%=MarksheetCtl.OP_CANCEL%>"><br>  								                          
                            </div> 							
						
                    <%
                        } else {
                    %>
                    <div class="text-center">                            
                                <input type="submit" class="btn btn-success" name="operation" value="<%=MarksheetCtl.OP_SAVE%>" >
                                <input type="submit" class="btn btn-secondary" name="operation"value="<%=MarksheetCtl.OP_RESET%>"><br>  								                          
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