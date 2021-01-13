/*===========================
  # IAdminDAO.java         
  - 인터페이스.
 ===========================*/
package com.lookation.dao;

import java.sql.SQLException;

public interface IAdminDAO
{
	public String loginAdmin(String admin_id, String admin_pw) throws SQLException;

}