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
import com.lookation.dao.ILocationDAO;
import com.lookation.dao.IPackageDAO;
import com.lookation.dto.LocationDTO;
import com.lookation.dto.PackageDTO;


@Controller
public class Package
{
	@Autowired
	public SqlSession sqlSession;

	// 패키지 폼 관리
	@RequestMapping(value="/actions/packagemanager.action", method=RequestMethod.GET)
	public String packageManager(HttpServletRequest request, Model model)
	{
		String loc_code = request.getParameter("loc_code");
		HttpSession session = request.getSession();       
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		
		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
		    // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			//
			IPackageDAO packDao = sqlSession.getMapper(IPackageDAO.class);
			model.addAttribute("formList", packDao.packageFormList(loc_code));
			//
		    
		    // 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}
		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=host";
		}	
		
		return "../WEB-INF/views/host/inputPackageInfoForm.jsp?loc_code" + loc_code; 
	}
	// 패키지 추가 폼
	@RequestMapping(value="/actions/packageform.action", method=RequestMethod.GET)
	public String packageForm(HttpServletRequest request, Model model)
	{	
		String loc_code = request.getParameter("loc_code");
		HttpSession session = request.getSession();       
	
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		                                                                             
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));
			
		    result = "signed";                                                                                
		}                                                                                
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=host";
		}
		
		return "../WEB-INF/views/host/inputPackageInfo.jsp?loc_code=" + loc_code; 
	}
	// 패키지 추가
	@RequestMapping(value="/actions/inputpackageform.action", method= {RequestMethod.POST, RequestMethod.GET})
	public String inputPackageForm(HttpServletRequest request, Model model)
	{	
		String loc_code = request.getParameter("loc_code");
		HttpSession session = request.getSession();       
		
		String name = request.getParameter("inputPackageName");
		String start = request.getParameter("locationPackageStart");
		String end = request.getParameter("locationPackageEnd");
		String price = request.getParameter("locationPackagePrice");
		
		if(name == null || start == null || end == null || price == null)
		{
			return "../WEB-INF/views/common/wrongAccess.jsp?identify=host";
		}
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
			                                                                               
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			//
			PackageDTO p = new PackageDTO();
			p.setName(name); 
			p.setTime_start(start); 
			p.setTime_end(end); 
			p.setPrice(price); 
			p.setLoc_code(loc_code);
			
			IPackageDAO locDao = sqlSession.getMapper(IPackageDAO.class);
			locDao.insertPackage(p);
			//
		    
		    result = "signed";                                                                                
		}                                                                                   
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=host";
		}
		
		return "redirect:packagemanager.action?loc_code=" + loc_code; 
	}
	// 패키지 삭제
	@RequestMapping(value = "/actions/deletepackageform.action", method = {RequestMethod.POST, RequestMethod.GET})
	public String deletePackageForm(HttpServletRequest request, Model model)
	{
		String loc_code = request.getParameter("loc_code");

		HttpSession session = request.getSession();

		String accountCode = (String) session.getAttribute("hostCode");
		String result = "noSigned";

		if (accountCode != null)
		{
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
			model.addAttribute("info", dao.getInfo(accountCode));

			ILocationDAO locDao = sqlSession.getMapper(ILocationDAO.class);
			LocationDTO loc = new LocationDTO();
			loc.setHost_code(accountCode);
			loc.setLoc_code(loc_code);

			// 삭제하는 작업이므로 철저한 확인이 필요하다.
			// 실제 이 공간이 해당 호스트의 공간인지 검사
			// 맞다면 삭제한다.
			
			if (locDao.findLocation(loc) > 0)
			{
				String selectPackage = request.getParameter("selectPackage");
				
				if(selectPackage == null)
				{
					return "../WEB-INF/views/common/wrongAccess.jsp?identify=host";
				}
				
				PackageDTO p = new PackageDTO();
				p.setCode(selectPackage);

				IPackageDAO packDao = sqlSession.getMapper(IPackageDAO.class);
				packDao.deletePackage(p);
			}
			//

			result = "signed";
		}
		model.addAttribute("result", result);

		if (result.equals("noSigned"))
		{
			return "redirect:loginform.action?identify=host";
		}

		return "redirect:packagemanager.action?loc_code=" + loc_code;
	}
	
	// 패키지 적용폼
	@RequestMapping(value="/actions/packageapplyform.action", method=RequestMethod.GET)
	public String packageApplyForm(HttpServletRequest request, Model model)
	{
		String loc_code = request.getParameter("loc_code");
		
		HttpSession session = request.getSession();       
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		                                                                                  
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			//
			ILocationDAO locDao = sqlSession.getMapper(ILocationDAO.class);
			LocationDTO loc = new LocationDTO();
			loc.setHost_code(accountCode);
			loc.setLoc_code(loc_code);


			// 예약이 적용되는 작업이므로 철저한 확인이 필요하다.
			// 실제 이 공간이 해당 호스트의 공간인지 검사
			// 맞다면 패키지 내역을 띄워준다.
			if (locDao.findLocation(loc) > 0)
			{
				IPackageDAO packDao = sqlSession.getMapper(IPackageDAO.class);
				model.addAttribute("formList", packDao.packageFormList(loc_code));
				model.addAttribute("applyList", packDao.packageApplyList(loc_code));
				model.addAttribute("applyBookList", packDao.packageApplyBookList(loc_code));
				
				// 예약이 되어있는 패키지도 불러온다.
			}
			//
		    
		    result = "signed";                                                                                
		}                                                                                   
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=host";
		}
		
		return "../WEB-INF/views/host/packageApply.jsp?loc_code" + loc_code;
	}
	
	
	// 패키지 실제 저장
	@RequestMapping(value="/actions/packageapplyajax.action", method= {RequestMethod.POST, RequestMethod.GET})
	public String packageApplyAjax(HttpServletRequest request, Model model)
	{
		String loc_code = request.getParameter("loc_code");
		
		HttpSession session = request.getSession();       
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		                                                                               
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));                                                                             
		}
		else
		{
			// 회원이 아닌 경우 적용하지 않는다.
			return "";
		}
	
		
		// 예약이 적용되는 작업이므로 철저한 확인이 필요하다.
		// 실제 이 공간이 해당 호스트의 공간인지 검사
		// 아니라면 패키지 적용을 하지 않는다.
		ILocationDAO locDao = sqlSession.getMapper(ILocationDAO.class);
		LocationDTO loc = new LocationDTO();
		loc.setHost_code(accountCode);
		loc.setLoc_code(loc_code);
		
		if (locDao.findLocation(loc) == 0)
			return "";
		
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
