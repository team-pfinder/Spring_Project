package com.lookation.mybatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;

import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dto.AccountDTO;

@RunWith(MockitoJUnitRunner.class)
public class AccountLoginTest
{
	@Mock
	private SqlSession sqlSession;

	@Mock
	private IMemberAccountDAO memberAccountDAO;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpSession session;

	@InjectMocks
	private Account account;

	@Before
	public void setUp()
	{
		when(request.getSession()).thenReturn(session);
		when(sqlSession.getMapper(IMemberAccountDAO.class)).thenReturn(memberAccountDAO);
	}

	@Test
	public void validMemberLoginStoresMemberCodeAndReturnsToPreviousPage()
	{
		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("email")).thenReturn("user@example.com");
		when(request.getParameter("pw")).thenReturn("secret");
		when(memberAccountDAO.isLogin(any(AccountDTO.class))).thenReturn("M0001");
		when(session.getAttribute("beforeUrl")).thenReturn("membermain.action");

		String view = account.login(request, new ExtendedModelMap());

		assertThat(view, is("redirect:membermain.action"));
		verify(session).setAttribute("memberCode", "M0001");
	}

	@Test
	public void loginPassesSubmittedEmailAndPasswordToAccountLookup()
	{
		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("email")).thenReturn("user@example.com");
		when(request.getParameter("pw")).thenReturn("secret");
		when(memberAccountDAO.isLogin(any(AccountDTO.class))).thenReturn("M0001");
		when(session.getAttribute("beforeUrl")).thenReturn("membermain.action");

		account.login(request, new ExtendedModelMap());

		ArgumentCaptor<AccountDTO> captor = ArgumentCaptor.forClass(AccountDTO.class);
		verify(memberAccountDAO).isLogin(captor.capture());
		assertThat(captor.getValue().getEmail(), is("user@example.com"));
		assertThat(captor.getValue().getPw(), is("secret"));
	}

	@Test
	public void invalidMemberLoginDoesNotCreateSession()
	{
		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("email")).thenReturn("user@example.com");
		when(request.getParameter("pw")).thenReturn("wrong");
		when(memberAccountDAO.isLogin(any(AccountDTO.class))).thenReturn(null);

		String view = account.login(request, new ExtendedModelMap());

		assertThat(view, is("redirect:loginform.action?identify=member&result=fail"));
		verify(session, never()).setAttribute(any(String.class), any());
	}
}
