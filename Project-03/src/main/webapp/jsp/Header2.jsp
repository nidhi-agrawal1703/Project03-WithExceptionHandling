
<%@page import="in.co.rays.proj3.dto.UserDTO"%>
<%@page import="in.co.rays.proj3.controller.LoginCtl"%>
<%@page import="in.co.rays.proj3.dto.RoleDTO"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>

<html>
<head>
	<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
	
	<script type="text/javascript" src="/ORSProject-04/js/datepicker.js"></script>

	<script type="text/javascript" src="/ORSProject-04/js/checkbox.js"></script>	
	
	<!-- Bootstrap Libraries -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js"></script>
     
     <style>
        .footer {
            position: fixed;
            left: 0;
            bottom: 0;
            width: 100%;
            color: white;
            text-align: center;
            background-image: linear-gradient(to bottom right, grey, black);
        }

        .e {
            background: linear-gradient(135deg, #000428, #004e92);
        }
    </style>


</head>
<%
UserDTO user = (UserDTO) session.getAttribute("user");

boolean userLoggedIn = user != null;

String welcomeMsg = "Hi, ";

if (userLoggedIn) {
	String role = (String) session.getAttribute("role");
	welcomeMsg += user.getFirstName() + " (" + role + ")";//welcomeMsg =welcomeMsg+ user.getFirstName() + " (" + role + ")";
} else {
	welcomeMsg += "Guest";
}
%>

<%
if(userLoggedIn){
%>
<div>
    <nav class="navbar navbar-expand-lg fixed-top e">
      <a class="navbar-brand" href="#">
        <img src="https://www.raystec.com/assets/img/rays/customLogoOuterglow.png" width="190" height="50">
      </a>
        <ul class="navbar-nav ml-auto">

          <li class="nav-item dropdown" style="padding-right: 40px;">
            <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">
              Hi, Guest
            </a>
            <div class="dropdown-menu">
              <a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>"><i class="fa fa-sign-in-alt"></i> Login</a>
              <a class="dropdown-item" href="<%=ORSView.USER_CTL%>"><i class="fa fa-registered"></i> User Registration</a>
            </div>
          </li>
        </ul>
    </nav>
  </div>
  
<%}else{ %>
<div>
    <nav class="navbar navbar-expand-lg fixed-top e">
      <a class="navbar-brand" href="#">
        <img src="https://www.raystec.com/assets/img/rays/customLogoOuterglow.png" width="190" height="50">
      </a>

      <ul class="navbar-nav ml-auto">

        <!-- USER -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">User</a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><i class="fa fa-user-circle"></i> Add User</a>
            <a class="dropdown-item" href="#"><i class="fa fa-user-friends"></i> User List</a>
          </div>
        </li>

        <!-- MARKSHEET -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">Marksheet</a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><i class="fa fa-file"></i> Add Marksheet</a>
            <a class="dropdown-item" href="#"><i class="fa fa-list"></i> Marksheet List</a>
            <a class="dropdown-item" href="#"><i class="fa fa-star"></i> Merit List</a>
            <a class="dropdown-item" href="#"><i class="fa fa-copy"></i> Get Marksheet</a>
          </div>
        </li>

        <!-- ROLE -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">Role</a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><i class="fa fa-user-tie"></i> Add Role</a>
            <a class="dropdown-item" href="#"><i class="fa fa-users"></i> Role List</a>
          </div>
        </li>

        <!-- COLLEGE -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">College</a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><i class="fa fa-university"></i> Add College</a>
            <a class="dropdown-item" href="#"><i class="fa fa-building"></i> College List</a>
          </div>
        </li>

        <!-- COURSE -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">Course</a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><i class="fa fa-book-open"></i> Add Course</a>
            <a class="dropdown-item" href="#"><i class="fa fa-list"></i> Course List</a>
          </div>
        </li>

        <!-- STUDENT -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">Student</a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><i class="fa fa-user-graduate"></i> Add Student</a>
            <a class="dropdown-item" href="#"><i class="fa fa-users"></i> Student List</a>
          </div>
        </li>

        <!-- FACULTY -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">Faculty</a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><i class="fa fa-user-tie"></i> Add Faculty</a>
            <a class="dropdown-item" href="#"><i class="fa fa-users"></i> Faculty List</a>
          </div>
        </li>

        <!-- TIMETABLE -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">Time Table</a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><i class="fa fa-clock"></i> Add TimeTable</a>
            <a class="dropdown-item" href="#"><i class="fa fa-clock"></i> TimeTable List</a>
          </div>
        </li>

        <!-- SUBJECT -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">Subject</a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><i class="fa fa-book"></i> Add Subject</a>
            <a class="dropdown-item" href="#"><i class="fa fa-list"></i> Subject List</a>
          </div>
        </li>

        <!-- PROFILE / LOGOUT -->
        <li class="nav-item dropdown" style="padding-right: 40px;">
          <a class="nav-link dropdown-toggle" data-toggle="dropdown" style="color:white;">
            Hi, Admin
          </a>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#"><i class="fa fa-user"></i> My Profile</a>
            <a class="dropdown-item" href="#"><i class="fa fa-key"></i> Change Password</a>
            <a class="dropdown-item" href="#"><i class="fa fa-sign-out-alt"></i> Logout</a>
          </div>
        </li>
      </ul>
    </nav>
  </div>


<%} %>

<table>
	<tr>
		<td width="90%"><a style="text-decoration: none;"
			href="<%=ORSView.WELCOME_CTL%>"><b>Welcome</b></a> | <%
			if (userLoggedIn) {
		%> <a style="text-decoration: none;"
			href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><b>Logout</b></a>

			<%
				} else {
			%> <a style="text-decoration: none;" href="<%=ORSView.LOGIN_CTL%>"><b>Login</b></a>
			<%
				}
			%></td>
		
	</tr>

	<tr>
		<td>
			<h3>
				<%=welcomeMsg%></h3>
		</td>
	</tr>


	<%
		if (userLoggedIn) {
	%>

	<tr>
		<td colspan="2">
		<a href="<%=ORSView.MY_PROFILE_CTL%>">My Profile</a> | 
		<a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</a> | 
		<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</a> | 
		<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet Merit List </a> 
		<%
 	   if (user.getRoleId() == RoleDTO.ADMIN) {
       %> | 
      <a href="<%=ORSView.COLLEGE_CTL%>">Add College</a> | 
      <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</a> | 
      <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</a> | 
	  <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</a> | 
	  <a href="<%=ORSView.USER_CTL%>">Add User</a> | 
	  <a href="<%=ORSView.USER_LIST_CTL%>">User List</a> | 
	  <a href="<%=ORSView.ROLE_CTL%>">Add Role</a> | 
	  <a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</a> |
	  	  
	  <%
 	}

 		if (user.getRoleId() == RoleDTO.COLLEGE || user.getRoleId() == RoleDTO.ADMIN) {
 %>  <a href="<%=ORSView.STUDENT_CTL%>">Add Student</a> | 
      <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</a> | 
      <a href="<%=ORSView.COURSE_CTL%>">Add Course</a> | 
      <a href="<%=ORSView.COURSE_LIST_CTL%>">Course List</a> | 
	  <a href="<%=ORSView.SUBJECT_CTL%>">Add Subject</a> | 
	  <a href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</a> | 
	  <a href="<%=ORSView.FACULTY_CTL%>">Add Faculty</a> | 
	  <a href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</a>|
	   
	   <%
 	}
 		if (user.getRoleId() == RoleDTO.COLLEGE || user.getRoleId() == RoleDTO.FACULTY
 				|| user.getRoleId() == RoleDTO.ADMIN) {
 %> | <a href="<%=ORSView.TIMETABLE_CTL%>">Add Timetable</a> | 
      <a href="<%=ORSView.TIMETABLE_LIST_CTL%>">Timetable List</a>|
      
       <%
 	}

 		if (user.getRoleId() == RoleDTO.ADMIN) {
 %> | <a href="/ORSProject-04/doc/index.html" target="blank">Java Doc</a> <%
 	}
 	} else {
 %> <a href="<%=ORSView.LOGIN_CTL%>"></a> <%
 	}
 %></td>

	</tr>

</table>
<hr>
</html>