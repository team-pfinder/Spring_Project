package com.lookation.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.lookation.dto.BankInfoDTO;


public interface IBankInfoDAO
{
	/* 이용자 */
	
	// 이용자 계좌 정보 조회
	public ArrayList<BankInfoDTO> memberBankInfoLists() throws SQLException;
	
	// 이용자 계좌 등록
	public int memberBankInfoAdd(BankInfoDTO dto) throws SQLException;
	
	// 이용자 계좌 갯수 카운트
	public int memberBankInfoCount(String identifyCode) throws SQLException;

	
	
	/* 호스트 */
	
	// 호스트 계좌 정보 조회
	public ArrayList<BankInfoDTO> hostBankInfoLists() throws SQLException;
	
	// 호스트 계좌등록
	public int hostBankInfoAdd(BankInfoDTO dto) throws SQLException;

	// 호스트 계좌 갯수 카운트
	public int hostBankInfoCount(String identifyCode) throws SQLException;
	
	
	/* 프로시저 */
	
	// 이용자, 호스트 계좌 정보 삭제
	public void bankInfoRemove(String identify, String sids) throws SQLException;;
	
	
	

}
