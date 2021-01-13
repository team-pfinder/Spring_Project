package com.lookation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.lookation.dto.HostBankAccountInfoDTO;
import com.lookation.dto.HostBankInfoAddDTO;
import com.lookation.dto.MemberBankAccountInfoDTO;
import com.lookation.dto.MemberBankInfoAddDTO;

public class BankAccountDAO implements IBankAccountDAO
{
	private DataSource dataSource;
	
	// setter 메소드 구성
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	// 이용자 등록된 계좌 리스트 출력
	@Override
	public ArrayList<MemberBankAccountInfoDTO> memberBankInfoLists() throws SQLException
	{
		Connection conn = dataSource.getConnection();

		ArrayList<MemberBankAccountInfoDTO> result = new ArrayList<MemberBankAccountInfoDTO>();
		
		String sql = "SELECT MEMBER_BANK_NUMBER, MEMBER_BANK, MEMBER_BANK_HOLDER"
				  + " FROM VIEW_MEMBER_BANK_INFO"
				  + " WHERE MEMBER_CODE = 'M000001'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		// 1. SELECT
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			MemberBankAccountInfoDTO bankAccountInfoDTO = new MemberBankAccountInfoDTO();
			
			bankAccountInfoDTO.setMemberBankNumber(rs.getString("MEMBER_BANK_NUMBER"));
			bankAccountInfoDTO.setMemberBank(rs.getString("MEMBER_BANK"));
			bankAccountInfoDTO.setMemberBankHolder(rs.getString("MEMBER_BANK_HOLDER"));
			
			result.add(bankAccountInfoDTO);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	// 호스트 등록된 계좌 리스트 출력
	@Override
	public ArrayList<HostBankAccountInfoDTO> hostBankInfoLists() throws SQLException
	{
		Connection conn = dataSource.getConnection();

		ArrayList<HostBankAccountInfoDTO> result = new ArrayList<HostBankAccountInfoDTO>();
		
		String sql = "SELECT *"
				  + " FROM VIEW_HOST_BANK_INFO"
				  + " WHERE HOST_CODE = 'H000006'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		// 1. SELECT
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			HostBankAccountInfoDTO hostAccountInfoDTO = new HostBankAccountInfoDTO();
			
			hostAccountInfoDTO.setHostBankNumber(rs.getString("HOST_BANK_NUMBER"));
			hostAccountInfoDTO.setHostBank(rs.getString("HOST_BANK"));
			hostAccountInfoDTO.setHostBankHolder(rs.getString("HOST_BANK_HOLDER"));
			
			result.add(hostAccountInfoDTO);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int memberBankInfoAdd(MemberBankInfoAddDTO dto) throws SQLException
	{
		Connection conn = dataSource.getConnection();

		int result = 0;
		String sql = "INSERT INTO MEMBER_BANK_INFO(MEMBER_BANK_NUMBER, MEMBER_CODE, MEMBER_BANK, MEMBER_BANK_HOLDER)" +
				    " VALUES(?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getMemberBankNumber());
		pstmt.setString(2, dto.getMemberCode());
		pstmt.setString(3, dto.getMemberBankNumber());
		pstmt.setString(4, dto.getMemberBankHolder());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int hostBankInfoAdd(HostBankInfoAddDTO dto) throws SQLException
	{
		Connection conn = dataSource.getConnection();

		int result = 0;
		String sql = "INSERT INTO MEMBER_BANK_INFO(HOST_BANK_NUMBER, HOST_CODE, HOST_BANK, HOST_BANK_HOLDER)" +
				    " VALUES(?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getHostBankNumber());
		pstmt.setString(2, dto.getHostCode());
		pstmt.setString(3, dto.getHostBankNumber());
		pstmt.setString(4, dto.getHostBankHolder());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}



}
