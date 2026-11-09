package com.lookation.dao;

import com.lookation.dto.LocationReviewDTO;


public interface ILocationReviewDAO
{
	
	
	/* 이용자 */
	// 이용자 리뷰 수정폼
	public LocationReviewDTO updateReviewForm(String review_code);
	
	// 이용자 리뷰 사진폼
	public LocationReviewDTO updateReviewImg(String review_code);
	
	// 이용자 텍스트 리뷰 작성
	public int insertMemReview(LocationReviewDTO dto);
	
	// 이용자 이미지 첨부 리뷰 작성
	public int insertMemImg(LocationReviewDTO dto);
	
	// 이용자 리뷰 수정
	public void updateMemReview(LocationReviewDTO dto);
	
	// 이용자 리뷰 사진 수정
	public void updateMemImg(LocationReviewDTO dto);
	
	// 이용자 리뷰 삭제
	public int deleteMemReview(LocationReviewDTO dto);
	
	/* 호스트 */
	
	// 호스트 리뷰 답글 작성
	public int insertHostReview(LocationReviewDTO dto);
	
	// 호스트 리뷰 수정폼
	public Object updateReviewReplyForm(String review_reply_code);
	
	// 호스트 리뷰 답글 수정
	public void updateHostReview(LocationReviewDTO dto);
	
	// 호스트 리뷰 답글 삭제
	public int deleteHostReview(LocationReviewDTO dto);

	

}
