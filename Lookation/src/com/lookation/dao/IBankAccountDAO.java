package com.lookation.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.lookation.dto.HostBankAccountInfoDTO;
import com.lookation.dto.HostBankInfoAddDTO;
import com.lookation.dto.MemberBankAccountInfoDTO;
import com.lookation.dto.MemberBankInfoAddDTO;

public interface IBankAccountDAO
{

	/* 이용자 */
	
	// 이용자 계좌 정보 조회
	public ArrayList<MemberBankAccountInfoDTO> memberBankInfoLists() throws SQLException;
	
	// 이용자 계좌 등록
	public int memberBankInfoAdd(MemberBankInfoAddDTO dto) throws SQLException;
	
	
	/* 호스트 */
	
	// 호스트 계좌 정보 조회
	public ArrayList<HostBankAccountInfoDTO> hostBankInfoLists() throws SQLException;
	
	// 호스트 계좌등록
	public int hostBankInfoAdd(HostBankInfoAddDTO dto) throws SQLException;

}
