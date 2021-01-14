package com.lookation.dao;

import com.lookation.dto.AccountDTO;

public interface IHostAccountDAO
{
	public String isLogin(AccountDTO account);
	public int confirmCount(AccountDTO account);
}
