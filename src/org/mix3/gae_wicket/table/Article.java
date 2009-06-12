package org.mix3.gae_wicket.table;

import java.util.ArrayList;
import java.util.List;

import org.h2.jaqu.Table;
import static org.h2.jaqu.Define.*;

public class Article implements Table{
	public Integer id;
	public String title;
	public String contents;
	
	@Override
	public void define() {
		primaryKey(id);
	}
}
