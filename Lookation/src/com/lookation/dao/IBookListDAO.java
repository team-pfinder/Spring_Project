package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.BookListDTO;


public interface IBookListDAO
{
	public ArrayList<BookListDTO> bookList(String member_code);
	
	public BookListDTO bookDetails(String book_code);
	
	public BookListDTO refundPrice(String book_code);
	
	public int memberCancel(BookListDTO dto);
	
	public int memberRefund(BookListDTO dto);
}
