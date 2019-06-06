package com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.GitDAO;

public class GitCommand implements Command {

	@Override
	public CommandForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		CommandForward forward = new CommandForward();
		
		List<String> list = GitDAO.getInstance().readDay();
		
		request.setAttribute("list", list);
		forward.setNextPath("git.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
