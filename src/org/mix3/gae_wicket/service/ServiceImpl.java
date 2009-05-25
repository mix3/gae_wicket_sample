package org.mix3.gae_wicket.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mix3.gae_wicket.WicketApplication;
import org.mix3.gae_wicket.model.ArticleModel;
import org.mix3.gae_wicket.model.CommentModel;
import org.mix3.gae_wicket.orm.DbManager;
import org.mix3.gae_wicket.table.Article;
import org.mix3.gae_wicket.table.Comment;

import com.google.inject.Singleton;

@Singleton
public class ServiceImpl implements Service{
	private DbManager dm;
	private String uri;
	private String username;
	private String password;
	
	public ServiceImpl() throws ClassNotFoundException, SQLException{
		Properties dbProperties = getDBProperties();
		uri = dbProperties.getProperty("db.uri");
		username = dbProperties.getProperty("db.username");
		password = dbProperties.getProperty("db.password");
		
		dm = new DbManager(uri, username, password);
	}
	
	private Properties getDBProperties() {
		Properties back = new Properties();
		InputStream is = WicketApplication.class.getResourceAsStream("/db.properties");
		if (is == null) {
			throw new RuntimeException("Unable to locate db.properties");
		}
		try {
			back.load(is);
			is.close();
		} catch (IOException e) {
			throw new                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              RuntimeException("Unable to load db.properties");
		}
		return back;
	}
	
	public void createArticle(ArticleModel am) throws SQLException {
		Article article = new Article();
		
		try{
			Article result = dm.from(article).orderByDesc(article.id).select().get(0);
			article.id = result.id + 1;
		}catch(IndexOutOfBoundsException e){
			article.id = 1;
		}
		article.title = am.getTitle();
		article.contents = am.getContents();
		
		dm.insert(article);
	}

	public void createComment(CommentModel cm) throws SQLException {
		Comment comment = new Comment();
		
		try{
			Comment result = dm.from(comment).where(comment.articleID).is(cm.getArticleID()).orderByDesc(comment.id).select().get(0);
			comment.id = result.id + 1;
		}catch(IndexOutOfBoundsException e){
			comment.id = 1;
		}
		
		comment.name = cm.getName();
		comment.contents = cm.getContents();
		comment.articleID = cm.getArticleID();
		
		dm.insert(comment);
	}
	
	public List<ArticleModel> getArticleList() throws SQLException {
		Article a = new Article();
		List<ArticleModel> result = new ArrayList<ArticleModel>();
		for(Article a_result : dm.from(a).orderByDesc(a.id).select()){
			result.add(new ArticleModel(a_result));
		}
		return result;
	}
	
	@Override
	public List<CommentModel> getCommentList(Integer id) throws SQLException {
		Comment c = new Comment();
		List<CommentModel> result = new ArrayList<CommentModel>();
		for(Comment c_result : dm.from(c).where(c.articleID).is(id).orderByDesc(c.id).select()){
			result.add(new CommentModel(c_result));
		}
		return result;
	}
}
