package com.lookation.dao;

import com.lookation.dto.QnaDTO;


public interface IQnaDAO
{
	/* 이용자 */
	
	// 이용자 Q&A 작성
	public int insertMemQna(QnaDTO dto);
	
	// 이용자 Q&A 수정
	// 수정하려는거 불러와야돼 loc, mem 필요한데
	// 젤 중요한 거슨 ... 게시글코드
	public QnaDTO updateForm(QnaDTO dto);
	
	// 이용자 Q&A 삭제
	public int deleteMemQna(QnaDTO dto);
	
	/* 호스트 */
	
	// 호스트 Q&A 답글 작성
	
	// 이용자 Q&A 답글 수정
	
	// 이용자 Q&A 답글 삭제

}
