package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.MessengerDTO;

public interface IHostMessengerDAO
{
	// 상대방 닉네임 찾기
	public String hSearch(String book_code);
	
	// 메시지 전체 출력
	public ArrayList<MessengerDTO> hMsgList(String book_code);
	
	// 메신저 코드 검색
	public String hSearchMsg(String book_code);
	
	// 텍스트 메시지 전송
	public int hSendMsg(MessengerDTO dto);
	
	// 이미지 전송
	public int hSendImg(MessengerDTO dto);
}
