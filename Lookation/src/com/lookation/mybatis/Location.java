package com.lookation.mybatis;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	    ILocationDAO dao = sqlSession.getMapper(ILocationDAO.class);
	    
	    LocationDTO loc = new LocationDTO();
	    loc.setHost_code("H000001");
	    
	    model.addAttribute("locList", dao.locList(loc));
		
		return "../WEB-INF/views/host/locationList.jsp";
	}
	
	
	@RequestMapping(value="/actions/basicform.action", method = RequestMethod.GET)
	public String basicForm(HttpServletRequest request, Model model)
	{
		return "../WEB-INF/views/host/inputBasicInfo.jsp";
	}
	
	@RequestMapping(value="/actions/basicajax.action", method = RequestMethod.POST)
	public String basicAjax(HttpServletRequest request, Model model)
	{
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
		
		return "";
	}
	
	@RequestMapping(value="/actions/inputbasicinfo.action", method = RequestMethod.POST)
	public String inputBasicInfo(HttpServletRequest request, Model model)
	{
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
		 	
		 	System.out.println(LocationManager.getName());
		 	System.out.println(LocationManager.getType());
		 	System.out.println(LocationManager.getShortIntro());
		 	System.out.println(LocationManager.getIntro());
		 	System.out.println(LocationManager.getThumbnail());
		 	System.out.println(LocationManager.getAddress());
		 	System.out.println(LocationManager.getDetailAddress());
		 	
		 	for(String s : LocationManager.getArrFacility())
		 	{
		 		System.out.println(s);
		 	}
		 	
	 		for(String s : LocationManager.getArrPrecaution())
	 		{
	 			System.out.println(s);
	 		}
		 	
 			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return "../WEB-INF/views/host/inputContactInfo.jsp";
	}
	
	@RequestMapping(value="/actions/inputcontactinfo.action", method = RequestMethod.POST)
	public String inputContactInfo(HttpServletRequest request, Model model)
	{
		LocationManager.setEmail(request.getParameter("inputEmail"));
		LocationManager.setTel(request.getParameter("inputContact"));
		LocationManager.setMainTel(request.getParameter("inputMainContact"));
		
		System.out.println(LocationManager.getEmail());
		System.out.println(LocationManager.getTel());
		System.out.println(LocationManager.getMainTel());
		
		return "../WEB-INF/views/host/inputBusinessInfo.jsp";
	}
	
	@RequestMapping(value="/actions/inputbusinessinfo.action", method = RequestMethod.POST)
	public String inputBusinessInfo(HttpServletRequest request, Model model)
	{
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
		 	
		 	System.out.println(LocationManager.getBizName());
		 	System.out.println(LocationManager.getBizCeo());
		 	System.out.println(LocationManager.getBizNum());
		 	System.out.println(LocationManager.getBizLicense());
		 	System.out.println(LocationManager.getBizCeoType());
		 	System.out.println(LocationManager.getBizMainType());
		 	System.out.println(LocationManager.getBizSubType());
		 	System.out.println(LocationManager.getBizAddresss());
		 	System.out.println(LocationManager.getBizDetailAddress());
		 	
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return "../WEB-INF/views/host/inputDetailInfo.jsp";
	}
	
	@RequestMapping(value="/actions/inputdetailinfo.action", method = RequestMethod.POST)
	public String inputDetailInfo(HttpServletRequest request, Model model)
	{
		try
		{
			MultipartRequest m = FileManager.upload(request, "images");
			ArrayList<String> fileNames = FileManager.getFileNames(m);
			
		 	LocationManager.setArrDetailImage(fileNames);
		 	LocationManager.setMinPeople(m.getParameter("inputMinPeople"));
		 	LocationManager.setMaxPeople(m.getParameter("inputMaxPeople"));
		 	
		 	for(String s : LocationManager.getArrDetailImage())
		 	{
		 		System.out.println(s);
		 	}
		 	System.out.println(LocationManager.getMinPeople());
		 	System.out.println(LocationManager.getMaxPeople());
 			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return "../WEB-INF/views/host/inputUsingInfo.jsp";
	}
	
	@RequestMapping(value="/actions/inputusinginfo.action", method = RequestMethod.POST)
	public String inputUsingInfo(HttpServletRequest request, Model model)
	{
		LocationManager.setUsingHour(request.getParameter("inputUsingHour"));
		LocationManager.setDayOff(request.getParameter("inputDayOff"));
		LocationManager.setAppointDayOff(request.getParameter("inputAppointDayoff"));
		
		System.out.println(LocationManager.getUsingHour());
		System.out.println(LocationManager.getDayOff());
		System.out.println(LocationManager.getAppointDayOff());
		
		return "../WEB-INF/views/host/locationList.jsp";
	}
	
	@RequestMapping(value="/actions/insertinfotest.action", method = RequestMethod.GET)
    public String insertinfotest(HttpServletRequest request, Model model)
    {

        LocationManager.setName("공간이름테스트");
	     LocationManager.setType("파티룸");
	     LocationManager.setShortIntro("공간한줄소개테스트");
	     LocationManager.setIntro("공간 소개 테스트 입니다. 상세하게 소개합니다.");
	     LocationManager.setThumbnail("썸네일.png");
	     LocationManager.setAddress("인천 연수구 랜드마크로 19");
	     LocationManager.setDetailAddress("상세 주소 테스트 123-123");

        LocationManager.setEmail("test123@good.com");
        LocationManager.setTel("010-1234-4321");
        LocationManager.setMainTel("02-0990-0880");

        LocationManager.setBizName("사업장이름테스트");
         LocationManager.setBizCeo("사업자대표테스트");
         LocationManager.setBizNum("사업자번호테스트");
         LocationManager.setBizLicense("사업자등록증.jpg");
         LocationManager.setBizCeoType("간이과세자");
         LocationManager.setBizMainType("주업태테스트");
         LocationManager.setBizSubType("주업종테스트");
        LocationManager.setBizAddresss("경기 안산시 단원구 라성로 4");
        LocationManager.setBizDetailAddress("사업자 상세 주소 테스트");

        ArrayList<String> f = new ArrayList<String>();
        f.add("시설안내1");
        f.add("시설안내2");
        f.add("시설안내3");
        LocationManager.setArrFacility(f);
        
        ArrayList<String> c = new ArrayList<String>();
        c.add("주의사항1");
        c.add("주의사항2");
        c.add("주의사항3");
        LocationManager.setArrPrecaution(c);
        
        
        ArrayList<String> files = new ArrayList<String>();
        files.add("상세이미지1.png");
        files.add("상세이미지2.jpg");
        files.add("상세이미지3.png");

        
        
        LocationManager.setArrDetailImage(files);
        LocationManager.setMinPeople("1");
        LocationManager.setMaxPeople("30");
         
        LocationManager.setWebUrl("test.com");
         
        LocationManager.setUsingHour("이용시간안내테스트");
        LocationManager.setDayOff("휴무안내테스트");
        LocationManager.setAppointDayOff("정기휴무안내테스트");


        // 쿼리문으로 db에 저장할 코드가 들어갈 위치

        ILocationDAO dao = sqlSession.getMapper(ILocationDAO.class);
        
        LocationDTO dto = new LocationDTO();
        
        
	    // -------------------- 입력 --------------------
        
        // 공간
        dto.setHost_code("H000001");
        
        dao.inputLoc(dto);	// loc_code set
        System.out.println(dto.getLoc_code());

        
        // 기본 정보
        dto.setLoc_type("파티룸");
        dto.setLoc_name("공간명테스트");
        dto.setLoc_short_intro("공간한줄소개테스트");
        dto.setLoc_intro("공간소개테스트");
        dto.setLoc_addr("주소테스트");
        dto.setLoc_detail_addr("상세주소테스트");
        
        dao.inputBasicInfo(dto);	// loc_basic_info_code set
        
        
        // 썸네일(기본정보)
        dto.setThumbnail_url("test.lookation.com");
        
        dao.inputThumbnail(dto);
        
        //System.out.println(LocationManager.getArrFacility());
        
        
        // 시설안내(기본정보)
        //-- insert 이므로 배열에 담을 필요없음. 
        //   mybatis에서 for문으로 담는다
		
		for (String str : LocationManager.getArrFacility()) 
		{
			 dto.setFacility_content(str); 
			 dao.inputFacilityInfo(dto); 
		}
		
		// 주의사항(기본정보)
		  
		for (String str : LocationManager.getArrPrecaution())
		{
			dto.setCaution_content(str);
			dao.inputCaution(dto);
		}
		
		// 연락처 정보
		dto.setLoc_email("rlaghwls@naver.com");
		dto.setLoc_tel("010-1234-5678");
		dto.setLoc_main_tel("02-2222-2233");
		
		dao.inputContact(dto);
		
		
		// 사업자 정보
	    dto.setBiz_name("사업자이름테스트");
	    dto.setBiz_ceo("사업자대표테스트");
	    dto.setBiz_license_url("테스트사업자등록증.jpg");
	    dto.setBiz_ceo_type("개인사업자");
	    dto.setBiz_main_type("주업태테스트");
	    dto.setBiz_sub_type("주업종테스트");
	    dto.setBiz_addr("서울시 동대문구 이문동 1");
	    dto.setBiz_license_number("123-45-67890");
	    
	    dao.inputBizInfo(dto);
	    
	    
	    // 상세 정보
	    dto.setLoc_detail_addr("101동 101호");
	    dto.setMin_people("2");
	    dto.setMax_people("22");
	    
	    dao.inputDetailInfo(dto);	// 상세정보코드 set
	    System.out.println(dto.getLoc_detail_info_code());	
	    
	    
	    // 상세 이미지
	    for (String str : LocationManager.getArrDetailImage())
		{
			dto.setLoc_detail_img_url(str);
			dao.inputDetailImg(dto);
		}
	    
	    // 웹 사이트
	    dto.setLoc_web_url("http://websitetest.com");
	    dao.inputLocWeb(dto);
	    
	    
	    // 이용 정보  
	    dto.setLoc_use_hour("11:00 ~ 24:00");
	    dto.setLoc_use_day_off("매주 수요일");
	    dto.setLoc_use_appoint_day_off("3/11");
	    
	    dao.inputUsingInfo(dto);
	    
	    
	    // 패키지 양식
	    
	    dao.inputPackageFormInfo(dto);	// set package_form_code
	    System.out.println(dto.getPackage_form_code());
	    
	    
	    // 검수 신청
	    dao.inputInspectRegList(dto);  // set inspect_reg_code
	    System.out.println(dto.getInspect_reg_code());
	    
	    
	    
	    // -------------------- 수정 --------------------
	    
	    // 기본 정보
	    dao.modifyBasicInfo(dto);
		
		
		// 기본 정보(썸네일)
		//public void modifyThumbnail(LocationDTO dto);
		
		
		/*
		// 기본 정보(시설안내)
		public void modifyFacility(LocationDTO dto);
		
		// 기본 정보(주의사항)
		public void modifyCaution(LocationDTO dto);
		
		// 연락처 정보
		public void modifyContact(LocationDTO dto);
		
		// 사업자 정보
		public void modifyBizInfo(LocationDTO dto);
		
		// 상세정보
		public void modifyDetailInfo(LocationDTO dto);
		
		// 상세정보 이미지
		public void modifyDetailImg(LocationDTO dto);
		
		// 공간웹사이트
		public void modifyLocWeb(LocationDTO dto);
		
		// 이용안내
		public void modifyUsingInfo(LocationDTO dto);
	    */
	    
        return "../WEB-INF/views/host/locationList.jsp";
    }
}
