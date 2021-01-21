package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.LoadAndExchangeDTO;

public interface ILoadAndExchangeDAO
{
	
	/* 이용자 조회/충전/환전 */

	// 이용자 계좌 리스트 조회
	public ArrayList<LoadAndExchangeDTO> memberBankInfoList(LoadAndExchangeDTO dto);

	// 이용자 잔액 조회
	public int memberGetBalance(LoadAndExchangeDTO dto);
	
	// 이용자 충전 신청
	public int memberLoadRegister(LoadAndExchangeDTO dto);
	
	// 이용자 충전 안내
	public LoadAndExchangeDTO memberLoadRegisterNotice(String identifyCode);
	
	// 이용자 환전 신청
	public int memberExchangeRegister(LoadAndExchangeDTO dto);
	
	// 이용자 환전 안내
	public LoadAndExchangeDTO memberExchangeNotice(String identifyCode);
	
	/* ---------------------------------------------------------------------------------- */
	/* 호스트 조회/환전 */
	
	// 호스트 계좌 리스트 조회
	public ArrayList<LoadAndExchangeDTO> hostBankInfoList(LoadAndExchangeDTO dto);
	
	// 호스트 잔액 조회
	public int hostGetBalance(LoadAndExchangeDTO dto);

	// 호스트 환전 신청
	public int hostExchangeRegister(LoadAndExchangeDTO dto);

	// 호스트 환전 안내
	public LoadAndExchangeDTO hostExchangeNotice(String identifyCode);

	
}
