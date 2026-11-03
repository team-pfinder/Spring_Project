package com.lookation.dao;

import org.apache.ibatis.annotations.Param;

import com.lookation.dto.PasswordResetDTO;

public interface IPasswordResetDAO
{
	public int insert(PasswordResetDTO reset);
	public PasswordResetDTO findValid(@Param("tokenHash") String tokenHash);
	public int markUsed(@Param("tokenHash") String tokenHash);
}
