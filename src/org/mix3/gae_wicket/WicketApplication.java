package org.mix3.gae_wicket;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.SharedResourceRequestTargetUrlCodingStrategy;
import org.apache.wicket.session.ISessionStore;
import org.mix3.gae_wicket.page.DBBoardPage;
 
public class WicketApplication extends WebApplication{
    public WicketApplication(){}
    
    public Class<? extends Page> getHomePage(){
        return DBBoardPage.class;
    }
	
	@Override
	protected ISessionStore newSessionStore() {
		return new HttpSessionStore(this);
	}
	
	@Override
	public String getConfigurationType() {
		return Application.DEPLOYMENT;
	}
	
    @Override
    protected void init() {
    	this.getResourceSettings().setResourcePollFrequency(null);
    	
    	addComponentInstantiationListener(new GuiceComponentInjector(this));
    	
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		
		mountBookmarkablePage("/db", DBBoardPage.class);
		
		ResourceReference favicon = new ResourceReference(DBBoardPage.class, "favicon.ico");
		mount(new SharedResourceRequestTargetUrlCodingStrategy("/favicon.ico", favicon.getSharedResourceKey()));
	}
}