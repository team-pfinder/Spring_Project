package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.MemberBlackManagerDTO;

public interface IMemberBlackManagerDAO
{
	// 호스트 블랙리스트 조회
	public ArrayList<MemberBlackManagerDTO> listBlackMem();
	
	// 블랙리스트 등록할 호스트 조회
	public String searchMemEmail(String member_code);
	
	// 호스트 블랙리스트 등록
	public int regBlackMem(MemberBlackManagerDTO dto);
	
	// 호스트 블랙리스트 해제
	public int delBlackMem(String member_email);
	
	// 호스트 신고내역 상세보기
	public ArrayList<MemberBlackManagerDTO> viewReportMem(String member_code);
}
