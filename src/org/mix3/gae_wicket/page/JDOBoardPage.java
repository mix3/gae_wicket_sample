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
import org.mix3.gae_wicket.jdo.ArticleStore;
import org.mix3.gae_wicket.jdo.CommentStore;

import com.google.appengine.api.datastore.Key;

public class JDOBoardPage extends MyAbstractWebPage{
	@SuppressWarnings("serial")
	public JDOBoardPage(PageParameters parameters){
    	super(parameters);
		
    	add(new FeedbackPanel("feed"));
        add(new BoardForm("form"));
        
		add(new PropertyListView<ArticleStore>("aList", service.getArticleList()){
			@Override
			protected void populateItem(ListItem<ArticleStore> item) {
				ArticleStore as = item.getModelObject();
		        item.add(new Label("title"));
		        item.add(new MultiLineLabel("contents"));
				item.add(new PropertyListView<CommentStore>("cList", service.getCommentList(as)){
				    @Override
				    protected void populateItem(ListItem<CommentStore> item) {
				        item.add(new Label("name"));
				        item.add(new Label("contents"));
				    }
				});
		        item.add(new CommentForm("form", as.getKey()));
			}
		});
	}
	
	@SuppressWarnings("serial")
	private class BoardForm extends Form<ArticleStore>{
        private ArticleStore as = new ArticleStore();
        public BoardForm(String id) {
            super(id);
            setDefaultModel(new CompoundPropertyModel<ArticleStore>(as));
            add(new TextField<String>("title"));
            add(new TextArea<String>("contents"));
        }
		@Override
		protected void onSubmit() {
			service.createArticle(as);
            setResponsePage(JDOBoardPage.class);
		}
    }
	
	@SuppressWarnings("serial")
	private class CommentForm extends Form<CommentStore>{
		private CommentStore cs = new CommentStore();
    	public CommentForm(String id, Key key) {
			super(id);
			setDefaultModel(new CompoundPropertyModel<CommentStore>(cs));
			cs.setParentKey(key);
            add(new TextField<String>("name"));
            add(new TextField<String>("contents"));
		}
		@Override
		protected void onSubmit() {
			service.createComment(cs);
            setResponsePage(JDOBoardPage.class);
		}
    }
}
