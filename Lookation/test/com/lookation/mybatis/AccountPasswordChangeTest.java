package com.lookation.mybatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
import com.lookation.util.PasswordHasher;

@RunWith(MockitoJUnitRunner.class)
public class AccountPasswordChangeTest
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
	public void changingPasswordStoresHashAndInvalidatesSession()
	{
		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("pw_new")).thenReturn("newsecret");
		when(session.getAttribute("memberCode")).thenReturn("M0001");

		String view = account.changePassword(request, new ExtendedModelMap());

		assertThat(view, is("redirect:loginform.action?identify=member"));
		ArgumentCaptor<AccountDTO> captor = ArgumentCaptor.forClass(AccountDTO.class);
		verify(memberAccountDAO).modifyPassword(captor.capture());
		assertTrue(PasswordHasher.matches("newsecret", captor.getValue().getPw()));
		verify(session).invalidate();
	}
}
