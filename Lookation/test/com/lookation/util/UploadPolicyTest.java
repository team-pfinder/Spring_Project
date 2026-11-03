package com.lookation.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import org.junit.Test;

public class UploadPolicyTest
{
	@Test
	public void acceptsCommonImageExtensions()
	{
		assertTrue(UploadPolicy.isAllowedName("photo.jpg"));
		assertTrue(UploadPolicy.isAllowedName("photo.JPEG"));
		assertTrue(UploadPolicy.isAllowedName("photo.png"));
		assertTrue(UploadPolicy.isAllowedName("photo.gif"));
		assertTrue(UploadPolicy.isAllowedName("photo.webp"));
	}

	@Test
	public void rejectsExecutableAndUnknownExtensions()
	{
		assertFalse(UploadPolicy.isAllowedName("shell.jsp"));
		assertFalse(UploadPolicy.isAllowedName("archive.zip"));
		assertFalse(UploadPolicy.isAllowedName(null));
	}

	@Test
	public void rejectsFileWhoseContentDoesNotMatchImageSignature() throws Exception
	{
		File fake = File.createTempFile("upload", ".jpg");
		fake.deleteOnExit();
		try (FileOutputStream out = new FileOutputStream(fake))
		{
			out.write("<% out.println(\"pwned\"); %>".getBytes("UTF-8"));
		}

		assertFalse(UploadPolicy.hasValidImageSignature(fake));
	}

	@Test
	public void acceptsPngSignature() throws Exception
	{
		File png = File.createTempFile("upload", ".png");
		png.deleteOnExit();
		byte[] header = new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };
		Files.write(png.toPath(), header);

		assertTrue(UploadPolicy.hasValidImageSignature(png));
	}
}
