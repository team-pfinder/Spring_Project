package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.NoticeDTO;

public interface INoticeManagerDAO
{
	//관리자 공지사항 주요 속성 구성
	//공지사항목록 조회
	public ArrayList<NoticeDTO> A_noticeList();
	//공지사항 작성
	public int add(NoticeDTO dto);
	//공지사항 삭제
	public int remove(NoticeDTO dto);
	//공지사항 수정
	public int update(NoticeDTO dto);
	//공지사항 데이터 확인
	//(notice_code를 활용하여 공지사항 정보 검색)
	public NoticeDTO search(String notice_code);
}
