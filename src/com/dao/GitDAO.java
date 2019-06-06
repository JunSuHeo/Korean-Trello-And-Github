package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GitDAO {
	
	private DataSource dataFactory;
	
	private GitDAO() {
		try {
			Context ctx = new InitialContext();
			dataFactory = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
			
		}catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static class LazyHolder {
		public static final GitDAO INSTANCE = new GitDAO();
	}
	
	public static GitDAO getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	public void insert(String boardname, String commit) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		Date d = new Date();
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String day = form.format(d).toString();
		
		System.out.println(day);
		
		try {
			// 而ㅻ꽖�뀡�쓣 媛��졇�삩�떎.
			con = dataFactory.getConnection();
			
			// 荑쇰━臾�
			String query = "insert into history (boardname, day, commit) values(?, ?, ?)";
			System.out.println(commit);
			pstmt = con.prepareStatement(query);
			
			// 荑쇰━臾몄뿉 媛� �엯�젰
			pstmt.setString(1, boardname);
			pstmt.setString(2, day);
			pstmt.setString(3, commit);
			
			// 荑쇰━臾� �떎�뻾
			// pstmt.executeUpdate()�뒗
			// Insert, update, delete, create, drop�븷�븣 �궗�슜 
			int n = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		renum("history");
	}
	
	public void make_folder(String day, String parent_num, String folder_name) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		Date d = new Date();
		
		try {
			// 而ㅻ꽖�뀡�쓣 媛��졇�삩�떎.
			con = dataFactory.getConnection();
			
			// 荑쇰━臾�
			String query = "insert into folder (day, type, name, boardname, parent_num) values(?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);
			
			// 荑쇰━臾몄뿉 媛� �엯�젰
			pstmt.setString(1, day);
			pstmt.setString(2, "1");
			pstmt.setString(3, folder_name);
			pstmt.setString(4, "test");
			pstmt.setString(5, parent_num);
			
			// 荑쇰━臾� �떎�뻾
			// pstmt.executeUpdate()�뒗
			// Insert, update, delete, create, drop�븷�븣 �궗�슜 
			int n = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void make_code(String day, String parent_num, String code_name, String code) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 而ㅻ꽖�뀡�쓣 媛��졇�삩�떎.
			con = dataFactory.getConnection();
			
			// 荑쇰━臾�
			String query = "insert into folder (day, type, name, boardname, code, parent_num) values(?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);
			
			// 荑쇰━臾몄뿉 媛� �엯�젰
			pstmt.setString(1, day);
			pstmt.setString(2, "0");
			pstmt.setString(3, code_name);
			pstmt.setString(4, "test");
			pstmt.setString(5, code);
			pstmt.setString(6, parent_num);
			
			// 荑쇰━臾� �떎�뻾
			// pstmt.executeUpdate()�뒗
			// Insert, update, delete, create, drop�븷�븣 �궗�슜 
			int n = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void renum(String table) {
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			con = dataFactory.getConnection();
			
			String query1 = "SET @CNT = 0;";
			String query2 = "UPDATE " + table + " set " + table + ".num = @CNT:=@CNT+1;";
			
			System.out.println(query1);
			
			pstmt1 = con.prepareStatement(query1);
			pstmt2 = con.prepareStatement(query2);
			
			pstmt1.executeUpdate();
			pstmt2.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt1 != null)pstmt1.close();
				if(pstmt2 != null)pstmt2.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<String> readDay()
	{
		List<String> day = new ArrayList<String>();
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dataFactory.getConnection();
			
			String query = "SELECT day FROM history;";
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				day.add(rs.getString("day"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return day;
	}

	public void makeFile(String path, String day, boolean type, String name, String boardname, String code)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 而ㅻ꽖�뀡�쓣 媛��졇�삩�떎.
			con = dataFactory.getConnection();
			
			// 荑쇰━臾�
			String query = "insert into folder (path, day, type, name, boardname, code) values(?, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(query);
			
			// 荑쇰━臾몄뿉 媛� �엯�젰
			pstmt.setString(1, path);
			pstmt.setString(2, day);
			pstmt.setBoolean(3, type);
			pstmt.setString(4, name);
			pstmt.setString(5, boardname);
			pstmt.setString(6, code);
			
			// 荑쇰━臾� �떎�뻾
			// pstmt.executeUpdate()�뒗
			// Insert, update, delete, create, drop�븷�븣 �궗�슜 
			int n = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		renum("folder");
	}
	
	public List<String> readDay(String day, String num)
	{
		List<String> name = new ArrayList<String>();
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dataFactory.getConnection();
			
			String query = "SELECT name FROM folder where day = '"+ day +"'&&parent_num = " + num + ";";
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				name.add(rs.getString("name"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return name;
	}
	
	public List<String> readParentNum(String parent_num)
	{
		List<String> name = new ArrayList<String>();
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dataFactory.getConnection();
			
			String query = "SELECT name FROM folder where num = "+ parent_num +";";
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				name.add(rs.getString("name"));				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return name;
	}
	
	public String getParentNum(String filename)
	{
		List<String> name = new ArrayList<String>();
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String parent_num = null;
		
		try {
			con = dataFactory.getConnection();
			
			String query = "SELECT num FROM folder where name = '"+ filename +"';";			
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				parent_num = rs.getString("parent_num");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return parent_num;
	}
	public String getType(String name, String day, String parent_num)
	{
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String type = null;
		
		try {
			con = dataFactory.getConnection();
			
			String query = "SELECT type FROM folder where name = '"+ name +"' && day = '"  + day + "' && parent_num = " + parent_num + ";";	
			System.out.println(query);
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				type = rs.getString("type");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return type;
	}
	
	public String getCode(String name, String day, String parent_num)
	{
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String code = null;
		
		try {
			con = dataFactory.getConnection();
			
			String query = "SELECT code FROM folder where name = '"+ name +"' && day = '"  + day + "' && parent_num = " + parent_num + ";";	
			System.out.println(query);
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				code = rs.getString("code");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}
	
	public String getNum(String name, String day, String parent_num)
	{
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String num = null;
		
		try {
			con = dataFactory.getConnection();
			String query;
			if(name == null) {
				query = "select num from folder where day = '" + day + "' && parent_num = " + parent_num + ";";
			}
			else {
				query = "select num from folder where name = '" + name + "' && day = '" + day + "' && parent_num = " + parent_num + ";";
			}
			
			System.out.println(query);
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				num = rs.getString("num");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return num;
	}
}
