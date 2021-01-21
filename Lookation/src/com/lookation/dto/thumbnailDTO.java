package com.lookation.dto;

public class thumbnailDTO
{
	private String thumbnail_code, loc_basic_info_code
		  , thumbnail_url;

	public String getThumbnail_code()
	{
		return thumbnail_code;
	}

	public void setThumbnail_code(String thumbnail_code)
	{
		this.thumbnail_code = thumbnail_code;
	}

	public String getLoc_basic_info_code()
	{
		return loc_basic_info_code;
	}

	public void setLoc_basic_info_code(String loc_basic_info_code)
	{
		this.loc_basic_info_code = loc_basic_info_code;
	}

	public String getThumbnail_url()
	{
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url)
	{
		this.thumbnail_url = thumbnail_url;
	}
	
}
