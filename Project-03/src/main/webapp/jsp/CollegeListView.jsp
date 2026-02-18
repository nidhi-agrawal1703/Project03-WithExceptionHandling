<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="java.util.Collections"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.CollegeListCtl"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.CollegeDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>College List</title>
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

<!-- UseBean Tag -->
        <jsp:useBean id="dto" class="in.co.rays.proj3.dto.CollegeDTO" scope="request"></jsp:useBean>
<!-- Main Content -->

<div align="center" class="container-fluid">
<h1 class="text-dark font-weight-bold pb-3"><u>College List</u></h1>

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
<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="POST">
            <%
                int pageNo = ServletUtility.getPageNo(request);
				System.out.println(pageNo);            
                int pageSize = ServletUtility.getPageSize(request);
                System.out.println(pageSize);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
                System.out.println(nextPageSize);
                List<CollegeDTO> collegeList = (List<CollegeDTO>) request.getAttribute("collegeList");

                List<CollegeDTO> list = (List<CollegeDTO>) ServletUtility.getList(request);
                Iterator<CollegeDTO> it = list.iterator();

                if (list.size() != 0) {
            %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
 						
 						
<!-- Search Form -->
<div class="row search-row">
	<div class="col-md-3">
		
	</div>
	<div class="col-md-3">
		<%=HTMLUtility.getList("collegeId", String.valueOf(dto.getId()), collegeList)%>&emsp;	
		</div>
	<div class="col-md-3">
	<input type="text" name="city" placeholder="Enter College City" value="<%=ServletUtility.getParameter("city", request)%>">
	</div>
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
                        <th><input type="checkbox" id="selectall" />Select</th>
                        <th>S.No</th>
                        <th>College Name</th>
                        <th>Address</th>
                        <th>State</th>
                        <th>City</th>
                        <th>Phone No</th>
                        <th>Edit</th>
                    </tr>
                </thead>
	               <%
                    while (it.hasNext()) {
                        dto = it.next();
                %>
	
		<tbody align="center">
                    <tr>
                        <td><input type="checkbox" class="case" name="ids" value="<%=dto.getId()%>"></td>
                        <td><%=index++ %></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getName()%></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getAddress()%></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getState()%></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getCity()%></td>
                        <td style="text-align: center;"><%=dto.getPhoneNo()%></td>
                        <td style="text-align: center;"><a href="CollegeCtl?id=<%=dto.getId()%>">Edit</a></td>
                    </tr>
 				<%
                    }
                %>
                  </tbody>
			
	
	</table>

</div>

<!-- Search Button -->
<div class="row mt-3" style="width: 95%; margin:auto;">
            <div class="col-md-3">
                <button class="btn btn-primary btn-md" name="operation" value="<%=CollegeListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>Previous</button>
            </div>

            <div class="col-md-3">
                <button class="btn btn-primary btn-md" name="operation" value="<%=CollegeListCtl.OP_NEW%>">Add New</button>
            </div>

            <div class="col-md-3">
                <button class="btn btn-primary btn-md" name="operation" value="<%=CollegeListCtl.OP_DELETE%>">Delete</button>
            </div>

            <div class="col-md-3 text-right">
                <button class="btn btn-primary btn-md" name="operation" value="<%=CollegeListCtl.OP_NEXT%>" <%= (nextPageSize != 0) ? "" : "disabled" %>>Next</button>
            </div>
        </div>
 <!-- End of search button -->
 <%
                }
                if (list.size() == 0) {
            %>
            <table>
                <tr>
                    <td align="right">
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>
            <%
                }
            %>
	</form>
</div>
<!-- End of Main Content -->

<!-- Footer File -->
<%@include file="Footer.jsp"%>
</body>
</html>