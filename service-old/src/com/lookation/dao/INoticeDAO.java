package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.NoticeDTO;

public interface INoticeDAO
{
	//공지사항 목록 조회
	public ArrayList<NoticeDTO> U_noticeList();
	//공지사항 클릭 시 상세보기
	public NoticeDTO U_ndetailList(String notice_code);
	
}
