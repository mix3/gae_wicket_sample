package org.mix3.gae_wicket.orm;

import java.sql.SQLException;
import java.util.List;

import org.h2.jaqu.Db;
import org.h2.jaqu.Query;

public class DbManager {
	private DbPool dbPool;
	
	public DbManager(String uri, String user, String pass) throws ClassNotFoundException, SQLException{
		dbPool = DbPool.getInstance(uri, user, pass);
	}
	
	public <T extends Object> Query<T> from(T alias) {
		Db db = dbPool.getDb(5);
		Query<T> result = db.from(alias);
		dbPool.releaseDb(db);
		return result;
	}
	
	public <T> void insert(T t) {
		Db db = dbPool.getDb(5);
		db.insert(t);
		dbPool.releaseDb(db);
	}
	
	public <T> void insertAll(List<T> list) {
		Db db = dbPool.getDb(5);
		db.insertAll(list);
		dbPool.releaseDb(db);
	}
}
