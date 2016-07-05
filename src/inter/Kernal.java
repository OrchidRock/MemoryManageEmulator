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
	
	
	private Hashtable<Integer, PCB> pcbs=new Hashtable<>();// save  pcb for all process
	private int PidMaxNumber=-1;
	private int pidCurrentIndex=0;
	
	private Hashtable<Integer, ArrayList<Integer>> mappingtable=null; // local address ===> disk address
	
	private Integer currentProcessPid=-1; //
	
	private int replacePolicy=-1;
	//allcote pcb number
	public static int PageTableMaxNumber=-1;
	
	public static Kernal createNewInstance(){
		if(kernal==null)
			kernal=new Kernal();
		//load conf
		Properties properties=ConfLoader.getPropertiesByConfType(ConfType.Kernal);
		LABN=Integer.valueOf(properties.getProperty("LABN"));
		PABN=Integer.valueOf(properties.getProperty("PABN"));
		
		Properties properties_pt=ConfLoader.getPropertiesByConfType(ConfType.PageTable);
		PtSizeOptimizePolicy=Integer.valueOf(properties_pt.getProperty("SizeOptimizePolicy"));
		if(PtSizeOptimizePolicy==Kernal.Inverted)
			PageTableMaxNumber=1;
		else
			PageTableMaxNumber=(int)Math.pow(2,PABN-5);
		kernal.PidMaxNumber=(int)Math.pow(2,PABN-5);
		
		kernal.replacePolicy=kernal.getReplacePolicyTag(properties.getProperty("ReplacePolicy"));
		return kernal;
	}
	public static Kernal getInstance(){
		return kernal;
	}
	public PCB allocatePCB(){
		
		int pid=getNextPid();
		
		if(PtSizeOptimizePolicy==Kernal.Inverted){
			
		}else{
			
		}
		
		/*PidMaxIndex++;
		PageTableMaxIndex++;
		PCB pcb=new PCB(PidMaxIndex,PageTableMaxIndex);
		pcbs.put(new Integer(PidMaxIndex), pcb);
		
		PageTable pTable=new PageTable();
		pTable.addNewPtAddress(PageTableMaxIndex);
		
		//Memory.getInstance().addPage(pTable,int index);
*/		//return pcb;
		return null;
	}
	public void pagefaultExceptionRoutine(int pid,int localAddress){
		currentProcessPid=pid;
		
		// load mapping conf
		if(mappingtable==null)
			mappingtable=MappingLoader.loadMappingTable();
		// get replace physical number
		//if drity to disk
		
		
	}
	private int getNextPid(){
		for(int i=pidCurrentIndex ;i<PidMaxNumber;i++){ // first fit
			if(pcbs.get(new Integer(i))==null){
				pidCurrentIndex=i;
				return i;
			}
		}
		for(int i=1;i<pidCurrentIndex;i++){
			if(pcbs.get(new Integer(i))==null){
				pidCurrentIndex=i;
				return i;
			}
		}
		return -1;
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
