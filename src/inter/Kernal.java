package inter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import tool.ConfLoader;
import tool.MappingLoader;
import tool.ConfLoader.ConfType;

public class Kernal {
	
	public final static int
		RANDOM=0,FIFO=1,LRU=2;
	public final static int 
		Inverted=5,Traditional=6;
	    
	private static Kernal kernal;
	private Kernal(){}
	
	
	public static int LABN=-1;
	public static int PABN=-1;
	public static int PtSizeOptimizePolicy=-1; //Page Table SizeOptimizePolicy
	
	public static int PageTableMaxIndex=-1;
	
	private Hashtable<Integer, PCB> pcbs=new Hashtable<>();// save  pcb for all process
	
	private Hashtable<Integer, ArrayList<Integer>> mappingtable=null; // local address ===> disk address
	
	private Integer currentProcessPid=-1; //
	
	private int replacePolicy=-1;
	//allcote pcb number
	private int PidMaxIndex=0;
	public static Kernal createNewInstance(){
		if(kernal==null)
			kernal=new Kernal();
		//load conf
		Properties properties=ConfLoader.getPropertiesByConfType(ConfType.Kernal);
		LABN=Integer.valueOf(properties.getProperty("LABN"));
		PABN=Integer.valueOf(properties.getProperty("PABN"));
		
		Properties properties_pt=ConfLoader.getPropertiesByConfType(ConfType.PageTable);
		PtSizeOptimizePolicy=Integer.valueOf(properties_pt.getProperty("SizeOptimizePolicy"));
		
		kernal.replacePolicy=kernal.getReplacePolicyTag(properties.getProperty("ReplacePolicy"));
		return kernal;
	}
	public static Kernal getInstance(){
		return kernal;
	}
	public PCB allocatePCB(){
		PidMaxIndex++;
		PageTableMaxIndex++;
		PCB pcb=new PCB(PidMaxIndex,PageTableMaxIndex);
		pcbs.put(new Integer(PidMaxIndex), pcb);
		
		PageTable pTable=new PageTable();
		pTable.addNewPtAddress(PageTableMaxIndex);
		
		//Memory.getInstance().addPage(pTable,int index);
		return pcb;
	}
	public void pagefaultExceptionRoutine(int pid,int localAddress){
		currentProcessPid=pid;
		
		// load mapping conf
		if(mappingtable==null)
			mappingtable=MappingLoader.loadMappingTable();
		// get replace physical number
		//if drity to disk
		
		
	}
	@Deprecated
	public Integer getCurrentProcessPid(){
		return currentProcessPid;
	}
	public PCB getCurrentProcessPCB(){
		return pcbs.get(currentProcessPid);
	}
	@Deprecated
	public PCB getPCBByPid(int pid){
		return pcbs.get(new Integer(pid));
	}
	public void freePCBByPid(int pid){
		pcbs.remove(new Integer(pid));
	}
	public static int getReplacePolicyTag(String policyname){
		int tag=-1;
		switch (policyname) {
		case "FIFO":
			tag=FIFO;
			break;
		case "LRU":
			tag=LRU;
			break;
		case "RANDOM":
			tag=RANDOM;
			break;
		default:
			System.err.println("error data from ConfLoader.Kernal");
			break;
		}
		return tag;
	}
}
