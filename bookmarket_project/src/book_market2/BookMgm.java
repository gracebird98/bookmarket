package book_market2;

import java.util.ArrayList;

/*
 * 도서관리담당
 * @author tj
 * 
 */
public class BookMgm {
	//Field
	ArrayList<BookVo> bookList;
	
	//Constructor
	public BookMgm() {
		bookList = new ArrayList<BookVo>();
		createList();		
	}
		
	//Method
	
	//구매가능 리스트 만들기
	public void createList() {
			String[] titleList = {"JUST JAVA","오라클 SQL 기술","JSP 프로그래밍","스프링 퀵 스타트"};
			String[] authorList = {"황희정","홍형정","최범균","홍길동"};
			String[] isbnList = {"ISBN1234","ISBN5678","ISBN9012","ISBN1342"};
			int[] priceList = {27000,15000,30000,20000};
			
			//bookList에 도서 추가
			for(int i=0; i<titleList.length;i++) {
				BookVo book = new BookVo();
				book.setTitle(titleList[i]);
				book.setAuthor(authorList[i]);
				book.setIsbn(isbnList[i]);
				book.setPrice(priceList[i]);
				
				bookList.add(book);	//이거 안하면 데이터 안들어가서 null뜬다.
			}
	}
	
	
	public void showList() {
		System.out.println("-----------------------------------------");
		System.out.println("\t구매 가능 도서 리스트");
		System.out.println("-----------------------------------------");
			for(BookVo book : bookList) {
				System.out.print(book.getIsbn()+" | ");
				System.out.print(book.getTitle()+"\t| ");
				System.out.print(book.getAuthor()+" | ");
				System.out.print(book.getPrice()+"| \n");
			}
		System.out.println("-----------------------------------------");
	}
	
	//장바구니에 추가될 도서 검색
	public BookVo search(String isbn) {
		BookVo book = null;
		
		for(BookVo sbook : bookList ) {
			if(sbook.getIsbn().equals(isbn)) {
				book = sbook;
			}
		}
		
		return book;
	}
	
	
	
	
	
	
	
	
	
}//class
