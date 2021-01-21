/*
 	BookList.java
 	  이용자 예약리스트 화면
 */

package com.lookation.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.lookation.dao.IBookListDAO;
import com.lookation.dto.BookListDTO;

import javax.servlet.http.HttpServletRequest;

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
		IBookListDAO dao = sqlSession.getMapper(IBookListDAO.class);
		
		String member_code = request.getParameter("memCode");
		
		model.addAttribute("bookList", dao.bookList(member_code));
		
		return "../WEB-INF/views/user/bookList.jsp";
	}

	
	// 예약 상세보기 팝업창
	@RequestMapping(value = "/actions/bookdetails.action", method = RequestMethod.GET)
	public String bookDetails(String book_code, ModelMap model)
	{
		IBookListDAO dao = sqlSession.getMapper(IBookListDAO.class);

		model.addAttribute("details", dao.bookDetails(book_code));
		
		return "/WEB-INF/views/user/bookDetails.jsp";
	}

	// 예약 취소 팝업창
	@RequestMapping(value = "/actions/bookcancel.action", method = RequestMethod.GET)
	public String bookCancelPop(String book_code, ModelMap model)
	{
		IBookListDAO dao = sqlSession.getMapper(IBookListDAO.class);
		
		model.addAttribute("cancel", dao.refundPrice(book_code));
		
		return "/WEB-INF/views/user/bookCancel.jsp";
	}
	
	// 예약 취소 액션
	@RequestMapping(value = "/actions/membercancel.action", method = RequestMethod.POST)
	public String memberCancel(String book_code, BookListDTO dto, ModelMap model)
	{
		IBookListDAO dao = sqlSession.getMapper(IBookListDAO.class);
		
		dao.memberCancel(dto);
		dao.memberRefund(dto);
		System.out.println(dto.getBook_code());
		System.out.println(dto.getMember_cancel_reason());
		
		return "/WEB-INF/views/user/bookList.jsp?";
	}

	 
}
	  
	 