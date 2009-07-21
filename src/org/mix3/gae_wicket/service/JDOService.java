package org.mix3.gae_wicket.service;

import java.util.List;

import org.mix3.gae_wicket.jdo.ArticleStore;
import org.mix3.gae_wicket.jdo.CommentStore;

import com.google.inject.ImplementedBy;

@ImplementedBy(JDOServiceImpl.class)
public interface JDOService {
	// read
	public List<ArticleStore> getArticleList();
	public List<CommentStore> getCommentList(ArticleStore as);
	
	// create
	public void createArticle(ArticleStore as);
	public void createComment(CommentStore cs);
}