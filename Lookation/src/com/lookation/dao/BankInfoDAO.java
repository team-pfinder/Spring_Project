package com.lookation.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.lookation.dto.BankInfoDTO;


public class BankInfoDAO implements IBankInfoDAO
{
	private DataSource dataSource;
	
	// setter 메소드 구성
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	// 이용자 등록된 계좌 리스트 출력
	@Override
	public ArrayList<BankInfoDTO> memberBankInfoLists(String identifyCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();

		ArrayList<BankInfoDTO> result = new ArrayList<BankInfoDTO>();
		
		String sql = "SELECT MEMBER_BANK_NUMBER, MEMBER_BANK, MEMBER_BANK_HOLDER"
				  + " FROM VIEW_MEMBER_BANK_INFO"
				  + " WHERE MEMBER_CODE = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, identifyCode);
		
		
		// 1. SELECT
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			BankInfoDTO bankInfoDTO = new BankInfoDTO();
			
			bankInfoDTO.setBankNumber(rs.getString("MEMBER_BANK_NUMBER"));
			bankInfoDTO.setBank(rs.getString("MEMBER_BANK"));
			bankInfoDTO.setBankHolder(rs.getString("MEMBER_BANK_HOLDER"));
			
			result.add(bankInfoDTO);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	// 호스트 등록된 계좌 리스트 출력
	@Override
	public ArrayList<BankInfoDTO> hostBankInfoLists(String identifyCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();

		ArrayList<BankInfoDTO> result = new ArrayList<BankInfoDTO>();
		
		String sql = "SELECT *"
				  + " FROM VIEW_HOST_BANK_INFO"
				  + " WHERE HOST_CODE = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, identifyCode);
		
		// 1. SELECT
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			BankInfoDTO hostInfoDTO = new BankInfoDTO();
			
			hostInfoDTO.setBankNumber(rs.getString("HOST_BANK_NUMBER"));
			hostInfoDTO.setBank(rs.getString("HOST_BANK"));
			hostInfoDTO.setBankHolder(rs.getString("HOST_BANK_HOLDER"));
			
			result.add(hostInfoDTO);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	@Override
	public int memberBankInfoCount(String identifyCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		int result = 0;
		String sql = "SELECT COUNT(*) AS CNT"
				  + " FROM MEMBER_BANK_INFO"
				  + " WHERE MEMBER_CODE = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, identifyCode);

		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result = rs.getInt("CNT");
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int memberBankInfoAdd(BankInfoDTO dto) throws SQLException
	{
		Connection conn = dataSource.getConnection();

		int result = 0;
		String sql = "INSERT INTO MEMBER_BANK_INFO(MEMBER_BANK_NUMBER, MEMBER_CODE, MEMBER_BANK, MEMBER_BANK_HOLDER)" +
				    " VALUES(?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getBankNumber());
		pstmt.setString(2, dto.getIdentifyCode());
		pstmt.setString(3, dto.getBank());
		pstmt.setString(4, dto.getBankHolder());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int hostBankInfoAdd(BankInfoDTO dto) throws SQLException
	{
		Connection conn = dataSource.getConnection();

		int result = 0;
		String sql = "INSERT INTO HOST_BANK_INFO(HOST_BANK_NUMBER, HOST_CODE, HOST_BANK, HOST_BANK_HOLDER)" +
				    " VALUES(?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getBankNumber());
		pstmt.setString(2, dto.getIdentifyCode());
		pstmt.setString(3, dto.getBank());
		pstmt.setString(4, dto.getBankHolder());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int hostBankInfoCount(String identifyCode) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		int result = 0;
		String sql = "SELECT COUNT(*) AS CNT"
				  + " FROM HOST_BANK_INFO"
				  + " WHERE HOST_CODE = '" + identifyCode +"'";	// ※주의 테스트용 코드명 대입할때 따옴표 들어가야함 
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result = rs.getInt("CNT");
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public void bankInfoRemove(String identify, String bankNumber) throws SQLException
	{
		
		Connection conn = dataSource.getConnection();

		String sql = "{call PRC_BANKINFO_DELETE(?, ?)}";
		CallableStatement cstmt = conn.prepareCall(sql);
		cstmt.setString(1, identify);
		cstmt.setString(2, bankNumber);
		int result = cstmt.executeUpdate();
		if (result > 0)
		{
			System.out.println(">> 프로시저 호출을 통해 계좌정보들 삭제 완료~!!!");
		}

		cstmt.close();
		conn.close();
		
	}

}

