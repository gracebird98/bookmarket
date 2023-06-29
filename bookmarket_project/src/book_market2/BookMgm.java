package book_market2;

import java.util.ArrayList;

/*
 * �����������
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
	
	//���Ű��� ����Ʈ �����
	public void createList() {
			String[] titleList = {"JUST JAVA","����Ŭ SQL ���","JSP ���α׷���","������ �� ��ŸƮ"};
			String[] authorList = {"Ȳ����","ȫ����","�ֹ���","ȫ�浿"};
			String[] isbnList = {"ISBN1234","ISBN5678","ISBN9012","ISBN1342"};
			int[] priceList = {27000,15000,30000,20000};
			
			//bookList�� ���� �߰�
			for(int i=0; i<titleList.length;i++) {
				BookVo book = new BookVo();
				book.setTitle(titleList[i]);
				book.setAuthor(authorList[i]);
				book.setIsbn(isbnList[i]);
				book.setPrice(priceList[i]);
				
				bookList.add(book);	//�̰� ���ϸ� ������ �ȵ��� null���.
			}
	}
	
	
	public void showList() {
		System.out.println("-----------------------------------------");
		System.out.println("\t���� ���� ���� ����Ʈ");
		System.out.println("-----------------------------------------");
			for(BookVo book : bookList) {
				System.out.print(book.getIsbn()+" | ");
				System.out.print(book.getTitle()+"\t| ");
				System.out.print(book.getAuthor()+" | ");
				System.out.print(book.getPrice()+"| \n");
			}
		System.out.println("-----------------------------------------");
	}
	
	//��ٱ��Ͽ� �߰��� ���� �˻�
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
