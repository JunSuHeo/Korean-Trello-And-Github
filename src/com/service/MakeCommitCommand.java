package com.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.GitDAO;



public class MakeCommitCommand implements Command{
	public CommandForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		CommandForward forward = new CommandForward();
		
		forward.setNextPath("gitCommand.do");
		
		String commit = request.getParameter("commit");
		
		GitDAO.getInstance().insert("test", commit);
		
		return forward;
	}

}
