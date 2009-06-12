package org.h2.jaqu.orm;

import java.util.List;

import org.h2.jaqu.Db;
import org.h2.jaqu.Query;
import org.h2.jaqu.Table;

public class DbManager {
	private DbPool dbPool;
	
	public DbManager(String uri, String user, String pass){
		dbPool = DbPool.getInstance(uri, user, pass);
	}
	
	public DbPool getDbPool(){
		return dbPool;
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
	
	public <T> void merge(T t) {
		Db db = dbPool.getDb(5);
		db.merge(t);
		dbPool.releaseDb(db);
	}
	
	public void migrate(Class<? extends Table>... alias){
		Db db = dbPool.getDb(5);
		db.migrate(alias);
		dbPool.releaseDb(db);
	}
}
