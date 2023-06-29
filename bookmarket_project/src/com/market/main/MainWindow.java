package com.market.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.market.bookitem.BookInIt;
import com.market.commons.MarketFont;
import com.market.dao.BookDao;
import com.market.dao.CartDao;
import com.market.dao.DBConn;
import com.market.dao.MemberDao;
import com.market.page.AdminLoginDialog;
import com.market.page.AdminPage;
import com.market.page.CartAddItemPage;
import com.market.page.CartItemListPage;
import com.market.page.CartShippingPage;
import com.market.page.GuestInfoPage;
import com.market.vo.MemberVo;

import book_market2.CartMgm;

public class MainWindow extends JFrame {
	static JPanel mMenuPanel, mPagePanel;
	public static MemberVo member;	//-> OrderUserVo 포함하고 있음
	MemberDao  memberDao;
	BookDao bookDao;
	CartDao cartDao;
	CartMgm cm;
	MainWindow main = this;
	
	//여러개의 다오들을(book,member,cartdao) 넘겨야해서 hashMap써서 daoList로 묶어서 하나로 보내버리자~
	Map<String,DBConn> daoList = new HashMap<String,DBConn>();
	
	
	public MainWindow(String title, int x, int y, int width, int height) {
		cm = new CartMgm();	//장바구니 메니저 실행시 만들어지게함 
		this.member = member;
		initContainer(title, x, y, width, height);

		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("./images/shop.png").getImage());
	}
	//오버로딩
	public MainWindow(Map param) {
		bookDao = new BookDao();
		cartDao = new CartDao();
		cm = new CartMgm();	//장바구니 메니저 실행시 만들어지게함
		this.member = (MemberVo)param.get("member");
		this. memberDao = (MemberDao)param.get("memberDao");
		
		daoList.put("memberDao", memberDao); //put으로 daoList에 dao각각 넣기
		daoList.put("bookDao", bookDao);
		daoList.put("cartDao", cartDao);
		
		String title = (String)param.get("title");
		int x = (int)param.get("x");
		int y = (int)param.get("y");
		int width = (int)param.get("width");
		int height = (int)param.get("height");
		initContainer(title, x, y, width, height);

		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("./images/shop.png").getImage());
	}

	private void initContainer(String title, int x, int y, int width, int height) {
		setTitle(title);
		setBounds(x, y, width, height);
		setLayout(null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - 1000) / 2, (screenSize.height - 750) / 2);

		mMenuPanel = new JPanel();
		mMenuPanel.setBounds(0, 20, width, 130);
		menuIntroduction();	
		add(mMenuPanel);

		mPagePanel = new JPanel();
		mPagePanel.setBounds(0, 150, width, height);
		add(mPagePanel);

		//장바구니 항목 추가 화면 디폴트로 출력
		mPagePanel.removeAll();	//앞에 뜬 창들 다 지우는거임
		BookInIt.init();//book.txt에서 읽은 정보 ArrayList에 넣는거
		mPagePanel.add(new CartAddItemPage(mPagePanel, cm, bookDao, cartDao));
		mPagePanel.revalidate();
		mPagePanel.repaint();
		
		
		
	}

	private void menuIntroduction() {

		JButton bt1 = new JButton("고객 정보 확인하기", new ImageIcon("./images/1.png"));
		bt1.setBounds(0, 0, 100, 50);
		MarketFont.getFont(bt1);
		mMenuPanel.add(bt1);
		
		//람다식으로 바꾸기
		//고객정보 확인 이벤트
		bt1.addActionListener(e -> {
				
				mPagePanel.removeAll();  
				mPagePanel.add(new GuestInfoPage(mPagePanel, member, memberDao)); 
				mPagePanel.revalidate(); 
				mPagePanel.repaint(); 
			
		});
		
		
		
		
		/**장바구니 상품 목록보기**/
		JButton bt2 = new JButton("장바구니 상품 목록보기", new ImageIcon("./images/2.png"));
		bt2.setBounds(0, 0, 100, 30);
		MarketFont.getFont(bt2);
		mMenuPanel.add(bt2);

		bt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (cartDao.getSize(member.getMid().toUpperCase()) == 0)//장바구니 사이즈 체크
					JOptionPane.showMessageDialog(bt2, "장바구니가 비어 있습니다", "message", JOptionPane.ERROR_MESSAGE);
				else {
					
					
					mPagePanel.removeAll();
					mPagePanel.add(new CartItemListPage(mPagePanel, cm, daoList));
					mPagePanel.revalidate();
					mPagePanel.repaint();

				}
			}
		});

		/**장바구니 전체삭제**/
//		JButton bt3 = new JButton("장바구니 전체삭제", new ImageIcon("./images/3.png"));
//		bt3.setBounds(0, 0, 100, 30);
//		MarketFont.getFont(bt3);
////		mMenuPanel.add(bt3);
//
//		bt3.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//				if (cartDao.getSize(member.getMid().toUpperCase()) == 0) {
//					JOptionPane.showMessageDialog(bt3, "장바구니가 비어 있습니다", "message", JOptionPane.ERROR_MESSAGE);
//				}else {
//					mPagePanel.removeAll();
//					mPagePanel.add(new CartItemListPage(mPagePanel, cm, daoList));
//					mPagePanel.revalidate();
//					mPagePanel.repaint();
//				}
//			}
//		});

		/**
		 * 장바구니 항목 추가하기
		 */
		JButton bt4 = new JButton("장바구니 항목 추가하기", new ImageIcon("./images/3.png"));
		MarketFont.getFont(bt4);
		mMenuPanel.add(bt4);
		
		bt4.addActionListener(e -> {
			
				mPagePanel.removeAll();	//앞에 뜬 창들 다 지우는거임
				BookInIt.init();//book.txt에서 읽은 정보 ArrayList에 넣는거
				mPagePanel.add(new CartAddItemPage(mPagePanel, cm, bookDao, cartDao));
				mPagePanel.revalidate();
				mPagePanel.repaint();
			
		});//= 람다식

		
		/**장바구니 항목 수량 줄이기 버튼 이벤트**/
//		JButton bt5 = new JButton("장바구니 항목수량 줄이기", new ImageIcon("./images/5.png"));
//		MarketFont.getFont(bt5);
////		mMenuPanel.add(bt5);
//		
//		bt5.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//				if (cartDao.getSize(member.getMid().toUpperCase()) == 0) {
//					JOptionPane.showMessageDialog(bt5, "장바구니가 비어있습니다", "message", JOptionPane.ERROR_MESSAGE);
//				}else {
//
//					mPagePanel.removeAll();
//					CartItemListPage cartList = new CartItemListPage(mPagePanel, cm, daoList);
//					if(cartList.mSelectRow == -1) {	//아무것도 선택하지 않았을떄 
//						JOptionPane.showMessageDialog(bt5, "수량을 차감할 항목을 선택해주세요");
//					}else {
//						cartList.mSelectRow = -1;
//					}
//				}
//				mPagePanel.add(new CartItemListPage(mPagePanel, cm, daoList));
//				mPagePanel.revalidate();
//				mPagePanel.repaint();
//			}
//		});
		
		
		/**장바구니 항목 삭제하기**/
//		JButton bt6 = new JButton("장바구니 항목 삭제하기", new ImageIcon("./images/6.png"));
//		MarketFont.getFont(bt6);
////		mMenuPanel.add(bt6);
//
//		bt6.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//				if (cartDao.getSize(member.getMid().toUpperCase()) == 0) {
//					JOptionPane.showMessageDialog(bt3, "장바구니가 비어있습니다", "message", JOptionPane.ERROR_MESSAGE);
//				}else {
//
//					mPagePanel.removeAll();
//					CartItemListPage cartList = new CartItemListPage(mPagePanel, cm, daoList);
//					if(cartList.mSelectRow == -1) {
//						JOptionPane.showMessageDialog(bt6, "삭제할 항목을 선택해주세요");
//					}else {
//						cartList.mSelectRow = -1;
//					}
//				}
//				mPagePanel.add(new CartItemListPage(mPagePanel, cm, daoList));
//
//				mPagePanel.revalidate();
//				mPagePanel.repaint();
//			}
//		});
		
		
		/**주문하기**/
		JButton bt7 = new JButton("주문하기", new ImageIcon("./images/4.png"));
		MarketFont.getFont(bt7);
		mMenuPanel.add(bt7);

		bt7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (cartDao.getSize(member.getMid().toUpperCase()) == 0)
					JOptionPane.showMessageDialog(bt7, "장바구니가 비어있습니다", "message", JOptionPane.ERROR_MESSAGE);
				else {

					mPagePanel.removeAll();
					mPagePanel.add(new CartShippingPage(mPagePanel, cm, main, daoList));
					mPagePanel.revalidate();
					mPagePanel.repaint();
				}
			}
		});
		

		JButton bt8 = new JButton("로그아웃", new ImageIcon("./images/5.png"));
		MarketFont.getFont(bt8);
		mMenuPanel.add(bt8);

		bt8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int select = JOptionPane.showConfirmDialog(bt8, "로그아웃 하시겠습니까? ");

				if (select == 0) {
					setVisible(false); //현재화면 감추기
					new GuestWindow("온라인 서점", 0, 0, 1000, 750);
//					System.exit(1);
				}
			}
		});

		if(member.getMid().toUpperCase().equals("ADMIN1234")) {
			JButton bt9 = new JButton("관리자", new ImageIcon("./images/6.png"));
			MarketFont.getFont(bt9);
			mMenuPanel.add(bt9);
	
			bt9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AdminLoginDialog adminDialog;
					JFrame frame = new JFrame();
					adminDialog = new AdminLoginDialog(frame, "관리자 화면", memberDao);
					adminDialog.setVisible(true);
					if (adminDialog.isLogin) {
						mPagePanel.removeAll();
						mPagePanel.add(new AdminPage(mPagePanel, bookDao));
						mPagePanel.revalidate();
						mPagePanel.repaint();
					}
				}
			});
		}//IF문
	}
}


