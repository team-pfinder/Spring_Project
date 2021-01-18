package com.lookation.dto;

public class LoadAndExchangeDTO
{
	private String memberCode, bankNumber, bank, regdate, expiredate, bankHolder;
	private int balance, amount;
	
	// getter / setter 구성
	public String getBankHolder()
	{
		return bankHolder;
	}
	public void setBankHolder(String bankHolder)
	{
		this.bankHolder = bankHolder;
	}
	public String getMemberCode()
	{
		return memberCode;
	}
	public void setMemberCode(String memberCode)
	{
		this.memberCode = memberCode;
	}
	public String getBankNumber()
	{
		return bankNumber;
	}
	public void setBankNumber(String bankNumber)
	{
		this.bankNumber = bankNumber;
	}
	public int getAmount()
	{
		return amount;
	}
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
	public String getBank()
	{
		return bank;
	}
	public void setBank(String bank)
	{
		this.bank = bank;
	}
	public int getBalance()
	{
		return balance;
	}
	public void setBalance(int balance)
	{
		this.balance = balance;
	}
	public String getRegdate()
	{
		return regdate;
	}
	public String getExpiredate()
	{
		return expiredate;
	}
	public void setExpiredate(String expiredate)
	{
		this.expiredate = expiredate;
	}
	public void setRegdate(String regdate)
	{
		this.regdate = regdate;
	}
	
}
