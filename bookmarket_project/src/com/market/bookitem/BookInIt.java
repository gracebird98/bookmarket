package com.market.bookitem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import book_market2.BookVo;

public class BookInIt {
	private static ArrayList<BookVo> mBookList;
	private static int mTotalBook = 0;

	public static void init() {
		mTotalBook = totalFileToBookList();//책 수량 체크
		mBookList = new ArrayList<BookVo>();
		setFileToBookList(mBookList);
	}

	public static int totalFileToBookList() {
		try {
			FileReader fr = new FileReader("book.txt");
			BufferedReader reader = new BufferedReader(fr);	//파일(book.txt)에서 한줄한줄 읽어오는데 중간에 창고만들어진다 
			//권 수 체크 
			String str;
			int num = 0;
			while ((str = reader.readLine()) != null) {
				if (str.contains("ISBN"))//한줄안에 isbn 잇으면 카운트 진행하겠다는 것  1권, 또 isbn 잇으면 2권...
					++num;
			}
			reader.close();
			fr.close();
			return num;
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

	public static void setFileToBookList(ArrayList<BookVo> booklist) {	//ArrayList에 1차원 배열 만들어서 넣어주는 작업
		try {
			FileReader fr = new FileReader("book.txt");
			BufferedReader reader = new BufferedReader(fr);

			String str2;
			String[] readBook = new String[7];	//String 배열로 7개 만들어짐 한권에 들어가는 정보가 book.txt에서 isbn포함 7줄임

			while ((str2 = reader.readLine()) != null) {
				BookVo book = new BookVo();//추가
				
				if (str2.contains("ISBN")) {
					book.setIsbn(str2);
					book.setTitle(reader.readLine());
					book.setPrice(Integer.parseInt(reader.readLine()));
					book.setAuthor(reader.readLine());
					book.setDesc(reader.readLine());
					book.setBfield(reader.readLine());
					book.setPdate(reader.readLine());
				}
				booklist.add(book);
			}
			reader.close();
			fr.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static ArrayList<BookVo> getmBookList() {
		return mBookList;
	}

	public static void setmBookList(ArrayList<BookVo> mBookList) {
		BookInIt.mBookList = mBookList;
	}

	public static int getmTotalBook() {
		return mTotalBook;
	}

	public static void setmTotalBook(int mTotalBook) {
		BookInIt.mTotalBook = mTotalBook;
	}
}