package com.lookation.dto;

public class detailInfoDTO
{
	private String loc_detail_info_code, loc_code;
	private int min_people, max_people;
	
	public String getLoc_detail_info_code()
	{
		return loc_detail_info_code;
	}
	public void setLoc_detail_info_code(String loc_detail_info_code)
	{
		this.loc_detail_info_code = loc_detail_info_code;
	}
	public String getLoc_code()
	{
		return loc_code;
	}
	public void setLoc_code(String loc_code)
	{
		this.loc_code = loc_code;
	}
	public int getMin_people()
	{
		return min_people;
	}
	public void setMin_people(int min_people)
	{
		this.min_people = min_people;
	}
	public int getMax_people()
	{
		return max_people;
	}
	public void setMax_people(int max_people)
	{
		this.max_people = max_people;
	}
	
	
}
