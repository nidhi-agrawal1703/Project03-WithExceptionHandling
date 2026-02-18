<%@page import="in.co.rays.proj3.dto.RoleDTO"%>
<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.UserCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>Add User</title>
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

h1, h2, h3 {
	color: #004e92;
}
</style>

</head>
<body class="bgColor">
	<!-- Header File -->
	<%@ include file="Header.jsp"%>

	<!-- Bean -->
	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO"
		scope="request"></jsp:useBean>

	<!-- Main Content -->
	<div class="container pt-4">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="card input-group-addon">
					<div class="card-body">
						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (dto.getFirstName() != null) {
						%>
						<h3 class="text-center text-primary pb-3">Update User</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary pb-3">Add User</h3>
						<%
							}
						%>

						<!-- Static Success Message -->
						<%
							if (ServletUtility.getSuccessMessage(request).length() > 0) {
						%>
						<div class="alert alert-success alert-dismissable">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<h4><%=ServletUtility.getSuccessMessage(request)%></h4>
						</div>
						<%
							}
						%>


						<!-- Static Error Message -->
						<%
							if (ServletUtility.getErrorMessage(request).length() > 0) {
						%>
						<div class="alert alert-danger alert-dismissable">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<h4><%=ServletUtility.getErrorMessage(request)%></h4>
						</div>
						<%
							}
						%>
						<!-- Form Start -->
						<form action="<%=ORSView.USER_CTL%>" method="post">

							<input type="hidden" name="id" value="<%=dto.getId()%>">
							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">


							<!-- Preload data -->
							<%
								List<RoleDTO> roleList = (List<RoleDTO>) request.getAttribute("roleList");
							%>
							<!-- FirstName -->
							<label><b>First Name</b><span style="color: red">*</span></label>
							<div class="input-group mb-3">
								<div class="input-class-prepend">
									<span class="input-group-text" id="basic-addon1"><i
										class="fa fa-user-alt" style="font-size: 25px;"></i></span>

								</div>
								<input type="text" class="form-control" name="firstName"
									placeholder="First Name"
									value="<%=DataUtility.getStringData(dto.getFirstName())%>">
								<%=ServletUtility.getErrorMessage("firstName", request)%>
							</div>
							<!-- Last Name -->
							<label><b>Last Name</b><span style="color: red">*</span></label>
							<div class="input-group mb-3">
								<div class="input-class-prepend">
									<span class="input-group-text" id="basic-addon1"><i
										class="fa fa-user-alt" style="font-size: 25px;"></i></span>

								</div>
								<input type="text" class="form-control" name="lastName"
									placeholder="Last Name"
									value="<%=DataUtility.getStringData(dto.getLastName())%>">
								<%=ServletUtility.getErrorMessage("lastName", request)%>
							</div>
							
							<!-- Login Id -->
							<label><b>Email Id</b><span style="color: red">*</span></label>
							<div class="input-group mb-3">
								<div class="input-class-prepend">
									<span class="input-group-text" id="basic-addon1"><i
										class="fas fa-envelope" style="font-size: 25px;"></i></span>

								</div>
								<input type="text" class="form-control" name="login"
									placeholder="Login Id"
									value="<%=DataUtility.getStringData(dto.getLogin())%>">
								<%=ServletUtility.getErrorMessage("email", request)%>
							</div>
							
							<!-- Password -->
							<label><b>Password</b><span style="color: red">*</span></label>
							<div class="input-group mb-3">
								<div class="input-class-prepend">
									<div class="input-group-text">
										<i class="fa fa-key" style="font-size: 25px;"></i>
									</div>
								</div>
								<input type="password" class="form-control" name="password"
									placeholder="Password"
									value="<%=DataUtility.getStringData(dto.getPassword())%>">
								<%=ServletUtility.getErrorMessage("password", request)%>
							</div>

							<!-- Confirm Password -->
							<label><b>Confirm Password</b><span style="color: red">*</span></label>
							<div class="input-group mb-3">
								<div class="input-class-prepend">
									<div class="input-group-text">
										<i class="fa fa-key" style="font-size: 25px;"></i>
									</div>
								</div>
								<input type="password" class="form-control"
									name="confirmPassword" placeholder="Confirm Password"
									value="<%=DataUtility.getStringData(dto.getConfirmPassword())%>">
								<%=ServletUtility.getErrorMessage("confirmPassword", request)%>
							</div>
							
							<!-- DOB -->
							<label><b>Date of Birth</b><span style="color: red">*</span></label>
							<div class="input-group mb-3">
								<div class="input-class-prepend">
									<span class="input-group-text" id="basic-addon1"><i
										class="fas fa-calendar-alt" style="font-size: 25px;"></i></span>

								</div>
								<input type="text" readonly="readonly" id="udate" name="dob"
									placeholder="Select Date of Birth"
									value="<%=DataUtility.getDateString(dto.getDob())%>">
								<%=ServletUtility.getErrorMessage("dob", request)%>
							</div>

							<!-- Gender -->
							<label><b>Gender</b><span style="color: red">*</span></label>
							<div class="input-group mb-3">
								<div class="input-class-prepend">
									<span class="input-group-text" id="basic-addon1"><i
										class="fas fa-venus-mars" style="font-size: 25px;"></i></span>

								</div>
								<%
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("Male", "Male");
									map.put("Female", "Female");
									String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
								%>
								<%=htmlList%>
								<%=ServletUtility.getErrorMessage("gender", request)%>
							</div>

							<!-- Role -->
							<label><b>Role</b><span style="color: red">*</span></label>
							<div class="input-group mb-3">
								<div class="input-class-prepend">
									<span class="input-group-text" id="basic-addon1"><i
										class="fas fa-user-tag" style="font-size: 25px;"></i></span>

								</div>
								<%
									String htmlRoleList = HTMLUtility.getList("roleId", String.valueOf(dto.getRoleId()), roleList);
								%>
								<%=htmlRoleList%>
								<%=ServletUtility.getErrorMessage("roleId", request)%>
							</div>

							<!-- Mobile No -->
							<label><b>Mobile No</b><span style="color: red">*</span></label>
							<div class="input-group mb-3">
								<div class="input-class-prepend">
									<span class="input-group-text" id="basic-addon1"><i
										class="fas fa-phone" style="font-size: 25px;"></i></span>

								</div>
								<input type="text" class="form-control" name="mobileNo"
									placeholder="Mobile No"
									value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
								<%=ServletUtility.getErrorMessage("mobileNo", request)%>
							</div>


							<!-- Buttons -->
							<%
								if (dto.getFirstName() != null) {
									//System.out.println("In UserView Buttons section "+dto.getFirstName());
							%>
							<div class="text-center">
								<input type="submit" class="btn btn-success" name="operation"
									value="Update"> <input type="submit"
									class="btn btn-secondary" name="operation" value="Cancel"><br>
							</div>

							<%
								} else {
							%>
							<div class="text-center">
								<input type="submit" class="btn btn-success" name="operation"
									value="<%=UserCtl.OP_SAVE%>"> <input type="submit"
									class="btn btn-secondary" name="operation"
									value="<%=UserCtl.OP_RESET%>"><br>
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