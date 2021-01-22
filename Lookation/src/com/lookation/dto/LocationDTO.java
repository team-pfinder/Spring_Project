package com.lookation.dto;

public class LocationDTO
{
	// LOC
	private String loc_code, host_code, loc_reg_date;
	
	// basic info
	private String loc_basic_info_code, loc_type_code, loc_type
				 , loc_name, loc_short_intro, loc_intro
				 , loc_addr, loc_detail_addr;
	

	// thumbnail info
	private String thumbnail_code, thumbnail_url;
	
	// facility info
	private String facility_code, facility_content;
	
	// caution info
	private String caution_code, caution_content;

	
	// contact info
	private String loc_contact_code, loc_email, loc_tel, loc_main_tel;
	
	// business info
	private String biz_info_code, biz_name, biz_ceo
				 , biz_license_url, biz_ceo_type
				 , biz_main_type, biz_sub_type
			  	 , biz_addr, biz_license_number;
	
	// detail info
	private String loc_detail_info_code, min_people, max_people;

	// detail img
	private String loc_detail_img_code, loc_detail_img_url;
	
	// loc web
	private String loc_web_code, loc_web_url;
	
	// using info
	private String loc_use_info_code
	 			 , loc_use_hour, loc_use_day_off
	 			 , loc_use_appoint_day_off;

	public String getLoc_code()
	{
		return loc_code;
	}

	public void setLoc_code(String loc_code)
	{
		this.loc_code = loc_code;
	}

	public String getHost_code()
	{
		return host_code;
	}

	public void setHost_code(String host_code)
	{
		this.host_code = host_code;
	}

	public String getLoc_reg_date()
	{
		return loc_reg_date;
	}

	public void setLoc_reg_date(String loc_reg_date)
	{
		this.loc_reg_date = loc_reg_date;
	}

	public String getLoc_basic_info_code()
	{
		return loc_basic_info_code;
	}

	public void setLoc_basic_info_code(String loc_basic_info_code)
	{
		this.loc_basic_info_code = loc_basic_info_code;
	}

	public String getLoc_type_code()
	{
		return loc_type_code;
	}

	public void setLoc_type_code(String loc_type_code)
	{
		this.loc_type_code = loc_type_code;
	}

	public String getLoc_name()
	{
		return loc_name;
	}

	public void setLoc_name(String loc_name)
	{
		this.loc_name = loc_name;
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

	public String getThumbnail_code()
	{
		return thumbnail_code;
	}

	public void setThumbnail_code(String thumbnail_code)
	{
		this.thumbnail_code = thumbnail_code;
	}

	public String getThumbnail_url()
	{
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url)
	{
		this.thumbnail_url = thumbnail_url;
	}

	public String getFacility_code()
	{
		return facility_code;
	}

	public void setFacility_code(String facility_code)
	{
		this.facility_code = facility_code;
	}

	public String getFacility_content()
	{
		return facility_content;
	}

	public void setFacility_content(String facility_content)
	{
		this.facility_content = facility_content;
	}

	public String getCaution_code()
	{
		return caution_code;
	}

	public void setCaution_code(String caution_code)
	{
		this.caution_code = caution_code;
	}

	public String getCaution_content()
	{
		return caution_content;
	}

	public void setCaution_content(String caution_content)
	{
		this.caution_content = caution_content;
	}

	public String getLoc_contact_code()
	{
		return loc_contact_code;
	}

	public void setLoc_contact_code(String loc_contact_code)
	{
		this.loc_contact_code = loc_contact_code;
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

	public String getBiz_info_code()
	{
		return biz_info_code;
	}

	public void setBiz_info_code(String biz_info_code)
	{
		this.biz_info_code = biz_info_code;
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

	public String getBiz_license_url()
	{
		return biz_license_url;
	}

	public void setBiz_license_url(String biz_license_url)
	{
		this.biz_license_url = biz_license_url;
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

	public String getLoc_detail_info_code()
	{
		return loc_detail_info_code;
	}

	public void setLoc_detail_info_code(String loc_detail_info_code)
	{
		this.loc_detail_info_code = loc_detail_info_code;
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

	public String getLoc_detail_img_code()
	{
		return loc_detail_img_code;
	}

	public void setLoc_detail_img_code(String loc_detail_img_code)
	{
		this.loc_detail_img_code = loc_detail_img_code;
	}

	public String getLoc_detail_img_url()
	{
		return loc_detail_img_url;
	}

	public void setLoc_detail_img_url(String loc_detail_img_url)
	{
		this.loc_detail_img_url = loc_detail_img_url;
	}

	public String getLoc_web_code()
	{
		return loc_web_code;
	}

	public void setLoc_web_code(String loc_web_code)
	{
		this.loc_web_code = loc_web_code;
	}

	public String getLoc_web_url()
	{
		return loc_web_url;
	}

	public void setLoc_web_url(String loc_web_url)
	{
		this.loc_web_url = loc_web_url;
	}

	public String getLoc_use_info_code()
	{
		return loc_use_info_code;
	}

	public void setLoc_use_info_code(String loc_use_info_code)
	{
		this.loc_use_info_code = loc_use_info_code;
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
	

	public String getLoc_type()
	{
		return loc_type;
	}

	public void setLoc_type(String loc_type)
	{
		this.loc_type = loc_type;
	}
}


