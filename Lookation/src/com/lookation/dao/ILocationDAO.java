package com.lookation.dao;

import com.lookation.dto.LocationDTO;


public interface ILocationDAO
{
		
	// 기본 정보
	public void inputBasicInfo(LocationDTO dto);
	
	// 기본 정보(썸네일)
	public void inputThumbnail(LocationDTO dto);
	
	// 기본 정보(시설안내)
	public void inputFacility(LocationDTO dto);
	
	// 기본 정보(주의사항)
	public void inputCaution(LocationDTO dto);
	
	// 연락처 정보
	public void inputContact(LocationDTO dto);
	
	// 사업자 정보
	public void inputBizInfo(LocationDTO dto);
	
	// 상세정보
	public void inputDetailInfo(LocationDTO dto);
	
	// 상세정보 이미지
	public void inputDetailImg(LocationDTO dto);
	
	// 공간웹사이트
	public void inputLocWeb(LocationDTO dto);
	
	// 이용안내
	public void inputUsingInfo(LocationDTO dto);
	
	
}
