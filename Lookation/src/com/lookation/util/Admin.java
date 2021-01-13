/*====================
	# Admin.java
 ====================*/

package com.lookation.util;

public class Admin
{
	//주요 속성 구성
	private String admin_id;
	private String admin_pw;
	private String admin_nickname;
	
	public String getAdmin_id() 
	{
		return admin_id;
	}
	public void setAdmin_id(String admin_id) 
	{
		this.admin_id = admin_id;
	}
	public String getAdmin_pw() {
		return admin_pw;
	}
	public void setAdmin_pw(String admin_pw) 
	{
		this.admin_pw = admin_pw;
	}
	public String getAdmin_nickname()
	{
		return admin_nickname;
	}
	public void setAdmin_nickname(String admin_nickname)
	{
		this.admin_nickname = admin_nickname;
	}
}

