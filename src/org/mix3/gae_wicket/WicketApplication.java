package org.mix3.gae_wicket;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.request.target.coding.SharedResourceRequestTargetUrlCodingStrategy;
import org.apache.wicket.session.ISessionStore;
import org.mix3.gae_wicket.page.DBBoardPage;
import org.mix3.gae_wicket.page.HomePage;
import org.mix3.gae_wicket.page.JDOBoardPage;
 
public class WicketApplication extends WebApplication{
	private boolean isLocalMode = true;
	
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
		isLocalMode = super.getServletContext().getServerInfo().startsWith("Google App Engine Development");
		return isLocalMode ? Application.DEVELOPMENT : Application.DEPLOYMENT;
	}
    
    @Override
    protected void init() {
    	super.init();
    	if(isLocalMode){
    		getResourceSettings().setResourceWatcher(
    				new AppEngineModificationWatcher());
    	}
    	
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		
		mountBookmarkablePage("/home", HomePage.class);
		mountBookmarkablePage("/db", DBBoardPage.class);
		mountBookmarkablePage("/jdo", JDOBoardPage.class);
		
		ResourceReference favicon = new ResourceReference(WicketApplication.class, "favicon.ico");
		mount(new SharedResourceRequestTargetUrlCodingStrategy("/favicon.ico", favicon.getSharedResourceKey()));
	}

	@Override
	protected WebRequest newWebRequest(HttpServletRequest servletRequest) {
		if(isLocalMode){
			getResourceSettings().getResourceWatcher(true).start(
					getResourceSettings().getResourcePollFrequency());
		}
		return super.newWebRequest(servletRequest);
	}
}