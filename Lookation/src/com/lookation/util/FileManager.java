package com.lookation.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileManager
{
	
	public static MultipartRequest upload(HttpServletRequest request, String saveFolderName) 
			throws IOException
	{
		// 올린 파일들의 경로정보를 받아온다.(상대경로 /Lookaion 부터)
		String realFolder = "";
		int maxSize = 1024*1024*5;
		String encType = "utf-8";
		ServletContext sContext = request.getServletContext();
		realFolder = sContext.getRealPath(saveFolderName);
		
		return new MultipartRequest(request, 
				realFolder, maxSize, encType, new DefaultFileRenamePolicy());
	}
	
	public static ArrayList<String> getFileNames(MultipartRequest multi)
	{
		ArrayList<String> result = new ArrayList<String>();
		String fileName = "";

		Enumeration<?> files = multi.getFileNames();
		while (files.hasMoreElements())
		{
			String file = (String) files.nextElement();
			fileName = multi.getFilesystemName(file);
			if(fileName != null)
				result.add(fileName);
		}

		return result;
	}
	
	// 파일 다운로드
	// saveFileName : 서버에 저장된 파일 이름
	// originalFileName : 클라이언트가 업로드한 파일 이름
	// path : 서버에 저장된 경로
	public static boolean doFileDownload(String saveFileName, String originalFileName, String path, HttpServletResponse response)
	{
		String load_dir = path + File.separator + saveFileName;
		
		try
		{
			if (originalFileName == null || originalFileName.equals(""))
			{
				originalFileName = saveFileName;
			}
			originalFileName = new String(originalFileName.getBytes("EUC-KR"), "8859_1");
			
		} catch (UnsupportedEncodingException e)
		{
			System.out.println(e.toString());
		}
		
		try
		{
			File file = new File(load_dir);
			if(file.exists())
			{
				byte[] readByte = new byte[4096];
				
				response.setContentType("application/octet-stream");
				response.setHeader("Content-disposition", "attachment;filename=" + originalFileName);
				
				BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
				
				OutputStream os = response.getOutputStream();
				
				int read;
				while((read = fis.read(readByte, 0, 4096)) != -1)
				{
					os.write(readByte, 0, read);
				}
				
				os.flush();
				os.close();
				fis.close();
				
				return true;
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return false;
	}
	
	// 실제 파일 삭제(제거)
	public static void doFileDelete(String fileName, String path)
	{
		try
		{
			File file = null;
			String fullFileName = path + File.separator + fileName;
			file = new File(fullFileName);
			if (file.exists())
			{
				file.delete();
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
