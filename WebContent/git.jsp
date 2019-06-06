<%@page import="com.dao.BoardDAO" %>
<%@page import = "java.util.List" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">


</head>
<body>
	<a href="makehistory.do">히스토리 생성</a>
	<a href="deletehistory.do">히스토리 삭제</a><br />
	
	<table width="500" border="0">
	
	<c:forEach items="${list}" var="day">
	<c:set var="parent_num" value="0" />
		<tr>
			<td><a href="viewhistory.do?day=${day}&parent_num=${0}" class="history">${day}</a></td>
		</tr>
	</c:forEach>
	
	</table>
</body>
</html>