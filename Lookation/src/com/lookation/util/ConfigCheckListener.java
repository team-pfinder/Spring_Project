package com.lookation.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/** 기동 시 필수 설정값을 검사해, 비밀값 누락 상태로 서비스가 뜨지 않게 한다. */
public class ConfigCheckListener implements ServletContextListener
{
	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		AppConfig.require("LOOKATION_MAIL_USERNAME");
		AppConfig.require("LOOKATION_MAIL_PASSWORD");
		AppConfig.require("LOOKATION_DB_PASSWORD");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event)
	{
	}
}
