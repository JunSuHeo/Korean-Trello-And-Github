<%@page import="com.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>회원가입</title>
    
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
    <style type="text/css">
		.join-container{
			height: 370px;
		    margin-top: 5%;
		    margin-bottom: 5%;
		    width: 500px;
		}
		.join-form{
		    padding: 5%;
		    box-shadow: 0 5px 8px 0 rgba(0, 0, 0, 0.2), 0 9px 26px 0 rgba(0, 0, 0, 0.19);
		}
		.join-form h3{
		    text-align: center;
		    color: #333;
		}
		
		.join-container form{
		    padding: 10%;
		}
		.btnSubmit
		{
		    width: 50%;
		    border-radius: 1rem;
		    padding: 1.5%;
		    border: none;
		    cursor: pointer;
		}
		.join-form .btnSubmit{
		    font-weight: 600;
		    color: #fff;
		    background-color: #0062cc;
		}
		
		.join-form .btnJoin{
		    color: #0062cc;
		    font-weight: 600;
		    text-decoration: none;
		}
    </style>
    
    <script type="text/javascript">
    
    	var idPattern = /[A-Za-z0-9+]+$/;
    	
    	function checkValue()
    	{
    		inputForm = eval("document.joinInfo");
    		if(!inputForm.username.value)
    		{
    			alert("사용자 이름을 입력해주세요");
    			inputForm.id.focus();
    			return false;
    		}
    		if(!inputForm.id.value)
    		{
    			alert("아이디를 입력해주세요");
    			inputForm.id.focus();
    			return false;
    		}
    		if(!inputForm.password.value)
    		{
    			alert("비밀번호를 입력해주세요");
    			inputForm.password.focus();
    			return false;
    		}
    		if(!inputForm.passwordRepeat.value)
    		{
    			alert("비밀번호를 한번 더 입력해주세요");
    			inputForm.passwordRepeat.focus();
    			return false;
    		}
    		if(inputForm.password.value != inputForm.passwordRepeat.value)
    		{
    			alert("비밀번호가 확인 란과 일치하지 않습니다");
    			inputForm.password.focus();
    			return false;
    		}
    		
    		// 아이디 형식 검사(길이 5 이상, 영어, 숫자만 포함)
    		if(inputForm.id.value.length <= 4)
    		{
    			idComment.innerHTML = "아이디는 5글자 이상이여야 합니다.";
    			inputForm.id.focus();
    			return false;
    		}else if(!idPattern.test(inputForm.id.value))
			{
				idComment.innerHTML = "아이디는 영어, 숫자만 들어갈 수 있습니다.";
				inputForm.id.focus();
    			return false;
			}
    		
    		//아래는 
    		if(document.idChkForm.idChk.value == 0)
	   		{
    			idComment.innerHTML = "이미 존재하는 아이디 입니다.";
    			inputForm.id.focus();
    			return false;
	   		}
    	}
    	
    	function inputIdChk()
    	{
    		// 입력한 아이디 값
			var idVal = document.joinInfo.id.value;
			
    		// 아이디 확인 폼
    		var frm = document.idChkForm;
			
			if(idVal)
			{
				frm.id.value = idVal;
				frm.target = "idChkFrame";
				frm.action = "idChkCommand.do";
				frm.submit();
			}
    	}
    	
    	function _inputIdChk()
    	{
    		// 아이디 입력창 및 추가 내용
    		var idComment = document.getElementById("idComment");
    		
    		// 입력한 아이디 값
    		var idVal = document.joinInfo.id.value;
    		
    		// 아이디 중복 체크 값
    		var idChkVal = document.idChkForm.idChk.value;
    		
    		if(idVal)
    		{
				if(idVal.length <= 4)
	   			{
	   				idComment.innerHTML = "아이디는 5글자 이상이여야 합니다.";
	   			}else if(!idPattern.test(idVal))
	   			{
	   				idComment.innerHTML = "아이디는 영어, 숫자만 들어갈 수 있습니다.";
	   			}else if(idChkVal == 0)
		   		{
		    		idComment.innerHTML = "이미 존재하는 아이디 입니다.";
		   		}else
		   		{
		   			idComment.innerHTML = "사용 가능한 아이디 입니다.";
		   		}
    		}
    	}
    	
    	function onIdChkFrameLoad()
    	{
       		var frm = parent.document.idChkForm;
			var idChk = frm.idChk;
			
			// servlet으로 부터 id 확인 값 가져옴
    		var idCheckVal = <%=(Integer)request.getAttribute("idChk")%>
			
			// id 확인 값이 ""이 되는 경우 경우 idChk.value를 수정하지 않음
    		if(idCheckVal == 0 || idCheckVal == 1)
    		{
    			idChk.value = idCheckVal;
    		}
			
    		parent._inputIdChk();
    	}
    	
    </script>
    
</head>

<body onload='onIdChkFrameLoad()'>

    <div class="container join-container">
        <div class="join-form">
        	<h3 color="gray" size="6">회원가입</h3>
        
	        <form name="joinInfo" action="joinCommand.do" method="post" onsubmit="return checkValue()">
	        	<div class="form-group">
	        		사용자 이름<br>
	        		<input type="text" name="username" class="form-control"/>
	        	</div>
	        	
	        	<div class="form-group">
	        		아이디<br>
	        		<input type="text" name="id" class="form-control" onkeyup="inputIdChk()"/>
	        		<small style="color:red" id="idComment"></small>
	        	</div>
	        	
	        	<div class="form-group">
	        		비밀번호<br>
	        		<input type="password" name="password" class="form-control"/>
	        	</div>
	        	
	        	<div class="form-group">
	        		비밀번호 확인<br>
	        		<input type="password" name="passwordRepeat" class="form-control"/>
	        	</div>
	        	
	        	<div class="form-group">
	        		<input type="submit" class="btnSubmit" value="가입하기"/>
	        	</div>
	        	
	            <div class="form-group">
	            	아이디가 이미 있으신가요? <a class="btnLogin" href="main.do">로그인</a>
	            </div>
	        </form>
        </div>
        
        <%
        	// 아이디나 비밀번호가 틀릴경우 Alert로 표시
			Object failed = request.getAttribute("login failed");
			if(failed != null && ((Boolean)failed))
			{
	     		out.println("<script type='text/javascript'>");
	     		out.println("alert('아이디나 비밀번호가 유효하지 않습니다!');");
	     		out.println("</script>");
	     		out.flush();
			}
		%>
        
    </div>
    
    <!-- 아이디 중복체크 -->
    <form name="idChkForm" style="display:none" method="post">
    	<input type="hidden" name="id"/>
    	<input type="hidden" name="idChk" value=0/>
	</form>
    <iframe name='idChkFrame' style='width:0px; height:0px; display:none'>
    </iframe>
</body>

</html>