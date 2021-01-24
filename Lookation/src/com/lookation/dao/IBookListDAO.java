package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.BookListDTO;


public interface IBookListDAO
{
    // 이용자 예약리스트
    public ArrayList<BookListDTO> bookList(String member_code);

    // 이용자, 호스트 예약 상세
    public BookListDTO bookDetails(String book_code);

    // 이용자, 호스트 취소시 환불 가격 계산
    public BookListDTO refundPrice(String book_code);

    // 이용자 취소
    public int memberCancel(BookListDTO dto);

    // 이용자, 호스트
    public int refund(BookListDTO dto);

    // 이용자 닉네임 받아오기
    public String memberNick(String member_code);


    // 호스트 예약 리스트
    public ArrayList<BookListDTO> bookListHost(String host_code);

    // 호스트 취소
    public int hostCancel(BookListDTO dto);

    // 호스트 닉네임 받아오기
    public String hostNick(String host_code);


}


