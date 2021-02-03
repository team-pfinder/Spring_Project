package com.lookation.mybatis;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dao.IMemberMessengerDAO;
import com.lookation.dto.MessengerDTO;
import com.lookation.util.FileManager;
import com.oreilly.servlet.MultipartRequest;

@Controller
public class MemberMessenger
{
	@Autowired
	public SqlSession sqlSession;
	
	// 메시지 전체 출력하기	
	@RequestMapping(value="/actions/mmessenger.action", method=RequestMethod.GET)
	public String messageList(Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                      
		String accountCode = (String)session.getAttribute("memberCode"); 

		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			// 예약 코드로 접속
			String book_code = request.getParameter("book_code");
			
			IMemberMessengerDAO msgDao = sqlSession.getMapper(IMemberMessengerDAO.class);
			
			model.addAttribute("book_code", book_code);
			// 상대방 닉네임 검색
			model.addAttribute("nick", msgDao.mSearch(book_code));
			// 메시지 리스트 전체
			model.addAttribute("msg", msgDao.mMsgList(book_code));
			// 메시지 코드 검색
			model.addAttribute("msg_code", msgDao.mSearchMsg(book_code));
			// 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}
		model.addAttribute("result", result);                                               

		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=member";
		}
		return "../WEB-INF/views/user/userMessenger.jsp";
	}
	
	// 메시지 전송
	@RequestMapping(value="/actions/mmsgsend.action", method = { RequestMethod.POST, RequestMethod.GET })
    public String imgSend(HttpServletRequest request, Model model, MessengerDTO dto) throws IOException
    {
		IMemberMessengerDAO dao = sqlSession.getMapper(IMemberMessengerDAO.class);
		MultipartRequest m = FileManager.upload(request, "images");
        ArrayList<String> imageList = FileManager.getFileNames(m);
		
        try
        {
            dto.setBook_code(m.getParameter("book_code"));
            dto.setMember_msg_content(m.getParameter("member_msg_content"));
            dto.setMsg_code(m.getParameter("msg_code"));
            
			if (imageList.isEmpty())
			{
				dao.mSendMsg(dto);
				
			}
			else 
			{
				dto.setMsg_img_url(imageList.get(0));
				dao.mSendImg(dto);
				
			}

        } catch (Exception e)
        {
            e.toString();
        }
		
        // 원래 있던 채팅방으로 돌아가기
     	return "redirect:mmessenger.action?book_code=" + dto.getBook_code();
    }
}
