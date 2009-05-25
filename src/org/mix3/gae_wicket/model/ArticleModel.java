package org.mix3.gae_wicket.model;
 
import java.io.Serializable;

import org.mix3.gae_wicket.table.Article;
 
@SuppressWarnings("serial")
public class ArticleModel implements Serializable{
	private int id;
    private String title;
    private String contents;
    
    public ArticleModel(){}
    public ArticleModel(Integer id, String title, String contents){
    	this.id = id;
        this.title = title;
        this.contents = contents;
    }
    public ArticleModel(Article a){
        this.id = a.id;
    	this.title = a.title;
    	this.contents = a.contents;
    }
    
    // setter/getter
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContents() {
        return contents;
    }
	public void setContents(String contents) {
        this.contents = contents;
    }
}