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
	public String bookapplyBasic(Model model, BookApplyDTO dto, HttpServletRequest request)
	{	
		IBookApplyDAO dao = sqlSession.getMapper(IBookApplyDAO.class);
		
		// ※ 임시 ------------------------------------------------------
		String member_code = "M000002";
		String loc_code = "L000002";
		// --------------------------------------------------------------
		
		// 이전 폼(locationDetail) 에서
		// apply_package_code 와 book_people 입력한 내용 받아옴
		String apply_package_code = request.getParameter("apply_package_code"); 
		String book_people = request.getParameter("book_people");
		
		// 공간정보 출력
		model.addAttribute("loc_code", loc_code);
		model.addAttribute("profile", dao.setProfile(member_code));
		model.addAttribute("basic", dao.bookapplyBasic(loc_code));
		model.addAttribute("bizinfo", dao.bookapplyBizInfo(loc_code));
		
		// 선택한 패키지의 정보 출력
		model.addAttribute("selectPack", dao.bookPackage(apply_package_code));
		model.addAttribute("apply_package_code", apply_package_code);
		model.addAttribute("book_people", book_people);
		
		System.out.println(apply_package_code + "+" + book_people);
		
		return "../WEB-INF/views/user/bookApply.jsp";
	}
	
	
	// 예약 및 결제
	@RequestMapping(value="/actions/bookpay.action", method = RequestMethod.POST)
	public String MileageCheck(ModelMap model, BookApplyDTO dto, String member_code)
	{
		IBookApplyDAO dao = sqlSession.getMapper(IBookApplyDAO.class);
		
		// 해당 멤버의 마일리지 잔액 가져오기
		int mileage = dao.mileageCheck(member_code);
		//System.out.println("마일리지 : " + mileage);
		
		/* 패키지 정보 임시로 전페이지 라디오버튼에 심어놓음 */
		
		// 마일리지 가격이 더 크면
		if(mileage >= dto.getPackage_price())
		{
			// 예약내역 테이블 추가
			dao.insertBookList(dto);
			
			// 실예약자, 결제내역 테이블 추가
			dao.actualBooking(dto);
			
			model.addAttribute("notice", dao.bookNotice(member_code));
			
			return "../WEB-INF/views/user/bookApplyNotice.jsp";
		}
		else
		{
			return "../WEB-INF/views/user/mypageMember.jsp";
		}
		
	}
	
}

