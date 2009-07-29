package org.mix3.gae_wicket.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.mix3.gae_wicket.entity.ArticleEntity;
import org.mix3.gae_wicket.entity.CommentEntity;
import org.mix3.gae_wicket.jdo.service.PersistenceService;

import com.google.appengine.api.datastore.Key;

public class JDOBoardPage extends MyAbstractWebPage{
	@SuppressWarnings("serial")
	public JDOBoardPage(PageParameters parameters){
    	super(parameters);
		
    	add(new FeedbackPanel("feed"));
        add(new BoardForm("form"));
        
		add(new PropertyListView<ArticleEntity>("aList", PersistenceService.getArticleEntity()){
			@Override
			protected void populateItem(ListItem<ArticleEntity> item) {
				ArticleEntity as = item.getModelObject();
		        item.add(new Label("title"));
		        item.add(new MultiLineLabel("content"));
				item.add(new PropertyListView<CommentEntity>("cList", PersistenceService.getCommentEntity(as)){
				    @Override
				    protected void populateItem(ListItem<CommentEntity> item) {
				        item.add(new Label("name"));
				        item.add(new Label("content"));
				    }
				});
		        item.add(new CommentForm("form", as.getKey()));
			}
		});
	}
	
	@SuppressWarnings("serial")
	private class BoardForm extends Form<ArticleEntity>{
        private ArticleEntity as = new ArticleEntity();
        public BoardForm(String id) {
            super(id);
            setDefaultModel(new CompoundPropertyModel<ArticleEntity>(as));
            add(new TextField<String>("title"));
            add(new TextArea<String>("content"));
        }
		@Override
		protected void onSubmit() {
			PersistenceService.createArticle(as);
            setResponsePage(JDOBoardPage.class);
		}
    }
	
	@SuppressWarnings("serial")
	private class CommentForm extends Form<CommentEntity>{
		private CommentEntity cs = new CommentEntity();
    	public CommentForm(String id, Key key) {
			super(id);
			setDefaultModel(new CompoundPropertyModel<CommentEntity>(cs));
			cs.setParentKey(key);
            add(new TextField<String>("name"));
            add(new TextField<String>("content"));
		}
		@Override
		protected void onSubmit() {
			PersistenceService.createComment(cs);
            setResponsePage(JDOBoardPage.class);
		}
    }
}
