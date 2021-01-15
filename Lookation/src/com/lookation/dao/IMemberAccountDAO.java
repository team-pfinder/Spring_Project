package com.lookation.dao;

import com.lookation.dto.AccountDTO;

public interface IMemberAccountDAO
{
	public String isLogin(AccountDTO account);
	public int confirmCount(AccountDTO account);
	public AccountDTO getInfo(String code);
	//
	public void modifyTel(AccountDTO account);
	public String getBlackListDate(String member_code);
	public void modifyPassword(AccountDTO account);
}
