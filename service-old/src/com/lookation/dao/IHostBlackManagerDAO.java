package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.HostBlackManagerDTO;

public interface IHostBlackManagerDAO
{
	// 호스트 블랙리스트 조회
	public ArrayList<HostBlackManagerDTO> listBlackHost();
	
	// 블랙리스트 등록할 호스트 조회
	public String searchEmail(String host_code);
	
	// 호스트 블랙리스트 등록
	public int regBlackHost(HostBlackManagerDTO dto);
	
	// 호스트 블랙리스트 해제
	public int delBlackHost(String host_email);
	
	// 호스트 신고내역 상세보기
	public ArrayList<HostBlackManagerDTO> viewReportHost(String host_code);
}
