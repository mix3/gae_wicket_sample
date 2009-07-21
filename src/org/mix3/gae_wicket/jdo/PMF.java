package org.mix3.gae_wicket.jdo;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class PMF {
	private static final PersistenceManagerFactory INSTANCE
		= JDOHelper.getPersistenceManagerFactory("transactions-optional");
	public static PersistenceManagerFactory get(){
		return INSTANCE;
	}
	private PMF() {
		throw new AssertionError();
	}
}
