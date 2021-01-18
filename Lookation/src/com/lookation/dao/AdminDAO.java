//AdminDAO.java

package com.lookation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class AdminDAO implements IAdminDAO
{
	// 인터페이스 자료형 기반 속성 구성
	private DataSource dataSource;
		
	// setter 메소드 구성
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	// 인터페이스에서 선언된 메소드 재정의 ------------------------------------------------

	//관리자 로그인
	@Override
	public String loginAdmin(String admin_id, String admin_pw) throws SQLException
	{
		String result = null;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT ADMIN_NICKNAME"
				+ " FROM ADMIN " 
				+ " WHERE ADMIN_ID = ? "
				+ " AND ADMIN_PW = ? ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, admin_id);
		pstmt.setString(2, admin_pw);
	
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
			result = rs.getString("ADMIN_NICKNAME");
		
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
}
