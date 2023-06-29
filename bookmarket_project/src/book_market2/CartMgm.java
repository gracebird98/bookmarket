package book_market2;

import java.util.ArrayList;
import java.util.Scanner;

public class CartMgm {
	//Field
	ArrayList<BookVo> cartList;
	private ArrayList<CartItemVo> cartItemList;
	int count = 0;
	Scanner scan;
	
	//constructor
	public CartMgm() {
		cartList = new ArrayList<BookVo>();
		cartItemList = new ArrayList<CartItemVo>();
	}
	
	public CartMgm(Scanner scan) {
		this.scan = scan;
		cartList = new ArrayList<BookVo>();
		cartItemList = new ArrayList<CartItemVo>();
	}
	
	//method
	
	/**
	 * 장바구니에 book 추가
	 */
	public boolean insert(BookVo book) {
		boolean result = false;
//		result = cartList.add(book);	//add�� boolean���� ����
		
		//cartItemList�� �߰��ϱ�
		if(cartItemList.size() != 0) {//������ īƮ�� �����������
			//for(�⺻)�� ����Ͽ� book�� isbn�� cartItemList�� isbn ��
			boolean check = false;
			CartItemVo currItem = null;	//currItem = �ߺ�üũ���� ������
			for(int i=0; i<cartItemList.size();i++) {	//Ȯ�� for�� ������ //�ι�° ���������ʹ� �ߺ� �˻��ؾ��� �̹� ����ִ� ������
				CartItemVo item = cartItemList.get(i);
				if(item.getIsbn().equals(book.getIsbn())) {	//item�� �������� ���� / book = ������ ��� ����
					currItem = item;
					check = true;//=�ߺ����.
					i = cartItemList.size();
				}
				
			}
			add(check, currItem, book);	//add�޼ҵ� ȣ���� cartList�� �߰�  //for�� �ȿ� �θ� ��������� ��ġ �߿�!!!
			result = true;
			
		}else {//���ʷ� ������ īƮ�� �������� ����Ǵ� �ڵ�� �� �ѹ� ����� (�ߺ� �˻��� �ʿ� ����)
			
			CartItemVo item = new CartItemVo();
			item.setIsbn(book.getIsbn());
			item.setTitle(book.getTitle());
			item.setQty(1);
			item.setTotalPrice(book.getPrice());
			
			cartItemList.add(item);
			result = true;
		}
			
			return result;
	}
	
		
		
	public void add(boolean check, CartItemVo item, BookVo book) {
		if(check) {
					item.setQty(item.getQty()+1);
			
		}else {
			CartItemVo item2 = new CartItemVo();
			item2.setIsbn(book.getIsbn());
			item2.setTitle(book.getTitle());
			item2.setQty(1);
			item2.setTotalPrice(book.getPrice());
			
			cartItemList.add(item2);
		}
	}
		
		
	
	/**
	 * ��ٱ��� ��� ���
	 */
	public void showList() {
		if(getSize() != 0) {	
			System.out.println("------------------------------------------");
			System.out.println("\t\t��ٱ��� ��ǰ ��� ");
			System.out.println("------------------------------------------");
			System.out.println("����ID\t | \t����\t | \t�հ�");
			for(CartItemVo item : cartItemList) {
				
				System.out.print(item.getIsbn()+" |\t");
				System.out.print(item.getQty() +"  \t"+" |");
				System.out.print("\t"+item.getQty()* item.getTotalPrice()+"\n");//�հ�
				
			}
			System.out.println("------------------------------------------");
		}else {
			System.out.println("--��ٱ��ϰ� ����ֽ��ϴ�.--");
		}
	}
	
	/**
	 * ��ٱ��� �ֹ�����Ʈ ��� 
	 */
	public void showList(String order) {
		if(getSize() != 0) {	
//			System.out.println("------------------------------------------");
//			System.out.println("\t\t��ٱ��� ��ǰ ��� ");
			
			int orderTotalPrice = 0;	// = �ֹ� �ѱݾ�
			System.out.println("------------------------------------------");
			System.out.println("����ID\t | \t����\t | \t�հ�");
			for(CartItemVo item : cartItemList) {
				
				System.out.print(item.getIsbn()+" |\t");
				System.out.print(item.getQty() +"  \t"+" |");
				System.out.print("\t"+item.getQty()* item.getTotalPrice()+"\n");//�հ�
				orderTotalPrice += item.getQty()* item.getTotalPrice();	//�ѱݾ� ��� (��� �հ���� �������� �ϴϱ� �� ��ø���)
				
			}
			System.out.println("------------------------------------------");
			System.out.println("\t\t\t�ֹ� �ѱݾ� : " + orderTotalPrice );
		}else {
			System.out.println("--��ٱ��ϰ� ����ֽ��ϴ�.--");
		}
	}
	
	public boolean remove(String isbn) {
		boolean result = false;
		int idx = -1;
		//1. cartItemList�� �Ű����� isbn�� �����ϴ��� üũ
		for(int i=0; i<cartItemList.size();i++) {
			CartItemVo item = cartItemList.get(i);
			if(item.getIsbn().equals(isbn)) {
				idx = i;
			}
		}
		//2. �ش� �ε����� ���� item�� ����
		if(idx != -1) {
			System.out.print("������ �����Ͻðڽ��ϱ�? Y | N > ");
			String con = scan.next().toUpperCase();
			if(con.equals("Y")) {
				cartItemList.remove(idx);
				result = true;
			}
		}else {
			System.out.println("������ �׸��� �������� �ʽ��ϴ�.");
		}
		
		return result;
	}
	
	//getSize = cartItemList.size
	public int getSize() {
		return cartItemList.size();
	}
	
	public ArrayList<CartItemVo> getCartItemList(){
		return cartItemList;
	}
	
	/**
	 * ��ٱ��� ��ü ����
	 */
	public void remove() {
		cartItemList.clear();
	}
	
	//remove 오버로딩 - index 받아서 바로 삭제
	public boolean remove(int idx) {
		return cartItemList.remove(cartItemList.get(idx)); 
	}
	
	
	
	public void updateQty(String isbn, int qty) {
		int idx = -1;
		//�ش��ϴ� idx��������
		for(int i=0; i<getSize(); i++) {
			CartItemVo item = cartItemList.get(i);
			if(item.getIsbn().equals(isbn)) {
				idx = i;
			}
		}
		if(idx != -1) {
			CartItemVo item = cartItemList.get(idx);
				item.setQty(item.getQty()-1);
		}
				
	}
	
	public void updateQty(String isbn) {
		int idx = -1;
		//�ش��ϴ� idx��������
		for(int i=0; i<getSize(); i++) {
			CartItemVo item = cartItemList.get(i);
			if(item.getIsbn().equals(isbn)) {
				idx = i;
			}
		}
		if(idx != -1) {
			CartItemVo item = cartItemList.get(idx);
				item.setQty(item.getQty()-1);
		}
				
	}
	
	
	
	
	
	
}//class
