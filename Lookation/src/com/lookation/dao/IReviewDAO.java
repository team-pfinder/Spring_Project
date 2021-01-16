package com.lookation.dao;

import com.lookation.dto.ReviewDTO;


public interface IReviewDAO
{
	/* 이용자 */
	
	// 이용자 리뷰 작성
	public int insertMemReview(ReviewDTO dto);
	
	// 이용자 리뷰 수정
	
	// 이용자 리뷰 삭제
	public int deleteMemReview(ReviewDTO dto);
	
	/* 호스트 */
	
	// 호스트 Q&A 답글 작성
	
	// 이용자 Q&A 답글 수정
	
	// 이용자 Q&A 답글 삭제

}
