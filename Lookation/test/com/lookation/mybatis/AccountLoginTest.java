package com.lookation.mybatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
import com.lookation.util.PasswordHasher;

@RunWith(MockitoJUnitRunner.class)
public class AccountLoginTest
{
	// "secret"의 BCrypt 해시 (cost 10). 테스트 입력의 독립 기준값.
	private static final String SECRET_HASH = "$2a$10$9XGzMF96QmGcTQoPS6FlMusavuC/NNBw8JYFJo5fEzr04.7Ryv2oy";

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
	public void validMemberLoginWithHashedPasswordStoresMemberCodeAndReturnsToPreviousPage()
	{
		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("email")).thenReturn("user@example.com");
		when(request.getParameter("pw")).thenReturn("secret");
		when(memberAccountDAO.findPasswordByEmail("user@example.com")).thenReturn(SECRET_HASH);
		when(memberAccountDAO.findCodeByEmail("user@example.com")).thenReturn("M0001");
		when(session.getAttribute("beforeUrl")).thenReturn("membermain.action");

		String view = account.login(request, new ExtendedModelMap());

		assertThat(view, is("redirect:membermain.action"));
		verify(session).setAttribute("memberCode", "M0001");
	}

	@Test
	public void legacyPlaintextPasswordIsRehashedOnSuccessfulLogin()
	{
		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("email")).thenReturn("user@example.com");
		when(request.getParameter("pw")).thenReturn("secret");
		when(memberAccountDAO.findPasswordByEmail("user@example.com")).thenReturn("secret");
		when(memberAccountDAO.findCodeByEmail("user@example.com")).thenReturn("M0001");
		when(session.getAttribute("beforeUrl")).thenReturn("membermain.action");

		account.login(request, new ExtendedModelMap());

		ArgumentCaptor<AccountDTO> captor = ArgumentCaptor.forClass(AccountDTO.class);
		verify(memberAccountDAO).modifyPassword(captor.capture());
		assertThat(captor.getValue().getCode(), is("M0001"));
		assertTrue(PasswordHasher.matches("secret", captor.getValue().getPw()));
	}

	@Test
	public void invalidPasswordDoesNotCreateSession()
	{
		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("email")).thenReturn("user@example.com");
		when(request.getParameter("pw")).thenReturn("wrong");
		when(memberAccountDAO.findPasswordByEmail("user@example.com")).thenReturn(SECRET_HASH);

		String view = account.login(request, new ExtendedModelMap());

		assertThat(view, is("redirect:loginform.action?identify=member&result=fail"));
		verify(session, never()).setAttribute(any(String.class), any());
	}

	@Test
	public void unknownEmailDoesNotCreateSession()
	{
		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("email")).thenReturn("nobody@example.com");
		when(request.getParameter("pw")).thenReturn("secret");
		when(memberAccountDAO.findPasswordByEmail("nobody@example.com")).thenReturn(null);

		String view = account.login(request, new ExtendedModelMap());

		assertThat(view, is("redirect:loginform.action?identify=member&result=fail"));
		verify(session, never()).setAttribute(any(String.class), any());
	}

	@Test
	public void confirmPasswordUsesStoredHashInsteadOfPlainComparison()
	{
		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("pw")).thenReturn("secret");
		when(session.getAttribute("memberCode")).thenReturn("M0001");
		when(memberAccountDAO.findPasswordByCode("M0001")).thenReturn(SECRET_HASH);
		when(session.getAttribute("requestUrl")).thenReturn("mypage.action");

		String view = account.confirmPassword(request, new ExtendedModelMap());

		assertThat(view, is("redirect:mypage.action?identify=member"));
	}
}
