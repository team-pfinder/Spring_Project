package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHostHistoryListDAO;
import com.lookation.dao.IMemberHistoryListDAO;
import com.lookation.dao.ISampleDAO;

@Controller
public class HostHistoryList
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/actions/hostmileagehistorylist.action", method = RequestMethod.GET)
	public String sample(Model model)
	{
		IHostHistoryListDAO dao = sqlSession.getMapper(IHostHistoryListDAO.class);
		String hostCode = "H000001";
		
		
		model.addAttribute("calHistory", dao.calHistory(hostCode));
		model.addAttribute("exchangeHistory", dao.exchangeHistory(hostCode));
		
		return "../WEB-INF/views/host/mileageHistory.jsp";
	}
}
