package org.h2.jaqu.orm;

import java.sql.SQLException;

import org.h2.jaqu.Db;

public abstract class Transaction<T> {
	private DbManager dbManager = null;
	protected Db dm = null;
	private T back = null;
	private SQLException toThrow = null;
	
	protected Transaction(DbManager dm){
		this.dbManager = dm;
	}
	
	public T execute() throws SQLException{
		try{
			dm = dbManager.getDbPool().getDb(5);
			dm.setAutoCommit(false);
			back = Transaction.this.run();
			dm.commit();
		}catch(SQLException e){
			if (dm != null) {
				dm.rollback();
			}
			toThrow = new SQLException("DB Error");
		}finally{
			if (dm == null) {
				return null;
			}
			dm.setAutoCommit(true);
			dbManager.getDbPool().releaseDb(dm);
		}
		if (toThrow != null) {
			throw toThrow;
		}
		return back;
	}
	
	protected abstract T run() throws SQLException;
}
