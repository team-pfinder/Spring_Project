package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.MemberExchangeHistoryDTO;
import com.lookation.dto.MemberLoadHistoryDTO;
import com.lookation.dto.MemberPayHistoryDTO;
import com.lookation.dto.MemberRefundHistoryDTO;

public interface IMemberHistoryListDAO
{
	// 이용자 사용내역 조회
	public ArrayList<MemberPayHistoryDTO> payHistory(String memberCode);
	
	// 이용자 충전내역 조회
	public ArrayList<MemberLoadHistoryDTO> loadHistory(String memberCode);
	
	// 이용자 환전내역 조회
	public ArrayList<MemberExchangeHistoryDTO> exchangeHistory(String memberCode);
	
	// 이용자 환불내역 조회
	public ArrayList<MemberRefundHistoryDTO> refundHistory(String memberCode);
}
