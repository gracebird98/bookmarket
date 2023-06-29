package com.market.dao;

public class DBTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBConn db = new DBConn();
		db.getStatement();
		db.getPreparedStatement("select * from emp");
		db.close();
	}

}
