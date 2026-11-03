package com.lookation.dto;

import java.util.Date;

public class PasswordResetDTO
{
	private String token_hash;
	private String identify;
	private String email;
	private Date expires_at;
	private Date used_at;
	private Date created_at;

	public String get_token_hash() { return token_hash; }
	public void set_token_hash(String token_hash) { this.token_hash = token_hash; }
	public String getIdentify() { return identify; }
	public void setIdentify(String identify) { this.identify = identify; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public Date get_expires_at() { return expires_at; }
	public void set_expires_at(Date expires_at) { this.expires_at = expires_at; }
	public Date get_used_at() { return used_at; }
	public void set_used_at(Date used_at) { this.used_at = used_at; }
	public Date get_created_at() { return created_at; }
	public void set_created_at(Date created_at) { this.created_at = created_at; }
}
