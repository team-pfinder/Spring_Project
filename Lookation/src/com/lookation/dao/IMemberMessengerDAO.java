package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.MessengerDTO;

public interface IMemberMessengerDAO
{
	// 상대방 닉네임 찾기
	public String mSearch(String book_code);
	
	// 메시지 전체 출력
	public ArrayList<MessengerDTO> mMsgList(String book_code);
	
	// 메신저 코드 검색
	public String mSearchMsg(String book_code);
	
	// 텍스트 메시지 전송
	public int mSendMsg(MessengerDTO dto);
	
	// 이미지 전송
	public int mSendImg(MessengerDTO dto);
}
