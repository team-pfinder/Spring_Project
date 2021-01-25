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
import com.lookation.dao.IMemberQnaListDAO;
import com.lookation.dto.MemberQnaListDTO;

@Controller
public class MemberQnaList
{
	@Autowired
	private SqlSession sqlSession;
	
	// QnA 리스트 불러오기
	@RequestMapping(value = "/actions/userqnalist.action", method = RequestMethod.GET)
	public String memberQnaList(String member_code, Model model, HttpServletRequest request)
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
			IMemberQnaListDAO qnaDao = sqlSession.getMapper(IMemberQnaListDAO.class);
			model.addAttribute("qnaList", qnaDao.memberQnaList(accountCode));
			model.addAttribute("nickName", qnaDao.memberNickName(accountCode));
			
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
		
		return "/WEB-INF/views/user/userQnaList.jsp";
	}
	
	
	// QnA 리스트에서 QnA 수정
	@RequestMapping(value="/actions/modifyqnainlist.action", method = RequestMethod.POST)
	public void modifyQna(MemberQnaListDTO dto)
	{
		IMemberQnaListDAO dao = sqlSession.getMapper(IMemberQnaListDAO.class);
		
		dao.modifyqnalist(dto);
	
		//return "/WEB-INF/views/user/userQnaList.jsp";
	}
	
	// QnA 리스트에서 QnA 삭제
	@RequestMapping(value="/actions/deleteqnainlist.action", method = RequestMethod.GET)
	public String deleteQna(MemberQnaListDTO dto)
	{
		IMemberQnaListDAO dao =sqlSession.getMapper(IMemberQnaListDAO.class);
		
		dao.deleteqnalist(dto);
		return "redirect:userqnalist.action";
	}
	
}
