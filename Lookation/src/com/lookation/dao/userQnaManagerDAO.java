package com.lookation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.lookation.dto.adminUserDTO;

public class userQnaManagerDAO implements IuserQnaManager
{
	private DataSource dataSource;

	// setter 메소드 구성
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	// userQnaManager 화면에서 보여지는 qna 리스트 출력 메소드
	@Override
	public ArrayList<adminUserDTO> basicList() throws SQLException
	{
		ArrayList<adminUserDTO> result = new ArrayList<adminUserDTO>();
		
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT QNA_CODE, MEMBER_NICKNAME, MEMBER_EMAIL"
				+ ", LOC_CODE, QNA_CONTENT"
				+ " FROM ADMIN_QNA_BASIC_VIEW";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			adminUserDTO dto = new adminUserDTO();
			dto.setQnaCode(rs.getString("QNA_CODE"));
			dto.setMemberNickname(rs.getString("MEMBER_NICKNAME"));
			dto.setMemberEmail(rs.getString("MEMBER_EMAIL"));
			dto.setLocCode(rs.getString("LOC_CODE"));
			dto.setQnaContent(rs.getString("QNA_CONTENT"));
			
			result.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 상세보기 클릭 시 보여지는 출력 메소드
	@Override
	public adminUserDTO detailList(String qnaCode) throws SQLException
	{
		adminUserDTO result = new adminUserDTO();
		
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT LOC_CODE, QNA_DATE, MEMBER_NICKNAME, QNA_CONTENT"
				+ " FROM ADMIN_QNA_DETAIL_VIEW"
				+ " WHERE QNA_CODE=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, qnaCode);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			result.setLocCode(rs.getString("LOC_CODE"));
			result.setQnaDate(rs.getString("QNA_DATE"));
			result.setMemberNickname(rs.getString("MEMBER_NICKNAME"));
			result.setQnaContent(rs.getString("QNA_CONTENT"));
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// qna 삭제 메소드 ( 삭제 테이블에 추가 )
	@Override
	public int remove(adminUserDTO dto) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "INSERT INTO QNA_REMOVE(QNA_REMOVE_CODE"
				+ ", QNA_CODE, QNA_REMOVE_DATE)"
				+ " VALUES (?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getQnaRemoveCode());
		pstmt.setString(2, dto.getQnaCode());
		pstmt.setString(3, dto.getQnaRemoveDate());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
}
