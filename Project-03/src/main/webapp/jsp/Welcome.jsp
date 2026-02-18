<%@page import="in.co.rays.proj3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>Welcome to ORS</title>
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
	

</style>
</head>
<body class="bgColor">
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.WELCOME_CTL%>">
	
	<br> <br> <br>
		<h1 align="center">
			<font size="10px" color="navy">Welcome to ORS</font>
		</h1>
	
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>