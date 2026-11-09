package com.lookation.util;

/**
 * 환경변수/시스템 속성에서 설정값을 읽는다.
 * 비밀값은 코드·설정 파일에 두지 않고, 누락 시 기동 단계에서 실패한다.
 */
public final class AppConfig
{
	private AppConfig()
	{
	}

	public static String get(String key)
	{
		String value = System.getProperty(key);
		if (value == null || value.isEmpty())
		{
			value = System.getenv(key);
		}
		return (value == null || value.isEmpty()) ? null : value;
	}

	public static String require(String key)
	{
		String value = get(key);
		if (value == null)
		{
			throw new IllegalStateException("필수 설정값이 없습니다: " + key);
		}
		return value;
	}
}
