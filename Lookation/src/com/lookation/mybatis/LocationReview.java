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
	@RequestMapping(value = "/actions/writereview.action", method = RequestMethod.GET)
	public String reviewForm(Model model, HttpServletRequest request)
	{
		String loc_code = request.getParameter("loc_code");
		String identify = request.getParameter("identify");

		HttpSession session = request.getSession();
		String accountCode = (String) session.getAttribute(identify + "Code");

		String result = "noSigned";

		if (accountCode != null)
		{
			if (identify.equals("member"))
			{
				IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
				model.addAttribute("info", dao.getInfo(accountCode));
				String member_code = request.getParameter("member_code");
				model.addAttribute("member_code", member_code);
			}
			else if (identify.equals("host"))
			{
				IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
				model.addAttribute("info", dao.getInfo(accountCode));
				String hostCode = request.getParameter("hostCode");
				model.addAttribute("hostCode", hostCode);
			}
			result = "signed";
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.
		model.addAttribute("result", result);
		model.addAttribute("accountCode", accountCode);
		
		// 로그인이 안되어 있다면
		if (result.equals("noSigned"))
		{
			return "redirect:loginform.action?identify=" + identify;
		}
		model.addAttribute("loc_code", loc_code);
	
		return "../WEB-INF/views/common/writeReviewPopup.jsp";
	}

	@RequestMapping(value = "/actions/modifyformreview.action", method = RequestMethod.GET)
	public String reviewModifyForm(Model model, HttpServletRequest request)
	{
		String identify = request.getParameter("identify");

		ILocationReviewDAO locDao = sqlSession.getMapper(ILocationReviewDAO.class);

		HttpSession session = request.getSession();
		String accountCode = (String) session.getAttribute(identify + "Code");

		String result = "noSigned";

		if (accountCode != null)
		{
			// 수정 폼의 경우 각각 다른 메소드를 수행해야 한다.
			// 이용자 → updateReviewForm
			// 호스트 → updateReviewReplyForm

			// 이용자일 경우
			if (identify.equals("member"))
			{
				IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
				model.addAttribute("info", dao.getInfo(accountCode));

				String review_code = request.getParameter("review_code");
				model.addAttribute("modifyReview", locDao.updateReviewForm(review_code));
			}
			// 호스트일 경우
			else if (identify.equals("host"))
			{
				IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
				model.addAttribute("info", dao.getInfo(accountCode));

				String review_reply_code = request.getParameter("review_reply_code");
				model.addAttribute("modifyReviewReply", locDao.updateReviewReplyForm(review_reply_code));
			}
			result = "signed";
		}

		model.addAttribute("result", result);

		if (result.equals("noSigned"))
		{
			return "redirect:loginform.action?identify=" + identify;
		}

		return "../WEB-INF/views/common/modifyReviewPopup.jsp?identify=" + identify;
	}

	/* === 이용자 === */

	// 이용자 : 리뷰 작성
	@RequestMapping(value = "/actions/reviewinsert.action", method = { RequestMethod.POST, RequestMethod.GET })
	public void insertReview(LocationReviewDTO dto, HttpServletRequest request) throws IOException
	{
		ILocationReviewDAO locDao = sqlSession.getMapper(ILocationReviewDAO.class);

		MultipartRequest m = FileManager.upload(request, "images");
		ArrayList<String> imageList = FileManager.getFileNames(m);

		try
		{
			dto.setLoc_code(m.getParameter("loc_code"));
			dto.setMember_code(m.getParameter("member_code"));
			dto.setReview_rate(m.getParameter("review_rate"));
			dto.setReview_content(m.getParameter("review_content"));
			
			if( imageList.size()!=0){
				dto.setReview_img_url(imageList.get(0));
			}
			
			if (imageList.isEmpty())
			{
				locDao.insertMemReview(dto);
				
			}
			else 
			{
				locDao.insertMemImg(dto);
				
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
	}

	// 이용자 : 리뷰 수정
	@RequestMapping(value = "/actions/modifyreview.action", method = RequestMethod.POST)
	public void modifyReview(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);

		dao.updateMemReview(dto);

		// return "redirect:locationdetail.action";
	}

	// 이용자 : 리뷰 삭제
	@RequestMapping(value = "/actions/deletereview.action", method = RequestMethod.GET)
	public String deleteReview(LocationReviewDTO dto, HttpServletRequest request)
	{
		String loc_code = request.getParameter("loc_code");
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);

		dao.deleteMemReview(dto);

		return "redirect:locationdetail.action?loc_code=" + loc_code;
	}

	// 호스트 : 리뷰 답글 작성
	@RequestMapping(value = "/actions/reviewreplyinsert.action", method = RequestMethod.POST)
	public String insertReviewReply(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);

		dao.insertHostReview(dto);
		return "redirect:locationdetailhost.action";
	}

	// 호스트 : 리뷰 답글 수정
	@RequestMapping(value = "/actions/modifyreviewreply.action", method = RequestMethod.POST)
	public void modifyReviewReply(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);

		dao.updateHostReview(dto);
	}

	// 호스트 : 리뷰 답글 삭제
	@RequestMapping(value = "/actions/deletereviewreply.action", method = RequestMethod.GET)
	public String deleteReviewReply(LocationReviewDTO dto, HttpServletRequest request)
	{
		String loc_code = request.getParameter("loc_code");
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		dao.deleteHostReview(dto);

		return "redirect:locationdetailhost.action?loc_code=" + loc_code;
	}
}
