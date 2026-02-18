<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="in.co.rays.proj3.controller.RoleCtl"%>
<%@page import="in.co.rays.proj3.controller.BaseCtl"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Role</title>
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
	<form action="<%=ORSView.ROLE_CTL %>" method="post">
	<%@include file="Header.jsp" %>
	
	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.RoleDTO" scope="request"></jsp:useBean>
	
	<div align="center">
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                    if (dto != null && dto.getId() > 0) {
                    	System.out.println("bean data: "+dto.getId());
                %>Update<%
                    } else {
                %>Add<%
                    }
                %>
                Role
            </h1>
	
	<div style="height: 15px; margin-bottom: 12px" >
	<h3 align="center"><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h3>
	<h3 align="center"><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h3>
	</div>
	
			<input type="hidden" name="id" value="<%=dto.getId()%>">
            <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
	
	<table>
	
	<tr>
	<th align="left">Name<span style="color:red">*</span></th>
	<td align="center"><input type="text" name="name" placeholder="Enter Role Name" value="<%=DataUtility.getStringData(dto.getName())%>"></td>
	<td style="position:fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name",request) %></font></td>
	</tr>
	
	<tr>
	<th align="left">Description<span style="color:red">*</span></th>
	<td align="center"><textarea style="width: 170px; resize: none;" name="description" rows="3" placeholder="Enter short description"><%=DataUtility.getStringData(dto.getDescription())%></textarea></td>
	<td style="position:fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description",request) %></font></td>
	</tr>
	
	<tr>
                    <th></th>
                    <td></td>
  	</tr>

  	<tr>
  	<th></th>
  	<%
                        if (dto != null && dto.getId() > 0) {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=RoleCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=RoleCtl.OP_CANCEL%>">
                    </td>
                    <%
                        } else {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=RoleCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=RoleCtl.OP_RESET%>">
                    </td>
                    <%
                        }
                    %>
  	</tr>
	
	</table>
		
	</form>
</div>
<%@ include file="Footer.jsp"%>
</body>
</html>