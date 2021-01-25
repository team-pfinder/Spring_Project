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

import com.lookation.dao.IHostAccountDAO;
import com.lookation.dao.ILocationReviewDAO;
import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dto.LocationReviewDTO;
import com.lookation.util.FileManager;
import com.oreilly.servlet.MultipartRequest;

@Controller
public class LocationReview
{
	@Autowired
	private SqlSession sqlSession;
	
	// 리뷰팝업으로 이동
	@RequestMapping(value="/actions/writereview.action", method = RequestMethod.GET)
	public String reviewForm(Model model, HttpServletRequest request)
	{	
		String loc_code = request.getParameter("loc_code");
		String identify = request.getParameter("identify");
		
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();
		String accountCode = (String)session.getAttribute(identify + "Code"); 
		
		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 이용자일 경우                                                                            
			if(identify.equals("member"))                                                   
			{                                                                               
				IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	    
				model.addAttribute("info", dao.getInfo(accountCode));
				String member_code = request.getParameter("member_code");
				model.addAttribute("member_code", member_code);
				System.out.println("리뷰폼 호출" + member_code);

			}
			// 호스트일 경우
			else if(identify.equals("host"))                                                   
			{                                                                               
				IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
				model.addAttribute("info", dao.getInfo(accountCode));
				String hostCode = request.getParameter("hostCode");
				model.addAttribute("hostCode", hostCode);
			}
			// 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               
		model.addAttribute("accountCode", accountCode);
		System.out.println("뷰에 넘겨줄때 : " + accountCode);
		
		// 로그인이 안되어 있다면 
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=" + identify;
		}
		                                                                                    
		model.addAttribute("loc_code", loc_code);
		System.out.println("뷰에 넘겨줄때 : " + loc_code);
		
		return "../WEB-INF/views/common/writeReviewPopup.jsp";
	}
	
	@RequestMapping(value="/actions/modifyformreview.action", method = RequestMethod.GET)
	public String reviewModifyForm(Model model, HttpServletRequest request)
	{	
		// 공통 측면 뷰일 경우 사용자가 누구인지 알기 위해 
		// identify를 GET 받아야한다.
		String identify = request.getParameter("identify");
		
		ILocationReviewDAO locDao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();
		String accountCode = (String)session.getAttribute(identify + "Code"); 

		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		if(accountCode != null)                                         
		{       
			// 수정 폼의 경우 각각 다른 메소드를 수행해야 한다.
			// 이용자 → updateReviewForm
			// 호스트 → updateReviewReplyForm
			
			// 이용자일 경우                                                                            
			if(identify.equals("member"))                                                   
			{                                                                               
				IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	    
				model.addAttribute("info", dao.getInfo(accountCode));

				String review_code = request.getParameter("review_code");
				model.addAttribute("modifyReview", locDao.updateReviewForm(review_code));
			}
			// 호스트일 경우
			else if(identify.equals("host"))                                                   
			{                                                                               
				IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
				model.addAttribute("info", dao.getInfo(accountCode));

				String review_reply_code = request.getParameter("review_reply_code");
				model.addAttribute("modifyReviewReply", locDao.updateReviewReplyForm(review_reply_code));
			}
			// 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               

		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=" + identify;
		}
		                                                                                                                                                                                                                                                                                                                            
		// 뒤에 identify를 GET 해준다.                                                                                              
		return "../WEB-INF/views/common/modifyReviewPopup.jsp?identify=" + identify;                                        
	} 
	
	
	
	/*=== 이용자 ===*/
	
	// 이용자 : 리뷰 텍스트 작성
	@RequestMapping(value="/actions/reviewinsert.action", method = {RequestMethod.POST, RequestMethod.GET})
	public void insertReview(LocationReviewDTO dto, HttpServletRequest request) throws IOException
	{
		ILocationReviewDAO locDao = sqlSession.getMapper(ILocationReviewDAO.class);
		MultipartRequest m = new MultipartRequest(request, "images");
		String loc_name = m.getParameter("loc_name");
		System.out.println("loc_name 파라미터로 : " + loc_name);
		
		dto.setLoc_code(m.getParameter("loc_code"));
		
		System.out.println("loc_name : " + m.getParameter("loc_code"));
		dto.setMember_code(m.getParameter("member_code"));
		dto.setReview_rate(m.getParameter("review_rate"));
		dto.setReview_content(m.getParameter("review_content"));
		
		
		System.out.println("여기서 2 " + dto.getMember_code());
		
		locDao.insertMemReview(dto);
		
		System.out.println("확인 : " + dto.getLoc_code() + ", " + dto.getMember_code() 
		+ ", " + dto.getReview_rate() + ", " + dto.getReview_content() +" 끝! ");
		
	}
	
	// 이용자 : 리뷰 이미지 첨부 작성
	@RequestMapping(value="/actions/reviewimg.action", method = {RequestMethod.POST, RequestMethod.GET})
	public void insertReviewImg(LocationReviewDTO dto, HttpServletRequest request)
	{
		ILocationReviewDAO locDao = sqlSession.getMapper(ILocationReviewDAO.class);
		System.out.println("여기서 2 " + dto.getMember_code());
		
		try
		{
			MultipartRequest m = FileManager.upload(request, "images");
			ArrayList<String> imageList = FileManager.getFileNames(m);
			
			// ('L000001', 'M000002', 4, '안녕하세용', 'too.png');
			
			
			dto.setLoc_code(m.getParameter("loc_code"));
			dto.setMember_code(m.getParameter("member_code"));
			dto.setReview_rate(m.getParameter("review_rate"));
			dto.setReview_content(m.getParameter("review_content"));
			dto.setReview_img_url(imageList.get(0));
			
			
			
		} catch (Exception e)
		{
			e.toString();
		}
		
		System.out.println("파일첨부 확인 : " + dto.getLoc_code() + ", " + dto.getMember_code() 
		+ ", " + dto.getReview_rate() + ", " + dto.getReview_img_url() +" 끝! ");
		
		// 리뷰쓰기
		locDao.insertMemImg(dto);
	}
	
	// 이용자 : 리뷰 수정
	@RequestMapping(value="/actions/modifyreview.action", method = RequestMethod.POST)
	public void modifyReview(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.updateMemReview(dto);
		
		//return "redirect:locationdetail.action";
	}
		
	
	
	// 이용자 : 리뷰 삭제 
	@RequestMapping(value="/actions/deletereview.action", method = RequestMethod.GET)
	public void deleteReview(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.deleteMemReview(dto);
		
		//return "redirect:locationdetail.action";
	}
	
	// 호스트 : 리뷰 답글 작성
	@RequestMapping(value="/actions/reviewreplyinsert.action", method = RequestMethod.POST)
	public String insertReviewReply(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.insertHostReview(dto);
		return "redirect:locationdetailhost.action";
	}
	
	
	// 호스트 : 리뷰 답글 수정
	@RequestMapping(value="/actions/modifyreviewreply.action", method = RequestMethod.POST)
	public void modifyReviewReply(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.updateHostReview(dto);
		
		//return "redirect:locationdetailhost.action";
	}
	
	
	// 호스트 : 리뷰 답글 삭제
	@RequestMapping(value="/actions/deletereviewreply.action", method = RequestMethod.GET)
	public void deleteReviewReply(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.deleteHostReview(dto);
		
		//return "redirect:locationdetailhost.action";
	}
}
