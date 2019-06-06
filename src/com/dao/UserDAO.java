package com.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDAO {
	
	private DataSource dataFactory;
	
	// �떛湲��넠 �뙣�꽩(lazy holder) https://jeong-pro.tistory.com/86 李멸퀬
	private UserDAO() {
		try {
			Context ctx = new InitialContext();
			dataFactory = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
			
		}catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static class LazyHolder {
		public static final UserDAO INSTANCE = new UserDAO();
	}
	
	public static UserDAO getInstance() {
		return LazyHolder.INSTANCE;
	}
	// �떛湲��넠 �뿬湲곌퉴吏�!
	
	// 鍮꾨�踰덊샇 MD5 �빐�떆 泥섎━ (異쒖쿂: https://offbyone.tistory.com/286 [�돩怨� �떢�� 媛쒕컻�옄])
	public static String encodePassword(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			return byteToHexString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String byteToHexString(byte[] data) {
	    StringBuilder sb = new StringBuilder();
	    for(byte b : data) {
	        sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
	}
	// MD5 �뿬湲곌퉴吏�!
	
	// �쑀�� �젙蹂� 異붽�
	public void insert(String id, String password, String userName) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String encodedPwd = encodePassword(password);
		
		try {
			// 而ㅻ꽖�뀡�쓣 媛��졇�삩�떎.
			con = dataFactory.getConnection();
			
			// 荑쇰━臾�
			String query = "insert into User (id, password, username) values(?, ?, ?)";
			
			pstmt = con.prepareStatement(query);
			
			// 荑쇰━臾몄뿉 媛� �엯�젰
			pstmt.setString(1, id);
			pstmt.setString(2, encodedPwd);
			pstmt.setString(3, userName);
			
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
	
	// 濡쒓렇�씤 泥댄겕
	public int loginCheck(String id, String password) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs;
		
		String encodedPwd = encodePassword(password);
		
		int returnVal = -1;
		
		try {
			// 而ㅻ꽖�뀡�쓣 媛��졇�삩�떎.
			con = dataFactory.getConnection();
			
			String query = "select password from User where id=?";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, id);
			System.out.println(query);
			
			// pstmt.executeQuery()�뒗
			// select �븷�븣 �궗�슜
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // �엯�젰�맂 �븘�씠�뵒�뿉 �빐�떦�븯�뒗 鍮꾨쾲�씠 �엳�뒗 寃쎌슦
				
				String dbPwd = rs.getString("password");// DB�뿉 湲곕줉�맂 password
				
				if(dbPwd.equals(encodedPwd)) {
					returnVal = 1;	// �엯�젰�맂 鍮꾨쾲�씠 留잙뒗 寃쎌슦!
				}else {
					returnVal = 0;	// 鍮꾨쾲�씠 ��由곌꼍�슦
				}
				
			}else { // �엯�젰�맂 �븘�씠�뵒�뿉 �빐�떦�븯�뒗 鍮꾨쾲�씠 �뾾�뒗 寃쎌슦
				returnVal = -1;
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
		System.out.println(returnVal);
		return returnVal;
	}
	
	// �븘�씠�뵒 以묐났 泥댄겕
	public int idCheck(String id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs;
		
		int returnVal = -1;
		
		try {
			// 而ㅻ꽖�뀡�쓣 媛��졇�삩�떎.
			con = dataFactory.getConnection();
			
			String query = "select id from User where id=?";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, id);
			
			// pstmt.executeQuery()�뒗
			// select �븷�븣 �궗�슜
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // �엯�젰�맂 �븘�씠�뵒�뿉 �빐�떦�븯�뒗 鍮꾨쾲�씠 �엳�뒗 寃쎌슦
				returnVal = 1;
			}else { // �엯�젰�맂 �븘�씠�뵒�뿉 �빐�떦�븯�뒗 鍮꾨쾲�씠 �뾾�뒗 寃쎌슦
				returnVal = -1;
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
		
		return returnVal;
	}
}
