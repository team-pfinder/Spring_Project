package com.lookation.dto;

import java.util.Date;

public class PasswordResetDTO
{
	private String tokenHash;
	private String identify;
	private String email;
	private Date expiresAt;
	private Date usedAt;
	private Date createdAt;

	public String getTokenHash() { return tokenHash; }
	public void setTokenHash(String tokenHash) { this.tokenHash = tokenHash; }
	public String getIdentify() { return identify; }
	public void setIdentify(String identify) { this.identify = identify; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public Date getExpiresAt() { return expiresAt; }
	public void setExpiresAt(Date expiresAt) { this.expiresAt = expiresAt; }
	public Date getUsedAt() { return usedAt; }
	public void setUsedAt(Date usedAt) { this.usedAt = usedAt; }
	public Date getCreatedAt() { return createdAt; }
	public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
