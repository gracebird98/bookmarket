package com.market.dao;

import java.util.ArrayList;

import com.market.vo.CartVo;
import com.market.vo.OrderVo;

public class CartDao extends DBConn{
	/**
	 *	장바구니 추가 
	 */
	public int insert(CartVo cartVo){
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append(" INSERT INTO BOOKMARKET_CART VALUES('C_'||LTRIM(TO_CHAR(SEQU_BOOKMARKET_CART_CID.NEXTVAL,'0000')),");
		sb.append(" sysdate, 1, ?, ? )");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, cartVo.getIsbn().toUpperCase());	//1번 ? 에 들어갈거
			pstmt.setString(2, cartVo.getMid().toUpperCase());	//2번 ? 에 들어갈거
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	};
	
	/**
	 * 장바구니 중복 도서 체크
	 */
	public int insertCheck(CartVo cartVo){
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("SELECT count(*) FROM BOOKMARKET_CART WHERE ISBN = ? and mid = ?"); //count로 중복 수량 체크

		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, cartVo.getIsbn().toUpperCase());	//1번 ? 에 들어갈거
			pstmt.setString(2, cartVo.getMid().toUpperCase());	//2번 ? 에 들어갈거
			
			rs = pstmt.executeQuery();
			while(rs.next()) result = rs.getInt(1); //executeQuery는 while문 써줘야함!
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	};
	
	/**
	 * 장바구니 사이즈 체크
	 */
	public int getSize(String mid) {
		int size = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("select COUNT(*) FROM BOOKMARKET_CART WHERE MID = ?");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid);
			
			rs = pstmt.executeQuery();
			while(rs.next()) size = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return size;
	}
	
	/**
	 * 로그인한 회원의 장바구니 리스트 출력하기
	 */
	public ArrayList<CartVo> select(String mid){
		ArrayList<CartVo> list = new ArrayList<CartVo>();
		StringBuffer sb = new StringBuffer(100);
		sb.append(" SELECT ROWNUM RNO, ISBN, TITLE, PRICE, QTY, TOTAL_PRICE, SPRICE, STOTAL_PRICE");
		sb.append(" , cid ");
		sb.append(" FROM(SELECT B.ISBN, B.TITLE, B.PRICE, C.QTY, B.PRICE*C.QTY TOTAL_PRICE,");
		sb.append(" TO_CHAR(B.PRICE,'L999,999') SPRICE,");
		sb.append(" TO_CHAR(B.PRICE*C.QTY, 'L999,999') STOTAL_PRICE, cid");
		sb.append(" FROM BOOKMARKET_BOOK B, BOOKMARKET_CART C, BOOKMARKET_MEMBER M");
		sb.append(" WHERE B.ISBN = C.ISBN AND C.MID = M.MID");
		sb.append(" AND M.MID = ? )");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CartVo cartVo = new CartVo();
				cartVo.setRno(rs.getInt(1));//중간에 수정할때 번호 하나씩 미뤄가며 하지말고 뒤에 8번으로 추가해도 된다.
				cartVo.setIsbn(rs.getString(2));
				cartVo.setTitle(rs.getString(3));
				cartVo.setPrice(rs.getInt(4));
				cartVo.setQty(rs.getInt(5));
				cartVo.setTotal_price(rs.getInt(6));
				cartVo.setSprice(rs.getString(7));
				cartVo.setStotal_price(rs.getString(8));
				cartVo.setCid(rs.getString(9));
				
				list.add(cartVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	
	/**
	 * 장바구니 항목 삭제하기
	 */
	public int delete(String cid){
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("DELETE FROM BOOKMARKET_CART WHERE CID = ? ");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, cid);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * 장바구니 항목 수정하기
	 */
	public int updateQty(String cid, String status) { //if문 구분위해 status 추가
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		if(status.equals("plus")) {//증가
			sb.append("update BOOKMARKET_CART SET QTY = QTY+1 WHERE CID = ?");
		}else {//감소
			sb.append("update BOOKMARKET_CART SET QTY = QTY-1 WHERE CID = ?");
		}
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, cid);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	
	/**
	 * 장바구니 비우기(로그인한 회원의 아이템 전체 삭제)
	 */
	public int deleteAll(String mid) {
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("DELETE FROM BOOKMARKET_CART WHERE MID = ? ");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	/**
	 * 주문테이블 데이터 생성 - 회원의 qty, isbn 리스트에 데이터 넣기
	 */
	public OrderVo getOrderVo(String mid) {
		OrderVo orderVo = new OrderVo();
		StringBuffer sb = new StringBuffer(50);
		sb.append("select QTY, ISBN FROM BOOKMARKET_CART WHERE MID = ? ORDER BY CDATE DESC");
		
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();

			rs.last(); //--> 커서를 가장 마지막으로 이동시키는 메소드 --> 에러남 커서 이동시키는 작업이 디폴트로 막혀있기때문! -> 풀어줘야함 -> DBConn가서 작업 -> 결과 : 커서위치 2
//			System.out.println("rowCount--> " + rs.getRow()); //커서 위치 확인작업: 로우카운트가 0 나옴. = 커서가 0에 위치하는게 디폴트임 -> 커서 위치를 가장 마지막으로 옮기고 싶음 -> pstmt생성될떄 rs.last사용해서 커서 위치 변경하기
			int[] qtyList = new int[rs.getRow()]; //사이즈는 실행하는 mid마다 달라서 사이즈 하나로 고정할수 없음 -> 뭘 줘야 할까? -> api -> getRow() : 커서 위치에 해당하는 로우 알려주는 메소드 찾음.
			String[] isbnList = new String[rs.getRow()];
			rs.beforeFirst(); //--> 커서를 1번 위인 0번으로 다시 이동
			
			int idx = 0;
			while(rs.next()) {
				//orderVo의 qtyList[], isbnList[] 데이터 저장작업 -> OrderVo 의 setqty 매개변수 타입 보기 
				qtyList[idx] = rs.getInt(1); 
				isbnList[idx] = rs.getString(2);
				idx++;
			}
			orderVo.setQtyList(qtyList);
			orderVo.setIsbnList(isbnList); //orderVo에 데이터 넣는 작업
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return orderVo;
	}
	
	
	
	
	
	
	
	
	
}
