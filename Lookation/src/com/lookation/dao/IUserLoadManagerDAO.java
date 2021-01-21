package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.UserLoadManagerDTO;

public interface IUserLoadManagerDAO
{
	// 충전 신청 목록 조회
	public ArrayList<UserLoadManagerDTO> loadRegList();
	
	// 충전 신청 승인 처리
	public int loadRegApprove(String regCode);
	
	// 충전 신청 반려 처리
	public int loadRegDeny(String regCode);

}
