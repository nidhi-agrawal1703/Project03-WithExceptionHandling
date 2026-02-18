<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.RoleListCtl"%>
<%@page import="in.co.rays.proj3.controller.BaseCtl"%>
<%@page import="in.co.rays.proj3.dto.RoleDTO"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>Role List</title>
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
<jsp:useBean id="dto" class="in.co.rays.proj3.dto.RoleDTO" ></jsp:useBean>

<!-- Main Content -->

<div align="center" class="container-fluid">
<h1 class="text-dark font-weight-bold pb-3"><u>Role List</u></h1>

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
	<form action="<%=ORSView.ROLE_LIST_CTL %>" method="post">
	
	<%
		int pageNo=ServletUtility.getPageNo(request);
		int pageSize=ServletUtility.getPageSize(request);
		int index=((pageNo-1)*pageSize)+1;
		int nextListSize=DataUtility.getInt(request.getAttribute("nextListSize").toString());
		
		List<RoleDTO> roleList=(List<RoleDTO>)request.getAttribute("roleList");
		 List<RoleDTO> list = (List<RoleDTO>) ServletUtility.getList(request);
         Iterator<RoleDTO> it = list.iterator();
		
         if (list.size() != 0) {
		
		%>	 						
<!-- Search Form -->
<div class="row search-row">
	<div class="col-md-4">
		
	</div>
	<div class="col-md-4">
		<%=HTMLUtility.getList("roleId", String.valueOf(dto.getId()), roleList)%>
		
	</div>
	<div class="col-md-4">
	<input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=RoleListCtl.OP_SEARCH%>">
     	<input type="submit" class="btn btn-secondary btn-md" name="operation" value="<%=RoleListCtl.OP_RESET%>">
	</div>
	<div class="col-md-3">
		
	</div>
</div>

<!-- Table -->
<div>
	<table class="table table-bordered table-light table-hover">
		<thead>
                    <tr align="center" style="background:#8C8C8C;">
                        <th>Select<br><input type="checkbox" id="selectall" /></th>
                        <th>S.No</th>
                        <th>Role</th>
                        <th>Description</th>
                        <th>Edit</th>
                    </tr>
                </thead>
<!--Iterator starts  -->
<%
                    while (it.hasNext()) {
                        dto = (RoleDTO) it.next();
                %>

	
		<tbody align="center">
                    <tr>
                        <td><input type="checkbox" class="case" name="ids" value="<%=dto.getId()%>"></td>
                        <td style="text-align: center;"><%=index++ %></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getName() %></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=dto.getDescription() %></td>
                        <td><a href="RoleCtl?id=<%=dto.getId()%>">Edit</a></td>
                    </tr>

                    
                </tbody>
	<%} %>		
	
	</table>
	
<!-- Iterator Ends -->
</div>

<!-- Search Button -->
<div class="row mt-3" style="width: 95%; margin:auto;">
            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=RoleListCtl.OP_PREVIOUS%>">
            </div>

            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=RoleListCtl.OP_NEW%>">
            </div>

            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=RoleListCtl.OP_DELETE%>">
            </div>

            <div class="col-md-3 text-right">
                <input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=RoleListCtl.OP_NEXT%>">
            </div>
        </div>
 <%
                }
                if (list.size() == 0) {
            %>
         <table>
                <tr>
                    <td align="right">
                        <input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=RoleListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>
            <%
                }
            %>
           
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