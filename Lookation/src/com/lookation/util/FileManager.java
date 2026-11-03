package com.lookation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class FileManager
{
	private static final int MAX_UPLOAD_BYTES = 10 * 1024 * 1024;

	/** 업로드 파일은 웹루트 밖에 저장한다. */
	public static File getUploadDirectory()
	{
		String configured = System.getProperty("lookation.upload.dir");
		if (configured == null || configured.isEmpty())
		{
			configured = System.getenv("LOOKATION_UPLOAD_DIR");
		}
		if (configured == null || configured.isEmpty())
		{
			configured = System.getProperty("user.home") + File.separator + "lookation-uploads";
		}
		File dir = new File(configured);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 업로드를 수행하고 저장된 파일이 이미지인지 검증한다.
	 * 검증에 실패하면 저장 파일을 지우고 IOException을 던져 호출자가 저장을 중단하게 한다.
	 */
	public static MultipartRequest upload(HttpServletRequest request, String saveFolderName)
			throws IOException
	{
		File uploadDir = getUploadDirectory();
		String encType = "utf-8";

		MultipartRequest multi = new MultipartRequest(request,
				uploadDir.getAbsolutePath(), MAX_UPLOAD_BYTES, encType,
				new UuidRenamePolicy());

		Enumeration<?> files = multi.getFileNames();
		while (files.hasMoreElements())
		{
			String field = (String) files.nextElement();
			String storedName = multi.getFilesystemName(field);
			if (storedName == null)
			{
				continue;
			}
			File stored = new File(uploadDir, storedName);
			if (!UploadPolicy.isAllowedName(storedName) || !UploadPolicy.hasValidImageSignature(stored))
			{
				stored.delete();
				throw new IOException("허용되지 않은 업로드 파일입니다: " + storedName);
			}
		}
		return multi;
	}

	public static ArrayList<String> getFileNames(MultipartRequest multi)
	{
		ArrayList<String> result = new ArrayList<String>();
		Enumeration<?> files = multi.getFileNames();
		while (files.hasMoreElements())
		{
			String file = (String) files.nextElement();
			String fileName = multi.getFilesystemName(file);
			if (fileName != null)
			{
				result.add(fileName);
			}
		}
		return result;
	}

	public static void doFileDelete(HttpServletRequest request, String folderName, String fileName)
	{
		if (!UploadPolicy.isAllowedName(fileName))
		{
			return;
		}
		File file = new File(getUploadDirectory(), fileName);
		if (file.isFile())
		{
			file.delete();
		}
	}

	private static class UuidRenamePolicy implements FileRenamePolicy
	{
		private final DefaultFileRenamePolicy fallback = new DefaultFileRenamePolicy();

		@Override
		public File rename(File file)
		{
			String extension = UploadPolicy.extensionOf(file.getName());
			String suffix = extension.isEmpty() ? "" : "." + extension.toLowerCase(Locale.ROOT);
			File target = new File(file.getParentFile(), UUID.randomUUID().toString().replace("-", "") + suffix);
			if (target.exists())
			{
				return fallback.rename(file);
			}
			return target;
		}
	}
}
