package com.lookation.mybatis;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * 매퍼 XML의 #{property}가 DTO 접근자와 실제로 연결되는지 확인한다.
 * MyBatis는 실행 시점에 getter를 찾으므로, 이름이 어긋나면 런타임에야 실패한다.
 */
public class MapperPropertyTest
{
	private static final Pattern PROPERTY = Pattern.compile("#\\{([a-zA-Z_][a-zA-Z0-9_]*)\\}");

	@Test
	public void signupVerificationInsertPropertiesExistOnDto() throws Exception
	{
		assertPropertiesExist("service-old/src/com/lookation/mybatis/mapper/SignupVerificationDAO.xml",
				"com.lookation.dto.SignupPendingDTO");
	}

	@Test
	public void passwordResetInsertPropertiesExistOnDto() throws Exception
	{
		assertPropertiesExist("service-old/src/com/lookation/mybatis/mapper/PasswordResetDAO.xml",
				"com.lookation.dto.PasswordResetDTO");
	}

	private void assertPropertiesExist(String mapperPath, String dtoClassName) throws Exception
	{
		String xml = new String(Files.readAllBytes(Paths.get(mapperPath)), StandardCharsets.UTF_8);
		Class<?> dto = Class.forName(dtoClassName);
		Set<String> methods = new LinkedHashSet<String>();
		for (Method method : dto.getMethods())
		{
			methods.add(method.getName().toLowerCase());
		}

		Matcher matcher = PROPERTY.matcher(xml);
		while (matcher.find())
		{
			String property = matcher.group(1);
			String capitalized = property.substring(0, 1).toUpperCase() + property.substring(1);
			boolean found = methods.contains(("get" + capitalized).toLowerCase())
					|| methods.contains(("set" + capitalized).toLowerCase())
					|| methods.contains(("is" + capitalized).toLowerCase());
			assertTrue("DTO에 접근자가 없습니다: " + property, found);
		}
	}
}
