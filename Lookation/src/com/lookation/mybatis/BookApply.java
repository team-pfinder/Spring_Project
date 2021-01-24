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
import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dto.BookApplyDTO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	@RequestMapping(value = "/actions/bookapply.action", method = RequestMethod.POST)
	public String bookapplyBasic(Model model, BookApplyDTO dto, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();
		// ※ 임시 ------------------------------------------------------
		session.setAttribute("memberCode", "M000001");
		// --------------------------------------------------------------
		String accountCode = (String)session.getAttribute("memberCode");
		
		
		// 로그인 확인을 기록하기 위함
		String result = "noSigned";

		// 회원 코드가 세션에 세팅되어 있다면
		if (accountCode != null)
		{
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
			// db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.

			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
			IBookApplyDAO bookDao = sqlSession.getMapper(IBookApplyDAO.class);
			model.addAttribute("info", dao.getInfo(accountCode));

			// 이전 폼(locationDetail) 에서
			String loc_code = dto.getLoc_code();
			String apply_package_code = dto.getApply_package_code();
			int book_people = dto.getBook_people();

			// 공간정보 출력
			model.addAttribute("loc_code", dto.getLoc_code());
			model.addAttribute("profile", bookDao.setProfile(accountCode));
			model.addAttribute("basic", bookDao.bookapplyBasic(loc_code));
			model.addAttribute("bizinfo", bookDao.bookapplyBizInfo(loc_code));

			// 선택한 패키지의 정보 출력
			model.addAttribute("selectPack", bookDao.bookPackage(apply_package_code));
			model.addAttribute("apply_package_code", apply_package_code);
			model.addAttribute("book_people", book_people);

			// 로그인이 되었음을 기록한다.
			result = "signed";
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.
		model.addAttribute("result", result);

		if (result.equals("noSigned"))
		{
			// 로그인 창으로 이동한다.
			return "redirect:loginform.action?identify=member";
		}

		return "../WEB-INF/views/user/bookApply.jsp";
	}

	// 예약 및 결제
	@RequestMapping(value = "/actions/bookpay.action", method = RequestMethod.POST)
	public String MileageCheck(ModelMap model, BookApplyDTO dto, String member_code
			, HttpServletResponse response, HttpServletRequest request)throws IOException
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

			// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.
			IBookApplyDAO bookDao = sqlSession.getMapper(IBookApplyDAO.class);

			// 해당 멤버의 마일리지 잔액 가져오기
			int mileage = bookDao.mileageCheck(member_code);
			System.out.println("마일리지 : " + mileage);
			System.out.println("package_price" + dto.getPackage_price());
			// 마일리지 가격이 더 크면
			if (mileage >= dto.getPackage_price())
			{
				// 예약내역 테이블 추가
				bookDao.insertBookList(dto);

				// 실예약자, 메시지, 결제내역 테이블 추가
				bookDao.actualBooking(dto);

				model.addAttribute("notice", bookDao.bookNotice(member_code));

				return "../WEB-INF/views/user/bookApplyNotice.jsp";
			} else
			{
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('마일리지가 부족합니다. 마일리지 충전 페이지로 이동합니다.');</script>");
				out.flush();

			// 로그인이 되었음을 기록한다.
		        result = "signed";      
		    }                                                                          
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               

		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=member";
		}
		                                                                                                                                                                                                                                                                                                                                                                                                                             
		return "../WEB-INF/views/user/mypageMember.jsp";                                 
		}

	}

