package com.lookation.dto;

public class LocationDetailDTO
{
	// 공간코드, 공간이름, 공간유형, 공간소개짧, 공간소개긴,
	// 주소, 상세주소, 이용시간, 휴무, 정기휴무
	private String locationCode, locName, locType, shortIntro, intro
	, addr, detailAddr, hostNickName, locRegDate, usingHour, dayOff, appointDayOff
	, boardCode, memCode, content, date, memberNickName, reviewRate
	, count, url, rvimgCount, qna_content
	, replyRemove, removeCount, hostCode, replyContent, replyDate, replyCode
	, apply_package_code; 
	
	private String packageName, applyDate;
	
	private String bizName, bizCeo, bizCeoType, bizMainType, bizSubType
	, bizLicenseNum, facility, caution;
	
	private int minPeople, maxPeople, packStart, packEnd, packPrice;
	
	
	
	
	public String getApply_package_code()
	{
		return apply_package_code;
	}

	public void setApply_package_code(String apply_package_code)
	{
		this.apply_package_code = apply_package_code;
	}

	public String getReplyCode()
	{
		return replyCode;
	}

	public void setReplyCode(String replyCode)
	{
		this.replyCode = replyCode;
	}

	public String getQna_content()
	{
		return qna_content;
	}

	public void setQna_content(String qna_content)
	{
		this.qna_content = qna_content;
	}

	public String getRvimgCount()
	{
		return rvimgCount;
	}

	public void setRvimgCount(String rvimgCount)
	{
		this.rvimgCount = rvimgCount;
	}

		
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getBoardCode()
	{
		return boardCode;
	}

	public void setBoardCode(String boardCode)
	{
		this.boardCode = boardCode;
	}

	public String getMemCode()
	{
		return memCode;
	}

	public void setMemCode(String memCode)
	{
		this.memCode = memCode;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getMemberNickName()
	{
		return memberNickName;
	}

	public void setMemberNickName(String memberNickName)
	{
		this.memberNickName = memberNickName;
	}

	public String getReviewRate()
	{
		return reviewRate;
	}

	public void setReviewRate(String reviewRate)
	{
		this.reviewRate = reviewRate;
	}

	
	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	public String getApplyDate()
	{
		return applyDate;
	}

	public void setApplyDate(String applyDate)
	{
		this.applyDate = applyDate;
	}

	public int getPackStart()
	{
		return packStart;
	}

	public void setPackStart(int packStart)
	{
		this.packStart = packStart;
	}

	public int getPackEnd()
	{
		return packEnd;
	}

	public void setPackEnd(int packEnd)
	{
		this.packEnd = packEnd;
	}

	public int getPackPrice()
	{
		return packPrice;
	}

	public void setPackPrice(int packPrice)
	{
		this.packPrice = packPrice;
	}

	public String getLocationCode()
	{
		return locationCode;
	}

	public void setLocationCode(String locationCode)
	{
		this.locationCode = locationCode;
	}

	public String getLocType()
	{
		return locType;
	}

	public void setLocType(String locType)
	{
		this.locType = locType;
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

	public String getLocName()
	{
		return locName;
	}

	public void setLocName(String locName)
	{
		this.locName = locName;
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

	public String getAddr()
	{
		return addr;
	}

	public void setAddr(String addr)
	{
		this.addr = addr;
	}

	public String getDetailAddr()
	{
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr)
	{
		this.detailAddr = detailAddr;
	}

	public int getMinPeople()
	{
		return minPeople;
	}

	public void setMinPeople(int minPeople)
	{
		this.minPeople = minPeople;
	}

	public int getMaxPeople()
	{
		return maxPeople;
	}

	public void setMaxPeople(int maxPeople)
	{
		this.maxPeople = maxPeople;
	}

	public String getFacility()
	{
		return facility;
	}

	public void setFacility(String facility)
	{
		this.facility = facility;
	}

	public String getCaution()
	{
		return caution;
	}

	public void setCaution(String caution)
	{
		this.caution = caution;
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

	public String getBizLicenseNum()
	{
		return bizLicenseNum;
	}

	public void setBizLicenseNum(String bizLicenseNum)
	{
		this.bizLicenseNum = bizLicenseNum;
	}

	public String getHostNickName()
	{
		return hostNickName;
	}

	public void setHostNickName(String hostNickName)
	{
		this.hostNickName = hostNickName;
	}

	public String getLocRegDate()
	{
		return locRegDate;
	}

	public void setLocRegDate(String locRegDate)
	{
		this.locRegDate = locRegDate;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getCount()
	{
		return count;
	}

	public void setCount(String count)
	{
		this.count = count;
	}

	public String getReplyRemove()
	{
		return replyRemove;
	}

	public void setReplyRemove(String replyRemove)
	{
		this.replyRemove = replyRemove;
	}

	public String getRemoveCount()
	{
		return removeCount;
	}

	public void setRemoveCount(String removeCount)
	{
		this.removeCount = removeCount;
	}

	public String getHostCode()
	{
		return hostCode;
	}

	public void setHostCode(String hostCode)
	{
		this.hostCode = hostCode;
	}

	public String getReplyContent()
	{
		return replyContent;
	}

	public void setReplyContent(String replyContent)
	{
		this.replyContent = replyContent;
	}

	public String getReplyDate()
	{
		return replyDate;
	}

	public void setReplyDate(String replyDate)
	{
		this.replyDate = replyDate;
	}
	
	
	
}
