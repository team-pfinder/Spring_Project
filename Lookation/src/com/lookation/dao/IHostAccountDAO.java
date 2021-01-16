package com.lookation.dao;

import com.lookation.dto.AccountDTO;

public interface IHostAccountDAO
{
	public String isLogin(AccountDTO account);
	public int confirmCount(AccountDTO account);
	public AccountDTO getInfo(String code);
	//
	public void modifyTel(AccountDTO account);
	public String getBlackListDate(String host_code);
	public void modifyPassword(AccountDTO account);
}
