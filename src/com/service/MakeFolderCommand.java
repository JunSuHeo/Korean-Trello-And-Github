package com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.GitDAO;

public class MakeFolderCommand implements Command{
public CommandForward execute(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		CommandForward forward = new CommandForward();
		
		String day = request.getParameter("day");
		String parent_num = request.getParameter("parent_num");
		String num = "0";
		String folder_name = request.getParameter("folder_name");
		
		System.out.println(parent_num + "³Ñ¹ö");
		
		List<String> list = GitDAO.getInstance().readDay(day, parent_num);
		
		request.setAttribute("list", list);
		request.setAttribute("day", day);
		request.setAttribute("parent_num", parent_num);
		
		System.out.println(folder_name);
		
		GitDAO.getInstance().make_folder(day, parent_num, folder_name);
		
		forward.setNextPath("viewhistory.do");
		
		return forward;
	}
}