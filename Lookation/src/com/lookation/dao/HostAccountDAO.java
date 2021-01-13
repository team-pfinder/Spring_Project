package com.lookation.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.lookation.dto.AccountDTO;

public class HostAccountDAO implements IAccountDAO
{
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public boolean add(AccountDTO account) throws SQLException
	{
		boolean result = false;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "{CALL CREATE_HOST_ACCOUNT(?, ?, ?, ?, ?)}";
		CallableStatement cstmt = conn.prepareCall(sql);
		cstmt.setString(1, account.getEmail());
		cstmt.setString(2, account.getPw());
		cstmt.setString(3, account.getNick());
		cstmt.setString(4, account.getName());
		cstmt.setString(5, account.getTel());
	
		result = cstmt.execute();
		
		cstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int countEmail(String email) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT COUNT(*) AS COUNT"
				+ " FROM HOST_PROFILE"
				+ " WHERE HOST_EMAIL=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, email);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next())
			result = rs.getInt("COUNT");
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int countNick(String nick) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT COUNT(*) AS COUNT"
				+ " FROM HOST_PROFILE"
				+ " WHERE HOST_NICKNAME=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, nick);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next())
			result = rs.getInt("COUNT");
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

}
