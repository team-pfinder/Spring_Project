package com.lookation.dao;

import com.lookation.dto.LocationQnaDTO;


public interface ILocationQnaDAO
{
	/* 이용자 */
	
	// 이용자 Q&A 작성
	public int insertMemQna(LocationQnaDTO dto);
	
	// 이용자 Q&A 수정 폼
	// 젤 중요한 거슨 ... 게시글코드
	public LocationQnaDTO updateQnaForm(String qna_code);
	
	// 이용자 Q&A 수정
	public int updateMemQna(LocationQnaDTO dto);
	
	// 이용자 Q&A 삭제
	public int deleteMemQna(LocationQnaDTO dto);
	
	
	
	/* 호스트 */
	
	// 호스트 Q&A 답글 작성
	public int insertHostQna(LocationQnaDTO dto);
	
	// 호스트 Q&A 답글 수정폼
	public LocationQnaDTO updateQnaReplyForm(String qna_reply_code);
	
	// 호스트 Q&A 답글 수정
	public int updateHostQna(LocationQnaDTO dto);
	
	// 호스트 Q&A 답글 삭제
	public int deleteHostQna(String qna_reply_code);



	
}
