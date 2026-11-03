package com.lookation.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 세션 토큰 기반 CSRF 방어.
 * 안전한 메서드는 통과시키고, 상태 변경 메서드는 폼 파라미터나 헤더의 토큰을 요구한다.
 */
public class CsrfFilter implements Filter
{
	public static final String SESSION_TOKEN_KEY = "csrfToken";
	public static final String PARAMETER_NAME = "_csrf";
	public static final String HEADER_NAME = "X-CSRF-Token";

	private static final Set<String> SAFE_METHODS = new HashSet<String>(
			Arrays.asList("GET", "HEAD", "OPTIONS", "TRACE"));
	private static final SecureRandom RANDOM = new SecureRandom();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		HttpSession session = request.getSession();
		String expected = (String) session.getAttribute(SESSION_TOKEN_KEY);
		if (expected == null)
		{
			expected = newToken();
			session.setAttribute(SESSION_TOKEN_KEY, expected);
		}

		if (!SAFE_METHODS.contains(request.getMethod()))
		{
			String provided = request.getHeader(HEADER_NAME);
			if (provided == null)
			{
				provided = request.getParameter(PARAMETER_NAME);
			}
			if (provided == null)
			{
				provided = fromQueryString(request.getQueryString());
			}
			if (!constantTimeEquals(expected, provided))
			{
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy()
	{
	}

	public static String newToken()
	{
		byte[] bytes = new byte[32];
		RANDOM.nextBytes(bytes);
		StringBuilder builder = new StringBuilder(bytes.length * 2);
		for (byte b : bytes)
		{
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}

	private static String fromQueryString(String queryString)
	{
		if (queryString == null)
		{
			return null;
		}
		for (String pair : queryString.split("&"))
		{
			int eq = pair.indexOf('=');
			if (eq > 0 && PARAMETER_NAME.equals(pair.substring(0, eq)))
			{
				try
				{
					return java.net.URLDecoder.decode(pair.substring(eq + 1), "UTF-8");
				}
				catch (java.io.UnsupportedEncodingException e)
				{
					return null;
				}
			}
		}
		return null;
	}

	private static boolean constantTimeEquals(String expected, String provided)
	{
		if (expected == null || provided == null)
		{
			return false;
		}
		return MessageDigest.isEqual(expected.getBytes(), provided.getBytes());
	}
}
