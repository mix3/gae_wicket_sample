package org.mix3.gae_wicket.service;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.mix3.gae_wicket.PMF;
import org.mix3.gae_wicket.entity.ArticleEntity;
import org.mix3.gae_wicket.entity.CommentEntity;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.inject.Singleton;

@Singleton
public class JDOServiceImpl implements JDOService{
	private PersistenceManager pm;
	
	public JDOServiceImpl(){
		pm = PMF.get().getPersistenceManager();
//		pm = JDOHelper
//				.getPersistenceManagerFactory("transactions-optional")
//				.getPersistenceManager();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleEntity> getArticleList() {
		Query q = pm.newQuery(ArticleEntity.class);
		q.setOrdering("date desc");
		return (List<ArticleEntity>) q.execute();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommentEntity> getCommentList(ArticleEntity as) {
		Query q = pm.newQuery(CommentEntity.class);
		q.setFilter("parentKey == pParentKey");
		q.declareParameters("java.lang.String pParentKey");
		q.setOrdering("date desc");
		return (List<CommentEntity>) q.execute(KeyFactory.keyToString(as.getKey()));
	}
	
	@Override
	public void createArticle(ArticleEntity as) {
		as.setDate(new Date(System.currentTimeMillis()));
		pm.makePersistent(as);
	}
	
	@Override
	public void createComment(CommentEntity cs) {
		cs.setDate(new Date(System.currentTimeMillis()));
		pm.makePersistent(cs);
	}
}