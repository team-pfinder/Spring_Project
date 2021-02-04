package com.lookation.mybatis;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dao.IMemberReviewListDAO;
import com.lookation.dto.MemberReviewListDTO;

@Controller
public class MemberReviewList
{
	@Autowired
	private SqlSession sqlSession;
	
	// 리뷰 리스트 불러오기
	@RequestMapping(value = "/actions/userreviewlist.action", method = RequestMethod.GET)
	public String userReviewList(String member_code, Model model, HttpServletRequest request)
	{
		HttpSession session = request.getSession();                                      
		String accountCode = (String)session.getAttribute("memberCode"); 

		String result = "noSigned";                                                         

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			IMemberReviewListDAO reviewDao = sqlSession.getMapper(IMemberReviewListDAO.class);
			
			model.addAttribute("reviewList", reviewDao.memReviewList(accountCode));
			model.addAttribute("nickName", reviewDao.memNickName(accountCode));

		    result = "signed";                                                                                
		}
		model.addAttribute("result", result);                                               

		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=member";
		}
		                                                                                                                                                                                                                                                                                                                                                                                                                             
		return "/WEB-INF/views/user/userReviewList.jsp";
	}


	// 리뷰 리스트에서 리뷰 수정
	@RequestMapping(value="/actions/modifyreviewinlist.action", method = RequestMethod.POST)
	public void modifyReview(MemberReviewListDTO dto)
	{
		IMemberReviewListDAO dao = sqlSession.getMapper(IMemberReviewListDAO.class);
		
		dao.modifyreviewlist(dto);
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
