package com.lookation.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class BankInfoDAOTest
{
	private DataSource dataSource;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;
	private BankInfoDAO dao;

	@Before
	public void setUp() throws Exception
	{
		dataSource = mock(DataSource.class);
		connection = mock(Connection.class);
		statement = mock(PreparedStatement.class);
		resultSet = mock(ResultSet.class);

		dao = new BankInfoDAO();
		dao.setDataSource(dataSource);

		when(dataSource.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);
	}

	@Test
	public void hostCodeIsBoundAsParameterInsteadOfConcatenated() throws Exception
	{
		String hostile = "H001' OR '1'='1";

		dao.hostBankInfoCount(hostile);

		ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
		verify(connection).prepareStatement(sql.capture());
		assertFalse("입력값이 SQL 문자열에 들어가면 안 된다", sql.getValue().contains(hostile));
		assertThat(sql.getValue().contains("?"), is(true));
		verify(statement).setString(1, hostile);
	}
}
