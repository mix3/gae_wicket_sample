package org.mix3.gae_wicket.logic;

import java.util.Date;

import org.mix3.gae_wicket.entity.ArticleEntity;

public class ArticleEntityLogic extends AbstractLogic<ArticleEntity>{
	public static ArticleEntity create(ArticleEntity entity){
		entity.setDate(new Date(System.currentTimeMillis()));
		return entity;
	}
//	public static ArticleEntity create(ArticleEntity ae){
//		ArticleEntity entity = new ArticleEntity(ae);
//		return entity;
//	}
//	
//	public static ArticleEntity create(String title, String content){
//		ArticleEntity entity = new ArticleEntity(title, content);
//		return entity;
//	}
}
