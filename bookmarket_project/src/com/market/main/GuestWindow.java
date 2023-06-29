package com.market.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.market.commons.MarketFont;
import com.market.dao.MemberDao;
import com.market.vo.MemberVo;


public class GuestWindow extends JFrame implements ActionListener {
	MemberDao memberDao;
	JTextField nameField; 
	JPasswordField phoneField;
	JButton enterButton, resetButton, exitButton;
	
	public GuestWindow(String title, int x, int y, int width, int height) {
		memberDao = new MemberDao();
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

		JPanel userPanel = new JPanel();
		userPanel.setBounds(0, 100, 1000, 256);

		ImageIcon imageIcon = new ImageIcon("./images/user.png");
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH));
		JLabel userLabel = new JLabel(imageIcon);
		userPanel.add(userLabel);
		add(userPanel);

		JPanel titlePanel = new JPanel();
		titlePanel.setBounds(0, 350, 1000, 50);
		add(titlePanel);

		JLabel titleLabel = new JLabel("-- 로그인 정보를 입력해주세요 --");
		MarketFont.getFont(titleLabel);
		titleLabel.setForeground(Color.BLUE);
		titlePanel.add(titleLabel);

		JPanel namePanel = new JPanel();
		namePanel.setBounds(0, 400, 1000, 50);
		add(namePanel);

		JLabel nameLabel = new JLabel("아 이 디 : ");
		MarketFont.getFont(nameLabel);
		namePanel.add(nameLabel);

		nameField = new JTextField(10);
		MarketFont.getFont(nameField);
		namePanel.add(nameField);

		JPanel phonePanel = new JPanel();
		phonePanel.setBounds(0, 450, 1000, 50);
		add(phonePanel);

		JLabel phoneLabel = new JLabel("패스워드: ");
		MarketFont.getFont(phoneLabel);
		phonePanel.add(phoneLabel);

		phoneField = new JPasswordField(10);
		MarketFont.getFont(phoneField);
		phonePanel.add(phoneField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 500, 1000, 100);
		add(buttonPanel);

		
		JLabel buttonLabel2 = new JLabel(" 다시쓰기 ");
		MarketFont.getFont(buttonLabel2);
		resetButton = new JButton();
		resetButton.add(buttonLabel2);
		buttonPanel.add(resetButton);
		
		JLabel buttonLabel = new JLabel(" 로그인 ", new ImageIcon("images/shop.png"), JLabel.LEFT);
		MarketFont.getFont(buttonLabel);
		enterButton = new JButton();
		enterButton.add(buttonLabel);
		buttonPanel.add(enterButton);
		
		JLabel buttonLabel3 = new JLabel(" 쇼핑 종료 ");
		MarketFont.getFont(buttonLabel3);
		exitButton = new JButton();
		exitButton.add(buttonLabel3);
		buttonPanel.add(exitButton);
		
		
		phoneField.addActionListener(this);
		enterButton.addActionListener(this);
		resetButton.addActionListener(this);
		exitButton.addActionListener(this);

//		enterButton.addActionListener( e -> {	//enterButton : 쇼핑하기
//
//				JLabel message = new JLabel("사용자 정보 에러");
//				MarketFont.getFont(message);
//
//				if (nameField.getText().isEmpty()) {
//					JOptionPane.showMessageDialog(null, "이름을 입력해주세요");
//					nameField.requestFocus();
//				}else if(phoneField.getText().isEmpty()) {
//					JOptionPane.showMessageDialog(null, "연락처를 입력해주세요");
//					phoneField.requestFocus();
//				}else {
//					//UserInIt.init(nameField.getText(), Integer.parseInt(phoneField.getText())); //init 기울임꼴 -> static으로 만들어졌다
//					//사용자 정보저장: UserVo
////					UserVo user = new UserVo();	//UserVo로는 주문불가라서 주석처리	
//					UserVo user = new OrderUserVo();	//자식의 모습(OrderUserVo)으로 부모 생성 (OrderUserVo는 UserVo의 자식임 extends되어있는거 확인함)
//					user.setName(nameField.getText());
//					user.setPhoneNumber(phoneField.getText());
//					//dispose(); 
//					setVisible(false);
//					new MainWindow("온라인 서점", 0, 0, 1000, 750, user);
//			
//				}
//			});
	}

	public void actionPerformed(ActionEvent e) {
		
			Object obj = e.getSource();
			
			if(obj == phoneField || obj == enterButton) {
				//로그인 처리
				JLabel message = new JLabel("사용자 정보 에러");
				MarketFont.getFont(message);

				if (nameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");
					nameField.requestFocus();
				}else if(phoneField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "패스워드를 입력해주세요");
					phoneField.requestFocus();
				}else {
					String mid = nameField.getText().trim();
					String pass = phoneField.getText().trim();
					if(memberDao.select(mid, pass) == 1) {
//						System.out.println("로그인 성공");
						MemberVo member = new MemberVo();	//자식의 모습(OrderUserVo)으로 부모 생성 (OrderUserVo는 UserVo의 자식임 extends되어있는거 확인함)
						member.setMid(nameField.getText().trim());
						member.setPass(phoneField.getText().trim());
						setVisible(false);
						
						Map param = new HashMap(); //너무 많아서 하나에 담으려고
						param.put("title", "온라인 서점");
						param.put("x", 0);
						param.put("y", 0);
						param.put("width", 1000);
						param.put("height",750);
						param.put("member", member);
						param.put("memberDao", memberDao);
						new MainWindow(param);
						
					}else {
						//로그인 실패
						String msg = "아이디 또는 패스워드가 다릅니다. \n\t다시 입력해주세요.";
						JOptionPane.showMessageDialog(null, msg);
						nameField.setText("");
						phoneField.setText("");
						nameField.requestFocus();
					}
			
				}
			}else if(obj == resetButton) {
				nameField.setText("");
				phoneField.setText("");
				nameField.requestFocus();
				
			}else if(obj == exitButton) {
				int select = JOptionPane.showConfirmDialog(null, "정말로 종료하시겠습니까?");
				if(select == 0) {
					System.exit(0);
				}
			}
		
		
			
		};
	
	
	
	
	
	
	
	
}