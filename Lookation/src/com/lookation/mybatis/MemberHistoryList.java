package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IMemberHistoryListDAO;
import com.lookation.dao.ISampleDAO;

@Controller
public class MemberHistoryList
{
	@Autowired
	private SqlSession sqlSession;
	
	
	@RequestMapping(value="/actions/membermileagehistorylist.action", method = RequestMethod.GET)
	public String sample(Model model)
	{
		IMemberHistoryListDAO dao = sqlSession.getMapper(IMemberHistoryListDAO.class);
		String memberCode = "M000001";
		
		model.addAttribute("payHistory", dao.payHistory(memberCode));
		model.addAttribute("loadHistory", dao.loadHistory(memberCode));
		model.addAttribute("exchangeHistory", dao.exchangeHistory(memberCode));
		model.addAttribute("refundHistory", dao.refundHistory(memberCode));
		
		return "../WEB-INF/views/user/mileageHistory.jsp";
	}
	
}
