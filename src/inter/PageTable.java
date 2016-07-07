package inter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import gui.Window;
import tool.ConfLoader;
import tool.ConfLoader.ConfType;

public class PageTable extends Page {
	private Hashtable<Key, Integer> ptes = new Hashtable<>();

	// search the pagetable
	public int searchPPN(int pid, int LA) {
		Integer result = -1;
		if (Kernal.PtSizeOptimizePolicy == Kernal.Inverted) {
			InvertPTEKey Ikey = new InvertPTEKey(pid, LA);
			/*Set<Key> keys=ptes.keySet();
			for(Key key : keys){
				System.err.println(key.hashCode());
			}
			System.err.println(Ikey.hashCode());*/
			result = ptes.get(Ikey);
			if (result == null) {
				result = -1;
			}
		} else if (Kernal.PtSizeOptimizePolicy == Kernal.Traditional) {
			TrditionalPTEKey Tkey = new TrditionalPTEKey(LA);
			result = ptes.get(Tkey);
			if (result == null) {
				result = -1;
			}
		}
		return result;
	}

	// when the pagefault happened get the new info from PLB and refresh the
	// ptes
	public boolean DrefreshPagetable(int pid, int LA, int PA) {
		// D presents Dirty
		if (Kernal.PtSizeOptimizePolicy == Kernal.Inverted) {
			InvertPTEKey Ikey = new InvertPTEKey(pid, LA);
			if(ptes.containsKey(Ikey)){
				ptes.replace(Ikey, PA);
				if(Window.getInstance()!=null)
					Window.getInstance().pagetableUpdate(2, Ikey.toString()+"&"+PA);
			}
			else{
				ptes.put(Ikey, PA);
				if(Window.getInstance()!=null)
					Window.getInstance().pageTableadd(2,Ikey.toString()+"&"+PA);
			}
		} else if (Kernal.PtSizeOptimizePolicy == Kernal.Traditional) {
			TrditionalPTEKey Tkey = new TrditionalPTEKey(LA);
			if(ptes.containsKey(Tkey)){
				ptes.replace(Tkey, PA);
				if(Window.getInstance()!=null)
					Window.getInstance().pagetableUpdate(1, Tkey.toString()+"&"+PA);
			}
			else{
				ptes.put(Tkey, PA);
				if(Window.getInstance()!=null)
					Window.getInstance().pageTableadd(1,Tkey.toString()+"&"+PA);
			}
		}
		return true;
	}

	// when the mamory has changed and would will affect other process`s
	// pagetable , this mathod will be remoted
	public boolean IrefreshPagetable(int PA) {
		if (!ptes.containsValue(new Integer(PA))) {
			System.err.println("pagetable pointer to error page");
			return false;
		}
		Set<Key> keys = ptes.keySet();
		for (Key key : keys) {
			Integer pa = ptes.get(key);
			if (pa.equals(PA)) {
				ptes.replace(key, null);
				if(Window.getInstance()!=null)
					Window.getInstance().pagetableUpdate(1, key.toString()+"&"+"null");
			}
		}
		return true;

	}

	public void freePage(int pid) throws InterruptedException {
		Set<Key> keys = ptes.keySet();
		if (Kernal.PtSizeOptimizePolicy == Kernal.Inverted) {
			for(Key key : keys){
				InvertPTEKey iKey=(InvertPTEKey)key;
				if(iKey.getPid()==pid){
					Integer pa = ptes.get(key);
					Page page = Memory.getInstance().getPage(pa);
					page.freeBit = true;
				}
			}
		} else {
			for (Key key : keys) {
				Integer pa = ptes.get(key);
				Page page = Memory.getInstance().getPage(pa);
				page.freeBit = true;
			}
		}
	}
	public ArrayList<String> getAllPteString(){
		ArrayList<String> ptelist=new ArrayList<>();
		Set<Key> keys=ptes.keySet();
		for(Key key : keys){
			Integer value=ptes.get(key);
			ptelist.add(key.toString()+"&"+value);
		}
		return ptelist;
	}
}
