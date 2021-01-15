package com.lookation.dto;

public class adminUserDTO
{
	// userQnaManager 에서 사용할 주요 속성 구성
	private String qna_code, member_nickname, member_email, loc_code, qna_content
		, qna_date, qna_remove_code, qna_remove_date;
	private int count;
	
	// userReviewManager 에서 사용할 주요 속성 구성
	
	
	// userQnaManager getter / setter 구성
	public String getQna_code()
	{
		return qna_code;
	}
	public void setQna_code(String qna_code)
	{
		this.qna_code = qna_code;
	}
	public String getMember_nickname()
	{
		return member_nickname;
	}
	public void setMember_nickname(String member_nickname)
	{
		this.member_nickname = member_nickname;
	}
	public String getMember_email()
	{
		return member_email;
	}
	public void setMember_email(String member_email)
	{
		this.member_email = member_email;
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
	public String getQna_remove_code()
	{
		return qna_remove_code;
	}
	public void setQna_remove_code(String qna_remove_code)
	{
		this.qna_remove_code = qna_remove_code;
	}
	public String getQna_remove_date()
	{
		return qna_remove_date;
	}
	public void setQna_remove_date(String qna_remove_date)
	{
		this.qna_remove_date = qna_remove_date;
	}
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	
}
