package com.lookation.dto;

import java.util.Date;

public class SignupPendingDTO
{
	private String token_hash;
	private String identify;
	private String email;
	private String pw_hash;
	private String nick;
	private String name;
	private String tel;
	private Date expires_at;
	private Date used_at;
	private Date created_at;

	public String getToken_hash() { return token_hash; }
	public void setToken_hash(String token_hash) { this.token_hash = token_hash; }
	public String getIdentify() { return identify; }
	public void setIdentify(String identify) { this.identify = identify; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPw_hash() { return pw_hash; }
	public void setPw_hash(String pw_hash) { this.pw_hash = pw_hash; }
	public String getNick() { return nick; }
	public void setNick(String nick) { this.nick = nick; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getTel() { return tel; }
	public void setTel(String tel) { this.tel = tel; }
	public Date getExpires_at() { return expires_at; }
	public void setExpires_at(Date expires_at) { this.expires_at = expires_at; }
	public Date getUsed_at() { return used_at; }
	public void setUsed_at(Date used_at) { this.used_at = used_at; }
	public Date getCreated_at() { return created_at; }
	public void setCreated_at(Date created_at) { this.created_at = created_at; }
}
