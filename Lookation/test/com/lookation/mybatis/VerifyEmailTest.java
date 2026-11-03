package com.lookation.mybatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;

import com.lookation.dao.IAccountDAO;
import com.lookation.dao.ISignupVerificationDAO;
import com.lookation.dto.AccountDTO;
import com.lookation.dto.SignupPendingDTO;
import com.lookation.util.TokenGenerator;

@RunWith(MockitoJUnitRunner.class)
public class VerifyEmailTest
{
	@Mock
	private SqlSession sqlSession;

	@Mock
	private ISignupVerificationDAO verificationDAO;

	@Mock
	private IAccountDAO memberDao;

	@Mock
	private IAccountDAO hostDao;

	@Mock
	private HttpServletRequest request;

	@InjectMocks
	private VerifyEmail controller;

	@Before
	public void setUp()
	{
		when(sqlSession.getMapper(ISignupVerificationDAO.class)).thenReturn(verificationDAO);
		when(request.getParameter("token")).thenReturn("raw-token");
	}

	@Test
	public void validTokenCreatesAccountWithStoredHashAndMarksTokenUsed() throws Exception
	{
		SignupPendingDTO pending = new SignupPendingDTO();
		pending.setIdentify("member");
		pending.setEmail("new@example.com");
		pending.set_pw_hash("$2a$10$hash");
		pending.setNick("새회원");
		pending.setName("홍길동");
		pending.setTel("01012345678");
		when(verificationDAO.findValid(TokenGenerator.sha256("raw-token"))).thenReturn(pending);
		when(verificationDAO.markUsed(TokenGenerator.sha256("raw-token"))).thenReturn(1);

		String view = controller.verify(request, new ExtendedModelMap());

		assertThat(view, is("../WEB-INF/views/common/emailVerified.jsp"));
		ArgumentCaptor<AccountDTO> captor = ArgumentCaptor.forClass(AccountDTO.class);
		verify(memberDao).add(captor.capture());
		assertThat(captor.getValue().getPw(), is("$2a$10$hash"));
		verify(verificationDAO).markUsed(TokenGenerator.sha256("raw-token"));
	}

	@Test
	public void expiredOrReusedTokenCreatesNoAccount() throws Exception
	{
		when(verificationDAO.findValid(any(String.class))).thenReturn(null);

		String view = controller.verify(request, new ExtendedModelMap());

		assertThat(view, is("../WEB-INF/views/common/emailVerifyFail.jsp"));
		verify(memberDao, never()).add(any(AccountDTO.class));
		verify(hostDao, never()).add(any(AccountDTO.class));
	}

	@Test
	public void hostTokenCreatesHostAccount() throws Exception
	{
		SignupPendingDTO pending = new SignupPendingDTO();
		pending.setIdentify("host");
		pending.setEmail("host@example.com");
		pending.set_pw_hash("$2a$10$hash");
		when(verificationDAO.findValid(any(String.class))).thenReturn(pending);
		when(verificationDAO.markUsed(any(String.class))).thenReturn(1);

		controller.verify(request, new ExtendedModelMap());

		verify(hostDao).add(any(AccountDTO.class));
		verify(memberDao, never()).add(any(AccountDTO.class));
	}
}
