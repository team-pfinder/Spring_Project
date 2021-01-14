package com.lookation.dao;

import com.lookation.dto.AccountDTO;

public interface IMemberAccountDAO
{
	public String isLogin(AccountDTO account);
	public int confirmCount(AccountDTO account);
}
