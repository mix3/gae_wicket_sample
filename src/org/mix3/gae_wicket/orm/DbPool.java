package org.mix3.gae_wicket.orm;

import java.sql.SQLException;
import java.util.Vector;

import org.h2.jaqu.Db;

public class DbPool {
	private static DbPool dbPool = null;
	private int maxConnection = 5;
	private Vector<Db> dbs = null;
	
	public static DbPool getInstance(String uri, String user, String pass) throws ClassNotFoundException, SQLException{
		if(DbPool.dbPool == null) {
			DbPool.dbPool = new DbPool(uri, user, pass);
		}
		return DbPool.dbPool;
	}
	
	private DbPool(String uri, String user, String pass) throws ClassNotFoundException, SQLException{
		Class.forName("org.h2.Driver");
		dbs = new Vector<Db>();
		for(int i = 0; i < maxConnection; i++) {
			dbs.add(Db.open(uri, user, pass));
		}
	}
	
	public synchronized Db getDb(int count){
		if(count < 1){
			return null;
		}
		if(dbs.size() > 0){
			Db db = (Db)dbs.get(0);
			dbs.remove(0);
			return db;
		}else{
			try {
				wait(100);
			} catch (InterruptedException e) {
			}
			return getDb(count - 1);
		}
	}
	
	public synchronized void releaseDb(Db db){
		dbs.add(db);
	}
}
