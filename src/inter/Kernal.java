package inter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;


import algorithm.*;
import gui.Window;
import tool.ConfLoader;
import tool.MappingLoader;
import tool.ConfLoader.ConfType;


public class Kernal {
	public final static int StackSize = (int) Math.pow(2, 5);
	public final static int RANDOM = 100, FIFO = 101, LRU = 102;
	public final static int Inverted = 2, Traditional = 1;
	public final static int Global = 10, Local = 11;

	private static Kernal kernal;

	private Kernal() {
	}

	public static int LABN = -1;
	public static int PABN = -1;
	public static int PtSizeOptimizePolicy = -1; // Page Table
													// SizeOptimizePolicy

	private Hashtable<Integer, PCB> pcbs = new Hashtable<>();// save pcb for all
																// process
	private int PidMaxNumber = -1;
	private int pidCurrentIndex = 1;

	private Hashtable<Integer, ArrayList<Integer>> mappingtable = null; // local
																		// address
																		// ===>
																		// disk
																		// //
																		// address

	private Integer currentProcessPid = -1; //

	public int replacePolicy ;
	private ReplaceAlgorithm replaceAlgorithm = null;
	private int replaceAlloctePolicy = -1;
	// allcote pcb number
	public static int PageTableMaxNumber = -1;

	public static Kernal createNewInstance() {
		if (kernal == null)
			kernal = new Kernal();
		// load conf
		Properties properties = ConfLoader.getPropertiesByConfType(ConfType.Kernal);
		LABN = Integer.valueOf(properties.getProperty("LABN"));
		PABN = Integer.valueOf(properties.getProperty("PABN"));

		Properties properties_pt = ConfLoader.getPropertiesByConfType(ConfType.PageTable);
		String pop = properties_pt.getProperty("SizeOptimizePolicy");
		PtSizeOptimizePolicy = getSizeOptimizedPolicy(pop);
		if (PtSizeOptimizePolicy == Kernal.Inverted){
			PageTableMaxNumber = 1;
			if(Window.getInstance()!=null)
				Window.getInstance().initPagetablePolicy(true);
		}
		else{
			PageTableMaxNumber = ((int) Math.pow(2, PABN)) / StackSize;
			if(Window.getInstance()!=null)
				Window.getInstance().initPagetablePolicy(false);
		}
		kernal.PidMaxNumber = ((int) Math.pow(2, PABN)) / StackSize - 1;

		kernal.replacePolicy = kernal.getReplacePolicyTag(properties.getProperty("ReplacePolicy"));
		kernal.replaceAlloctePolicy = kernal.getReplaceAllocateTag(properties.getProperty("ReplaceAllocatePolicy"));
		return kernal;
	}

	public static Kernal getInstance() {
		return kernal;
	}

	public PCB allocatePCB() throws InterruptedException {

		int pid = getNextPid();
		if (pid == -1) {
			System.err.println("too many process,kernal can not allocate space!");
			return null;
		}
		int ptindex = -1;

		if (PtSizeOptimizePolicy == Inverted) {
			PageTable pagetable = (PageTable) Memory.getInstance().getPage(0);
			if (pagetable == null) {
				pagetable = new PageTable();
				pagetable.addNewPtAddress(0);
				Memory.getInstance().addPage(pagetable, 0);
			}
			ptindex = 0;
		} else { // Tranditional
			PageTable pageTable = null;
			for (int i = 0; i < PageTableMaxNumber; i++) {
				if (Memory.getInstance().getPage(i) == null) {
					pageTable = new PageTable();
					pageTable.addNewPtAddress(i);
					Memory.getInstance().addPage(pageTable, i);
					ptindex = i;
					break;
				}
			}

		}
		if (ptindex == -1) {
			System.err.println("too many process,kernal can not allocate space!");
			return null;
		}

		PCB pcb = new PCB(pid, ptindex);
		pcbs.put(new Integer(pid), pcb);
		return pcb;
	}

	public int pagefaultExceptionRoutine(int pid, int la) throws InterruptedException {

		// load mapping conf
		if (mappingtable == null)
			mappingtable = MappingLoader.loadMappingTable();

		currentProcessPid = pid;
		int result = -1;
		// find sacrifice page
		int pageindex = -1;
		if (replaceAlloctePolicy == Global) {
			int maxpa = (int) Math.pow(2, PABN) - 1;
			int minpa = PidMaxNumber + 1;
			pageindex=replaceAlgorithm.newPageReference(minpa, maxpa,ReplaceAlgorithm.MEMORY);
		} else {
			int minpa = PageTableMaxNumber + pid * StackSize;
			int maxpa = minpa + StackSize - 1;
			pageindex=replaceAlgorithm.newPageReference(minpa, maxpa,ReplaceAlgorithm.MEMORY);
		}
		result = pageindex;
		ArrayList<Integer> dasList = mappingtable.get(new Integer(la));
		if (dasList == null) {
			System.err.println("kernal can not switch page to busy disk!");
			return -1;
		}
		Integer[] das = new Integer[dasList.size()];
		for (int i = 0; i < das.length; i++)
			das[i] = dasList.get(i);
		NormalPage page = (NormalPage) Memory.getInstance().getPage(pageindex);
		boolean pageiSnull=false;
		if (page == null) { // free
			page = new NormalPage(null, das);
			page.dirtyBit = false;
			Memory.getInstance().addPage(page, pageindex);
			result = pageindex;
			pageiSnull=true;
		} else {
			if (page.dirtyBit) { // write to disk
				Disk.getInstance().writeBack(page.getDiskAddress(), page.getData());
				page.dirtyBit = false;
			}
			// update pde
			if (page.freeBit) {
				ArrayList<Integer> ptIndexList = page.getPtAddress();
				for (int i = 0; i < ptIndexList.size(); i++) {
					PageTable pageTable = (PageTable) Memory.getInstance().getPage(ptIndexList.get(i));
					pageTable.IrefreshPagetable(pageindex);
				}
			}
		}
		page.addNewPtAddress(getCurrentProcessPtIndex());

		// for other algorithm
		page.referenceBit = true;
		page.freeBit = false;
		// load disk data
		page.setData(Disk.getInstance().read(das));
		page.setNewReferenceTime(new Date().getTime());
		if(Window.getInstance()!=null){
			if(pageiSnull)
				Window.getInstance().memoryPageAdd(pageindex+"&"+page.toString());
			else 
				Window.getInstance().memoryPageUpdate(pageindex+"&"+page.toString());
		}
		return result;
	}

	public ArrayList<Integer> getAllDiskAddress() {
		if (mappingtable == null)
			mappingtable = MappingLoader.loadMappingTable();
		Set<Integer> keys = mappingtable.keySet();
		ArrayList<Integer> result = new ArrayList<>();
		for (Integer key : keys) {
			ArrayList<Integer> value = mappingtable.get(key);
			result.addAll(value);
		}
		return result;
	}

	private int getNextPid() {
		for (int i = pidCurrentIndex; i < PidMaxNumber; i++) { // first fit
			if (pcbs.get(new Integer(i)) == null) {
				pidCurrentIndex = i;
				return i;
			}
		}
		for (int i = 1; i < pidCurrentIndex; i++) {
			if (pcbs.get(new Integer(i)) == null) {
				pidCurrentIndex = i;
				return i;
			}
		}
		return -1;
	}

	private int getCurrentProcessPtIndex() {
		PCB pcb = getPCBByPid(currentProcessPid);
		return pcb.ptIndex;
	}

	@Deprecated
	public PCB getCurrentProcessPCB() {
		return pcbs.get(currentProcessPid);
	}

	public PCB getPCBByPid(int pid) {
		return pcbs.get(new Integer(pid));
	}

	public void freePCBByPid(int pid) throws InterruptedException {
		Integer key = new Integer(pid);
		PCB pcb = pcbs.get(key);
		PageTable pageTable = (PageTable) Memory.getInstance().getPage(pcb.ptIndex);
		pageTable.freePage(pid);
		if (replacePolicy == Traditional) {
			Memory.getInstance().addPage(null, pcb.ptIndex);
		}
		pcbs.remove(key);
	}

	public static int getReplacePolicyTag(String policyname) {
		int tag = -1;
		switch (policyname) {
		case "FIFO":
			tag = FIFO;
			int num=(int)Math.pow(2, PABN)-kernal.PidMaxNumber-1;
			kernal.replaceAlgorithm = new FIFO(num,kernal.PidMaxNumber-1);
			break;
		case "LRU":
			tag = LRU;
			kernal.replaceAlgorithm = new LRU();
			break;
		case "RANDOM":
			tag = RANDOM;
			kernal.replaceAlgorithm = new RANDOM();
			break;
		default:
			System.err.println("error data from ConfLoader.Kernal.ReplacePolicy");
			break;
		}
		return tag;
	}

	private static int getSizeOptimizedPolicy(String name) {
		int r = -1;
		switch (name) {
		case "Inverted":
			r = Inverted;
			break;
		case "Traditional":
			r = Traditional;
			break;
		default:
			System.err.println("Kernal.PageTable.conf has error data");
			break;
		}
		return r;
	}

	private static int getReplaceAllocateTag(String name) {
		int tag = -1;
		switch (name) {
		case "Global":
			tag = Global;
			break;
		case "Local":
			tag = Local;
			break;
		default:
			System.err.println("error data from ConfLoader.Kernal.ReplaceAllocatePolicy");
			break;
		}
		return tag;
	}
}
