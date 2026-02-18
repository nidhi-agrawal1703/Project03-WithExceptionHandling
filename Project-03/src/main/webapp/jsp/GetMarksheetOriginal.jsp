<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="in.co.rays.proj3.controller.GetMarksheetCtl"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.MarksheetDTO"%>
<%@page import="in.co.rays.proj3.dto.CourseDTO"%>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
<title>Get Marksheet</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.MarksheetDTO" scope="request"></jsp:useBean>
	<jsp:useBean id="coursebean" class="in.co.rays.proj3.dto.CourseDTO" scope="request"></jsp:useBean>
	
	<div align="center">
        <h1 align="center" style="margin-bottom: -15; color: navy">Get Marksheet</h1>

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
 						
        <form action="<%=ORSView.GET_MARKSHEET_CTL %>" method="post">
        	<input type="hidden" name="id" value="<%=dto.getId()%>">
        	<table>
                <tr>
                    <th align="left">Roll No :-</th>
                    <td>
                        <input type="text" name="rollNo" placeholder="Enter Roll No." maxlength="5"
                               value="<%=ServletUtility.getParameter("rollNo", request)%>">&nbsp;
                    </td>
                    <td><input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_GO%>"></td>
                    <td style="position: fixed;">
                        <font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font>
                    </td>
                </tr>
            </table>
            <br>

            <%
                int physics = DataUtility.getInt(DataUtility.getStringData(dto.getPhysics()));
                int chemistry = DataUtility.getInt(DataUtility.getStringData(dto.getChemistry()));
                int maths = DataUtility.getInt(DataUtility.getStringData(dto.getMaths()));

                int total = physics + chemistry + maths;
                float percentage = (float) total / 3;
                percentage = Float.parseFloat(new DecimalFormat("##.##").format(percentage));

                if (dto.getRollNo() != null && dto.getRollNo().trim().length() > 0) {
            %>

            <table border="1" style="border: groove; width: 35%">
                <tr>
                    <td align="center" style="background-color: #fdebc5; color: maroon;">
                        <h2>Rays Technologies, Indore</h2>
                    </td>
                </tr>
            </table>

            <table border="1" style="border: groove; width: 35%">
                <tr>
                    <td align="center" style="width: 15%">Name</td>
                    <th align="center" style="width: 35%; text-transform: capitalize;"><%=DataUtility.getStringData(dto.getName())%></th>

                    <td align="center" style="width: 15%">Roll No</td>
                    <th align="center" style="width: 25%; text-transform: uppercase;"><%=DataUtility.getStringData(dto.getRollNo())%></th>
                </tr>
                <tr>
                    <td align="center" style="width: 15%">Status</td>
                    <th align="center" style="width: 35%">Regular</th>

                    <td align="center" style="width: 15%">Course</td>
                    <th align="center" style="width: 25%; text-transform: uppercase;"><%=DataUtility.getStringData(coursebean.getCourseName())%></th>
                </tr>
            </table>

            <table border="1" style="border: groove; width: 35%">
                <tr style="background-color: #e6e6e485;">
                    <th align="center" style="width: 25%">Subject</th>
                    <th align="center" style="width: 25%">Earned Credits</th>
                    <th align="center" style="width: 25%">Total Credits</th>
                    <th align="center" style="width: 25%">Grade</th>
                </tr>

                <tr>
                    <td align="center">Physics</td>
                    <td align="center">
                        <%=physics%>
                        <%
                            if (physics < 33) {
                        %><span style="color: red">*</span><%
                            }
                        %>
                    </td>
                    <td align="center">100</td>
                    <td align="center">
                        <%
                            if (physics > 90 && physics <= 100) { %>A+<%
                            } else if (physics > 80 && physics <= 90) { %>A<%
                            } else if (physics > 70 && physics <= 80) { %>B+<%
                            } else if (physics > 60 && physics <= 70) { %>C+<%
                            } else if (physics > 50 && physics <= 60) { %>C<%
                            } else if (physics >= 33 && physics <= 50) { %>D<%
                            } else if (physics >= 0 && physics < 33) { %><span style="color: red;">F</span><%
                            }
                        %>
                    </td>
                </tr>

                <tr>
                    <td align="center">Chemistry</td>
                    <td align="center">
                        <%=chemistry%>
                        <%
                            if (chemistry < 33) {
                        %><span style="color: red">*</span><%
                            }
                        %>
                    </td>
                    <td align="center">100</td>
                    <td align="center">
                        <%
                            if (chemistry > 90 && chemistry <= 100) { %>A+<%
                            } else if (chemistry > 80 && chemistry <= 90) { %>A<%
                            } else if (chemistry > 70 && chemistry <= 80) { %>B+<%
                            } else if (chemistry > 60 && chemistry <= 70) { %>C+<%
                            } else if (chemistry > 50 && chemistry <= 60) { %>C<%
                            } else if (chemistry >= 33 && chemistry <= 50) { %>D<%
                            } else if (chemistry >= 0 && chemistry < 33) { %><span style="color: red;">F</span><%
                            }
                        %>
                    </td>
                </tr>

                <tr>
                    <td align="center">Maths</td>
                    <td align="center">
                        <%=maths%>
                        <%
                            if (maths < 33) {
                        %><span style="color: red">*</span><%
                            }
                        %>
                    </td>
                    <td align="center">100</td>
                    <td align="center">
                        <%
                            if (maths > 90 && maths <= 100) { %>A+<%
                            } else if (maths > 80 && maths <= 90) { %>A<%
                            } else if (maths > 70 && maths <= 80) { %>B+<%
                            } else if (maths > 60 && maths <= 70) { %>C+<%
                            } else if (maths > 50 && maths <= 60) { %>C<%
                            } else if (maths >= 33 && maths <= 50) { %>D<%
                            } else if (maths >= 0 && maths < 33) { %><span style="color: red;">F</span><%
                            }
                        %>
                    </td>
                </tr>
            </table>

            <table border="1" style="border: groove; width: 35%">
                <tr style="background-color: #e6e6e485;">
                    <th align="center" style="width: 25%">Total Marks</th>
                    <th align="center" style="width: 25%">Percentage (%)</th>
                    <th align="center" style="width: 25%">Division</th>
                    <th align="center" style="width: 25%">Result</th>
                </tr>

                <tr>
                    <th align="center">
                        <%=total%>
                        <%
                            if (total < 99 || physics < 33 || chemistry < 33 || maths < 33) {
                        %><span style="color: red;">*</span><%
                            }
                        %>
                    </th>
                    <th align="center"><%=percentage%> %</th>
                    <th align="center">
                        <%
                            if (percentage >= 60 && percentage <= 100) { %>1<sup>st</sup><%
                            } else if (percentage >= 40 && percentage < 60) { %>2<sup>nd</sup><%
                            } else if (percentage >= 0 && percentage < 40) { %>3<sup>rd</sup><%
                            }
                        %>
                    </th>
                    <th align="center">
                        <%
                            if (physics >= 33 && chemistry >= 33 && maths >= 33) {
                        %><span style="color: forestgreen;">Pass</span><%
                            } else {
                        %><span style="color: red;">Fail</span><%
                            }
                        %>
                    </th>
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