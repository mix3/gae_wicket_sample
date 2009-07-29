package org.mix3.gae_wicket.page;
 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
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
import org.objetdirect.wickext.core.effects.EffectSpeed;
import org.objetdirect.wickext.core.effects.fading.FadeOut;
import org.objetdirect.wickext.core.events.MouseEvent;
import org.objetdirect.wickext.core.events.WickextAjaxEventBehavior;
import org.objetdirect.wickext.core.javascript.JsQuery;

import com.google.inject.Inject;
 
public class DBBoardPage extends MyAbstractWebPage{
	@Inject
	private Service service;
	private List<ArticleModel> artileList = new ArrayList<ArticleModel>();
	
	@SuppressWarnings({ "unchecked", "serial" })
	public DBBoardPage(PageParameters parameters){
    	super(parameters);
    	try {
			artileList = service.getArticleList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		WebMarkupContainer div1 = new WebMarkupContainer("firstDiv");
		add(div1);
		final WebMarkupContainer div2 = new WebMarkupContainer("secondDiv");
		div2.setOutputMarkupId(true);
		add(div2);
		
		div1.add(new WickextAjaxEventBehavior(MouseEvent.DBLCLICK){
			@Override
			protected void respond(AjaxRequestTarget target) {
				target.appendJavascript(new JsQuery(div2)
					.$()
					.chain(new FadeOut(EffectSpeed.SLOW))
					.render()
					.toString());
			}
		});
		
    	add(new FeedbackPanel("feed"));
        add(new BoardForm("form"));
		add(new ListView("articleList", artileList){
			@Override
		    protected void populateItem(ListItem item) {
		        ArticleModel am = (ArticleModel)item.getModelObject();
		        item.add(new Label("title", new PropertyModel(am, "title")));
		        item.add(new MultiLineLabel("contents", new PropertyModel(am, "contents")));
				item.add(new ListView("commentList", new PropertyModel(am, "cList")){
				    @Override
				    protected void populateItem(ListItem item) {
				        CommentModel cm = (CommentModel)item.getModelObject();
				        item.add(new Label("name", new PropertyModel(cm, "name")));
				        item.add(new Label("contents", new PropertyModel(cm, "contents")));
				    }
				});
		        item.add(new CommentForm("form", am.getId()));
		    }
		});
    }
    
	@SuppressWarnings({ "unchecked", "serial" })
	private class BoardForm extends Form{
        private ArticleModel am = new ArticleModel();
        public BoardForm(String id) {
            super(id);
            add(new TextField("title", new PropertyModel(am, "title")));
            add(new TextArea("contents", new PropertyModel(am, "contents")));
        }
		@Override
		protected void onSubmit() {
        	try {
				service.createArticle(am);
			} catch (SQLException e) {
				error("SQLException");
			}
			//setRedirect(true);
            setResponsePage(DBBoardPage.class);
		}
    }
    @SuppressWarnings({ "serial", "unchecked" })
	private class CommentForm extends Form{
		private CommentModel cm = new CommentModel();
    	public CommentForm(String id, int articleID) {
			super(id);
			cm.setArticleID(articleID);
            add(new TextField("name", new PropertyModel(cm, "name")));
            add(new TextField("contents", new PropertyModel(cm, "contents")));
		}
		@Override
		protected void onSubmit() {
        	try {
				service.createComment(cm);
			} catch (SQLException e) {
				error("SQLException");
			}
			//setRedirect(true);
            setResponsePage(DBBoardPage.class);
		}
    }
}