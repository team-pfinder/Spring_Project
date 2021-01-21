package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IMemberMessengerDAO;
import com.lookation.dto.MessengerDTO;

@Controller
public class MemberMessenger
{
	@Autowired
	public SqlSession sqlSession;
	
	// 메시지 전체 출력하기	
	@RequestMapping(value="/actions/mmessenger.action", method=RequestMethod.GET)
	public String messageList(Model model, HttpServletRequest request)
	{
		// 예약 코드로 접속
		String book_code = request.getParameter("book_code");
		
		IMemberMessengerDAO dao = sqlSession.getMapper(IMemberMessengerDAO.class);
		
		model.addAttribute("book_code", book_code);
		// 상대방 닉네임 검색
		model.addAttribute("nick", dao.mSearch(book_code));
		// 메시지 리스트 전체
		model.addAttribute("msg", dao.mMsgList(book_code));
		// 메시지 코드 검색
		model.addAttribute("msg_code", dao.mSearchMsg(book_code));
		
		return "../WEB-INF/views/user/userMessenger.jsp";
	}
	
	
	// 메시지 전송
	@RequestMapping(value="/actions/mmsgsend.action", method=RequestMethod.POST)
	public String messageSend(Model model, MessengerDTO dto)
	{
		IMemberMessengerDAO dao = sqlSession.getMapper(IMemberMessengerDAO.class);
		
		System.out.println(dto.getBook_code());
		dao.mSendMsg(dto);
		
		// 원래 있던 채팅방으로 돌아가기
		return "redirect:mmessenger.action?book_code=" + dto.getBook_code();
	}
	
	// 이미지 전송
	@RequestMapping(value="/actions/mimgsend.action", method=RequestMethod.POST)
	public String imgSend(Model model, MessengerDTO dto)
	{
		IMemberMessengerDAO dao = sqlSession.getMapper(IMemberMessengerDAO.class);
		
		dao.mSendImg(dto);
		
		// 원래 있던 채팅방으로 돌아가기
		return "redirect:mmessenger.action?book_code=" + dto.getBook_code();
	}
}
