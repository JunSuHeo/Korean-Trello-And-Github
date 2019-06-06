<%@page import="com.dao.BoardDAO" %>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import = "java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	#makefolder{
		display : none;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<a onclick="fun()">폴더 생성</a>
	<a href="makecode.do?day=${day}&parent_num=${parent_num}">코드 생성</a><br />
	
	<div id="makefolder">
		<form action="makefolder.do?day=${day}&parent_num=${parent_num}" method="post">
			폴더명 : <input type="text" name="folder_name" />
			<input type="submit" value="생성" />
		</form>
	</div>
	
	<script>
		function fun(){
			var x = document.getElementById("makefolder");
			if(x.style.display === "none"){
				x.style.display = "block";
			}else{
				x.style.display = "none";
			}
		}
	</script>
	
	<table width="500" border="0">
	
	<c:forEach items="${list}" var="name">
	<c:set var="day" value="${day}" />
	<c:set var="parent_num" value="${parent_num}" />
		<tr>
			<td><a href="viewhistory.do?day=${day}&parent_num=${parent_num}&name=${name}" class="history">${name}</a></td>
		</tr>
	</c:forEach>
	
	</table>
</body>
</html>