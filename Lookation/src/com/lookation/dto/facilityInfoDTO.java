package com.lookation.dto;

public class facilityInfoDTO
{
	private String facility_code, loc_basic_info_code
		  , facility_content;

	public String getFacility_code()
	{
		return facility_code;
	}

	public void setFacility_code(String facility_code)
	{
		this.facility_code = facility_code;
	}

	public String getLoc_basic_info_code()
	{
		return loc_basic_info_code;
	}

	public void setLoc_basic_info_code(String loc_basic_info_code)
	{
		this.loc_basic_info_code = loc_basic_info_code;
	}

	public String getFacility_content()
	{
		return facility_content;
	}

	public void setFacility_content(String facility_content)
	{
		this.facility_content = facility_content;
	}
	
}
