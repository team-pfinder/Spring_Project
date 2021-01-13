package com.lookation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


public class DeleteMemberDAO implements IDeleteMemberDAO
{
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	
	// 이미 탈퇴했는지 확인
	@Override
	public int searchMemCode(String memCode) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT COUNT(*) AS COUNT FROM MEMBER_WITHDRAW"
				   + " WHERE MEMBER_CODE = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, memCode);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result = rs.getInt("COUNT");
		}
		
		return result;
	}
	
	// 예약내역 존재하는지 확인 
	@Override
	public int checkBook(String memCode) throws SQLException
	{
		int result = 0;
		Connection conn = dataSource.getConnection();
		String sql = "SELECT COUNT(*) AS COUNT"
				   + " FROM BOOK_LIST B"
				   + " JOIN APPLY_PACKAGE AP"
				   + " ON B.APPLY_PACKAGE_CODE = AP.APPLY_PACKAGE_CODE"
				   + " JOIN PACKAGE P"
				   + " ON AP.PACKAGE_CODE = P.PACKAGE_CODE"
				   + " JOIN PACKAGE_FORM PF"
				   + " ON P.PACKAGE_FORM_CODE = PF.PACKAGE_FORM_CODE"
				   + " WHERE AP.APPLY_DATE > SYSDATE AND B.MEMBER_CODE=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, memCode);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result = rs.getInt("COUNT");
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 환전계좌정보 삭제
	@Override
	public int delExchangeInfo(String memCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE MEMBER_EXCHANGE_BANK_INFO"
			       + " WHERE MEMBER_BANK_NUMBER"
			       + " IN ( SELECT MEMBER_BANK_NUMBER"
			       + " FROM MEMBER_BANK_INFO"
			       + " WHERE MEMBER_CODE=?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 충전신청계좌에서 해당계좌번호로 이루어진것들 찾아서 삭제
	@Override
	public int delLoadReg(String memCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE LOAD_REG_BANK_INFO"
				   + " WHERE MEMBER_BANK_NUMBER"
				   + " IN ( SELECT MEMBER_BANK_NUMBER"
				   + " FROM MEMBER_BANK_INFO"
				   + " WHERE MEMBER_CODE=?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, memCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 멤버계좌정보에서 해당계좌번호 찾아 삭제

	@Override
	public int delBankInfo(String memCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE FROM MEMBER_BANK_INFO WHERE MEMBER_CODE=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, memCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 멤버프로필에서 해당멤버 삭제
	@Override
	public int delProfile(String memCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE FROM MEMBER_PROFILE WHERE MEMBER_CODE=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, memCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 탈퇴이용자 테이블에 추가
	@Override
	public int insertDelTbl(String memCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "INSERT INTO MEMBER_WITHDRAW(MEMBER_WITHDRAW_CODE"
				   + ", MEMBER_CODE, MEMBER_WITHDRAW_DATE)"
				   + " VALUES(F_CODE('MW', MW_SEQ.NEXTVAL), ? ,SYSDATE)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, memCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
}
