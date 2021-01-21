package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.SearchDTO;

public interface ISearchDAO
{
	// 검색어 없는 검색결과
	//public ArrayList<SearchDTO> searchLoc();
	
	// 검색어 입력해서 검색
	public ArrayList<SearchDTO> searchKeyword(String keyword);
	
}
