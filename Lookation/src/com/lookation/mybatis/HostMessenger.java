package com.lookation.mybatis;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHostAccountDAO;
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
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                         
		String accountCode = (String)session.getAttribute("hostCode"); 

		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
		    // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.
			// 예약 코드로 접속
			String book_code = request.getParameter("book_code");
			
			IHostMessengerDAO msgDao = sqlSession.getMapper(IHostMessengerDAO.class);
			
			model.addAttribute("book_code", book_code);
			
			// 상대방 닉네임 검색
			model.addAttribute("nick", msgDao.hSearch(book_code));
			// 메시지 리스트 전체
			model.addAttribute("msg", msgDao.hMsgList(book_code));
			// 메시지 코드 검색
			model.addAttribute("msg_code", msgDao.hSearchMsg(book_code));

			// 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               

		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=host";
		}
		
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
            
            //String msg_img_url = String.valueOf(FileManager.getFileNames(m));
            String msg_img_url = m.getParameter("msg_img_url");
            model.addAttribute("book_code", book_code);
            model.addAttribute("msg_img_url", msg_img_url);
            dao.hSendImg(dto);

        } catch (Exception e)
        {
            e.toString();
        }
		
        return "redirect:hmessenger.action";
    }
	
}
