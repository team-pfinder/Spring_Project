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
		
		System.out.println(requestUrl);
		
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
			session.setAttribute(identify + "Code", accountCode);
			
			// 세션에 저장했던 이전 요청 액션을 가지고 온다.
			requestUrl = (String)session.getAttribute("requestUrl");
			
			// 이전 요청 페이지를 다시 요청해야 함
			return "redirect:" + requestUrl;
		}
	}

	// 본인인증
	@RequestMapping(value="/actions/confirmpasswordform.action", method=RequestMethod.GET)
	public String confirmPasswordForm(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		
		// 세션을 가져옴
		HttpSession session = request.getSession(); 
		
		// 회원 코드를 가져온다.           
		String accountCode = (String)session.getAttribute(identify + "Code");       
		if(accountCode == null)                               
		{
			// 로그인 창으로 이동한다.
			return "redirect:loginform.action?identify=" + identify;	
		}
		
		// 검증 후 요청되는 액션
		String requestUrl = request.getParameter("requestUrl");
		
		System.out.println(requestUrl);
		
		if(requestUrl != null && !requestUrl.contains("confirmpasswordform.action"))
		{
			session.setAttribute("requestUrl", requestUrl);
		}
		
		// 이전 액션
		String beforePage = request.getHeader("Referer");
		String beforeUrl = "";
		if(beforePage == null)
		{
			if(identify.equals("host"))
				beforeUrl = "hostmain.action";
			else if(identify.equals("member"))
				beforeUrl = "membermain.action";
		}
		else
		{
			// 뒷부분 액션만 잘라오기
			String[] subArr = beforePage.split("/");
			beforeUrl = subArr[subArr.length-1];
		}
		
		// 취소를 눌렀을때 요청되는 액션
		if(!beforeUrl.contains("confirmpasswordform.action"))
			session.setAttribute("beforeUrl", beforeUrl);
		
		return "../WEB-INF/views/common/confirmPassword.jsp?identify=" + identify;
	}
	
	@RequestMapping(value="/actions/confirmpassword.action", method=RequestMethod.POST)
	public String confirmPassword(HttpServletRequest request, Model model)
	{
		// 공통 측면 뷰일 경우 사용자가 누구인지 알기 위해 
		// identify를 GET 받아야한다.
		String identify = request.getParameter("identify");
		
		// 세션을 통한 검증                                                                    
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute(identify + "Code"); 
		
		
		// 로그인 확인
		if(accountCode == null)                               
		{   
			System.out.println("check!");
			// 로그인이 안되어 있다면 로그인 창으로 이동한다.
			return "redirect:loginform.action?identify=" + identify;	
		}
		
		// 패스워드 검증	
		AccountDTO account = new AccountDTO();
		account.setCode(accountCode);
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
			String requestUrl = (String)session.getAttribute("requestUrl");
			session.setAttribute("requestUrl", null);
			System.out.println(requestUrl);
			// 다음 요청 페이지를 요청해야 함
			// 뭔지는 상황마다 다르다.
			return "redirect:" + requestUrl + "?identify=" + identify;
		}
		
	}
	
	// 프로필
	@RequestMapping(value="/actions/profile.action", method=RequestMethod.GET)
	public String profile(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute(identify + "Code"); 
		
		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned"; 
		
		// 회원 코드가 세션에 세팅되어 있다면   
		if(accountCode != null)                               
		{  	
			// 호스트일 경우                                                             
			if(identify.equals("host"))                                           
			{                                                                     
				IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	
				model.addAttribute("info", dao.getInfo(accountCode));
				model.addAttribute("blackListDate", dao.getBlackListDate(accountCode));
			}  
		        // 멤버일 경우
			if(identify.equals("member"))                                           
			{             
				System.out.println("test");
				IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	
				model.addAttribute("info", dao.getInfo(accountCode)); 
				model.addAttribute("blackListDate", dao.getBlackListDate(accountCode));
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
		
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=" + identify;
		}
		
		return "../WEB-INF/views/common/profile.jsp?identify=" + identify;
	}
	
	@RequestMapping(value="/actions/ajaxmodifytel.action", method=RequestMethod.POST)
	public String ajaxModifyTel(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		
		// 세션 정보를 가지고 온다.
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute(identify + "Code"); 
		
		if(accountCode == null)    
			return "../WEB-INF/views/ajax/AccountAjax.jsp";
		
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
	public String changePasswordForm(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		
		// 세션 정보를 가지고 온다.
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute(identify + "Code");                                                      
		String result = "noSigned";
		
		// 둘다 존재하는 경우 (로그인 된 경우)                                                                         
		if(accountCode != null)                               
		{            
			// 호스트일 경우                                                             
			if(identify.equals("host"))                                           
			{                                                                     
				// 호스트 측면 해야할 컨트롤러 작업
				IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	
				model.addAttribute("info", dao.getInfo(accountCode));               
		                                          	                                              
			}  
		        // 멤버일 경우
			if(identify.equals("member"))                                           
			{                                                                     
				// 이용자 측면 해야할 컨트롤러 작업
				IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	
				model.addAttribute("info", dao.getInfo(accountCode));               
		                                           
			}
		        
		    result = "signed";	                                                                   
		}                                                                 
		model.addAttribute("result", result); 
		
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=" + identify;
		}
		
		return "../WEB-INF/views/common/changePassword.jsp?identify=" + identify;
	}
	
	@RequestMapping(value="/actions/changepassword.action", method=RequestMethod.POST)
	public String changePassword(HttpServletRequest request, Model model)
	{
		// 공통 측면 뷰일 경우 사용자가 누구인지 알기 위해 
		// identify를 GET 받아야한다.
		String identify = request.getParameter("identify");

		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute(identify + "Code");                                                        
                                                  	
		if(accountCode == null)
		{
			// 로그인 창으로 이동한다.
			return "redirect:loginform.action?identify=" + identify;	
		}	                                                                  
		  
		String newPw = request.getParameter("pw_new");
		
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
		
		return "redirect:profile.action?identify=" + identify;
	}
	
	// 비밀번호 찾기 (변경)
	@RequestMapping(value="/actions/findpasswordform.action", method=RequestMethod.GET)
	public String findPasswordForm(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		
		model.addAttribute("result", "noSigned");
		
		return "../WEB-INF/views/common/findPassword.jsp?identify=" + identify;
	}
	
	@RequestMapping(value="/actions/ajaxfindemail.action", method=RequestMethod.POST)
	public String ajaxFindEmail(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		
		String email = request.getParameter("email");
		AccountDTO account = new AccountDTO();
		account.setEmail(email);
		
		// Email의 존재여부 확인
		String result = "존재하지 않는 이메일";
		if(identify.equals("host"))
		{
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
			
			if(dao.countEmail(account) > 0)
				result = "이미 존재하는 이메일";
			
			model.addAttribute("result", result);
		}
		else if(identify.equals("member"))
		{
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
			
			if(dao.countEmail(account) > 0)
				result = "이미 존재하는 이메일";
			
			model.addAttribute("result", result);
		}

		return "../WEB-INF/views/ajax/AccountAjax.jsp"; 
	}
	
	@RequestMapping(value="/actions/changepasswordnologinform.action", method=RequestMethod.POST)
	public String changePasswordNoLoginForm(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		String email = request.getParameter("email");
		
		model.addAttribute("email", email);
		
		return "../WEB-INF/views/common/changePasswordNoLogin.jsp?identify=" + identify;
	}
	
	@RequestMapping(value="/actions/changepasswordnologin.action", method=RequestMethod.POST)
	public String changePasswordNoLogin(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		String email = request.getParameter("email");
		String newPw = request.getParameter("pw_new");
		String requestUrl = "";
		
		AccountDTO account = new AccountDTO();
		account.setEmail(email);
		account.setPw(newPw);
		
		if(identify.equals("host"))
		{
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
			dao.modifyPasswordNoLogin(account);
			requestUrl = "hostmain.action";
		}
		else if(identify.equals("member"))
		{
			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
			dao.modifyPasswordNoLogin(account);
			requestUrl = "membermain.action";
		}
		
		return "redirect:" + requestUrl;
	}
}
