/*===========================
  # IAdminDAO.java
  - 인터페이스.
 ===========================*/
package com.lookation.dao;

import java.sql.SQLException;

public interface IAdminDAO
{
	public String findPassword(String adminId) throws SQLException;
	public String findNickname(String adminId) throws SQLException;
	public void updatePassword(String adminId, String hashedPassword) throws SQLException;
}
