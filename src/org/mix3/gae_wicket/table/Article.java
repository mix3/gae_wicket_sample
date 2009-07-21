package org.mix3.gae_wicket.table;

import static org.h2.jaqu.Define.primaryKey;

import org.h2.jaqu.Table;

public class Article implements Table{
	public Integer id;
	public String title;
	public String contents;
	
	@Override
	public void define() {
		primaryKey(id);
	}
}
