package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.util.EmailManager;


@Controller
public class blackListEmail
{

	@RequestMapping(value="/actions/", method= {RequestMethod.POST, RequestMethod.GET})
	public String sendPasswordEmail(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		String email = request.getParameter("email");
		
		if(email == null)
		{
			return "../WEB-INF/views/common/wrongAccess.jsp?identify=" + identify;
		}
		
		String html = "";
		
		String titleType = (identify.equals("host") ? "호스트" : "이용자");
		String title = "■■■■■  블랙리스트 전환 알림 메일입니다.  ■■■■■";


		EmailManager.sendHtml(email, "lookation", title, html);

		
		model.addAttribute("email", email);
		model.addAttribute("result", "noSigned");
		
		return "../WEB-INF/views/common/sendFindPassword.jsp?identify" + identify;
	}
}
