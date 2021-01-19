package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHostAccountDAO;


@Controller
public class Package
{
	@Autowired
	public SqlSession sqlSession;
	
	// 패키지
	@RequestMapping(value="/actions/packageInfo.action", method=RequestMethod.GET)
	public String packageInfo(HttpServletRequest request, Model model)
	{
		// 세션 검사
		HttpSession session = request.getSession();
		String identify = (String)session.getAttribute("identify");   
		String accountCode = (String)session.getAttribute("accountCode"); 
		String result = "noSigned";
		
		if(identify != null && accountCode != null)                               
		{                                                                        
			if(identify.equals("host"))                                           
			{                                                                     
				IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	
				model.addAttribute("info", dao.getInfo(accountCode));               
		                                          	                                              
			}  
		    
			// 멤버일 경우
			if(identify.equals("member"))                                           
			{                                                                     
				// 로그인 창으로 이동한다.
				return "redirect:loginform.action?identify=" + identify;	               
		                                           
			}
		        
		    result = "signed";	                                                                   
		}
		// 로그인이 안된 경우
		else
		{
			// 로그인 창으로 이동한다.
			return "redirect:loginform.action?identify=" + identify;	
		}	                                                                  
		model.addAttribute("result", result);  
		
		String result = "noSigned";
		return "";
	}
}
