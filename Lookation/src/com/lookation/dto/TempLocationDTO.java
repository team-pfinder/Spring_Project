package com.lookation.dto;

import java.util.ArrayList;

public class TempLocationDTO
{
	// private String
	// loc info
	private String loc_code;
	
	// basic info
	private String name, type, shortIntro, intro, thumbnail, address, detailAddress;
	private ArrayList<String> arrPrecaution, arrFacility;

	// business info
	private String bizName, bizCeo, bizNum, bizCeoType, bizMainType, bizSubType, bizLicense, bizAddresss,
			bizDetailAddress;

	// contact info
	private String email, tel, mainTel;

	// detail info
	private String minPeople, maxPeople;
	private ArrayList<String> arrDetailImage;

	// loc web
	private String webUrl;

	// using info
	private String usingHour, dayOff, appointDayOff;

	
	public String getLoc_code()
	{
		return loc_code;
	}

	public void setLoc_code(String loc_code)
	{
		this.loc_code = loc_code;
	}

	public String getBizAddresss()
	{
		return bizAddresss;
	}

	public void setBizAddresss(String bizAddresss)
	{
		this.bizAddresss = bizAddresss;
	}

	public String getBizDetailAddress()
	{
		return bizDetailAddress;
	}

	public void setBizDetailAddress(String bizDetailAddress)
	{
		this.bizDetailAddress = bizDetailAddress;
	}

	public String getUsingHour()
	{
		return usingHour;
	}

	public void setUsingHour(String usingHour)
	{
		this.usingHour = usingHour;
	}

	public String getDayOff()
	{
		return dayOff;
	}

	public void setDayOff(String dayOff)
	{
		this.dayOff = dayOff;
	}

	public String getAppointDayOff()
	{
		return appointDayOff;
	}

	public void setAppointDayOff(String appointDayOff)
	{
		this.appointDayOff = appointDayOff;
	}

	public String getMinPeople()
	{
		return minPeople;
	}

	public void setMinPeople(String minPeople)
	{
		this.minPeople = minPeople;
	}

	public String getMaxPeople()
	{
		return maxPeople;
	}

	public void setMaxPeople(String maxPeople)
	{
		this.maxPeople = maxPeople;
	}

	public ArrayList<String> getArrDetailImage()
	{
		return arrDetailImage;
	}

	public void setArrDetailImage(ArrayList<String> arrDetailImage)
	{
		this.arrDetailImage = arrDetailImage;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getMainTel()
	{
		return mainTel;
	}

	public void setMainTel(String mainTel)
	{
		this.mainTel = mainTel;
	}

	public String getBizLicense()
	{
		return bizLicense;
	}

	public void setBizLicense(String bizLicense)
	{
		this.bizLicense = bizLicense;
	}

	public String getBizName()
	{
		return bizName;
	}

	public void setBizName(String bizName)
	{
		this.bizName = bizName;
	}

	public String getBizCeo()
	{
		return bizCeo;
	}

	public void setBizCeo(String bizCeo)
	{
		this.bizCeo = bizCeo;
	}

	public String getBizNum()
	{
		return bizNum;
	}

	public void setBizNum(String bizNum)
	{
		this.bizNum = bizNum;
	}

	public String getBizCeoType()
	{
		return bizCeoType;
	}

	public void setBizCeoType(String bizCeoType)
	{
		this.bizCeoType = bizCeoType;
	}

	public String getBizMainType()
	{
		return bizMainType;
	}

	public void setBizMainType(String bizMainType)
	{
		this.bizMainType = bizMainType;
	}

	public String getBizSubType()
	{
		return bizSubType;
	}

	public void setBizSubType(String bizSubType)
	{
		this.bizSubType = bizSubType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getShortIntro()
	{
		return shortIntro;
	}

	public void setShortIntro(String shortIntro)
	{
		this.shortIntro = shortIntro;
	}

	public String getIntro()
	{
		return intro;
	}

	public void setIntro(String intro)
	{
		this.intro = intro;
	}

	public String getThumbnail()
	{
		return thumbnail;
	}

	public void setThumbnail(String thumbnail)
	{
		this.thumbnail = thumbnail;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getDetailAddress()
	{
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress)
	{
		this.detailAddress = detailAddress;
	}

	public ArrayList<String> getArrPrecaution()
	{
		return arrPrecaution;
	}

	public void setArrPrecaution(ArrayList<String> arrPrecaution)
	{
		this.arrPrecaution = arrPrecaution;
	}

	public ArrayList<String> getArrFacility()
	{
		return arrFacility;
	}

	public void setArrFacility(ArrayList<String> arrFacility)
	{
		this.arrFacility = arrFacility;
	}

	public String getWebUrl()
	{
		return webUrl;
	}

	public void setWebUrl(String webUrl)
	{
		this.webUrl = webUrl;
	}

}
