package org.mix3.gae_wicket.service;

import java.sql.SQLException;
import java.util.List;

import org.mix3.gae_wicket.model.ArticleModel;
import org.mix3.gae_wicket.model.CommentModel;

import com.google.inject.ImplementedBy;

@ImplementedBy(ServiceImpl.class)
public interface Service{
	// read
	public List<ArticleModel> getArticleList() throws SQLException;
	public List<CommentModel> getCommentList(Integer id) throws SQLException;
	
	// create
	public void createArticle(ArticleModel a) throws SQLException;
	public void createComment(CommentModel c) throws SQLException;
}
