package org.mix3.gae_wicket.service;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.mix3.gae_wicket.jdo.ArticleStore;
import org.mix3.gae_wicket.jdo.CommentStore;
import org.mix3.gae_wicket.jdo.PMF;

import com.google.appengine.api.datastore.KeyFactory;

public class JDOServiceImpl implements JDOService{
	private PersistenceManager pm;
	
	public JDOServiceImpl(){
		pm = PMF.get().getPersistenceManager();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleStore> getArticleList() {
		Query q = pm.newQuery(ArticleStore.class);
		q.setOrdering("key desc");
		return (List<ArticleStore>) q.execute();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommentStore> getCommentList(ArticleStore as) {
		Query q = pm.newQuery(CommentStore.class);
		q.setFilter("parentKey == pParentKey");
		q.declareParameters("java.lang.String pParentKey");
		q.setOrdering("key desc");
		return (List<CommentStore>) q.execute(KeyFactory.keyToString(as.getKey()));
	}
	
	@Override
	public void createArticle(ArticleStore as) {
		pm.makePersistent(as);
	}
	
	@Override
	public void createComment(CommentStore cs) {
		pm.makePersistent(cs);
	}
}