package com.lookation.util;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.SecureRandom;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CsrfFilterTest
{
	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private HttpSession session;

	@Mock
	private FilterChain chain;

	private CsrfFilter filter;

	@Before
	public void setUp() throws Exception
	{
		filter = new CsrfFilter();
		filter.init(null);
		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void getRequestsPassThrough() throws Exception
	{
		when(request.getMethod()).thenReturn("GET");

		filter.doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
	}

	@Test
	public void postWithoutTokenIsRejectedWithForbidden() throws Exception
	{
		when(request.getMethod()).thenReturn("POST");
		when(session.getAttribute(CsrfFilter.SESSION_TOKEN_KEY)).thenReturn("good-token");
		when(request.getHeader("X-CSRF-Token")).thenReturn(null);
		when(request.getParameter("_csrf")).thenReturn(null);

		filter.doFilter(request, response, chain);

		verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
		verify(chain, never()).doFilter(any(), any());
	}

	@Test
	public void postWithMatchingTokenPassesThrough() throws Exception
	{
		when(request.getMethod()).thenReturn("POST");
		when(session.getAttribute(CsrfFilter.SESSION_TOKEN_KEY)).thenReturn("good-token");
		when(request.getParameter("_csrf")).thenReturn("good-token");

		filter.doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
	}

	@Test
	public void firstRequestCreatesTokenOnSession() throws Exception
	{
		when(request.getMethod()).thenReturn("GET");

		filter.doFilter(request, response, chain);

		verify(session).setAttribute(any(String.class), any(String.class));
	}

	@Test
	public void generatedTokensAreLongAndRandom()
	{
		String first = CsrfFilter.newToken();
		String second = CsrfFilter.newToken();
		assertNotNull(first);
		assertNotNull(second);
		org.junit.Assert.assertNotEquals(first, second);
		org.junit.Assert.assertTrue(first.length() >= 32);
	}

	@Test
	public void postWithTokenInQueryStringPassesThroughForMultipartForms() throws Exception
	{
		when(request.getMethod()).thenReturn("POST");
		when(session.getAttribute(CsrfFilter.SESSION_TOKEN_KEY)).thenReturn("good-token");
		when(request.getParameter("_csrf")).thenReturn(null);
		when(request.getQueryString()).thenReturn("loc_code=L1&_csrf=good-token");

		filter.doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
	}
}
