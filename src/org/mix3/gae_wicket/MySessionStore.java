package org.mix3.gae_wicket;



/*
public class MySessionStore extends HttpSessionStore{
	private CacheFactory cacheFactory;
	private Cache cache;
	
	public MySessionStore(Application application){
		super(application);
		Map<Integer, Integer> props = new HashMap<Integer, Integer>();
		// cahceのexpire設定
		props.put(GCacheFactory.EXPIRATION_DELTA, 3600 * 3);
		try {
			cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(props);
		} catch (CacheException e) {
			e.printStackTrace();
		}
		
		cache.put("key", "value");
		System.out.println((String) cache.get("key"));
	}
}
*/
