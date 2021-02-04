package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.HelpDTO;

public interface IHelpManagerDAO
{
	//도움말 목록 조회
	public ArrayList<HelpDTO> A_helpList();
	//도움말 작성
	public int add(HelpDTO dto);
	//도움말 이미지
	public int addimg(HelpDTO dto);
	//도움말 삭제
	public int remove(HelpDTO dto);
	//도움말 이미지 삭제
	public int removeimg(HelpDTO dto);
	//삭제 시 해당 코드 찾기
	public int testcode(HelpDTO dto);
	//도움말 수정
	public int update(HelpDTO dto);
	//도움말 데이터 확인
	//(help_code를 활용하여 도움말 정보 검색)
	public HelpDTO search(String help_code);

}
