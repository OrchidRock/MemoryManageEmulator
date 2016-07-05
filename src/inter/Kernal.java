package inter;

import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.util.Properties;

import jdk.internal.dynalink.beans.StaticClass;
import tool.ConfLoader;
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
