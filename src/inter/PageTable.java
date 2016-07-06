package inter;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import tool.ConfLoader;
import tool.ConfLoader.ConfType;

public class PageTable extends Page {
	private HashMap<Key, Integer> ptes = new HashMap<>();

	// search the pagetable
	public int searchPPN(int pid, int LA) {
		Integer result = -1;
		if (Kernal.PtSizeOptimizePolicy == Kernal.Inverted) {
			InvertPTEKey Ikey = new InvertPTEKey(pid, LA);
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
	public boolean DrefreshPagetable(int pid, int LA, Integer PA) {
		// D presents Dirty
		if (Kernal.PtSizeOptimizePolicy == Kernal.Inverted) {
			InvertPTEKey Ikey = new InvertPTEKey(pid, LA);
			if(ptes.containsKey(Ikey))
				ptes.replace(Ikey, PA);
			else
				ptes.put(Ikey, PA);
		} else if (Kernal.PtSizeOptimizePolicy == Kernal.Traditional) {
			TrditionalPTEKey Tkey = new TrditionalPTEKey(LA);
			if(ptes.containsKey(Tkey))
				ptes.replace(Tkey, PA);
			else
				ptes.put(Tkey, PA);
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
			}
		}
		return true;

	}

	public void freePage(int pid) {
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
}
