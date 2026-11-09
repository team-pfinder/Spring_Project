package com.lookation.dto;

public class BookApplyDTO
{
	// 공간한줄설명, 공간유형, 예약인원, 패키지명, 예약날짜, 예약인원
	// , 예약자명, 이메일, 연락처, 요청사항
	// ,상호명, 대표자명, 소재지, 사업자번호, 연락처, 사업자 이메일
	// , 예약 주의사항...?
	
	// 서비스 동의여부
	
	private String loc_code, loc_name, loc_type, loc_short_intro
	, loc_intro, min_people, max_people
	, host_code, package_code, package_name
	, package_start, package_end
	, apply_date
	, biz_name, biz_ceo, biz_license_number
	, biz_addr, loc_email, loc_tel, loc_main_tel
	, member_code, mileage
	, book_code, apply_package_code, book_date, book_req
	, actual_booker, actual_booker_tel
	, book_pay_date, member_tel, member_name
	, hours, thumbnail_url;
	
	private int book_people, package_price;
	
	
	
	public String getThumbnail_url()
	{
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url)
	{
		this.thumbnail_url = thumbnail_url;
	}

	public String getHours()
	{
		return hours;
	}

	public void setHours(String hours)
	{
		this.hours = hours;
	}

	public int getPackage_price()
	{
		return package_price;
	}

	public void setPackage_price(int package_price)
	{
		this.package_price = package_price;
	}

	public String getMember_code()
	{
		return member_code;
	}

	public void setMember_code(String member_code)
	{
		this.member_code = member_code;
	}

	public String getLoc_name()
	{
		return loc_name;
	}

	public void setLoc_name(String loc_name)
	{
		this.loc_name = loc_name;
	}

	public String getLoc_type()
	{
		return loc_type;
	}

	public void setLoc_type(String loc_type)
	{
		this.loc_type = loc_type;
	}

	public String getLoc_short_intro()
	{
		return loc_short_intro;
	}

	public void setLoc_short_intro(String loc_short_intro)
	{
		this.loc_short_intro = loc_short_intro;
	}

	public String getLoc_intro()
	{
		return loc_intro;
	}

	public void setLoc_intro(String loc_intro)
	{
		this.loc_intro = loc_intro;
	}

	public String getMin_people()
	{
		return min_people;
	}

	public void setMin_people(String min_people)
	{
		this.min_people = min_people;
	}

	public String getMax_people()
	{
		return max_people;
	}

	public void setMax_people(String max_people)
	{
		this.max_people = max_people;
	}

	public String getHost_code()
	{
		return host_code;
	}

	public void setHost_code(String host_code)
	{
		this.host_code = host_code;
	}

	public String getLoc_code()
	{
		return loc_code;
	}

	public void setLoc_code(String loc_code)
	{
		this.loc_code = loc_code;
	}

	public String getPackage_code()
	{
		return package_code;
	}

	public void setPackage_code(String package_code)
	{
		this.package_code = package_code;
	}

	public String getPackage_name()
	{
		return package_name;
	}

	public void setPackage_name(String package_name)
	{
		this.package_name = package_name;
	}

	public String getPackage_start()
	{
		return package_start;
	}

	public void setPackage_start(String package_start)
	{
		this.package_start = package_start;
	}

	public String getPackage_end()
	{
		return package_end;
	}

	public void setPackage_end(String package_end)
	{
		this.package_end = package_end;
	}

	public String getApply_date()
	{
		return apply_date;
	}

	public void setApply_date(String apply_date)
	{
		this.apply_date = apply_date;
	}

	public String getBiz_name()
	{
		return biz_name;
	}

	public void setBiz_name(String biz_name)
	{
		this.biz_name = biz_name;
	}

	public String getBiz_ceo()
	{
		return biz_ceo;
	}

	public void setBiz_ceo(String biz_ceo)
	{
		this.biz_ceo = biz_ceo;
	}

	public String getBiz_license_number()
	{
		return biz_license_number;
	}

	public void setBiz_license_number(String biz_license_number)
	{
		this.biz_license_number = biz_license_number;
	}
	
	public String getBiz_addr()
	{
		return biz_addr;
	}

	public void setBiz_addr(String biz_addr)
	{
		this.biz_addr = biz_addr;
	}

	public String getLoc_email()
	{
		return loc_email;
	}

	public void setLoc_email(String loc_email)
	{
		this.loc_email = loc_email;
	}

	public String getLoc_tel()
	{
		return loc_tel;
	}

	public void setLoc_tel(String loc_tel)
	{
		this.loc_tel = loc_tel;
	}

	public String getLoc_main_tel()
	{
		return loc_main_tel;
	}

	public void setLoc_main_tel(String loc_main_tel)
	{
		this.loc_main_tel = loc_main_tel;
	}

	public String getMileage()
	{
		return mileage;
	}

	public void setMileage(String mileage)
	{
		this.mileage = mileage;
	}

	public String getBook_code()
	{
		return book_code;
	}

	public void setBook_code(String book_code)
	{
		this.book_code = book_code;
	}

	public String getApply_package_code()
	{
		return apply_package_code;
	}

	public void setApply_package_code(String apply_package_code)
	{
		this.apply_package_code = apply_package_code;
	}

	public int getBook_people()
	{
		return book_people;
	}

	public void setBook_people(int book_people)
	{
		this.book_people = book_people;
	}

	public String getBook_date()
	{
		return book_date;
	}

	public void setBook_date(String book_date)
	{
		this.book_date = book_date;
	}

	public String getBook_req()
	{
		return book_req;
	}

	public void setBook_req(String book_req)
	{
		this.book_req = book_req;
	}

	public String getActual_booker()
	{
		return actual_booker;
	}

	public void setActual_booker(String actual_booker)
	{
		this.actual_booker = actual_booker;
	}

	public String getActual_booker_tel()
	{
		return actual_booker_tel;
	}

	public void setActual_booker_tel(String actual_booker_tel)
	{
		this.actual_booker_tel = actual_booker_tel;
	}

	public String getBook_pay_date()
	{
		return book_pay_date;
	}

	public void setBook_pay_date(String book_pay_date)
	{
		this.book_pay_date = book_pay_date;
	}

	public String getMember_tel()
	{
		return member_tel;
	}

	public void setMember_tel(String member_tel)
	{
		this.member_tel = member_tel;
	}

	public String getMember_name()
	{
		return member_name;
	}

	public void setMember_name(String member_name)
	{
		this.member_name = member_name;
	}
	
	
	
	
	
}
