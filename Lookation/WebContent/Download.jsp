<%@page import="java.io.File"%>
<%@page import="com.lookation.util.FileManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	// Download.jsp
	
	String root = pageContext.getServletContext().getRealPath("/");
	String savePath = root + "downloads"; //+ File.separator + "saveFile";
	
	String saveFileName = request.getParameter("saveFileName");
	String originalFileName = request.getParameter("originalFileName");
	
	// 파일 다운로드
	out.clear();
	boolean flag = FileManager.doFileDownload(saveFileName, originalFileName, savePath, response);
%>