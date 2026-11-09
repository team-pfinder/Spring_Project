package com.lookation.dto;

public class MemberQnaListDTO
{
	String member_code, qna_code, loc_code
	, qna_content, qna_date, loc_name, removecount, member_nickname;
	
	public String getMember_nickname()
	{
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname)
	{
		this.member_nickname = member_nickname;
	}

	public String getMember_code()
	{
		return member_code;
	}

	public void setMember_code(String member_code)
	{
		this.member_code = member_code;
	}

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

	public String getQna_content()
	{
		return qna_content;
	}

	public void setQna_content(String qna_content)
	{
		this.qna_content = qna_content;
	}

	public String getQna_date()
	{
		return qna_date;
	}

	public void setQna_date(String qna_date)
	{
		this.qna_date = qna_date;
	}

	public String getLoc_name()
	{
		return loc_name;
	}

	public void setLoc_name(String loc_name)
	{
		this.loc_name = loc_name;
	}

	public String getRemovecount()
	{
		return removecount;
	}

	public void setRemovecount(String removecount)
	{
		this.removecount = removecount;
	}
	
	
}
