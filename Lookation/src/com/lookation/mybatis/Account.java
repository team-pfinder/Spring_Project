package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lookation.dao.IHostAccountDAO;
import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dto.AccountDTO;

@Controller
public class Account
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/actions/loginform.action", method=RequestMethod.GET)
	public String loginForm(HttpServletRequest request)
	{
		String identify = request.getParameter("identify");
		
		return "../WEB-INF/views/common/login.jsp?identify=" + identify;
	}
	
	@RequestMapping(value="/actions/login.action", method=RequestMethod.POST)
	public String login(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		
		// 세션에 로그인창 이전 요청 페이지를 담는다. 
		// 만약 이전 요청 페이지가 없는 경우 메인 화면으로 이동한다.
		HttpSession session = request.getSession();
		
		String requestUrl = request.getParameter("requestUrl");
		
		// 로그인창 액션이 아닌 요청 액션이 필요하므로
		if(!requestUrl.contains("loginform.action"))
		{
			session.setAttribute("requestUrl", requestUrl);
		}
		
		
		// 로그인 검증	
		String accountCode = null;
		AccountDTO account = new AccountDTO();
		account.setEmail(request.getParameter("email"));
		account.setPw(request.getParameter("pw"));
		
		if(identify.equals("member"))
		{
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
			accountCode = dao.isLogin(account);
		}
		else if(identify.equals("host"))
		{
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
			accountCode = dao.isLogin(account);
		}
		
	
		// 로그인 실패시
		if(accountCode == null)
		{
			// 로그인 실패. 다시 로그인창으로
			return "redirect:loginform.action?identify=" + identify + "&result=fail";
		}
		// 로그인 성공시
		else
		{
			// 세션 저장
			session.setAttribute("identify", identify);
			session.setAttribute("accountCode", accountCode);
			
			// 세션에 저장했던 이전 요청 액션을 가지고 온다.
			requestUrl = (String)session.getAttribute("requestUrl");
		
			// 이전 요청 페이지를 다시 요청해야 함
			return requestUrl;
		}
	}

	@RequestMapping(value="/actions/confirmpasswordform.action", method=RequestMethod.GET)
	public String confirmPasswordForm(HttpServletRequest request)
	{
		String identify = request.getParameter("identify");
		return "../WEB-INF/views/common/confirmPassword.jsp?identify=" + identify;
	}
	
	@RequestMapping(value="/actions/confirmpassword.action", method=RequestMethod.POST)
	public String confirmPassword(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		// 로그인 확인
		
		// 세션에서 코드를 통한 검증
		HttpSession session = request.getSession();
		String accountCode = (String)session.getAttribute("accountCode");
		
		// 패스워드 검증	
		AccountDTO account = new AccountDTO();
		account.setCode("H000001");
		account.setPw(request.getParameter("pw"));
		
		int count = 0;
		if(identify.equals("member"))
		{
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
			count = dao.confirmCount(account);
		}
		else if(identify.equals("host"))
		{
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
			count = dao.confirmCount(account);
		}
	
		// 검증 실패시
		if(count == 0)
		{
			// 다시 확인창으로
			return "redirect:confirmpasswordform.action?identify=" + identify + "&result=fail";
		}
		// 로그인 성공시
		else
		{
			// 세션에 저장했던 이전 요청 액션을 가지고 온다.
			//requestUrl = (String)session.getAttribute("requestUrl");
		
			// 다음 요청 페이지를 요청해야 함
			// 뭔지는 상황마다 다르다.
			return "Next.action";
		}
		
	}
}
