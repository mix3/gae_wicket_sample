package org.mix3.gae_wicket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.wicket.util.listener.ChangeListenerSet;
import org.apache.wicket.util.listener.IChangeListener;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.time.Time;
import org.apache.wicket.util.watch.IModifiable;
import org.apache.wicket.util.watch.IModificationWatcher;

public class AppEngineModificationWatcher implements IModificationWatcher{
	private static class Entry{
		Time lastModifiedTime;
		ChangeListenerSet listeners = new ChangeListenerSet();
		IModifiable modifiable;
	}
	
	private final Map<IModifiable, Entry> modifiableToEntry = new ConcurrentHashMap<IModifiable, Entry>();
	
	@Override
	public boolean add(IModifiable modifiable, IChangeListener listener) {
		Entry entry = modifiableToEntry.get(modifiable);
		if(entry == null){
			Time lastModifiedTime = modifiable.lastModifiedTime();
			if(lastModifiedTime != null){
				Entry newEntry = new Entry();
				
				newEntry.modifiable = modifiable;
				newEntry.lastModifiedTime = lastModifiedTime;
				newEntry.listeners.add(listener);
				
				modifiableToEntry.put(modifiable, newEntry);
			}
			return true;
		}else{
			return entry.listeners.add(listener);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public Set<IModifiable> getEntries() {
		return modifiableToEntry.keySet();
	}

	@Override
	public IModifiable remove(IModifiable modifiable) {
		Entry entry = modifiableToEntry.remove(modifiable);
		if(entry != null){
			return entry.modifiable;
		}
		return null;
	}

	@Override
	public void start(Duration pullFrequency) {
		Iterator<Entry> iterator = new ArrayList<Entry>(modifiableToEntry.values()).iterator();
		while(iterator.hasNext()){
			Entry entry = iterator.next();
			
			Time modifiableLastModified = entry.modifiable.lastModifiedTime();
			if((modifiableLastModified != null) && modifiableLastModified.after(entry.lastModifiedTime)){
				entry.listeners.notifyListeners();
				entry.lastModifiedTime = modifiableLastModified;
			}
		}
	}

}
