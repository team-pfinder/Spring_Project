package com.lookation.dto;

public class MemberBankAccountInfoDTO
{
	// 객체로 처리할 데이터 선언
	private String memberCode, memberBankNumber, memberBank, memberBankHolder;

	// getter / setter 생성
	public String getMemberCode()
	{
		return memberCode;
	}

	public void setMemberCode(String memberCode)
	{
		this.memberCode = memberCode;
	}

	public String getMemberBankNumber()
	{
		return memberBankNumber;
	}

	public void setMemberBankNumber(String memberBankNumber)
	{
		this.memberBankNumber = memberBankNumber;
	}

	public String getMemberBank()
	{
		return memberBank;
	}

	public void setMemberBank(String memberBank)
	{
		this.memberBank = memberBank;
	}

	public String getMemberBankHolder()
	{
		return memberBankHolder;
	}

	public void setMemberBankHolder(String memberBankHolder)
	{
		this.memberBankHolder = memberBankHolder;
	}


}
