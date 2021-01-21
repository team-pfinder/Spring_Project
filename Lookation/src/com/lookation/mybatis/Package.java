package com.lookation.mybatis;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHostAccountDAO;
import com.lookation.dao.IPackageDAO;
import com.lookation.dto.PackageDTO;


@Controller
public class Package
{
	@Autowired
	public SqlSession sqlSession;
	
	// 패키지 적용폼
	@RequestMapping(value="/actions/packageapplyform.action", method=RequestMethod.GET)
	public String packageApplyForm(HttpServletRequest request, Model model)
	{
		/* 나중에
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
		*/ 
		
		
		// 패키지를 가져온다.
		// 패키지를 가져오기 위한 공간 코드 필요
		// (공간코드 : 'L000001'을 가정하고 한다)
		
		IPackageDAO dao = sqlSession.getMapper(IPackageDAO.class);
		model.addAttribute("formList", dao.packageFormList("L000001"));
		model.addAttribute("applyList", dao.packageApplyList("L000001"));
		
		return "../WEB-INF/views/host/packageApply.jsp";
	}
	
	
	// 패키지 실제 저장
	@RequestMapping(value="/actions/packageapplyajax.action", method=RequestMethod.POST)
	public String packageApplyAjax(HttpServletRequest request, Model model)
	{
		// 세션 검사 필요..
		
		String[] applyCode = request.getParameterValues("apply_code[]");
		String[] packageCode = request.getParameterValues("package_code[]");
		String[] date = request.getParameterValues("date[]");
		String[] state = request.getParameterValues("state[]");
		
		IPackageDAO dao = sqlSession.getMapper(IPackageDAO.class);
		
		for(int i = 0; i < applyCode.length; i++)
		{
			PackageDTO p = new PackageDTO();
			// 기본정보 세팅
			p.setApply_code(applyCode[i]);
			p.setCode(packageCode[i]);
			p.setApply_date(date[i]);

			// 상태에 따라 insert, update, delete
			// 트랜잭션 수행
			if(state[i].equals("insert"))
			{
				//System.out.println("insert");
				dao.insertApply(p);
			}
			else if(state[i].equals("update"))
			{
				//System.out.println("update");
				dao.updateApply(p);
			}
			else if(state[i].equals("delete"))
			{
				//System.out.println("delete");
				dao.deleteApply(p);
			}
		}
		
		return "";
	}
	
	
}
