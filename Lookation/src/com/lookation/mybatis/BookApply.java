/*
 	BookApplyController.java
 	  이용자 예약신청화면 Controller
 */

package com.lookation.mybatis;

//import java.sql.SQLException;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.lookation.dao.IBookApplyDAO;
import com.lookation.dto.BookApplyDTO;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;


@Controller
public class BookApply
{
	@Autowired
	private SqlSession sqlSession;
	
	// BookApply 화면으로 이동
	@RequestMapping(value="/actions/bookapply.action", method = RequestMethod.GET)
	/*public String bookapplyBasic(ModelMap model, String loc_code, String member_code)*/
	public String bookapplyBasic(Model model, BookApplyDTO dto)
	{	
		IBookApplyDAO dao = sqlSession.getMapper(IBookApplyDAO.class);
		
		// loc_code가 안받아져 ... 

		// ※ 임시
		String member_code = "M000001";
		String loc_code = "L000001";
		
		model.addAttribute("loc_code", loc_code);
		model.addAttribute("basic", dao.bookapplyBasic(loc_code));
		model.addAttribute("bizinfo", dao.bookapplyBizInfo(loc_code));
		
		return "../WEB-INF/views/user/bookApply.jsp";
	}
	
	
	// 예약 및 결제
	@RequestMapping(value="/actions/bookpay.action", method = RequestMethod.POST)
	public String MileageCheck(ModelMap model, BookApplyDTO dto)
	{
		IBookApplyDAO dao = sqlSession.getMapper(IBookApplyDAO.class);
		
		// 멤버 코드 임시
		String member_code = "M000001";
		
		// 해당 멤버의 마일리지 잔액 가져오기
		int mileage = dao.mileageCheck(member_code);
		
		// 패키지 금액 가져오기
		/* 패키지 정보 임시 */
		int package_price = 10000;
		String apply_pakcage_code = "AP000001"; // 1~3
		int book_people = 4;
		
		
		if(mileage >= package_price)
		{
			
			System.out.println(book_people);
			// 예약내역에 추가
			
			dao.insertBookList(member_code);
			// 해당 예약내역의 예약코드 받아옴
			String book_code = dao.bookCheck(member_code);
			
			System.out.println(book_code);
			
			// 실예약자 추가
			dao.actualBooker(book_code);
			
			// 결제내역테이블 추가 
			dao.insertBookPay(dto);
			
			return "../WEB-INF/views/user/bookApplyNotice.jsp";
			
		}
		else
		{
			return "../WEB-INF/views/ajax/AccountAjax.jsp";
		}
		
	}
	
}

