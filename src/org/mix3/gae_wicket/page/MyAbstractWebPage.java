package org.mix3.gae_wicket.page;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;

public class MyAbstractWebPage extends WebPage{
	@SuppressWarnings("serial")
	public MyAbstractWebPage(){
		add(new HeaderContributor(new IHeaderContributor(){
			@Override
			public void renderHead(IHeaderResponse response) {
				response.renderString("<link type=\"image/x-icon\" rel=\"shortcut icon\" href=\"/favicon.ico\" />");
			}
		}));
	}
}
