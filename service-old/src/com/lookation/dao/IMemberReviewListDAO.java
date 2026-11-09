package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.MemberReviewListDTO;


public interface IMemberReviewListDAO
{
	// 게시글 전체 개수세기
	public int countReview(String member_code);
		
	// 이용자 리뷰 List
	public ArrayList<MemberReviewListDTO> memReviewList(String member_code);
	
	// 이용자 닉네임 불러오기
	public MemberReviewListDTO memNickName(String member_code);
	
	// 이용자 리뷰 수정
	public int modifyreviewlist(MemberReviewListDTO dto);
	
	// 이용자 리뷰 삭제
	public int deletereviewlist(MemberReviewListDTO dto);
}
