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
	
	// 로그인
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

	// 본인인증
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
	
	// 프로필
	@RequestMapping(value="/actions/profile.action", method=RequestMethod.GET)
	public String profile(HttpServletRequest request, Model model)
	{
		//String identify = request.getParameter("identify");
		
		// 로그인 확인
		// ...
		
		// 세션 정보를 가지고 온다.
		HttpSession session = request.getSession();
		String identify = "member";	
		String accountCode = "";
		
		if(identify.equals("host"))
			accountCode = "H000004";
		else if(identify.equals("member"))
			accountCode = "M000003";
		
		// 세션 정보의 회원 코드를 토대로 프로필 정보를 찾는다.	
		if(identify.equals("host"))
		{
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	
			model.addAttribute("info", dao.getInfo(accountCode));
			model.addAttribute("blackListDate", dao.getBlackListDate(accountCode));
		}
		else if(identify.equals("member"))
		{
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
			model.addAttribute("info", dao.getInfo(accountCode));
			model.addAttribute("blackListDate", dao.getBlackListDate(accountCode));
		}
		
		return "../WEB-INF/views/common/profile.jsp?identify=" + identify;
	}
	
	@RequestMapping(value="/actions/ajaxmodifytel.action", method=RequestMethod.POST)
	public String ajaxModifyTel(HttpServletRequest request, Model model)
	{
		//String identify = request.getParameter("identify");
		
		// 로그인 확인
		// ...
		// 세션 정보를 가지고 온다.
		HttpSession session = request.getSession();
		String identify = "member";
		
		//
		String accountCode = "";
		if(identify.equals("host"))
			accountCode = "H000001";
		else if(identify.equals("member"))
			accountCode = "M000001";
		
		String tel = request.getParameter("tel");
		AccountDTO account = new AccountDTO();
		account.setTel(tel);
		account.setCode(accountCode);
		
		// 세션 정보의 회원 코드를 토대로 회원 전화번호를 수정한다.
		if(identify.equals("host"))
		{
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
			
			// 수정 후 다시 가져옴
			dao.modifyTel(account);
			model.addAttribute("result", dao.getInfo(accountCode).getTel());
		}
		else if(identify.equals("member"))
		{
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
			
			// 수정 후 다시 가져옴
			dao.modifyTel(account);
			model.addAttribute("result", dao.getInfo(accountCode).getTel());
		}

		
		return "../WEB-INF/views/ajax/AccountAjax.jsp";
	}

	
	@RequestMapping(value="/actions/changepasswordform.action", method=RequestMethod.GET)
	public String changePasswordForm(HttpServletRequest request)
	{
		// 세션 정보를 가지고 온다.
		HttpSession session = request.getSession();
		String identify = "member";
		
		return "../WEB-INF/views/common/changePassword.jsp?identify=" + identify;
	}
	
	@RequestMapping(value="/actions/changepassword.action", method=RequestMethod.POST)
	public String changePassword(HttpServletRequest request)
	{
		// 세션 정보를 가지고 온다.
		HttpSession session = request.getSession();
		String identify = "member";
		String accountCode = "";
		
		if(identify.equals("host"))
			accountCode = "H000001";
		else if(identify.equals("member"))
			accountCode = "M000001";
		
		String newPw = request.getParameter("pw_new");
		
		System.out.println(newPw);
		
		AccountDTO account = new AccountDTO();
		account.setCode(accountCode);
		account.setPw(newPw);
		
		// 세션 정보의 회원 코드를 토대로 프로필 정보를 찾는다.	
		if(identify.equals("host"))
		{
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
			dao.modifyPassword(account);
		}
		else if(identify.equals("member"))
		{
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
			dao.modifyPassword(account);
		}
		
		return "redirect:profile.action";
	}
}
