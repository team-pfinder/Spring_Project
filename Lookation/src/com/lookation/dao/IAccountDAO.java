package com.lookation.dao;

import java.sql.SQLException;

import com.lookation.dto.AccountDTO;

public interface IAccountDAO
{
	public int add(AccountDTO account) throws SQLException;
	public int countEmail(String email) throws SQLException;
	public int countNick(String nick) throws SQLException;
}
