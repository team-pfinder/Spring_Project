package com.lookation.dto;

public class adminUserDTO
{
	// userQnaManager 에서 사용할 주요 속성 구성
	private String qnaCode, memberNickname, memberEmail, locCode, qnaContent
		, qnaDate, qnaRemoveCode, qnaRemoveDate;
	
	// userQnaManager getter / setter 구성
	
	public String getQnaCode()
	{
		return qnaCode;
	}

	public void setQnaCode(String qnaCode)
	{
		this.qnaCode = qnaCode;
	}

	public String getMemberNickname()
	{
		return memberNickname;
	}

	public void setMemberNickname(String memberNickname)
	{
		this.memberNickname = memberNickname;
	}

	public String getMemberEmail()
	{
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail)
	{
		this.memberEmail = memberEmail;
	}

	public String getLocCode()
	{
		return locCode;
	}

	public void setLocCode(String locCode)
	{
		this.locCode = locCode;
	}

	public String getQnaContent()
	{
		return qnaContent;
	}

	public void setQnaContent(String qnaContent)
	{
		this.qnaContent = qnaContent;
	}

	public String getQnaDate()
	{
		return qnaDate;
	}

	public void setQnaDate(String qnaDate)
	{
		this.qnaDate = qnaDate;
	}

	public String getQnaRemoveCode()
	{
		return qnaRemoveCode;
	}

	public void setQnaRemoveCode(String qnaRemoveCode)
	{
		this.qnaRemoveCode = qnaRemoveCode;
	}

	public String getQnaRemoveDate()
	{
		return qnaRemoveDate;
	}

	public void setQnaRemoveDate(String qnaRemoveDate)
	{
		this.qnaRemoveDate = qnaRemoveDate;
	}
	
}
