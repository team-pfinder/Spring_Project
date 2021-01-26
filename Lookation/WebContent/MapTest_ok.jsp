<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>    
<%
	String loc_code = request.getParameter("loc_code");

	String[] type = {"파티룸", "엠티장소"};
	String[] name = {"어승승일산", "어승승응암"};
	String[] addr = {"인천시 부평구 원적로 344", "인천시 부평구 원적로 416"};
	
	
	JSONArray jsonArr = new JSONArray();

	for(int i = 0; i < type.length; i++)	//1 ~ 5
	{
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("type", type[i]);
		jsonObj.put("name", name[i]);
		jsonObj.put("addr", addr[i]);
		
		jsonArr.add(jsonObj);		
	}

	out.println(jsonArr.toString());

%>