package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.ISampleDAO;

@Controller
public class BatisSample
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/actions/batissample.action", method = RequestMethod.GET)
	public String sample(Model model)
	{
		ISampleDAO dao = sqlSession.getMapper(ISampleDAO.class);
		
		model.addAttribute("info", dao.getTest());
		
		return "../WEB-INF/views/sample.jsp";
	}
}
