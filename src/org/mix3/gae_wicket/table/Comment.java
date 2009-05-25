package org.mix3.gae_wicket.table;

import org.h2.jaqu.Table;
import static org.h2.jaqu.Define.*;

public class Comment implements Table{
	public Integer id;
	public String name;
	public String contents;
	public Integer articleID;
	
	@Override
	public void define() {
		primaryKey(id, articleID);
	}
}
