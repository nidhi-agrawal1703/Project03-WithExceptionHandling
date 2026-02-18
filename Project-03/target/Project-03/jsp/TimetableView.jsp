<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.UserCtl"%>
<%@page import="in.co.rays.proj3.dto.ProductDTO"%>
<%@page import="in.co.rays.proj3.dto.CollegeDTO"%>
<%@page import="in.co.rays.proj3.dto.CourseDTO"%>
<%@page import="in.co.rays.proj3.dto.SubjectDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="in.co.rays.proj3.controller.TimetableCtl"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Timetable</title>
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
	
<!--  Preload Data-->
<%
	List l = (List) request.getAttribute("courseList");
	List li = (List) request.getAttribute("subjectList");
								%>
	
<!-- Bean Tag -->
	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.TimetableDTO" scope="request"></jsp:useBean>
	
	
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
						<h3 class="text-center text-primary pb-3">Update Timetable</h3>
						<%}else { %>
						<h3 class="text-center text-primary pb-3">Add Timetable</h3>
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
						<form action="<%=ORSView.TIMETABLE_CTL %>" method="post">
						
						<!-- Course -->
 							<label><b>Course</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-book-open" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<%=HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), l)%>
 								<%=ServletUtility.getErrorMessage("courseId", request)%>	 
 							</div>
 						
						<!-- Subject -->
 							<label><b>Subject</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-book" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<%=HTMLUtility.getList("subjectId", String.valueOf(dto.getSubjectId()), li)%>
 								<%=ServletUtility.getErrorMessage("subjectId", request)%>	 
 							</div>
							
		
 							<!-- Semester -->
 							<label><b>Semester</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-list" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<%
											HashMap map = new HashMap();
											map.put("1 semester", "1 semester");
											map.put("2 semester", "2 semester");
											map.put("3 semester", "3 semester");
											map.put("4 semester", "4 semester");
											map.put("5 semester", "5 semester");
											map.put("6 semester", "6 semester");
											map.put("7 semester", "7 semester");
											map.put("8 semester", "8 semester");
											map.put("9 semester", "9 semester");
											map.put("10 semester", "10 semester");

											String htmlList = HTMLUtility.getList("semesterId", dto.getSemester(), map);
										%>
										<%=htmlList%>
 								<%=ServletUtility.getErrorMessage("semesterId", request)%>	 
 							</div>
 							
 							
 							<!-- Exam Date -->
 							<label><b>Exam Date</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fas fa-calendar-alt" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<input type="text" id="udate" name="examDate" placeholder="Select Exam Date" value="<%=DataUtility.getDateString(dto.getExamDate())%>">
 								<%=ServletUtility.getErrorMessage("examDate", request)%>	 
 							</div>
 							
 					
 							<!-- Exam Time -->
 							<label><b>Exam Time</b><span style="color:red">*</span></label>
 							<div class="input-group mb-3">
 								<div class="input-class-prepend">
 									<span class="input-group-text" id="basic-addon1"><i class="fa fa-clock" style="font-size: 25px;"></i></span>
 									
 								</div>
 								<%
											HashMap map1 = new HashMap();
											map1.put("08:00 AM to 11:00 AM", "08:00 AM to 11:00 AM");
											map1.put("12:00PM to 3:00PM", "12:00PM to 3:00PM");
											map1.put("3:00PM to 6:00PM", "3:00PM to 6:00PM");
											String htmlList1 = HTMLUtility.getList("examId", dto.getExamTime(), map1);
										%>
										<%=htmlList1%>

 								<%=ServletUtility.getErrorMessage("examId", request)%>	 
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
                                <input type="submit" class="btn btn-success" name="operation" value="<%=TimetableCtl.OP_UPDATE%>" >
                                <input type="submit" class="btn btn-secondary" name="operation"value="<%=TimetableCtl.OP_CANCEL%>"><br>  								                          
                            </div> 							
						
                    <%
                        } else {
                    %>
                    <div class="text-center">                            
                                <input type="submit" class="btn btn-success" name="operation" value="<%=TimetableCtl.OP_SAVE%>" >
                                <input type="submit" class="btn btn-secondary" name="operation"value="<%=TimetableCtl.OP_RESET%>"><br>  								                          
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