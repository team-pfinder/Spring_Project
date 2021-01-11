package com.lookation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.lookation.dto.SampleDTO;


public class SampleDAO implements ISampleDAO
{
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	// 오버라이딩을 통해 해당 객체의 메서드 작업을 입력
	@Override
	public ArrayList<SampleDTO> lists() throws SQLException
	{
		ArrayList<SampleDTO> result = new ArrayList<SampleDTO>();
		
		Connection conn = dataSource.getConnection();
		
		String sql = "sql문";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		// ...
		
		// 1. SELECT
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			//...
		}
		
		// 2. UPDATE, INSERT
		int r = pstmt.executeUpdate();
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

}
