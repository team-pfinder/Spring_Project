package com.lookation.dto;

public class locListDTO
{
	private String loc_code, loc_name, thumbnail_url, loc_reg_date
		   , inspect_type;

	public String getLoc_code()
	{
		return loc_code;
	}

	public void setLoc_code(String loc_code)
	{
		this.loc_code = loc_code;
	}

	public String getLoc_name()
	{
		return loc_name;
	}

	public void setLoc_name(String loc_name)
	{
		this.loc_name = loc_name;
	}

	public String getThumbnail_url()
	{
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail)
	{
		this.thumbnail_url = thumbnail;
	}

	public String getLoc_reg_date()
	{
		return loc_reg_date;
	}

	public void setLoc_reg_date(String loc_reg_date)
	{
		this.loc_reg_date = loc_reg_date;
	}

	public String getInspect_type()
	{
		return inspect_type;
	}

	public void setInspect_type(String inspect_type)
	{
		this.inspect_type = inspect_type;
	}
	
}
