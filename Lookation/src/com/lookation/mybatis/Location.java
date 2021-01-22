package com.lookation.mybatis;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.util.FileManager;
import com.lookation.util.LocationManager;
import com.oreilly.servlet.MultipartRequest;

@Controller
public class Location
{
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

        ArrayList<String> files = new ArrayList<String>();
        files.add("상세이미지1.png");
        files.add("상세이미지2.jpg");
        files.add("상세이미지3.png");

        LocationManager.setArrDetailImage(files);
         LocationManager.setMinPeople("1");
         LocationManager.setMaxPeople("30");

        LocationManager.setUsingHour("이용시간안내테스트");
        LocationManager.setDayOff("휴무안내테스트");
        LocationManager.setAppointDayOff("정기휴무안내테스트");


        // 쿼리문으로 db에 저장할 코드가 들어갈 위치


        //


        return "../WEB-INF/views/host/locationList.jsp";
    }
}
