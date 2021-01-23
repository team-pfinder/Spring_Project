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
	
	@RequestMapping(value="/actions/deletelocation.action", method = RequestMethod.GET)
	public String deleteAction(HttpServletRequest request, Model model)
	{
	    ILocationDAO dao = sqlSession.getMapper(ILocationDAO.class);
	    
	    String loc_code = request.getParameter("loc_code");
	    
	    LocationDTO loc = new LocationDTO();
	    loc.setLoc_code(loc_code);
	    dao.deleteLocation(loc);
	    
	   
		return "redirect:locationlist.action";
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
		 	LocationManager.setWebUrl(m.getParameter("inputWebUrl"));
		 	
		 	for(String s : LocationManager.getArrDetailImage())
		 	{
		 		System.out.println(s);
		 	}
		 	System.out.println(LocationManager.getMinPeople());
		 	System.out.println(LocationManager.getMaxPeople());
		 	System.out.println(LocationManager.getWebUrl());
 			
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
		
		
		// -------------------- 입력 --------------------
		ILocationDAO dao = sqlSession.getMapper(ILocationDAO.class);
        
        LocationDTO dto = new LocationDTO();
        
        // 공간
        dto.setHost_code("H000001");
        
        dao.inputLoc(dto);	// loc_code set
   

        // 기본 정보
        dto.setLoc_type(LocationManager.getType());
        dto.setLoc_name(LocationManager.getName());
        dto.setLoc_short_intro(LocationManager.getShortIntro());
        dto.setLoc_intro(LocationManager.getIntro());
        dto.setLoc_addr(LocationManager.getAddress());
        dto.setLoc_detail_addr(LocationManager.getDetailAddress());
        
        dao.inputBasicInfo(dto);	// loc_basic_info_code set
        
        
        // 썸네일(기본정보)
        dto.setThumbnail_url(LocationManager.getThumbnail());
        
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
		dto.setLoc_email(LocationManager.getEmail());
		dto.setLoc_tel(LocationManager.getTel());
		dto.setLoc_main_tel(LocationManager.getMainTel());
		
		dao.inputContact(dto);
		
		
		// 사업자 정보
	    dto.setBiz_name(LocationManager.getBizName());
	    dto.setBiz_ceo(LocationManager.getBizCeo());
	    dto.setBiz_license_url(LocationManager.getBizLicense());
	    dto.setBiz_ceo_type(LocationManager.getBizCeoType());
	    dto.setBiz_main_type(LocationManager.getBizMainType());
	    dto.setBiz_sub_type(LocationManager.getBizSubType());
	    dto.setBiz_addr(LocationManager.getBizAddresss());
	    dto.setBiz_license_number(LocationManager.getBizNum());
	    
	    dao.inputBizInfo(dto);
	    
	    
	    // 상세 정보
	    dto.setMin_people(LocationManager.getMinPeople());
	    dto.setMax_people(LocationManager.getMaxPeople());
	    
	    dao.inputDetailInfo(dto);	// 상세정보코드 set
	    

	    // 상세 이미지
	    for (String str : LocationManager.getArrDetailImage())
		{
			dto.setLoc_detail_img_url(str);
			dao.inputDetailImg(dto);
		}
	    
	    // 웹 사이트
	    dto.setLoc_web_url(LocationManager.getWebUrl());
	    dao.inputLocWeb(dto);
	    
	    
	    // 이용 정보  
	    dto.setLoc_use_hour(LocationManager.getUsingHour());
	    dto.setLoc_use_day_off(LocationManager.getDayOff());
	    dto.setLoc_use_appoint_day_off(LocationManager.getAppointDayOff());
	    
	    dao.inputUsingInfo(dto);
	    
	    
	    // 검수 신청
	    dao.inputInspectRegList(dto);  // set inspect_reg_code
		
		
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
		LocationDTO dto = new LocationDTO();
		ArrayList<LocationDTO> arr = new ArrayList<LocationDTO>();
		
		// -------------------- 조회 --------------------
		
		// 공간 정보 조회 및 model set
		
		// 세션으로부터 호스트코드 불러와 담기(test : H000001)
		String host_code = request.getParameter("host_code"); // 현재 null
		host_code = "H000001";
		//System.out.println(host_code);
		dto.setHost_code(host_code);
		//System.out.println(dto.getHost_code());
		/*
		arr = dao.selectLoc(dto);
		
		for (int i = 0; i < arr.size(); i++)
		{
			System.out.println("[" + i + "번 ] : " + arr.get(i).getLoc_code());
			System.out.println("[" + i + "번 ] : " + arr.get(i).getHost_code());
			System.out.println("[" + i + "번 ] : " + arr.get(i).getLoc_reg_date());
		}
		*/
		
		// 현재 set 된 dto : host_code 
		//model.addAllAttribute(dao.selectLoc(dto));	// 공간테이블의 모든값 
		
		
		// ----------------------------------------------------------
		
		
		// 기본 정보 조회 및 model set
		
		// loc_code 데이터 불러오기(test : L0000001)
		String loc_code = request.getParameter("loc_code");	// 현재 null
		loc_code = "L000001";
		//System.out.println(loc_code);
		
		// 기본정보조회 dao의 where 조건절에 들어갈
		// loc_code를 dto에 set
 	    dto.setLoc_code(loc_code);

 	    // set한 공간코드를 기반으로 기본정보 조회 dao 호출, 확인(하나만)
 	    // System.out.println(dao.selectBasicInfo(dto).getLoc_basic_info_code());
 	    //-- LBIF000001
 	    //dao.selectBasicInfo(dto);		
 	    
 	    LocationDTO test = dao.selectBasicInfo(dto);
 	    System.out.println(test.getLoc_basic_info_code());
 	    
 	   
 	    // Idao로부터 dao.xml 매퍼로 새팅한 loc_code를 dao를 호출하며 
 	    // model.addAtrribute로 담음(key값은 테이블파일과 유사하도록)
 	    // (위에서 dto.set 으로 세팅된 host_code도 함께 ... 이후 계속 누적)
 	    
 	    model.addAttribute("basicInfo", dao.selectBasicInfo(dto));
 	    //-- 여기까지 기본정보 테이블의 각 컬럼 값을 dto 에 set 된 상태.. 	    
 	    
 	    
 	    /*
 	    // 썸네일(기본정보)

 	    // 이미 기본정보코드 가 set 된 상태..
 	    // 바로 썸네일 조회 dao 호출하여 각 컬럼을 dto에 set
 	    
 	    //System.out.println(dao.selectThumbnail(dto).getThumbnail_code());
 	    //dao.selectThumbnail(dto);
 	    
 	    dto.setLoc_basic_info_code(request.getParameter("loc_detail_info_code"));
 	    model.addAttribute("thumbnail", dao.selectThumbnail(dto));
  
 	    
 	    // 시설안내(기본정보)
 	    model.addAllAttributes(dao.selectFacilityInfo(dto));
 	    
 	    
 	    // 주의사항(기본정보)
 	    model.addAllAttributes(dao.selectCaution(dto));
	    
 	    
 	    // 연락처 정보
 	    model.addAttribute("contact", dao.selectContact(dto));
 	    

 	    // 사업자 정보
 	    model.addAttribute("bizInfo", dao.selectBizInfo(dto));
 	    
 	    
 	    // 상세 정보
 	    model.addAttribute("detailInfo", dao.selectDetailInfo(dto));
 	    
 	    
 	    // 상세 이미지
 	    dto.setLoc_detail_info_code(request.getParameter("loc_detail_info_code")); 
 	    model.addAttribute("detailImg", dao.selectDetailImg(dto));
 	    */
 	    
 	    // 이용 정보
 	    
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
