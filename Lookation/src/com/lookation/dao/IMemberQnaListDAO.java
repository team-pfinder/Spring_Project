package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.MemberQnaListDTO;

public interface IMemberQnaListDAO
{
	// 이용자 QnA List 
	public ArrayList<MemberQnaListDTO> memberQnaList(String member_code);
	
	// 이용자 닉네임 불러오기
	public MemberQnaListDTO memberNickName(String member_code);
	
	// 이용자 QnA 리스트에서 수정
	public int modifyqnalist(MemberQnaListDTO dto);

	// 이용자 QnA 리스트에서 삭제
	public int deleteqnalist(MemberQnaListDTO dto);
}
