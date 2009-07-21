package org.mix3.gae_wicket.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ArticleEntity implements Serializable{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String title;
	
	@Persistent
	private String content;
	
	@Persistent
	private Date date;
	
//	@Persistent(defaultFetchGroup = "true")
//	private List<CommentStore> cList;
	
//	public ArticleEntity(ArticleEntity ae){
//		this(ae.getTitle(), ae.getContent());
//		setDate(new Date(System.currentTimeMillis()));
//	}
//	
//	public ArticleEntity(String title, String content){
//		setTitle(title);
//		setContent(content);
//		setDate(new Date(System.currentTimeMillis()));
//	}
	
	// getter/setter
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
//	public List<CommentStore> getCList() {
//		return cList;
//	}
//	public void setCList(List<CommentStore> list) {
//		cList = list;
//	}
}
