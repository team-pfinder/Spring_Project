package com.lookation.dto;

public class NoticeDTO
{
	//관리자 공지사항 주요 속성 구성
	private String notice_code
				,important_notice_code
				,notice_title
				,notice_content
				,notice_date
				,important_notice;

	//getter setter구성
	public String getNotice_code()
	{
		return notice_code;
	}

	public void setNotice_code(String notice_code)
	{
		this.notice_code = notice_code;
	}

	public String getImportant_notice_code()
	{
		return important_notice_code;
	}

	public void setImportant_notice_code(String important_notice_code)
	{
		this.important_notice_code = important_notice_code;
	}

	public String getNotice_title()
	{
		return notice_title;
	}

	public void setNotice_title(String notice_title)
	{
		this.notice_title = notice_title;
	}

	public String getNotice_content()
	{
		return notice_content;
	}

	public void setNotice_content(String notice_content)
	{
		this.notice_content = notice_content;
	}

	public String getNotice_date()
	{
		return notice_date;
	}

	public void setNotice_date(String notice_date)
	{
		this.notice_date = notice_date;
	}
	public String getImportant_notice()
	{
		return important_notice;
	}

	public void setImportant_notice(String important_notice)
	{
		this.important_notice = important_notice;
	}

}
