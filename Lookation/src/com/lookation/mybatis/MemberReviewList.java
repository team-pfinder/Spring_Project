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
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                      
		String accountCode = (String)session.getAttribute("memberCode"); 

		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
			// db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.
			IMemberReviewListDAO reviewDao = sqlSession.getMapper(IMemberReviewListDAO.class);
			
			model.addAttribute("reviewList", reviewDao.memReviewList(accountCode));
			model.addAttribute("nickName", reviewDao.memNickName(accountCode));

			// 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}
		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               

		// 로그인이 안되어 있다면 
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
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
		
		//return "redirect:userreviewlist.action";
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
