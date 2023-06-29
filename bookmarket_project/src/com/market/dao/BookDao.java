package com.market.dao;

import java.util.ArrayList;

import com.market.vo.BookVo;

public class BookDao extends DBConn{
	//CRUD
	/**
	 * 도서 전체 리스트 조회
	 */
	public ArrayList<BookVo> select(){
		ArrayList<BookVo> list  = new ArrayList<BookVo>();
		StringBuffer sb = new StringBuffer(100);
		sb.append("SELECT * FROM BOOKMARKET_BOOK ORDER BY BDATE DESC");
		
		try {
			getPreparedStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookVo book = new BookVo();
				book.setIsbn(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setPrice(rs.getInt(3));
				book.setAuthor(rs.getString(4));
				book.setIntro(rs.getString(5));
				book.setPart(rs.getString(6));
				book.setPdate(rs.getString(7));
				book.setImg(rs.getString(8));
				book.setBdate(rs.getString(9));
				
				list.add(book);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
		return list;
	}
	
	/**
	 * 도서 등록(관리자)
	 */
	public int insert(BookVo bookVo) {
		int result = 0;
		StringBuffer sb = new StringBuffer(50);
		sb.append("INSERT INTO BOOKMARKET_BOOK(ISBN,TITLE,PRICE,AUTHOR,INTRO,PART,PDATE,IMG,BDATE)");
		sb.append("VALUES('ISBN_'||LTRIM(TO_CHAR(SEQU_BOOKMARKET_BOOK_ISBN.NEXTVAL,'0000')),?,?,?,?,?,?,null,SYSDATE)");
		
		try {
			getPreparedStatement(sb.toString());
			
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setInt(2, bookVo.getPrice());
			pstmt.setString(3, bookVo.getAuthor());
			pstmt.setString(4, bookVo.getIntro());
			pstmt.setString(5, bookVo.getPart());
			pstmt.setString(6, bookVo.getPdate());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	
}
