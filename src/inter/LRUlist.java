package inter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.*;

public class LRUlist {
	private static final float   hashTableLoadFactor = 0.75f;
	private final int maxSize=5;
	private LinkedHashMap LRUcache;
	public LRUlist(){
		 LRUcache = new LinkedHashMap(maxSize, hashTableLoadFactor, true) {
			 @Override
			 protected boolean removeEldestEntry (Entry eldest){
				 return size()>maxSize;
				 }
			 };

}
	public void pushPage(Object target){
		this.LRUcache.put(target, null);
	}
	public ArrayList<Entry> getAll(){
		return new ArrayList<>(LRUcache.entrySet());
	}
	public void printCache(){
		 for (Entry e : this.getAll())  
		      System.out.println (e.getKey() ); }  
	}

