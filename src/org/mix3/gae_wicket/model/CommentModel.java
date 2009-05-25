package org.mix3.gae_wicket.model;
 
import java.io.Serializable;

import org.mix3.gae_wicket.table.Comment;

@SuppressWarnings("serial")
public class CommentModel implements Serializable{
	private Integer id;
    private String name;
    private String contents;
    private Integer articleID;
    
    public CommentModel(){}
    public CommentModel(Integer id, String name, String contents, Integer articleID){
    	this.id = id;
        this.name = name;
        this.contents = contents;
        this.articleID = articleID;
    }
    public CommentModel(Comment c){
    	this.id = c.id;
    	this.name = c.name;
    	this.contents = c.contents;
    	this.articleID = c.articleID;
    }
    
    // setter/getter
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    public String getName() {
        return name;
    }
	public void setName(String name) {
        this.name = name;
    }
    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
	public Integer getArticleID() {
		return articleID;
	}
	public void setArticleID(Integer articleID) {
		this.articleID = articleID;
	}
}