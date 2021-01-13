package com.lookation.dto;

public class HostBankAccountInfoDTO
{
	// 객체로 처리할 데이터 선언
	private String hostCode, hostBankNumber, hostBank, hostBankHolder;

	// getter / setter 생성
	public String getHostCode()
	{
		return hostCode;
	}

	public void setHostCode(String hostCode)
	{
		this.hostCode = hostCode;
	}

	public String getHostBankNumber()
	{
		return hostBankNumber;
	}

	public void setHostBankNumber(String hostBankNumber)
	{
		this.hostBankNumber = hostBankNumber;
	}

	public String getHostBank()
	{
		return hostBank;
	}

	public void setHostBank(String hostBank)
	{
		this.hostBank = hostBank;
	}

	public String getHostBankHolder()
	{
		return hostBankHolder;
	}

	public void setHostBankHolder(String hostBankHolder)
	{
		this.hostBankHolder = hostBankHolder;
	}
	
}
