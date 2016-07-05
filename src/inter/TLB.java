package inter;

import java.util.Properties;


import tool.ConfLoader;
import tool.ConfLoader.ConfType;

public class TLB {
	
	private int replacePolicy=-1;
	private int PTECount=-1;
	private int degreeAssociative=-1;
	private TLBPTE[] ptes=null;
	
	
	private static TLB tlb;
	private TLB(){}
	public static TLB createNewInstance(){
		if(tlb==null)
			tlb=new TLB();
		//load conf
		Properties properties=ConfLoader.getPropertiesByConfType(ConfType.TLB);
		tlb.replacePolicy=Kernal.getReplacePolicyTag(properties.getProperty("ReplacePolicy"));
		tlb.PTECount=Integer.valueOf(properties.getProperty("PTECount"));
		tlb.degreeAssociative=Integer.valueOf(properties.getProperty("DegreeAssociative"));
		if(tlb.degreeAssociative>tlb.PTECount || tlb.degreeAssociative <1)
			System.err.println("conf.tlb has error data");
		tlb.ptes=new TLBPTE[tlb.PTECount];
		
		return tlb;
	}
	public static TLB getInstance(){
		return tlb;
	}
	public int searchTLB(int la){
		
		return 0;
	}
}
