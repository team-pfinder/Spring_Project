package com.lookation.mybatis;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IMemberReviewListDAO;
import com.lookation.dto.MemberReviewListDTO;

@Controller
public class MemberReviewList
{
	@Autowired
	private SqlSession sqlSession;
	
	// 리뷰 리스트 불러오기
	@RequestMapping(value = "/actions/userreviewlist.action", method = RequestMethod.GET)
	public String userReviewList(String member_code, Model model)
	{
		member_code= "M000003";
		IMemberReviewListDAO dao = sqlSession.getMapper(IMemberReviewListDAO.class);
		
		model.addAttribute("reviewList", dao.userReviewList(member_code));
		model.addAttribute("nickName", dao.userNickName(member_code));
		
		return "/WEB-INF/views/user/userReviewList.jsp";
	}


	// 리뷰 리스트에서 리뷰 수정
	@RequestMapping(value="/actions/modifyreviewinlist.action", method = RequestMethod.POST)
	public String modifyReview(MemberReviewListDTO dto)
	{
		IMemberReviewListDAO dao = sqlSession.getMapper(IMemberReviewListDAO.class);
		
		dao.modifyreviewlist(dto);
		
		return "redirect:userreviewlist.action";
	}
	
	// 리뷰 리스트에서 리뷰 삭제 
	@RequestMapping(value="/actions/deletereviewinlist.action", method = RequestMethod.GET)
	public String deleteReview(MemberReviewListDTO dto)
	{
		IMemberReviewListDAO dao = sqlSession.getMapper(IMemberReviewListDAO.class);
		
		dao.deletereviewlist(dto);
		
		return "redirect:userreviewlist.action";
	}

}
