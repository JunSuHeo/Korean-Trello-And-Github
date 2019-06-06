package com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.GitDAO;

public class ViewHistoryCommand implements Command{
	public CommandForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		CommandForward forward = new CommandForward();
		
		String day = request.getParameter("day");
		String parent_num = request.getParameter("parent_num");
		String name = request.getParameter("name");
		String num = "0";
		String type = null;
		System.out.println(parent_num + "패런트넘버");
		
		if(name != null) {
			num = GitDAO.getInstance().getNum(name, day, parent_num);
		}
		
		System.out.println(num + "넘버");
		
		List<String> list = GitDAO.getInstance().readDay(day, num);
		
		request.setAttribute("list", list);
		request.setAttribute("day", day);
		request.setAttribute("parent_num", num);
		
		if(name != null) {
			type = GitDAO.getInstance().getType(name, day, parent_num);
		}
		
		System.out.println(type);
		
		if(type == null || type.equals("1")) {
			forward.setNextPath("viewHistory.jsp");
		}
		else {
			String code = GitDAO.getInstance().getCode(name, day, parent_num);
			request.setAttribute("name", name);
			request.setAttribute("code", code);
			forward.setNextPath("viewCode.jsp");
		}
		
		return forward;
	}
}