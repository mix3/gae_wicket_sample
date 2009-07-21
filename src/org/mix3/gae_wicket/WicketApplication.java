package org.mix3.gae_wicket;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.SharedResourceRequestTargetUrlCodingStrategy;
import org.apache.wicket.session.ISessionStore;
import org.mix3.gae_wicket.page.DBBoardPage;
import org.mix3.gae_wicket.page.HomePage;
import org.mix3.gae_wicket.page.JDOBoardPage;
 
public class WicketApplication extends WebApplication{
	public WicketApplication(){
    	Logger.getLogger("org.apache.wicket").log(Level.INFO, "Application Start!");
    }
    
    public Class<? extends Page> getHomePage(){
        return HomePage.class;
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
    	super.init();
    	this.getResourceSettings().setResourcePollFrequency(null);
    	
    	addComponentInstantiationListener(new GuiceComponentInjector(this));
    	
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		
		mountBookmarkablePage("/home", HomePage.class);
		mountBookmarkablePage("/db", DBBoardPage.class);
		mountBookmarkablePage("/jdo", JDOBoardPage.class);
		
		ResourceReference favicon = new ResourceReference(WicketApplication.class, "favicon.ico");
		mount(new SharedResourceRequestTargetUrlCodingStrategy("/favicon.ico", favicon.getSharedResourceKey()));
	}
}