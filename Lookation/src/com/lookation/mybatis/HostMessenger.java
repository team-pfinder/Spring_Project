package com.lookation.mybatis;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHostMessengerDAO;
import com.lookation.dto.MessengerDTO;
import com.lookation.util.FileManager;
import com.oreilly.servlet.MultipartRequest;

@Controller
public class HostMessenger
{
	@Autowired
	public SqlSession sqlSession;
	
	// 메시지 전체 출력하기	
	@RequestMapping(value="/actions/hmessenger.action", method=RequestMethod.GET)
	public String messageList(Model model, HttpServletRequest request)
	{
		// 예약 코드로 접속
		String book_code = request.getParameter("book_code");
		
		IHostMessengerDAO dao = sqlSession.getMapper(IHostMessengerDAO.class);
		
		model.addAttribute("book_code", book_code);
		
		// 상대방 닉네임 검색
		model.addAttribute("nick", dao.hSearch(book_code));
		// 메시지 리스트 전체
		model.addAttribute("msg", dao.hMsgList(book_code));
		// 메시지 코드 검색
		model.addAttribute("msg_code", dao.hSearchMsg(book_code));
		
		return "../WEB-INF/views/host/hostMessenger.jsp";
	}
	
	
	// 메시지 전송
	@RequestMapping(value="/actions/hmsgsend.action", method=RequestMethod.POST)
	public String messageSend(Model model, MessengerDTO dto)
	{
		IHostMessengerDAO dao = sqlSession.getMapper(IHostMessengerDAO.class);
		
		dao.hSendMsg(dto);
		
		// 원래 있던 채팅방으로 돌아가기
		return "redirect:hmessenger.action?book_code=" + dto.getBook_code();
	}
	
	
	@RequestMapping(value="/actions/himgsend.action", method = RequestMethod.POST)
    public String imgSend(HttpServletRequest request, Model model, MessengerDTO dto)
    {
		IHostMessengerDAO dao = sqlSession.getMapper(IHostMessengerDAO.class);
		
        try
        {
            MultipartRequest m = FileManager.upload(request, "images");
            ArrayList<String> imageList = FileManager.getFileNames(m);
            model.addAttribute("imageList", imageList);
            
            String book_code = m.getParameter("book_code");
            System.out.println("b : " + book_code);
            
            //String msg_img_url = String.valueOf(FileManager.getFileNames(m));
            String msg_img_url = m.getParameter("msg_img_url");
            model.addAttribute("book_code", book_code);
            model.addAttribute("msg_img_url", msg_img_url);
            System.out.println(msg_img_url);
            dao.hSendImg(dto);

        } catch (Exception e)
        {
            e.toString();
        }
		
        return "redirect:hmessenger.action";
    }
	
}
