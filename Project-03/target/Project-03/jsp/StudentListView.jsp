<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.StudentListCtl"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.StudentDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student List</title>
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

<!-- Main Content -->

<div align="center" class="container-fluid">
<h1 class="text-dark font-weight-bold pb-3"><u>Student List</u></h1>

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
 						
 <form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

                
                List<StudentDTO> list = (List<StudentDTO>) ServletUtility.getList(request);
                Iterator<StudentDTO> it = list.iterator();

                if (list.size() != 0) {
            %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">

<!-- Search Form -->
<div class="row search-row">
	<div class="col-md-3">
		<input type="text" class="form-control" name="firstName" placeholder="Enter First Name"
		 value="<%=ServletUtility.getParameter("firstName", request)%>">
	</div>
	<div class="col-md-3">
		<input type="text" class="form-control" name="lastName" placeholder="Enter Last Name"
		value="<%=ServletUtility.getParameter("lastName", request)%>">
	</div>
	<div class="col-md-3">
	<input type="text" name="email" class="form-control" placeholder="Enter Email Id"
    value="<%=ServletUtility.getParameter("email", request)%>">&emsp;
	</div>
	<div class="col-md-3">
		<button class="btn btn-primary btn-md" name="operation" value="<%=StudentListCtl.OP_SEARCH%>">Search</button>
        <button class="btn btn-secondary btn-md" name="operation" value="<%=StudentListCtl.OP_RESET%>">Reset</button>
	</div>
</div>

<!-- Table -->
<div>
	<table class="table table-bordered table-light table-hover">
		<thead>
                    <tr align="center" style="background:#8C8C8C;">
                        <th>Select</th>
                        <th>S.No</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>College Name</th>
                        <th>Gender</th>
                        <th>Mobile No</th>
                        <th>Date of Birth</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <%
                    while (it.hasNext()) {
                        StudentDTO dto = it.next();
                %>
	
		<tbody align="center">
                    <tr>
                        <td style="text-align: center;"><input type="checkbox" class="case" name="ids" value="<%=dto.getId()%>"></td>
                        <td style="text-align: center;"><%= index++%></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getFirstName()%></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getLastName()%></td>
                        <td style="text-align: center;"><%=dto.getEmail()%></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getCollegeName()%></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getGender()%></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getMobileNo()%></td>
                        <%
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String date = sdf.format(dto.getDob());
                    %>
                        <td style="text-align: center; text-transform: capitalize;"><%=date%></td>
                        <td style="text-align: center;"><a href="StudentCtl?id=<%=dto.getId()%>">Edit</a></td>
                    </tr>

                    
                </tbody>
			
	<%} %>	
	</table>

</div>

<!-- Search Button -->
<div class="row mt-3" style="width: 95%; margin:auto;">
            <div class="col-md-3">
                <button class="btn btn-primary btn-md" name="operation" value="<%=StudentListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>Previous</button>
            </div>

            <div class="col-md-3">
                <button class="btn btn-primary btn-md" name="operation" value="<%=StudentListCtl.OP_NEW%>">Add New</button>
            </div>

            <div class="col-md-3">
                <button class="btn btn-primary btn-md" name="operation" value="<%=StudentListCtl.OP_DELETE%>">Delete</button>
            </div>

            <div class="col-md-3 text-right">
                <button class="btn btn-primary btn-md" name="operation" value="<%=StudentListCtl.OP_NEXT%>" <%= (nextPageSize != 0) ? "" : "disabled"%>>Next</button>
            </div>
        </div>
<%
                }
                if (list.size() == 0) {
            %>
            <table>
                <tr>
                    <td align="right">
                        <input type="submit" name="operation" value="<%=StudentListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>
            
            <%} %>        
</form>
</div>
<!-- End of Main Content -->

<!-- Footer File -->
<%@include file="Footer.jsp"%>
</body>
</html>