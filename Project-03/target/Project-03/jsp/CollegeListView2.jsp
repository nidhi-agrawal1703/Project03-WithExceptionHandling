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
    <title>College List</title>
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
	

</style>
    
</head>
<body class="bgColor">
    <%@include file="Header.jsp"%>
    <div align="center">
        <jsp:useBean id="dto" class="in.co.rays.proj3.dto.CollegeDTO" scope="request"></jsp:useBean>
        <h1 align="center" style="margin-bottom: -15; color: navy;">College List</h1>

        <div style="height: 15px; margin-bottom: 12px">
            <h3>
                <font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
            </h3>
            <h3>
                <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
            </h3>
        </div>

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

            <table style="width: 100%">
                <tr>
                    <td align="center">
                        <label><b>College Name : </b></label>
                        <%=HTMLUtility.getList("collegeId", String.valueOf(dto.getId()), collegeList)%>&emsp;
                        <label><b>City :</b></label>
                        <input type="text" name="city" placeholder="Enter College City" value="<%=ServletUtility.getParameter("city", request)%>">&emsp;
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_SEARCH%>">&nbsp;
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_RESET%>">
                    </td>
                </tr>
            </table>
            <br>

            <table border="1" style="width: 100%; border: groove;">
                <tr style="background-color: #e1e6f1e3;">
                    <th width="5%"><input type="checkbox" id="selectall" /></th>
                    <th width="5%">S.No</th>
                    <th width="25%">College Name</th>
                    <th width="25%">Address</th>
                    <th width="15%">State</th>
                    <th width="10%">City</th>
                    <th width="10%">Phone No</th>
                    <th width="5%">Edit</th>
                </tr>

                <%
                    while (it.hasNext()) {
                        dto = it.next();
                %>
                <tr>
                    <td style="text-align: center;">
                        <input type="checkbox" class="case" name="ids" value="<%=dto.getId()%>">
                    </td>
                    <td style="text-align: center;"><%=index++%></td>
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
            </table>

            <table style="width: 100%">
                <tr>
                    <td style="width: 25%">
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEW%>">
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_DELETE%>">
                    </td>
                    <td style="width: 25%" align="right">
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEXT%>" <%= (nextPageSize != 0) ? "" : "disabled" %>>
                    </td>
                </tr>
            </table>

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
    <%@ include file="Footer.jsp"%>
</body>
</html>