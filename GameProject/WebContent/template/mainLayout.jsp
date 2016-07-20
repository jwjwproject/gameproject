<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<title>La Casa - Real Estate HTML5 Home Page Template</title>
	<meta charset="utf-8">
	<meta name="author" content="pixelhint.com">
	<meta name="description" content="La casa free real state fully responsive html5/css3 home page website template"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0" />
	<link rel="stylesheet" type="text/css" href="/include/css/main.css">
	<link rel="stylesheet" type="text/css" href="/include/css/reset.css">
	<link rel="stylesheet" type="text/css" href="/include/css/responsive.css">

	<script type="text/javascript" src="/include/js/jquery.js"></script>
	<script type="text/javascript" src="/include/js/main.js"></script>
</head>
<body>
<!-- Header -->
	<tiles:insertAttribute name="header" />  
		<!-- Page Wrapper -->
	<tiles:insertAttribute name="body" />  
		<!-- Wrapper Ends Here -->
<!-- Copyright -->
	 <tiles:insertAttribute name="footer" />  
		
</body>
</html>