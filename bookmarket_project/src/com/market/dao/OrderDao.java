package com.market.dao;

import com.market.vo.OrderVo;

public class OrderDao extends DBConn{

	/**
	 * 데이터 추가 - Statement
	 */
	public int insert(OrderVo orderVo) {
		int result = 0;
		getStatement();//Prepared 안쓰는 이유 : for문 사용시 '?'에 들어갈 값이 항상 바뀌기 때문!
		
		try {
			for(int i = 0; i< orderVo.getQtyList().length; i++) {
				StringBuffer sb = new StringBuffer(50);
				
				sb.append("insert into bookmarket_order(oid,odate,qty,isbn,mid,name,phone,addr) ");
				sb.append(" values(");
				sb.append("'"+ orderVo.getOid() +"',");
				sb.append("'"+ orderVo.getOdate() +"',");
				sb.append(orderVo.getQtyList()[i] +",");
				sb.append("'"+ orderVo.getIsbnList()[i] +"',");
				sb.append("'"+ orderVo.getMid() +"',");
				sb.append("'"+ orderVo.getName() +"',");
				sb.append("'"+ orderVo.getPhone() +"',");
				sb.append("'"+ orderVo.getAddr() +"')");
				
//				System.out.println(sb.toString()); //확인작업
				result = stmt.executeUpdate(sb.toString()); //꼭 for문 안에 넣어야함! 한번 돌떄마다 결과 들어가야 하니까
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	/**
	 * 데이터 추가 - PreparedStatement
	 */
	public int insertPre(OrderVo orderVo) {
		int result = 0;
		StringBuffer sb = new StringBuffer(50);
		sb.append("insert into bookmarket_order(oid,odate,qty,isbn,mid,name,phone,addr) ");
		sb.append(" values(?,?,?,?,?,?,?,?)");
		
		try {
			getPreparedStatement(sb.toString());//for문에 넣지마!!!넣으면 마지막 결과만 가져옴

			for(int i=0; i<orderVo.getQtyList().length; i++) {
				//매핑
				pstmt.setString(1, orderVo.getOid());
				pstmt.setString(2, orderVo.getOdate());
				pstmt.setInt(3, orderVo.getQtyList()[i]);
				pstmt.setString(4, orderVo.getIsbnList()[i]);
				pstmt.setString(5, orderVo.getMid());
				pstmt.setString(6, orderVo.getName());
				pstmt.setString(7, orderVo.getPhone());
				pstmt.setString(8, orderVo.getAddr());
				
				pstmt.addBatch(); 
				pstmt.clearParameters();
			}
			//pstmt.executeUpdate(); -> 하나일때 사용
			result = pstmt.executeBatch().length;//-->prepared를 Statement로 만들어주는 작업  --> 그래서 그냥 처음부터 Statement쓰는게 좋음 근데 그냥 이거 써도됨   
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
