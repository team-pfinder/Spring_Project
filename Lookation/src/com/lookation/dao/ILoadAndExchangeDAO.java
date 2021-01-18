package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.LoadAndExchangeDTO;

public interface ILoadAndExchangeDAO
{

	// 계좌 리스트 조회
	public ArrayList<LoadAndExchangeDTO> bankInfoList(LoadAndExchangeDTO dto);

	// 이용자 잔액 조회
	public int getBalance(LoadAndExchangeDTO dto);
	
	// 이용자 충전 신청
	public int loadRegister(LoadAndExchangeDTO dto);
	
	// 이용자 충전 안내
	public LoadAndExchangeDTO loadRegisterNotice(String identifyCode);
	
	// 이용자 환전 신청
	public int exchangeRegister(LoadAndExchangeDTO dto);
	
	// 이용자 환전 안내
	public LoadAndExchangeDTO exchangeNotice(String identifyCode);
	
}
