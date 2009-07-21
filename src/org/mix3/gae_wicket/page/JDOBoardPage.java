package org.mix3.gae_wicket.page;

import java.util.List;

import javax.jdo.Query;

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
import org.mix3.gae_wicket.logic.ArticleEntityLogic;
import org.mix3.gae_wicket.logic.CommentEntityLogic;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class JDOBoardPage extends MyAbstractWebPage{
	@SuppressWarnings("serial")
	public JDOBoardPage(PageParameters parameters){
    	super(parameters);
		
    	add(new FeedbackPanel("feed"));
        add(new BoardForm("form"));
        
		add(new PropertyListView<ArticleEntity>("aList", getArticleEntity()){
			@Override
			protected void populateItem(ListItem<ArticleEntity> item) {
				ArticleEntity as = item.getModelObject();
		        item.add(new Label("title"));
		        item.add(new MultiLineLabel("content"));
				item.add(new PropertyListView<CommentEntity>("cList", getCommentEntity(as)){
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
//			service.createArticle(as);
			ArticleEntity entity = ArticleEntityLogic.create(as);
			new ArticleEntityLogic().save(entity);
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
//			service.createComment(cs);
			CommentEntity entity = CommentEntityLogic.create(cs);
			new CommentEntityLogic().save(entity);
            setResponsePage(JDOBoardPage.class);
		}
    }
	
	private List<ArticleEntity> getArticleEntity(){
		ArticleEntityLogic logic = new ArticleEntityLogic();
		Query query = logic.newQuery();
		query.setOrdering("date desc");
		return logic.list(query);
	}
	
	private List<CommentEntity> getCommentEntity(ArticleEntity articleEntity){
		CommentEntityLogic logic = new CommentEntityLogic();
		Query query = logic.newQuery();
		query.setFilter("parentKey == pParentKey");
		query.declareParameters("java.lang.String pParentKey");
		query.setOrdering("date desc");
		return logic.listWithParam(query, KeyFactory.keyToString(articleEntity.getKey()));
	}
}
