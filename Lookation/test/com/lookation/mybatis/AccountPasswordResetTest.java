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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;

import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dao.IPasswordResetDAO;
import com.lookation.dto.AccountDTO;
import com.lookation.dto.PasswordResetDTO;
import com.lookation.util.TokenGenerator;

@RunWith(MockitoJUnitRunner.class)
public class AccountPasswordResetTest
{
	@Mock
	private SqlSession sqlSession;
	@Mock
	private IPasswordResetDAO resetDAO;
	@Mock
	private IMemberAccountDAO memberAccountDAO;
	@Mock
	private HttpServletRequest request;
	@InjectMocks
	private Account account;

	@Before
	public void setUp()
	{
		when(sqlSession.getMapper(IPasswordResetDAO.class)).thenReturn(resetDAO);
		when(sqlSession.getMapper(IMemberAccountDAO.class)).thenReturn(memberAccountDAO);
		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("email")).thenReturn("user@example.com");
		when(request.getParameter("pw_new")).thenReturn("newsecret");
	}

	@Test
	public void resetWithoutTokenIsRejected()
	{
		when(request.getParameter("token")).thenReturn(null);

		String view = account.changePasswordNoLogin(request, new ExtendedModelMap());

		assertThat(view, is("../WEB-INF/views/common/wrongAccess.jsp?identify=member"));
		verify(memberAccountDAO, never()).modifyPasswordNoLogin(any(AccountDTO.class));
	}

	@Test
	public void resetWithTokenForAnotherEmailIsRejected()
	{
		when(request.getParameter("token")).thenReturn("raw");
		PasswordResetDTO reset = new PasswordResetDTO();
		reset.setEmail("other@example.com");
		reset.setIdentify("member");
		when(resetDAO.findValid(TokenGenerator.sha256("raw"))).thenReturn(reset);

		String view = account.changePasswordNoLogin(request, new ExtendedModelMap());

		assertThat(view, is("../WEB-INF/views/common/wrongAccess.jsp?identify=member"));
		verify(memberAccountDAO, never()).modifyPasswordNoLogin(any(AccountDTO.class));
	}

	@Test
	public void resetWithValidTokenChangesPasswordAndConsumesToken()
	{
		when(request.getParameter("token")).thenReturn("raw");
		PasswordResetDTO reset = new PasswordResetDTO();
		reset.setEmail("user@example.com");
		reset.setIdentify("member");
		when(resetDAO.findValid(TokenGenerator.sha256("raw"))).thenReturn(reset);
		when(resetDAO.markUsed(TokenGenerator.sha256("raw"))).thenReturn(1);

		String view = account.changePasswordNoLogin(request, new ExtendedModelMap());

		assertThat(view, is("redirect:membermain.action"));
		verify(memberAccountDAO).modifyPasswordNoLogin(any(AccountDTO.class));
		verify(resetDAO).markUsed(TokenGenerator.sha256("raw"));
	}
}
