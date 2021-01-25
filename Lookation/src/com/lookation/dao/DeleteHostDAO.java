package com.lookation.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

public class DeleteHostDAO implements IDeleteHostDAO
{
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		
	}
	
	// 이미 탈퇴했는지 확인 
	@Override
	public int checkHostCode(String hostCode) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT COUNT(*) AS COUNT"
		           + " FROM HOST_PROFILE"
				   + " WHERE HOST_CODE = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, hostCode);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result = rs.getInt("COUNT");
		}
		
		return result;
	}
	
	// 예약내역 존재하는지 확인 
	@Override
	public int checkBook(String hostCode) throws SQLException
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
				   + " JOIN LOC L"
				   + " ON PF.LOC_CODE = L.LOC_CODE"
				   + " WHERE AP.APPLY_DATE > SYSDATE"
				   + " AND L.HOST_CODE=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, hostCode);
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
	
	// 마일리지 남아있는지 확인
	@Override
	public int checkMileage(String hostCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		int mileage = 0;

		// PRC_HOST_MILEAGE(IN, OUT) : IN은 hostCode, OUT 은 마일리지 잔액 String 형태로
		String sql = "{call PRC_HOST_MILEAGE(?, ?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		cstmt.setString(1, hostCode);
		cstmt.registerOutParameter(2, Types.VARCHAR);
		cstmt.execute();
		
		mileage = Integer.parseInt(cstmt.getString(2));
		
		cstmt.close();
		conn.close();
		
		return mileage;
	}

	// 4. 공간 연락처정보 삭제
	@Override
	public int delContact(String hostCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE LOC_CONTACT"
				   + " WHERE LOC_CODE"
				   + " IN (SELECT LOC_CODE"
				   + " FROM LOC"
				   + " WHERE HOST_CODE = ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hostCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 5. 공간 사업자정보 삭제
	@Override
	public int delBizInfo(String hostCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE BIZ_INFO"
				   + " WHERE LOC_CODE"
				   + " IN (SELECT LOC_CODE"
				   + " FROM LOC"
				   + " WHERE HOST_CODE = ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hostCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	

	// 6. LOC_REMOVE(LRM)에 해당 공간 코드들을 삭제된 공간으로 INSERT
	@Override
	public int insertLocRemove(String hostCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		int result = 0;
		String sql = "{call PRC_DEL_LOC_INSERT(?)}";
		
		CallableStatement cstmt = conn.prepareCall(sql);
		cstmt.setString(1, hostCode);
		
		result = cstmt.executeUpdate();
		
		cstmt.close();
		conn.close();
		
		return result;
	}
	

	// 7. 환전계좌정보 삭제
	@Override
	public int delExchangeInfo(String hostCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE FROM HOST_EXCHANGE_BANK_INFO"
				   + " WHERE HOST_BANK_NUMBER"
				   + " IN (SELECT HOST_BANK_NUMBER"
				   + " FROM HOST_BANK_INFO"
				   + " WHERE HOST_CODE=?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hostCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 호스트 계좌정보 삭제
	@Override
	public int delBankInfo(String hostCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE FROM HOST_BANK_INFO"
				   + " WHERE HOST_CODE=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hostCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 호스트 프로필정보 삭제
	@Override
	public int delProfile(String hostCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE FROM HOST_PROFILE"
				   + " WHERE HOST_CODE= ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hostCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 탈퇴호스트 테이블에 추가
	@Override
	public int insertDelHost(String hostCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "INSERT INTO HOST_WITHDRAW(HOST_WITHDRAW_CODE"
				   + ", HOST_CODE, HOST_WITHDRAW_DATE)"
				   + " VALUES(F_CODE('HW', HW_SEQ.NEXTVAL)"
				   + ", ? ,SYSDATE)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, hostCode);
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

}
