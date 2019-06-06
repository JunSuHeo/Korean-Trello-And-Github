package com.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.GitDAO;

public class MakeCodeCommand implements Command{
public CommandForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		CommandForward forward = new CommandForward();
		
		String day = request.getParameter("day");
		String parent_num = request.getParameter("parent_num");
		
		request.setAttribute("day", day);
		request.setAttribute("parent_num", parent_num);
		
		forward.setNextPath("makecode.jsp");
		
		return forward;
	}
}
