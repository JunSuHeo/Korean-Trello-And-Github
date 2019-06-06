package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Command;
import com.service.CommandForward;
import com.service.IdCheckCommand;
import com.service.JoinCommand;
import com.service.LoginCommand;

@WebServlet({"/main.do","/loginCommand.do","/join.do","/joinCommand.do","/idChkCommand.do"})
public class LoginController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess( request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess( request, response);
	}
	
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
	    
		String requestURI = request.getRequestURI();
	    String contextPath = request.getContextPath();
	    String com = requestURI.substring(contextPath.length());
	    
	    CommandForward forward = null;
	    Command command = null;
	    
	    // 硫붿씤�솕硫�
	    if(com.equals("/main.do")) {
	    	forward = new CommandForward();
	    	forward.setRedirect(false);
	    	forward.setNextPath("loginForm.jsp");
	    }
	    // �쉶�썝媛��엯 �솕硫�
	    else if(com.equals("/join.do")) {
	    	forward = new CommandForward();
	    	forward.setRedirect(false);
	    	forward.setNextPath("joinForm.jsp");
	    }
	    // 濡쒓렇�씤 泥섎━
	    else if(com.equals("/loginCommand.do")) {
	    	command = new LoginCommand();
	    	forward = command.execute(request, response);
	    }
	    // �쉶�썝媛��엯 泥섎━
	    else if(com.equals("/joinCommand.do")) {
	    	command = new JoinCommand();
	    	forward = command.execute(request, response);
	    }
	    // �븘�씠�뵒 以묐났 �솗�씤 泥섎━
	    else if(com.equals("/idChkCommand.do")) {
	    	command = new IdCheckCommand();
	    	forward = command.execute(request, response);
	    }
	    
	    if(forward != null) {
	    	if(forward.isRedirect()) {
	    		response.sendRedirect(forward.getNextPath());
	    	} else {
	    		RequestDispatcher dis = request.getRequestDispatcher(forward.getNextPath());
			    dis.forward(request, response);
	    	}
	    }
	}
}