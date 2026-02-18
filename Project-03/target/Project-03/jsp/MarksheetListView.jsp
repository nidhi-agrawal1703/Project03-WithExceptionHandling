<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="java.util.Collections"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.MarksheetListCtl"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.MarksheetDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Marksheet List</title>
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
<jsp:useBean id="dto" class="in.co.rays.proj3.dto.MarksheetDTO" ></jsp:useBean>

<!-- Main Content -->

<div align="center" class="container-fluid">
<h1 class="text-dark font-weight-bold pb-3"><u>Marksheet List</u></h1>

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
	<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post"> 
	<%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

                List<MarksheetDTO> list = (List<MarksheetDTO>) ServletUtility.getList(request);
                Iterator<MarksheetDTO> it = list.iterator();

                if (list.size() != 0) {
            %>
	
	
	
	<!-- Hidden Fields -->
<input type="hidden" name="pageNo" value="<%=pageNo%>"> 
<input type="hidden" name="pageSize" value="<%=pageSize%>">
							
<!-- Search Form -->
<div class="row search-row">
	<div class="col-md-3">
		<input type="text" class="form-control" name="name" placeholder="Enter Name" value="<%=ServletUtility.getParameter("name", request)%>">
	</div>
	<div class="col-md-3">
		<input type="text" class="form-control" name="rollNo" placeholder="Enter Roll No" value="<%=ServletUtility.getParameter("rollNo", request)%>">
	</div>
	<div class="col-md-3">
	<input type="submit" class="btn btn-primary btn-md" name="operation" value="<%=MarksheetListCtl.OP_SEARCH%>">
    <input type="submit" class="btn btn-secondary btn-md" name="operation" value="<%=MarksheetListCtl.OP_RESET%>">
	</div>
	<div class="col-md-3"></div>
</div>

<!-- Table -->
<div>
	<table class="table table-bordered table-light table-hover">
		<thead>
                    <tr align="center" style="background:#8C8C8C;">
                    <th width="5%"><input type="checkbox" id="selectall" /></th>
                    <th width="5%">S.No</th>
                    <th width="10%">Roll No</th>
                    <th width="25%">Name</th>
                    <th width="10%">Physics</th>
                    <th width="10%">Chemistry</th>
                    <th width="10%">Maths</th>
                    <th width="10%">Total</th>
                    <th width="10%">Percentage (%)</th>
                    <th width="5%">Edit</th>

                    </tr>
                </thead>
	<%
                    while (it.hasNext()) {
                        dto = it.next();
                        int physics = dto.getPhysics();
                        int chemistry = dto.getChemistry();
                        int maths = dto.getMaths();
                        int total = physics + chemistry + maths;
                        float percentage = (float) total / 3;
                        percentage = Float.parseFloat(new DecimalFormat("##.##").format(percentage));
                %>
	
		<tbody align="center">
                    <tr>
                        <td style="text-align: center;">
                        <input type="checkbox" class="case" name="ids" value="<%=dto.getId()%>">
                    </td>
                    <td style="text-align: center;"><%=index++%></td>
                    <td style="text-align: center; text-transform: uppercase;"><%=dto.getRollNo()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=dto.getName()%></td>
                    <td style="text-align: center;"><%=dto.getPhysics()%></td>
                    <td style="text-align: center;"><%=dto.getChemistry()%></td>
                    <td style="text-align: center;"><%=dto.getMaths()%></td>
                    <td style="text-align: center;"><%=total%></td>
                    <td style="text-align: center;"><%=percentage%> %</td>
                    <td style="text-align: center;">
                        <a href="MarksheetCtl?id=<%=dto.getId()%>">Edit</a>
                    </td>                    </tr>

                    
                </tbody>
			
	<%
                    }
                %>
	</table>

</div>

<!-- Search Button -->
<div class="row mt-3" style="width: 95%; margin:auto;">
            <div class="col-md-3">
                <input type="submit" name="operation" class="btn btn-primary btn-md" value="<%=MarksheetListCtl.OP_PREVIOUS%>" value="<%=MarksheetListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
            </div>

            <div class="col-md-3">
                <input type="submit" name="operation" class="btn btn-primary btn-md" value="<%=MarksheetListCtl.OP_NEW%>">
            </div>

            <div class="col-md-3">
                <input type="submit" name="operation" class="btn btn-primary btn-md" value="<%=MarksheetListCtl.OP_DELETE%>">
            </div>

            <div class="col-md-3 text-right">
                <input type="submit" name="operation" class="btn btn-primary btn-md" value="<%=MarksheetListCtl.OP_NEXT%>" <%=nextListSize != 0 ? "" : "disabled"%>>
            </div>
        </div>

</div>
<!-- End of Main Content -->
	<%
                }
                if (list.size() == 0) {
            %>
            <table>
                <tr>
                    <td align="right">
                        <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>
            <%
                }
            %>
	
	</form>
<!-- End of Form -->
	
<!-- Footer File -->
<%@include file="Footer.jsp"%>
</body>
</html>