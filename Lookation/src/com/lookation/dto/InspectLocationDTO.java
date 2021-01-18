package com.lookation.dto;

public class InspectLocationDTO
{
	//InspectManager, InspectProcess 주요 속성 구성
	private String loc_name;
	private String loc_type;
	
	private String loc_short_intro, loc_intro,loc_addr
					,loc_detail_addr,facility_content
					,caution_content,min_people,max_people
					,loc_tel,loc_main_tel,loc_email
					,biz_name,biz_ceo,biz_ceo_type,biz_main_type
					,biz_sub_type,biz_addr,biz_license_number;
	
	private String loc_use_hour, loc_use_day_off,loc_use_appoint_day_off;
	
	private String package_name, package_start, package_end;
	
	private String loc_web_url;
	

	// InspectManager, InspectProcess
	// getter / setter 구성
	private String inspect_reg_code;
	public String getInspect_reg_code()
	{
		return inspect_reg_code;
	}

	public void setInspect_reg_code(String inspect_reg_code)
	{
		this.inspect_reg_code = inspect_reg_code;
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

	public String getLoc_addr()
	{
		return loc_addr;
	}

	public void setLoc_addr(String loc_addr)
	{
		this.loc_addr = loc_addr;
	}

	public String getLoc_detail_addr()
	{
		return loc_detail_addr;
	}

	public void setLoc_detail_addr(String loc_detail_addr)
	{
		this.loc_detail_addr = loc_detail_addr;
	}

	public String getFacility_content()
	{
		return facility_content;
	}

	public void setFacility_content(String facility_content)
	{
		this.facility_content = facility_content;
	}

	public String getCaution_content()
	{
		return caution_content;
	}

	public void setCaution_content(String caution_content)
	{
		this.caution_content = caution_content;
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

	public String getLoc_email()
	{
		return loc_email;
	}

	public void setLoc_email(String loc_email)
	{
		this.loc_email = loc_email;
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

	public String getBiz_ceo_type()
	{
		return biz_ceo_type;
	}

	public void setBiz_ceo_type(String biz_ceo_type)
	{
		this.biz_ceo_type = biz_ceo_type;
	}

	public String getBiz_main_type()
	{
		return biz_main_type;
	}

	public void setBiz_main_type(String biz_main_type)
	{
		this.biz_main_type = biz_main_type;
	}

	public String getBiz_sub_type()
	{
		return biz_sub_type;
	}

	public void setBiz_sub_type(String biz_sub_type)
	{
		this.biz_sub_type = biz_sub_type;
	}

	public String getBiz_addr()
	{
		return biz_addr;
	}

	public void setBiz_addr(String biz_addr)
	{
		this.biz_addr = biz_addr;
	}

	public String getBiz_license_number()
	{
		return biz_license_number;
	}

	public void setBiz_license_number(String biz_license_number)
	{
		this.biz_license_number = biz_license_number;
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
	
	public String getLoc_use_hour()
	{
		return loc_use_hour;
	}

	public void setLoc_use_hour(String loc_use_hour)
	{
		this.loc_use_hour = loc_use_hour;
	}

	public String getLoc_use_day_off()
	{
		return loc_use_day_off;
	}

	public void setLoc_use_day_off(String loc_use_day_off)
	{
		this.loc_use_day_off = loc_use_day_off;
	}

	public String getLoc_use_appoint_day_off()
	{
		return loc_use_appoint_day_off;
	}

	public void setLoc_use_appoint_day_off(String loc_use_appoint_day_off)
	{
		this.loc_use_appoint_day_off = loc_use_appoint_day_off;
	}
	
	public String getLoc_web_url()
	{
		return loc_web_url;
	}

	public void setLoc_web_url(String loc_web_url)
	{
		this.loc_web_url = loc_web_url;
	}
}
