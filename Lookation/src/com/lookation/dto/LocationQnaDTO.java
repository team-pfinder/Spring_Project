package com.lookation.dto;

public class LocationQnaDTO
{
	// 이용자 QNA
	private String qna_code, loc_code, member_code, qna_date
	, qna_content;
	
	// 호스트 QNA
	private String qna_reply_code, qna_reply_date, qna_reply_content, qna_reply_remove_code
	, qna_reply_remove_date;

	public String getQna_code()
	{
		return qna_code;
	}

	public void setQna_code(String qna_code)
	{
		this.qna_code = qna_code;
	}

	public String getLoc_code()
	{
		return loc_code;
	}

	public void setLoc_code(String loc_code)
	{
		this.loc_code = loc_code;
	}

	public String getMember_code()
	{
		return member_code;
	}

	public void setMember_code(String member_code)
	{
		this.member_code = member_code;
	}

	public String getQna_date()
	{
		return qna_date;
	}

	public void setQna_date(String qna_date)
	{
		this.qna_date = qna_date;
	}

	public String getQna_content()
	{
		return qna_content;
	}

	public void setQna_content(String qna_content)
	{
		this.qna_content = qna_content;
	}

	public String getQna_reply_code()
	{
		return qna_reply_code;
	}

	public void setQna_reply_code(String qna_reply_code)
	{
		this.qna_reply_code = qna_reply_code;
	}

	public String getQna_reply_date()
	{
		return qna_reply_date;
	}

	public void setQna_reply_date(String qna_reply_date)
	{
		this.qna_reply_date = qna_reply_date;
	}

	public String getQna_reply_content()
	{
		return qna_reply_content;
	}

	public void setQna_reply_content(String qna_reply_content)
	{
		this.qna_reply_content = qna_reply_content;
	}

	public String getQna_reply_remove_code()
	{
		return qna_reply_remove_code;
	}

	public void setQna_reply_remove_code(String qna_reply_remove_code)
	{
		this.qna_reply_remove_code = qna_reply_remove_code;
	}

	public String getQna_reply_remove_date()
	{
		return qna_reply_remove_date;
	}

	public void setQna_reply_remove_date(String qna_reply_remove_date)
	{
		this.qna_reply_remove_date = qna_reply_remove_date;
	}

	
}
