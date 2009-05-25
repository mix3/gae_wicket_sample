package org.mix3.gae_wicket.page;
 
import java.sql.SQLException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.mix3.gae_wicket.model.ArticleModel;
import org.mix3.gae_wicket.model.CommentModel;
import org.mix3.gae_wicket.service.Service;

import com.google.inject.Inject;
 
public class DBBoardPage extends MyAbstractWebPage{
	@Inject
	private Service service;
	
    public DBBoardPage(){
    	add(new FeedbackPanel("feed"));
        add(new BoardForm("form"));
        ListView listView = null;
		try {
			listView = new ListView("articleList", service.getArticleList()){
				@Override
			    protected void populateItem(ListItem item) {
			        ArticleModel am = (ArticleModel)item.getModelObject();
			        item.add(new Label("title", new PropertyModel(am, "title")));
			        item.add(new MultiLineLabel("contents", new PropertyModel(am, "contents")));
			        ListView listView = null;
					try {
						listView = new ListView("commentList", service.getCommentList(am.getId())){
						    @Override
						    protected void populateItem(ListItem item) {
						        CommentModel cm = (CommentModel)item.getModelObject();
						        item.add(new Label("name", new PropertyModel(cm, "name")));
						        item.add(new Label("contents", new PropertyModel(cm, "contents")));
						    }
						};
					} catch (SQLException e) {
						error("SQLException");
					}
			        item.add(listView);
			        item.add(new CommentForm("form", am));
			    }
			};
		} catch (SQLException e) {
			error("SQLException");
		}
        add(listView);
    }
    
    private class BoardForm extends Form{
        private ArticleModel am = new ArticleModel();
        public BoardForm(String id) {
            super(id);
            add(new TextField("title", new PropertyModel(am, "title")));
            add(new TextArea("contents", new PropertyModel(am, "contents")));
            add(new Button("submit"){
                @Override
                public void onSubmit() {
                	try {
						service.createArticle(am);
					} catch (SQLException e) {
						error("SQLException");
					}
                    setResponsePage(DBBoardPage.class);
                }
            });
        }
    }
    private class CommentForm extends Form{
		private CommentModel cm = new CommentModel();
		
    	public CommentForm(String id, final ArticleModel articleModel) {
			super(id);
			cm.setArticleID(articleModel.getId());
            add(new TextField("name", new PropertyModel(cm, "name")));
            add(new TextField("contents", new PropertyModel(cm, "contents")));
            add(new Button("submit"){
                @Override
                public void onSubmit() {
                	try {
						service.createComment(cm);
					} catch (SQLException e) {
						error("SQLException");
					}
                    setResponsePage(DBBoardPage.class);
                }
            });
		}
    }
}