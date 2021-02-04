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

		String result = "noSigned";                                                         

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.
			IMemberQnaListDAO qnaDao = sqlSession.getMapper(IMemberQnaListDAO.class);
			model.addAttribute("qnaList", qnaDao.memberQnaList(accountCode));
			model.addAttribute("nickName", qnaDao.memberNickName(accountCode));
			
			// 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}
		model.addAttribute("result", result);                                               

		if(result.equals("noSigned"))
		{
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
