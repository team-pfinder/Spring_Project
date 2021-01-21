package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.HostCalHistoryDTO;
import com.lookation.dto.HostExchangeHistoryDTO;


public interface IHostHistoryListDAO
{
	// 호스트 사용내역 조회
	public ArrayList<HostCalHistoryDTO> calHistory(String hostCode);
	
	// 호스트 충전내역 조회
	public ArrayList<HostExchangeHistoryDTO> exchangeHistory(String hostCode);
}
