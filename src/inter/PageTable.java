package inter;

import java.util.HashMap;
import java.util.Properties;

import tool.ConfLoader;
import tool.ConfLoader.ConfType;

public class PageTable {
// load configuration to choose the optimized policy
	//private int optimizedPolicy=-1;
	//private type=Kernal.;
	private int type=-1;
	private static PageTable invertPagetable;
	private HashMap<Key, Integer> ptes;
	public PageTable(){
		// load configuration to choose the optimized policy
		//Properties properties=ConfLoader.getPropertiesByConfType(ConfType.PageTable);
		//optimizedPolicy
		if(this.type==-1){
			if(invertPagetable==null){
				invertPagetable=new PageTable();
			}
			
		}
		
	}
	
}
