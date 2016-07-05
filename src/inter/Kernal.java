package inter;

import java.util.Properties;

import tool.ConfLoader;
import tool.ConfLoader.ConfType;

public class Kernal {
	
	public final static int
		RANDOM=0,FIFO=1,LRU=2;
	
	private static Kernal kernal;
	private Kernal(){}
	
	
	public static int LABN=-1;
	public static int PABN=-1;
	
	private int replacePolicy=-1;
	//allcote pcb number
	private int PidMaxIndex=0;
	public  int PageTableMaxIndex=-1;
	public static Kernal createNewInstance(){
		if(kernal==null)
			kernal=new Kernal();
		//load conf
		Properties properties=ConfLoader.getPropertiesByConfType(ConfType.Kernal);
		LABN=Integer.valueOf(properties.getProperty("LABN"));
		PABN=Integer.valueOf(properties.getProperty("PABN"));
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
		return pcb;
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
