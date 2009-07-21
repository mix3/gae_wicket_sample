package org.mix3.gae_wicket.service;

import java.util.List;

import org.mix3.gae_wicket.entity.ArticleEntity;
import org.mix3.gae_wicket.entity.CommentEntity;

import com.google.inject.ImplementedBy;

@ImplementedBy(JDOServiceImpl.class)
public interface JDOService {
	// read
	public List<ArticleEntity> getArticleList();
	public List<CommentEntity> getCommentList(ArticleEntity as);
	
	// create
	public void createArticle(ArticleEntity as);
	public void createComment(CommentEntity cs);
}