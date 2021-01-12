package com.lookation.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.lookation.dto.AccountDTO;

public class MemberAccountDAO implements IAccountDAO
{
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public int add(AccountDTO account) throws SQLException
	{
		int result = 0;
		
		return result;
	}

	@Override
	public int countEmail(String email) throws SQLException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countNick(String nick) throws SQLException
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
