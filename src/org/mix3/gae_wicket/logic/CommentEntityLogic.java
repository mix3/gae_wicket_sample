package org.mix3.gae_wicket.logic;

import java.util.Date;

import org.mix3.gae_wicket.entity.CommentEntity;

public class CommentEntityLogic extends AbstractLogic<CommentEntity>{
	public static CommentEntity create(CommentEntity entity){
		entity.setDate(new Date(System.currentTimeMillis()));
		return entity;
	}
//	public static CommentEntity create(CommentEntity ce){
//		CommentEntity entity = new CommentEntity(ce);
//		return entity;
//	}
//	
//	public static CommentEntity create(String name, String content){
//		CommentEntity entity = new CommentEntity(name, content);
//		return entity;
//	}
}
