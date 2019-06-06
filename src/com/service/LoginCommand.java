package com.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDAO;

public class LoginCommand implements Command {
	
	public CommandForward execute(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		CommandForward forward = new CommandForward();
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// DB�뿉�꽌 �궗�슜�옄 �젙蹂대�� 遺덈윭�� �씠 �젙蹂닿� �엳�뒗吏� �솗�씤.
		int checkVal = UserDAO.getInstance().loginCheck(id, password);
		
		if(checkVal == 1) { // 濡쒓렇�씤 �꽦怨�
			
			// �꽭�뀡�뿉 �떎�씠�뵒瑜� ���옣
			HttpSession session = request.getSession();
			
			forward.setRedirect(true);
			session.setAttribute("sessionId", id);
			forward.setNextPath("board.jsp");
			
			// TODO : 濡쒓렇�씤 �꽦怨� �썑 硫붿씤�솕硫댁쑝濡� �씠�룞
			
		}else { // 濡쒓렇�씤 �떎�뙣
			request.setAttribute("login failed", true);
			
			// �떎�떆 濡쒓렇�씤 �럹�씠吏� �쓣�썙以�
			forward.setRedirect(false);
			forward.setNextPath("loginForm.jsp");
		}
		
		return forward;
	}
	
}