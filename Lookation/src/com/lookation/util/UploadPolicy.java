package com.lookation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * 업로드 파일의 이름과 내용이 이미지인지 판단한다.
 * 확장자만으로는 실행 파일을 막을 수 없으므로 매직바이트를 함께 확인한다.
 */
public final class UploadPolicy
{
	private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<String>(
			Arrays.asList("jpg", "jpeg", "png", "gif", "webp"));

	private UploadPolicy()
	{
	}

	public static boolean isAllowedName(String fileName)
	{
		if (fileName == null || fileName.isEmpty())
		{
			return false;
		}
		if (fileName.contains("/") || fileName.contains("\\") || fileName.contains(".."))
		{
			return false;
		}
		return ALLOWED_EXTENSIONS.contains(extensionOf(fileName));
	}

	public static boolean hasValidImageSignature(File file)
	{
		if (file == null || !file.isFile())
		{
			return false;
		}
		byte[] header = new byte[12];
		int read;
		try (InputStream in = new FileInputStream(file))
		{
			read = in.read(header);
		}
		catch (IOException e)
		{
			return false;
		}
		if (read < 4)
		{
			return false;
		}
		String ext = extensionOf(file.getName());
		if ("jpg".equals(ext) || "jpeg".equals(ext))
		{
			return (header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8 && (header[2] & 0xFF) == 0xFF;
		}
		if ("png".equals(ext))
		{
			return read >= 8 && (header[0] & 0xFF) == 0x89 && header[1] == 0x50
					&& header[2] == 0x4E && header[3] == 0x47;
		}
		if ("gif".equals(ext))
		{
			return header[0] == 'G' && header[1] == 'I' && header[2] == 'F' && header[3] == '8';
		}
		if ("webp".equals(ext))
		{
			return read >= 12 && header[0] == 'R' && header[1] == 'I' && header[2] == 'F' && header[3] == 'F'
					&& header[8] == 'W' && header[9] == 'E' && header[10] == 'B' && header[11] == 'P';
		}
		return false;
	}

	public static String extensionOf(String fileName)
	{
		int dot = fileName.lastIndexOf('.');
		if (dot < 0 || dot == fileName.length() - 1)
		{
			return "";
		}
		return fileName.substring(dot + 1).toLowerCase(Locale.ROOT);
	}
}
