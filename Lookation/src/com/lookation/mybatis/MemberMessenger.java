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
	@RequestMapping(value="/actions/mmsgsend.action", method=RequestMethod.POST)
	public String messageSend(Model model, MessengerDTO dto)
	{
		IMemberMessengerDAO dao = sqlSession.getMapper(IMemberMessengerDAO.class);
		dao.mSendMsg(dto);
		
		return "redirect:mmessenger.action?book_code=" + dto.getBook_code();
	}
	
	
	@RequestMapping(value="/actions/mimgsend.action", method = RequestMethod.POST)
    public String imgSend(HttpServletRequest request, Model model, MessengerDTO dto)
    {
		IMemberMessengerDAO dao = sqlSession.getMapper(IMemberMessengerDAO.class);
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
         
            dao.mSendImg(dto);

        } catch (Exception e)
        {
            e.toString();
        }
        
       
		

        return "redirect:mmessenger.action";
    }
	
	/*
	 * // 이미지 전송
	 * 
	 * @RequestMapping(value="/actions/mimgsend.action", method=RequestMethod.POST)
	 * public String imgSend(Model model, MessengerDTO dto, HttpServletRequest
	 * request) { IMemberMessengerDAO dao =
	 * sqlSession.getMapper(IMemberMessengerDAO.class);
	 * 
	 * 
	 * //MultipartRequest m = null;
	 * 
	 * //ArrayList<String> images = FileManager.upload(request, "images", m);
	 * //String book_code = ((MultipartRequest)request).getParameter("book_code");
	 * 
	 * ArrayList<String> result = new ArrayList<String>();
	 * 
	 * String realFolder = ""; String fileName = "images"; int maxSize =
	 * 1024*1024*5; String encType = "utf-8"; ServletContext sContext =
	 * request.getServletContext(); realFolder = sContext.getRealPath(fileName);
	 * 
	 * String book_code = ""; try { MultipartRequest multi = new
	 * MultipartRequest(request, realFolder, maxSize, encType, new
	 * DefaultFileRenamePolicy()); Enumeration<?> files = multi.getFileNames();
	 * while(files.hasMoreElements()) { String file = (String)files.nextElement();
	 * fileName = multi.getFilesystemName(file); result.add(fileName); } book_code =
	 * multi.getParameter("book_code"); System.out.println(book_code); }
	 * catch(Exception e) { System.out.println(e.toString()); }
	 * 
	 * System.out.println(result.size());
	 * 
	 * model.addAttribute("book_code", book_code); model.addAttribute("images",
	 * result); dao.mSendImg(dto);
	 * 
	 * 
	 * 
	 * // 원래 있던 채팅방으로 돌아가기 return "redirect:mmessenger.action?book_code=" +
	 * dto.getBook_code(); }
	 */
}
