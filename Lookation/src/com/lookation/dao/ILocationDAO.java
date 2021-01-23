package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.LocationDTO;

public interface ILocationDAO
{
	// ---------------- identify --------------------
	public int findLocation(LocationDTO dto);
	
	// ----------------- list ------------------------
	public ArrayList<LocationDTO> locList(LocationDTO dto);
	
	// ----------------- input -----------------------
	
	// 공간 정보
	public void inputLoc(LocationDTO dto);
	
	// 기본 정보
	public void inputBasicInfo(LocationDTO dto);
	
	// 기본 정보(썸네일)
	public void inputThumbnail(LocationDTO dto);
	
	// 기본 정보(시설안내)
	public void inputFacilityInfo(LocationDTO dto);
	
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
	
	// 패키지 양식
	public void inputPackageFormInfo(LocationDTO dto);
	
	// 검수 신청
	public void inputInspectRegList(LocationDTO dto);
	
	
	// ----------------- update -----------------------
	
	// 기본 정보
	public void modifyBasicInfo(LocationDTO dto);
	
	// 기본 정보(썸네일)
	public void modifyThumbnail(LocationDTO dto);
	
	// 기본 정보(시설안내)
	public void modifyFacilityInfo(LocationDTO dto);
	
	// 기본 정보(주의사항)
	public void modifyCaution(LocationDTO dto);
	
	// 연락처 정보
	public void modifyContact(LocationDTO dto);
	
	// 상세정보
	public void modifyDetailInfo(LocationDTO dto);
	
	// 상세정보 이미지
	public void modifyDetailImg(LocationDTO dto);
	
	// 공간웹사이트
	public void modifyLocWeb(LocationDTO dto);
	
	// 이용안내
	public void modifyUsingInfo(LocationDTO dto);
	
	// ----------------- delete ------------------------
	
	public void deleteLocation(LocationDTO dto);
	
	// ----------------- select -----------------------
	
	// 공간 정보
	// parameter -> loc_code
	public ArrayList<LocationDTO> selectLoc(LocationDTO dto);
	
	// 기본 정보
	public LocationDTO selectBasicInfo(LocationDTO dto);
	
	// 기본 정보(썸네일)
	public LocationDTO selectThumbnail(LocationDTO dto);
	
	// 기본 정보(시설안내)
	public ArrayList<LocationDTO> selectFacilityInfo(LocationDTO dto);
	
	// 기본 정보(주의사항)
	public ArrayList<LocationDTO> selectCaution(LocationDTO dto);
	
	// 연락처 정보
	public LocationDTO selectContact(LocationDTO dto);
	
	// 사업자 정보
	public LocationDTO selectBizInfo(LocationDTO dto);

	// 상세정보
	public LocationDTO selectDetailInfo(LocationDTO dto);
	
	// 상세정보 이미지
	public ArrayList<LocationDTO> selectDetailImg(LocationDTO dto);
	
	// 공간 웹사이트
	public LocationDTO selectLocWeb(LocationDTO dto);
	
	// 이용안내
	public LocationDTO selectUsingInfo(LocationDTO dto);
	
	
}
