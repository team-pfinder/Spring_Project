package com.lookation.dto;

public class reportUserDTO
{
	// 주요 속성 구성
	private String loc_report_code, loc_code, loc_report_type_code
		, member_code, loc_report_reason, loc_report_date;

	// getter / setter 구성
	public String getLoc_report_code()
	{
		return loc_report_code;
	}

	public void setLoc_report_code(String loc_report_code)
	{
		this.loc_report_code = loc_report_code;
	}

	public String getLoc_code()
	{
		return loc_code;
	}

	public void setLoc_code(String loc_code)
	{
		this.loc_code = loc_code;
	}

	public String getLoc_report_type_code()
	{
		return loc_report_type_code;
	}

	public void setLoc_report_type_code(String loc_report_type_code)
	{
		this.loc_report_type_code = loc_report_type_code;
	}

	public String getMember_code()
	{
		return member_code;
	}

	public void setMember_code(String member_code)
	{
		this.member_code = member_code;
	}

	public String getLoc_report_reason()
	{
		return loc_report_reason;
	}

	public void setLoc_report_reason(String loc_report_reason)
	{
		this.loc_report_reason = loc_report_reason;
	}

	public String getLoc_report_date()
	{
		return loc_report_date;
	}

	public void setLoc_report_date(String loc_report_date)
	{
		this.loc_report_date = loc_report_date;
	}
}
