package com.lookation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 웹루트 밖 업로드 디렉터리의 이미지를 제공한다.
 * 파일명에 경로 구분자가 들어오면 거부하고, 이미지 확장자만 응답한다.
 */
public class UploadServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String pathInfo = request.getPathInfo();
		if (pathInfo == null || pathInfo.length() <= 1)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String fileName;
		try
		{
			fileName = URLDecoder.decode(pathInfo.substring(1), "UTF-8");
		}
		catch (IllegalArgumentException e)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		if (!UploadPolicy.isAllowedName(fileName))
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		File file = new File(FileManager.getUploadDirectory(), fileName);
		if (!file.isFile())
		{
			// 이관 전 웹루트 이미지도 같은 규칙으로 제공한다.
			String legacyPath = getServletContext().getRealPath("/images");
			file = legacyPath == null ? file : new File(legacyPath, fileName);
		}
		if (!file.isFile())
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		response.setContentType(contentType(fileName));
		response.setHeader("X-Content-Type-Options", "nosniff");
		response.setContentLengthLong(file.length());
		try (FileInputStream in = new FileInputStream(file);
			 OutputStream out = response.getOutputStream())
		{
			byte[] buffer = new byte[4096];
			int read;
			while ((read = in.read(buffer)) != -1)
			{
				out.write(buffer, 0, read);
			}
		}
	}

	private String contentType(String fileName)
	{
		String ext = UploadPolicy.extensionOf(fileName);
		if ("png".equals(ext))
		{
			return "image/png";
		}
		if ("gif".equals(ext))
		{
			return "image/gif";
		}
		if ("webp".equals(ext))
		{
			return "image/webp";
		}
		return "image/jpeg";
	}
}
