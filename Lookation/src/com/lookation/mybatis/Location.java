package com.lookation.mybatis;

import java.util.ArrayList;

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
import com.lookation.dto.LocationDTO;
import com.lookation.util.FileManager;
import com.lookation.util.LocationManager;
import com.oreilly.servlet.MultipartRequest;

@Controller
public class Location
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/actions/locationlist.action", method = RequestMethod.GET)
	public String locationList(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();       
		
		session.setAttribute("hostCode", "H000001");
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		
		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
		    // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			// 호스트측면 추가로 해야할 작업은 이 밑에 쓴다.
			ILocationDAO locDao = sqlSession.getMapper(ILocationDAO.class);
            
		    LocationDTO loc = new LocationDTO();
		    loc.setHost_code(accountCode);
		    model.addAttribute("locList", locDao.locList(loc));
		    
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
	    
		return "../WEB-INF/views/host/locationList.jsp";
	}
	
	@RequestMapping(value="/actions/deletelocation.action", method = RequestMethod.GET)
	public String deleteAction(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();       
		
		session.setAttribute("hostCode", "H000001");
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		                                                                                 
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			ILocationDAO locDao = sqlSession.getMapper(ILocationDAO.class);

			String loc_code = request.getParameter("loc_code");
			
			LocationDTO loc = new LocationDTO();
			loc.setHost_code(accountCode);
			loc.setLoc_code(loc_code);
			
			// 삭제하는 작업이므로 철저한 확인이 필요하다.
			// 실제 이 공간이 해당 호스트의 공간인지 검사
			// 맞다면 삭제한다.
			if(locDao.findLocation(loc) > 0)
				locDao.deleteLocation(loc);

		    result = "signed";                                                                                
		}                                                                                
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=host";
		}
		      
		return "redirect:locationlist.action";
	}
	
	
	@RequestMapping(value="/actions/basicform.action", method = RequestMethod.GET)
	public String basicForm(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();

		session.setAttribute("hostCode", "H000001");

		String accountCode = (String) session.getAttribute("hostCode");
		String result = "noSigned";

		if (accountCode != null)
		{
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
			model.addAttribute("info", dao.getInfo(accountCode));

			result = "signed";
		}
		model.addAttribute("result", result);

		if (result.equals("noSigned"))
		{
			return "redirect:loginform.action?identify=host";
		}

		return "../WEB-INF/views/host/inputBasicInfo.jsp";
	}
	
	@RequestMapping(value="/actions/basicajax.action", method = RequestMethod.POST)
	public String basicAjax(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();       
		
		session.setAttribute("hostCode", "H000001");
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		                                                                                   
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));
		    
			//
			String[] arrFacility = request.getParameterValues("facility[]");
			String[] arrPrecaution = request.getParameterValues("precaution[]");
			
			ArrayList<String> tmpFacility = new ArrayList<String>();
			ArrayList<String> tmpPrecaution = new ArrayList<String>();
			
			for(String facility : arrFacility)
			{
				tmpFacility.add(facility);
			}
			
			for(String precaution : arrPrecaution)
			{
				tmpPrecaution.add(precaution);
			}
			
			LocationManager.setArrFacility(tmpFacility);
			LocationManager.setArrPrecaution(tmpPrecaution);
			//
			
		    result = "signed";                                                                                
		}                                                                                   
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=host";
		}

		return "";
	}
	
	@RequestMapping(value="/actions/inputbasicinfo.action", method = RequestMethod.POST)
	public String inputBasicInfo(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();       
		
		session.setAttribute("hostCode", "H000001");
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		                                                                                   
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));
		    
			//
			try
			{
				MultipartRequest m = FileManager.upload(request, "images");
				ArrayList<String> fileNames = FileManager.getFileNames(m);
				
			 	LocationManager.setName(m.getParameter("inputLocName"));
			 	LocationManager.setType(m.getParameter("inputLocType"));
			 	LocationManager.setShortIntro(m.getParameter("inputShortIntro"));
			 	LocationManager.setIntro(m.getParameter("inputIntro"));
			 	LocationManager.setThumbnail(fileNames.get(0));
			 	LocationManager.setAddress(m.getParameter("inputAddr"));
			 	LocationManager.setDetailAddress(m.getParameter("inputDetailAddr"));
			 	
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			//
			
		    result = "signed";                                                                                
		}                                                                                   
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=host";
		}
			
		return "../WEB-INF/views/host/inputContactInfo.jsp";
	}
	
	@RequestMapping(value="/actions/inputcontactinfo.action", method = RequestMethod.POST)
	public String inputContactInfo(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();       
		
		session.setAttribute("hostCode", "H000001");
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		                                                                                   
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));
		    
			//
			LocationManager.setEmail(request.getParameter("inputEmail"));
			LocationManager.setTel(request.getParameter("inputContact"));
			LocationManager.setMainTel(request.getParameter("inputMainContact"));
			//
			
		    result = "signed";                                                                                
		}                                                                                   
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=host";
		}
		
		return "../WEB-INF/views/host/inputBusinessInfo.jsp";
	}
	
	@RequestMapping(value="/actions/inputbusinessinfo.action", method = RequestMethod.POST)
	public String inputBusinessInfo(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();       
		
		session.setAttribute("hostCode", "H000001");
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		                                                                                   
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));
		    
			//
			try
			{
				MultipartRequest m = FileManager.upload(request, "images");
				ArrayList<String> fileNames = FileManager.getFileNames(m);
				
			 	LocationManager.setBizName(m.getParameter("inputBizName"));
			 	LocationManager.setBizCeo(m.getParameter("inputBizCeo"));
			 	LocationManager.setBizNum(m.getParameter("inputBizNum"));
			 	LocationManager.setBizLicense(fileNames.get(0));
			 	LocationManager.setBizCeoType(m.getParameter("inputBizCeoType"));
			 	LocationManager.setBizMainType(m.getParameter("inputBizMainType"));
			 	LocationManager.setBizSubType(m.getParameter("inputBizSubType"));
	 			LocationManager.setBizAddresss(m.getParameter("inputAddr"));
	 			LocationManager.setBizDetailAddress(m.getParameter("inputDetailAddr"));
			 	
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			//
			
		    result = "signed";                                                                                
		}                                                                                   
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=host";
		}

		return "../WEB-INF/views/host/inputDetailInfo.jsp";
	}
	
	@RequestMapping(value="/actions/inputdetailinfo.action", method = RequestMethod.POST)
	public String inputDetailInfo(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();       
		
		session.setAttribute("hostCode", "H000001");
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		                                                                                   
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));
		    
			//
			try
			{
				MultipartRequest m = FileManager.upload(request, "images");
				ArrayList<String> fileNames = FileManager.getFileNames(m);
				
			 	LocationManager.setArrDetailImage(fileNames);
			 	LocationManager.setMinPeople(m.getParameter("inputMinPeople"));
			 	LocationManager.setMaxPeople(m.getParameter("inputMaxPeople"));
			 	LocationManager.setWebUrl(m.getParameter("inputWebUrl"));
	 			
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			//
			
		    result = "signed";                                                                                
		}                                                                                   
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=host";
		}
			
		return "../WEB-INF/views/host/inputUsingInfo.jsp";
	}
	
	@RequestMapping(value="/actions/inputusinginfo.action", method = RequestMethod.POST)
	public String inputUsingInfo(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();       
		
		session.setAttribute("hostCode", "H000001");
		
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";    
		                                                                                   
		if(accountCode != null)                                         
		{       
			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));
		    
			//
			LocationManager.setUsingHour(request.getParameter("inputUsingHour"));
			LocationManager.setDayOff(request.getParameter("inputDayOff"));
			LocationManager.setAppointDayOff(request.getParameter("inputAppointDayoff"));
			//
			
		    result = "signed";                                                                                
		}                                                                                   
		model.addAttribute("result", result); 
	    
		if(result.equals("noSigned"))
		{
		    return "redirect:loginform.action?identify=host";
		}
		
		
		// -------------------- 최종 입력 트랜잭션 수행 --------------------
		ILocationDAO dao = sqlSession.getMapper(ILocationDAO.class);
        
        LocationDTO dto = new LocationDTO();
        
        // 공간
        dto.setHost_code(accountCode);
        
        dao.inputLoc(dto);	// loc_code set
   

        // 1. 기본 정보
        dto.setLoc_type(LocationManager.getType());
        dto.setLoc_name(LocationManager.getName());
        dto.setLoc_short_intro(LocationManager.getShortIntro());
        dto.setLoc_intro(LocationManager.getIntro());
        dto.setLoc_addr(LocationManager.getAddress());
        dto.setLoc_detail_addr(LocationManager.getDetailAddress());
        
        dao.inputBasicInfo(dto);	// loc_basic_info_code set
        
        // 1-1. 시설안내(기본정보)
		for (String str : LocationManager.getArrFacility())
		{
			dto.setFacility_content(str);
			dao.inputFacilityInfo(dto);
		}

		// 1-2. 주의사항(기본정보)
		for (String str : LocationManager.getArrPrecaution())
		{
			dto.setCaution_content(str);
			dao.inputCaution(dto);
		}
        
        // 1-3. 썸네일(기본정보)
        dto.setThumbnail_url(LocationManager.getThumbnail());
       
        dao.inputThumbnail(dto);

        
		// 3. 연락처 정보
		dto.setLoc_email(LocationManager.getEmail());
		dto.setLoc_tel(LocationManager.getTel());
		dto.setLoc_main_tel(LocationManager.getMainTel());
		
		dao.inputContact(dto);
		
		
		// 4. 사업자 정보
	    dto.setBiz_name(LocationManager.getBizName());
	    dto.setBiz_ceo(LocationManager.getBizCeo());
	    dto.setBiz_license_url(LocationManager.getBizLicense());
	    dto.setBiz_ceo_type(LocationManager.getBizCeoType());
	    dto.setBiz_main_type(LocationManager.getBizMainType());
	    dto.setBiz_sub_type(LocationManager.getBizSubType());
	    dto.setBiz_addr(LocationManager.getBizAddresss());
	    dto.setBiz_license_number(LocationManager.getBizNum());
	    
	    dao.inputBizInfo(dto);
	    
	    
	    // 5. 상세 정보
	    dto.setMin_people(LocationManager.getMinPeople());
	    dto.setMax_people(LocationManager.getMaxPeople());
	    
	    dao.inputDetailInfo(dto);	// 상세정보코드 set
	    

	    // 6. 상세 이미지
	    for (String str : LocationManager.getArrDetailImage())
		{
			dto.setLoc_detail_img_url(str);
			dao.inputDetailImg(dto);
		}
	    
	    // 6-1. 웹 사이트
	    dto.setLoc_web_url(LocationManager.getWebUrl());
	    dao.inputLocWeb(dto);
	    
	    
	    // 7. 이용 정보  
	    dto.setLoc_use_hour(LocationManager.getUsingHour());
	    dto.setLoc_use_day_off(LocationManager.getDayOff());
	    dto.setLoc_use_appoint_day_off(LocationManager.getAppointDayOff());
	    
	    dao.inputUsingInfo(dto);
	    
	    
	    // 8. 검수 신청
	    dao.inputInspectRegList(dto);  // set inspect_reg_code
		
		
		return "redirect:locationlist.action";
	}
	
	// 미구현..
	@RequestMapping(value="/actions/insertinfotest.action", method = RequestMethod.GET)
    public String insertinfotest(HttpServletRequest request, Model model)
    {
        // 쿼리문으로 db에 저장할 코드가 들어갈 위치

        ILocationDAO dao = sqlSession.getMapper(ILocationDAO.class);
        
        LocationDTO dto = new LocationDTO();
   
	    // -------------------- 수정 --------------------
	    
	    // 기본 정보
	    dao.modifyBasicInfo(dto);
		
		// 기본 정보(썸네일)
		dao.modifyThumbnail(dto);

		// 기본 정보(시설안내)
		dao.modifyFacilityInfo(dto);
		
		// 기본 정보(주의사항)
		dao.modifyCaution(dto);
		
		// 연락처 정보
		dao.modifyContact(dto);

		// 상세정보
		dao.modifyDetailInfo(dto);
		
		// 상세정보 이미지
		dao.modifyDetailImg(dto);
		
		// 공간웹사이트
		dao.modifyLocWeb(dto);
		
		// 이용안내
		dao.modifyUsingInfo(dto);
		
	    
        return "../WEB-INF/views/host/locationList.jsp";
    }
	
	@RequestMapping(value="/actions/basicmodifyform.action", method = RequestMethod.GET)
	public String basicModifyForm(HttpServletRequest request, Model model)
	{
		ILocationDAO dao = sqlSession.getMapper(ILocationDAO.class);
        
		// -------------------- 조회 --------------------
		
		String loc_code = request.getParameter("loc_code");
		loc_code = "L000001";
		LocationDTO dto = new LocationDTO();
		dto.setLoc_code(loc_code);
		
    	// 공간 정보 -- 여기서는 loc_code이지만,
		// 다른 selectDAO는 해당 공간에 대한 모든 값에 접근할 수 있으면서
		// 이 공간만이 가지는 정보를 담은 데이터를 
		// dto로 세팅하여 넘겨주어 jsp 파일에 맞게 addAttribute해야한다.(ex)key → basicInfo)
		model.addAttribute("basicInfo", dao.selectLoc(dto));
    	
  
    	

    	/* 위 공간 정보와 마찬가지로 이런 방식으로 model.addAttribute로 세팅
    	   return할때 해당 jsp로 setAttribute하여 돌려주어 모든 값을 부여해줄 수 있는 상태가 된다.
    	   ( 이후 jsp 파일부터 본격적으로 뷰 상에서는 각 jsp에서 
    	     이 attribute의 dto값을 이용하여 dto.xxx형태로
    	     EL태그를 활용하여 각 input의 value 값에 담아주기 위함)
    	   
    	// 기본 정보
    	dao.selectBasicInfo();
    	
    	// 기본 정보(썸네일)
    	dao.selectThumbnail();
    	
    	// 기본 정보(시설안내)
    	dao.selectFacilityInfo();
    	
    	// 기본 정보(주의사항)
    	dao.selectCaution();

    	// 연락처 정보
    	dao.selectContact();
    	
    	// 사업자 정보
    	dao.selectBizInfo();

    	// 상세정보
    	dao.selectDetailInfo();
    	
    	// 상세정보 이미지
    	dao.selectDetailImg();
    	
    	// 공간 웹사이트
    	dao.selectLocWeb();
    	
    	// 이용안내
    	dao.selectUsingInfo();
		*/
		
		return "../WEB-INF/views/host/modifyBasicInfo.jsp";
		
		/*  	// .jsp 단에서
				
				<input type="text" value="${basicInfo.loc_intro}"/>
		 */
	}
	
	@RequestMapping(value="/actions/modifybasicinfo.action", method = RequestMethod.POST)
	public String modifyBasicInfo(HttpServletRequest request, Model model)
	{
		// 본격적인 이전 입력값의 조회 -> 업데이트 액션처리 반복 부분...
		// (받아온 값들을 dto로 set하고 update)
		
		// 입력폼에 입력된 값들을  request.getParameter() 로 받아와 dto에 세팅한다.
		// dao.modifyBasicInfo(dto) 
		
		// 그 다음 뷰 페이지 select로 입력된 값들 db로부터 매핑
		
		/* 반복(공간코드는 고정) -- input 과 크게 다르지 않고
		 * , 값을 받아오고 세팅하며 매핑하여 addattribute로 전달하는 차이밖에 없다.
		 		
		String loc_code = request.getParameter("loc_code");
		loc_code = "L000001";
		LocationDTO dto = new LocationDTO();
		dto.setLoc_code(loc_code);
		
    	// 공간 정보
    	model.addAttribute("basicInfo", dao.selectLoc(dto));
    	
		 */
		
		// model.addAttribute("뷰에서 부르려는 key 값(select에서 조회된 dto형태의 key)"
				//						, dao.modifyXxx())
		// 그리고, jsp가 다음버튼으로 넘어갈때마다 
		// 변경값이 적용되야하므로 model.setAttribute 해야 한다.
		
		return "../WEB-INF/views/host/modifyContactInfo.jsp";
	}
}
