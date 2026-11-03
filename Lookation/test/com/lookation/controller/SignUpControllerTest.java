package com.lookation.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.lookation.dao.IAccountDAO;
import com.lookation.dto.AccountDTO;
import com.lookation.util.PasswordHasher;

@RunWith(MockitoJUnitRunner.class)
public class SignUpControllerTest
{
	@Mock
	private IAccountDAO memberDao;

	@Mock
	private IAccountDAO hostDao;

	@Mock
	private HttpServletRequest request;

	private SignUpController controller;

	@Before
	public void setUp() throws Exception
	{
		controller = new SignUpController();
		controller.setMemberDao(memberDao);
		controller.setHostDao(hostDao);

		when(request.getParameter("identify")).thenReturn("member");
		when(request.getParameter("email")).thenReturn("new@example.com");
		when(request.getParameter("nick")).thenReturn("새회원");
		when(request.getParameter("pw")).thenReturn("secret");
		when(request.getParameter("name")).thenReturn("홍길동");
		when(request.getParameter("tel")).thenReturn("01012345678");
		when(memberDao.countEmail(anyString())).thenReturn(0);
		when(memberDao.countNick(anyString())).thenReturn(0);
		when(hostDao.countEmail(anyString())).thenReturn(0);
		when(hostDao.countNick(anyString())).thenReturn(0);
	}

	@Test
	public void memberSignUpStoresHashedPassword() throws Exception
	{
		controller.handleRequest(request, mock(HttpServletResponse.class));

		ArgumentCaptor<AccountDTO> captor = ArgumentCaptor.forClass(AccountDTO.class);
		verify(memberDao).add(captor.capture());
		String stored = captor.getValue().getPw();
		assertFalse("평문이 저장되면 안 된다", "secret".equals(stored));
		assertTrue(PasswordHasher.matches("secret", stored));
	}
}
