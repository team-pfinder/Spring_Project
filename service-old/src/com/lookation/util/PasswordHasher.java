package com.lookation.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 비밀번호 해시·검증 단일 지점.
 * 저장 값이 BCrypt 형식이 아니면 기존 평문 데이터로 간주해 비교만 허용하고,
 * 로그인 성공 시 호출자가 해시로 교체한다.
 */
public final class PasswordHasher
{
	private static final int COST = 10;

	private PasswordHasher()
	{
	}

	public static String hash(String rawPassword)
	{
		if (rawPassword == null)
		{
			throw new IllegalArgumentException("password must not be null");
		}
		return BCrypt.hashpw(rawPassword, BCrypt.gensalt(COST));
	}

	public static boolean matches(String rawPassword, String storedValue)
	{
		if (rawPassword == null || storedValue == null)
		{
			return false;
		}
		if (!isHashed(storedValue))
		{
			return rawPassword.equals(storedValue);
		}
		try
		{
			return BCrypt.checkpw(rawPassword, storedValue);
		}
		catch (IllegalArgumentException e)
		{
			return false;
		}
	}

	public static boolean isHashed(String storedValue)
	{
		return storedValue != null && storedValue.startsWith("$2");
	}
}
