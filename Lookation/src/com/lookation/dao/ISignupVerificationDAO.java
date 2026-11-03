package com.lookation.dao;

import org.apache.ibatis.annotations.Param;

import com.lookation.dto.SignupPendingDTO;

public interface ISignupVerificationDAO
{
	public int insertPending(SignupPendingDTO pending);
	public SignupPendingDTO findValid(@Param("tokenHash") String tokenHash);
	public int markUsed(@Param("tokenHash") String tokenHash);
}
