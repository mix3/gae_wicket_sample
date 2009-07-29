package org.mix3.gae_wicket.jdo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Query;

import org.mix3.gae_wicket.PMF;

public class JdoQuery<T> {
	private Class<T> entityClass;
	private String filter;
	private String ordering;
	private int rangeFrom = -1;
	private int rangeTo = -1;
	
	public JdoQuery(Class<T> entityClass){
		this.entityClass = entityClass;
	}
	
	public JdoQuery<T> filter(String filter){
		this.filter = filter;
		return this;
	}
	
	public JdoQuery<T> ordering(String ordering){
		this.ordering = ordering;
		return this;
	}
	
	public JdoQuery<T> range(int from, int to){
		this.rangeFrom = from;
		this.rangeTo = to;
		return this;
	}
	
	public T getSingleResult(){
		return getSingleResult((Object[]) null);
	}
	
	public T getSingleResult(Object... params){
		List<T> result = getResultList(params);
		if(result.isEmpty()){
			return null;
		} else {
			return result.get(0);
		}
	}
	
	public List<T> getResultList(){
		return getResultList((Object[]) null);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getResultList(Object... params){
		Query query = null;
		
		try {
			query = createQuery(params);
			
			if(params != null){
				return (List<T>) query.executeWithArray(params);
			} else {
				return (List<T>) query.execute();
			}
		} finally {
			if(query != null){
				query.closeAll();
			}
		}
	}
	
	public int getCount(){
		return getCount((Object[]) null);
	}
	
	public int getCount(Object... params){
		Query query = null;
		
		try {
			query = createQuery(params);
			query.setResult("count(this)");
			
			if(params != null){
				return (Integer) query.executeWithArray(params);
			} else {
				return (Integer) query.execute();
			}
			
		} finally {
			if(query != null){
				query.closeAll();
			}
		}
	}
	
	private Query createQuery(Object[] params){
		Query query = PMF.get().getPersistenceManager().newQuery(entityClass);
		
		List<String> declareParams = new ArrayList<String>();
		if(filter != null){
			query.setFilter(processParameters(filter, declareParams, params));
		}
		if(ordering != null){
			query.setOrdering(processParameters(ordering, declareParams, params));
		}
		if(rangeFrom != -1 && rangeTo != -1){
			query.setRange(rangeFrom, rangeTo);
		}
		
		StringBuilder sb = new StringBuilder();
		for(String declare: declareParams){
			if(sb.length() != 0){
				sb.append(", ");
			}
			sb.append(declare);
		}
		query.declareParameters(sb.toString());
		
		return query;
	}
	
	private static String processParameters(String str, List<String> declare, Object[] params){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<str.length();i++){
			char c = str.charAt(i);
			
			if(c == '?'){
				int index = declare.size();
				sb.append("param" + index);
				declare.add(params[index].getClass().getName() + " param" + index);
			} else {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
}
