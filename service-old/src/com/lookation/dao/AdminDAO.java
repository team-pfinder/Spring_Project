//AdminDAO.java

package com.lookation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class AdminDAO implements IAdminDAO
{
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public String findPassword(String adminId) throws SQLException
	{
		String sql = "SELECT ADMIN_PW FROM ADMIN WHERE ADMIN_ID = ?";
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setString(1, adminId);
			try (ResultSet rs = pstmt.executeQuery())
			{
				return rs.next() ? rs.getString("ADMIN_PW") : null;
			}
		}
	}

	@Override
	public String findNickname(String adminId) throws SQLException
	{
		String sql = "SELECT ADMIN_NICKNAME FROM ADMIN WHERE ADMIN_ID = ?";
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setString(1, adminId);
			try (ResultSet rs = pstmt.executeQuery())
			{
				return rs.next() ? rs.getString("ADMIN_NICKNAME") : null;
			}
		}
	}

	@Override
	public void updatePassword(String adminId, String hashedPassword) throws SQLException
	{
		String sql = "UPDATE ADMIN SET ADMIN_PW = ? WHERE ADMIN_ID = ?";
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setString(1, hashedPassword);
			pstmt.setString(2, adminId);
			pstmt.executeUpdate();
		}
	}
}
