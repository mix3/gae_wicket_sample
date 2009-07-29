package org.mix3.gae_wicket.jdo.service;

import java.util.Date;
import java.util.List;

import org.mix3.gae_wicket.PMF;
import org.mix3.gae_wicket.entity.ArticleEntity;
import org.mix3.gae_wicket.entity.CommentEntity;
import org.mix3.gae_wicket.jdo.JdoQuery;

import com.google.appengine.api.datastore.KeyFactory;

public class PersistenceService {
	public static <T> JdoQuery<T> from(Class<T> entityClass){
		return new JdoQuery<T>(entityClass);
	}
	
	public static void createArticle(ArticleEntity entity){
		entity.setDate(new Date(System.currentTimeMillis()));
		PMF.get().getPersistenceManager().makePersistent(entity);
	}
	
	public static void createComment(CommentEntity entity){
		entity.setDate(new Date(System.currentTimeMillis()));
		PMF.get().getPersistenceManager().makePersistent(entity);
	}
	
	public static void delete(Object entity){
		PMF.get().getPersistenceManager().deletePersistent(entity);
	}
	
	public static List<ArticleEntity> getArticleEntity(){
		return from(ArticleEntity.class)
				.ordering("date desc")
				.getResultList();
	}
	public static List<CommentEntity> getCommentEntity(ArticleEntity articleEntity){
		return from(CommentEntity.class)
				.filter("parentKey == ?")
				.ordering("date desc")
				.getResultList(KeyFactory.keyToString(articleEntity.getKey()));
	}
}
