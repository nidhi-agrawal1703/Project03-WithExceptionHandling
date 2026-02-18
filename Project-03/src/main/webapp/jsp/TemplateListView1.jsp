<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.UserListCtl"%>
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

<!-- JSP UseBean Tag -->
<jsp:useBean id="dto" class="in.co.rays.proj3.dto.StudentDTO" ></jsp:useBean>

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
 <!-- Form -->						
	<form action=""> 						
<!-- Search Form -->
<div class="row search-row">
	<div class="col-md-3">
		<input type="text" class="form-control" placeholder="">
	</div>
	<div class="col-md-3">
		<input type="text" class="form-control" placeholder="">
	</div>
	<div class="col-md-3"></div>
	<div class="col-md-3">
		<button class="btn btn-primary btn-md" name="operation">Search</button>
        <button class="btn btn-secondary btn-md" name="operation">Reset</button>
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
                        <th>Login ID</th>
                        <th>Gender</th>
                        <th>Role</th>
                        <th>DOB</th>
                        <th>Edit</th>
                    </tr>
                </thead>
	
		<tbody align="center">
                    <tr>
                        <td><input type="checkbox"></td>
                        <td>1</td>
                        <td>Rohit</td>
                        <td>Sharma</td>
                        <td>rohit@gmail.com</td>
                        <td>Male</td>
                        <td>Admin</td>
                        <td>10/10/2000</td>
                        <td><a href="#">Edit</a></td>
                    </tr>

                    
                </tbody>
			
	
	</table>

</div>

<!-- Search Button -->
<div class="row mt-3" style="width: 95%; margin:auto;">
            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-md" value="<%=UserListCtl.OP_PREVIOUS%>">
            </div>

            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-md" value="<%=UserListCtl.OP_NEXT%>">
            </div>

            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-md" value="<%=UserListCtl.OP_DELETE%>">
            </div>

            <div class="col-md-3 text-right">
                <input type="submit" class="btn btn-primary btn-md" value="<%=UserListCtl.OP_NEXT%>">
            </div>
        </div>

</div>
<!-- End of Main Content -->

<!-- Hidden Fields -->
<!--  <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
<input type="hidden" name="pageSize" value="<%=pageSize%>">-->
	</form>
<!-- End of Form -->
	
<!-- Footer File -->
<%@include file="Footer.jsp"%>
</body>
</html>