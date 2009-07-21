package org.mix3.gae_wicket.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CommentEntity implements Serializable{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.parent-pk", value = "true")
	private Key parentKey;
	
	@Persistent
	private String name;
	
	@Persistent
	private String content;
	
	@Persistent
	private Date date;
	
//	public CommentEntity(CommentEntity ce){
//		this(ce.getName(), ce.getContent());
//		setDate(new Date(System.currentTimeMillis()));
//	}
//	public CommentEntity(String name, String content){
//		setName(name);
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
	public Key getParentKey() {
		return parentKey;
	}
	public void setParentKey(Key parentKey) {
		this.parentKey = parentKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
