package com.lookation.dao;

import com.lookation.dto.ReviewDTO;


public interface IReviewDAO
{
	
	
	/* 이용자 */
	// 이용자 리뷰 수정폼
	public ReviewDTO updateReviewForm(String review_code);
	
	// 이용자 리뷰 작성
	public int insertMemReview(ReviewDTO dto);
	
	// 이용자 리뷰 수정
	public void updateMemReview(ReviewDTO dto);
	
	// 이용자 리뷰 삭제
	public int deleteMemReview(ReviewDTO dto);
	
	/* 호스트 */
	
	// 호스트 리뷰 답글 작성
	public int insertHostReview(ReviewDTO dto);
	
	// 호스트 리뷰 수정폼
	public Object updateReviewReplyForm(String reviewReplyCode);
	
	// 호스트 리뷰 답글 수정
	public void updateHostReview(ReviewDTO dto);
	
	// 호스트 리뷰 답글 삭제
	public int deleteHostReview(ReviewDTO dto);

	

}
