package com.lookation.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.lookation.dao.IAdminDAO;
import com.lookation.util.PasswordHasher;

@RunWith(MockitoJUnitRunner.class)
public class AdminLoginControllerTest
{
	private static final String SECRET_HASH = "$2a$10$9XGzMF96QmGcTQoPS6FlMusavuC/NNBw8JYFJo5fEzr04.7Ryv2oy";

	@Mock
	private IAdminDAO adminDAO;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpSession session;

	private AdminLoginController controller;

	@Before
	public void setUp()
	{
		controller = new AdminLoginController();
		controller.setDao(adminDAO);
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("admin_id")).thenReturn("admin");
	}

	@Test
	public void validAdminLoginStoresSessionAndRedirectsToMain() throws Exception
	{
		when(request.getParameter("admin_pw")).thenReturn("secret");
		when(adminDAO.findPassword("admin")).thenReturn(SECRET_HASH);
		when(adminDAO.findNickname("admin")).thenReturn("관리자");

		ModelAndView mav = controller.handleRequest(request, mock(HttpServletResponse.class));

		assertThat(mav.getViewName(), is("redirect:adminmain.action"));
		verify(session).setAttribute("admin_id", "admin");
	}

	@Test
	public void legacyPlaintextAdminPasswordIsRehashedOnLogin() throws Exception
	{
		when(request.getParameter("admin_pw")).thenReturn("secret");
		when(adminDAO.findPassword("admin")).thenReturn("secret");
		when(adminDAO.findNickname("admin")).thenReturn("관리자");

		controller.handleRequest(request, mock(HttpServletResponse.class));

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(adminDAO).updatePassword(any(String.class), captor.capture());
		assertTrue(PasswordHasher.matches("secret", captor.getValue()));
	}

	@Test
	public void invalidAdminPasswordDoesNotCreateSession() throws Exception
	{
		when(request.getParameter("admin_pw")).thenReturn("wrong");
		when(adminDAO.findPassword("admin")).thenReturn(SECRET_HASH);

		ModelAndView mav = controller.handleRequest(request, mock(HttpServletResponse.class));

		assertThat(mav.getViewName(), is("redirect:adminloginform.action"));
		verify(session, never()).setAttribute(any(String.class), any());
	}
}
