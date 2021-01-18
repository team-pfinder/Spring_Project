package com.lookation.dto;

public class reportDTO
{
	// 주요 속성 구성 (공간 신고) 이용자가 호스트 신고
	private String loc_code, loc_report_type, host_email, member_email
		, loc_report_date, loc_report_reason, loc_report_img_url
		, loc_report_code, report_proc_type, loc_report_proc_date
		, loc_report_proc_code, report_proc_type_code
		, host_blacklist_reason, host_blacklist_date
		, host_nickname, member_nickname;
	private int result_count, black_count;
	
	// 주요 속성 구성 (예약 신고) 호스트가 이용자 신고
	private String book_code, book_report_type, book_report_date
		, book_report_reason, book_report_img_url, book_report_code
		, book_report_proc_date, book_report_proc_code
		, member_blacklist_reason, member_blacklist_date;

	// getter / setter 구성
	public String getLoc_code()
	{
		return loc_code;
	}

	public void setLoc_code(String loc_code)
	{
		this.loc_code = loc_code;
	}

	public String getLoc_report_type()
	{
		return loc_report_type;
	}

	public void setLoc_report_type(String loc_report_type)
	{
		this.loc_report_type = loc_report_type;
	}

	public String getHost_email()
	{
		return host_email;
	}

	public void setHost_email(String host_email)
	{
		this.host_email = host_email;
	}

	public String getMember_email()
	{
		return member_email;
	}

	public void setMember_email(String member_email)
	{
		this.member_email = member_email;
	}

	public String getLoc_report_date()
	{
		return loc_report_date;
	}

	public void setLoc_report_date(String loc_report_date)
	{
		this.loc_report_date = loc_report_date;
	}

	public String getLoc_report_reason()
	{
		return loc_report_reason;
	}

	public void setLoc_report_reason(String loc_report_reason)
	{
		this.loc_report_reason = loc_report_reason;
	}

	public String getLoc_report_img_url()
	{
		return loc_report_img_url;
	}

	public void setLoc_report_img_url(String loc_report_img_url)
	{
		this.loc_report_img_url = loc_report_img_url;
	}

	public String getLoc_report_code()
	{
		return loc_report_code;
	}

	public void setLoc_report_code(String loc_report_code)
	{
		this.loc_report_code = loc_report_code;
	}

	public String getReport_proc_type()
	{
		return report_proc_type;
	}

	public void setReport_proc_type(String report_proc_type)
	{
		this.report_proc_type = report_proc_type;
	}

	public String getLoc_report_proc_date()
	{
		return loc_report_proc_date;
	}

	public void setLoc_report_proc_date(String loc_report_proc_date)
	{
		this.loc_report_proc_date = loc_report_proc_date;
	}

	public String getLoc_report_proc_code()
	{
		return loc_report_proc_code;
	}

	public void setLoc_report_proc_code(String loc_report_proc_code)
	{
		this.loc_report_proc_code = loc_report_proc_code;
	}

	public String getReport_proc_type_code()
	{
		return report_proc_type_code;
	}

	public void setReport_proc_type_code(String report_proc_type_code)
	{
		this.report_proc_type_code = report_proc_type_code;
	}

	public String getHost_blacklist_reason()
	{
		return host_blacklist_reason;
	}

	public void setHost_blacklist_reason(String host_blacklist_reason)
	{
		this.host_blacklist_reason = host_blacklist_reason;
	}

	public String getHost_blacklist_date()
	{
		return host_blacklist_date;
	}

	public void setHost_blacklist_date(String host_blacklist_date)
	{
		this.host_blacklist_date = host_blacklist_date;
	}

	public int getResult_count()
	{
		return result_count;
	}

	public void setResult_count(int result_count)
	{
		this.result_count = result_count;
	}

	public int getBlack_count()
	{
		return black_count;
	}

	public void setBlack_count(int black_count)
	{
		this.black_count = black_count;
	}

	public String getBook_code()
	{
		return book_code;
	}

	public void setBook_code(String book_code)
	{
		this.book_code = book_code;
	}

	public String getBook_report_type()
	{
		return book_report_type;
	}

	public void setBook_report_type(String book_report_type)
	{
		this.book_report_type = book_report_type;
	}

	public String getBook_report_date()
	{
		return book_report_date;
	}

	public void setBook_report_date(String book_report_date)
	{
		this.book_report_date = book_report_date;
	}

	public String getBook_report_reason()
	{
		return book_report_reason;
	}

	public void setBook_report_reason(String book_report_reason)
	{
		this.book_report_reason = book_report_reason;
	}

	public String getBook_report_img_url()
	{
		return book_report_img_url;
	}

	public void setBook_report_img_url(String book_report_img_url)
	{
		this.book_report_img_url = book_report_img_url;
	}

	public String getBook_report_code()
	{
		return book_report_code;
	}

	public void setBook_report_code(String book_report_code)
	{
		this.book_report_code = book_report_code;
	}

	public String getBook_report_proc_date()
	{
		return book_report_proc_date;
	}

	public void setBook_report_proc_date(String book_report_proc_date)
	{
		this.book_report_proc_date = book_report_proc_date;
	}

	public String getBook_report_proc_code()
	{
		return book_report_proc_code;
	}

	public void setBook_report_proc_code(String book_report_proc_code)
	{
		this.book_report_proc_code = book_report_proc_code;
	}

	public String getMember_blacklist_reason()
	{
		return member_blacklist_reason;
	}

	public void setMember_blacklist_reason(String member_blacklist_reason)
	{
		this.member_blacklist_reason = member_blacklist_reason;
	}

	public String getMember_blacklist_date()
	{
		return member_blacklist_date;
	}

	public void setMember_blacklist_date(String member_blacklist_date)
	{
		this.member_blacklist_date = member_blacklist_date;
	}

	public String getHost_nickname()
	{
		return host_nickname;
	}

	public void setHost_nickname(String host_nickname)
	{
		this.host_nickname = host_nickname;
	}

	public String getMember_nickname()
	{
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname)
	{
		this.member_nickname = member_nickname;
	}
}
