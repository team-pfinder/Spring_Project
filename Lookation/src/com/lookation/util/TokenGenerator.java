package com.lookation.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/** 이메일 인증 토큰 생성·해시. 원문 토큰은 메일 링크에만 쓰고 DB에는 해시를 저장한다. */
public final class TokenGenerator
{
	private static final SecureRandom RANDOM = new SecureRandom();

	private TokenGenerator()
	{
	}

	public static String newToken()
	{
		byte[] bytes = new byte[32];
		RANDOM.nextBytes(bytes);
		return toHex(bytes);
	}

	public static String sha256(String value)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return toHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new IllegalStateException(e);
		}
	}

	private static String toHex(byte[] bytes)
	{
		StringBuilder builder = new StringBuilder(bytes.length * 2);
		for (byte b : bytes)
		{
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}
}
