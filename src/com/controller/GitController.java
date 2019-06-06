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
import com.service.CreateCodeCommand;
import com.service.GitCommand;
import com.service.BoardCommand;
import com.service.MakeHistoryCommand;
import com.service.ViewFolderCommand;
import com.service.MakeCommitCommand;
import com.service.ViewHistoryCommand;
import com.service.MakeFolderCommand;
import com.service.MakeCodeCommand;

/**
 * Servlet implementation class BoardController
 */
@WebServlet({"/createcode.do", "/viewfolder.do", "/gitCommand.do", "/board.do","/makehistory.do", "/makecommit.do", "/viewhistory.do", "/makefolder.do", "/makecode.do"})
public class GitController extends HttpServlet {
	private static final long serialVersionUID = 1956201963237509938L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    
		String requestURI = request.getRequestURI();
	    String contextPath = request.getContextPath();
	    String com = requestURI.substring(contextPath.length());
	    
	    CommandForward forward = null;
	    Command command = null;
	    
	    if(com.equals("/board.do")) {
	    	forward = new CommandForward();
	    	forward.setRedirect(false);
	    	forward.setNextPath("board.jsp");
	    }
	    if(com.equals("/makehistory.do")) {
	    	command = new MakeHistoryCommand();
	    	forward = command.execute(request, response);
	    }
	    
	    if(com.equals("/makecommit.do")) {
	    	command = new MakeCommitCommand();
	    	forward = command.execute(request, response);
	    }
	    if(com.equals("/viewhistory.do")) {
	    	command = new ViewHistoryCommand();
	    	forward = command.execute(request, response);
	    }
	    if(com.equals("/makefolder.do")) {
	    	command = new MakeFolderCommand();
	    	forward = command.execute(request, response);
	    }
	    if(com.equals("/makecode.do")) {
	    	command = new MakeCodeCommand();
	    	forward = command.execute(request, response);
	    }
	    if(com.equals("/gitCommand.do")) {
	    	command = new GitCommand();
	    	forward = command.execute(request, response);
	    }
	    if(com.equals("/viewfolder.do")) {
	    	command = new ViewFolderCommand();
	    	forward = command.execute(request, response);
	    }
	    if(com.equals("/createcode.do")) {
	    	command = new CreateCodeCommand();
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
