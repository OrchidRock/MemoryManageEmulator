package inter;

import java.util.HashMap;
import java.util.Properties;

import tool.ConfLoader;
import tool.ConfLoader.ConfType;

public class PageTable extends Page{
	private HashMap<Key, Integer> ptes = new HashMap<>();

	// search the pagetable
	public int searchPPN(int pid, int LA) {
		Integer result = -1;
		if (Kernal.PtSizeOptimizePolicy == Kernal.Inverted) {
			InvertPTEKey Ikey = new InvertPTEKey(pid, LA);
			result = ptes.get(Ikey);
			if (result == null) {
				// pagefault
			}
		} else if (Kernal.PtSizeOptimizePolicy == Kernal.Traditional) {
			TrditionalPTEKey Tkey = new TrditionalPTEKey(LA);
			result = ptes.get(Tkey);
			if (result == null) {
				// pagefault
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
			ptes.put(Ikey, PA);
		} else if (Kernal.PtSizeOptimizePolicy == Kernal.Traditional) {
			TrditionalPTEKey Tkey = new TrditionalPTEKey(LA);
			ptes.put(Tkey, PA);
		}
		return true;
	}

	// when the mamory has changed and would will affect other process`s
	// pagetable , this mathod will be remoted
	public boolean IrefreshPagetable(int pid, int LA) {
		if (Kernal.PtSizeOptimizePolicy == Kernal.Inverted) {
			InvertPTEKey Ikey = new InvertPTEKey(pid, LA);
			ptes.replace(Ikey, null);
		} else if (Kernal.PtSizeOptimizePolicy == Kernal.Traditional) {
			TrditionalPTEKey Tkey = new TrditionalPTEKey(LA);
			ptes.replace(Tkey, null);
		}
		return true;

	}
}
