/*
 	BookList.java
 	  이용자 예약리스트 화면
 */

package com.lookation.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.lookation.dao.IBookListDAO;
import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dto.BookListDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookList
{
	@Autowired
	private SqlSession sqlSession;

	// 이용자 마이페이지 예약내역확인으로 이동
	@RequestMapping(value="/actions/booklist.action", method = RequestMethod.GET)
	public String bookList(ModelMap model, BookListDTO dto, HttpServletRequest request)
	{	
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

			IBookListDAO bookDao = sqlSession.getMapper(IBookListDAO.class);
			
			model.addAttribute("nick", bookDao.memberNick(accountCode));
			model.addAttribute("bookList", bookDao.bookList(accountCode));
			model.addAttribute("member_code", accountCode);

			// 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               

		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=member";
		}
		return "../WEB-INF/views/user/bookList.jsp";
	}

	
	// 예약 상세보기 팝업창
	@RequestMapping(value = "/actions/bookdetails.action", method = RequestMethod.GET)
	public String bookDetails(String book_code, ModelMap model, HttpServletRequest request)
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

			IBookListDAO bookDao = sqlSession.getMapper(IBookListDAO.class);
			model.addAttribute("details", bookDao.bookDetails(book_code));
			
			// 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               

		// ********* 만약 로그인기능이 사용되는 뷰페이지의 경우 이 코드를 추가한다. ********* 
		// 로그인이 안되어 있다면 
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=member";
		}
		                                                                                                                                                                                                                                                                                                                                                                                                                             
		return "/WEB-INF/views/user/bookDetails.jsp";                                          
	}

	// 예약 취소 팝업창
	@RequestMapping(value = "/actions/bookcancel.action", method = RequestMethod.GET)
	public String bookCancelPop(String book_code, ModelMap model, HttpServletRequest request)
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

			IBookListDAO bookDao = sqlSession.getMapper(IBookListDAO.class);
			model.addAttribute("cancel", bookDao.refundPrice(book_code));
			
			// 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               

		// ********* 만약 로그인기능이 사용되는 뷰페이지의 경우 이 코드를 추가한다. ********* 
		// 로그인이 안되어 있다면 
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=member";
		}
		                                                                                                                                                                                                                                                                                                                                                                                                                             
		return "/WEB-INF/views/user/bookCancel.jsp";    
	}
	
	// 예약 취소 액션
	@RequestMapping(value = "/actions/membercancel.action", method = RequestMethod.POST)
	public void memberCancel(String book_code, BookListDTO dto, ModelMap model)
	{
		IBookListDAO dao = sqlSession.getMapper(IBookListDAO.class);
		
		dao.memberCancel(dto);
		dao.memberRefund(dto);
	}

	 
}
	  
	 