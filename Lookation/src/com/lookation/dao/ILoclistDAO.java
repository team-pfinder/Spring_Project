package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.locListDTO;

public interface ILoclistDAO
{
	// 공간 조회(공간코드로 공간명, 썸네일, 공간등록일자, 검수상태 조회)
	public ArrayList<locListDTO> locList(String loc_code);
	
	// 공간 삭제
	public void removeLoc(locListDTO dto);
	
	
}
