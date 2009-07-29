package org.mix3.gae_wicket.logic;

import java.util.Date;

import org.mix3.gae_wicket.entity.CommentEntity;

public class CommentEntityLogic extends AbstractLogic<CommentEntity>{
	public static CommentEntity create(CommentEntity entity){
		entity.setDate(new Date(System.currentTimeMillis()));
		return entity;
	}
}
