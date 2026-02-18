<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.controller.MarksheetMeritListCtl"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.MarksheetDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>Marksheet Merit List</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
<%@include file="Header.jsp" %>
<div class="container">
        <h1 align="center" style="margin-bottom: -15; color: navy;">Marksheet Merit List</h1>

        <!-- Static Error Message -->
 						<%if(ServletUtility.getErrorMessage(request).length()>0) {%>
 						<div class="alert alert-danger alert-dismissable">
 						<button type="button" class="close" data-dismiss="alert">&times;</button>
                            <h4><%=ServletUtility.getErrorMessage(request)%></h4>
 						</div>
 						<% }%>

        <form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL %>" method="post">
        <%
        	int pageNo=ServletUtility.getPageNo(request);
        	int pageSize=ServletUtility.getPageSize(request);
        	int index=((pageNo-1)*pageSize)+1;
        	
        	List<MarksheetDTO> list=(List<MarksheetDTO>)ServletUtility.getList(request);
        	Iterator<MarksheetDTO> it=list.iterator();
        	
        	if(list.size()>0){
        %>
        <table border="1"
                   style="width: 100%; border: groove; padding: 1px; border-color: #e1e6f1e3;">
                <thead>
                    <tr style="background-color: #e1e6f1e3;">
                        <th width="5%">S.No</th>
                        <th width="10%">Roll No</th>
                        <th width="30%">Name</th>
                        <th width="10%">Physics</th>
                        <th width="10%">Chemistry</th>
                        <th width="10%">Maths</th>
                        <th width="10%">Total</th>
                        <th width="15%">Percentage (%)</th>
                    </tr>
                </thead>
        <%
			while(it.hasNext()){
				
			     MarksheetDTO dto=it.next();
			     
			     int physics=dto.getPhysics();
			     int chemistry=dto.getChemistry();
			     int maths=dto.getMaths();
			     int total=physics+chemistry+maths;
			     float percentage=(total/300)*100;
			     percentage = Float.parseFloat(new DecimalFormat("##.##").format(percentage));
        %>
         <tbody>
                    <tr>
                        <td style="text-align: center;"><%=index++%></td>
                        <td style="text-align: center; text-transform: uppercase;"><%=dto.getRollNo()%></td>
                        <td style="text-transform: capitalize; text-align: center;"><%=dto.getName()%></td>
                        <td style="text-align: center;"><%=dto.getPhysics()%></td>
                        <td style="text-align: center;"><%=dto.getChemistry()%></td>
                        <td style="text-align: center;"><%=dto.getMaths()%></td>
                        <td style="text-align: center;"><%=total%></td>
                        <td style="text-align: center;"><%=percentage%> %</td>
                    </tr>
                </tbody>
        <%}//Iterator %>
        </table>
        <%}//List %>
        <table>
                <tr>
                    <td align="right">
                        <input type="submit" name="operation" value="<%=MarksheetMeritListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>
        <input type="hidden" name="pageNo" value="<%=pageNo%>">
        <input type="hidden" name="pageSize" value="<%=pageSize%>">
        </form>
        </div>
        <%@ include file="Footer.jsp"%>
</body>
</html>