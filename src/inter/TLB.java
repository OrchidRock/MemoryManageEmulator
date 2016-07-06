package inter;

import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import tool.ConfLoader;
import tool.ConfLoader.ConfType;

public class TLB {
	
	private int replacePolicy=-1;
	private int PTECount=-1;
	private int degreeAssociative=-1;
	private int setNumber=-1;
	private TLBPTE[] ptes=null;
	
	private int accessCount=0;
	private int hitcount=0;
	private ReentrantLock lock=new ReentrantLock();
	
	
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
		tlb.setNumber=tlb.PTECount/tlb.degreeAssociative;
		tlb.ptes=new TLBPTE[tlb.PTECount];
		for(int i=0;i<tlb.PTECount;i++){
			TLBPTE tlbpte=new TLBPTE();
			tlbpte.dirtybit=false;
			tlbpte.validbit=false;
			tlb.ptes[i]=tlbpte;
		}
		return tlb;
	}
	public static TLB getInstance(){
		return tlb;
	}
	public int searchTLB(int la) throws InterruptedException{
		Thread.sleep(1);
		
		lock.lock();
		accessCount++;
		int result=-1;
		int TLBI=la%setNumber;
		int TLBT=la/setNumber;
		
		int startIndex=TLBI*degreeAssociative;
		for(int i=startIndex;i<startIndex+degreeAssociative;i++){
			TLBPTE tlbpte=ptes[i];
			if(tlbpte.validbit && tlbpte.tag==TLBT){
				result=tlbpte.pa;
				hitcount++;
				break;
			}
		}
		lock.unlock();
		return result;
	}
	public double getMissRate(){
		lock.lock();
		Double rate=(double)(accessCount-hitcount)/(double)accessCount;
		lock.unlock();
		return rate;
	}
	private int getReplaceLocation(int tlbi){
		int minpa = tlbi*degreeAssociative;
		return (int) Math.round(Math.random() * (degreeAssociative-1) + minpa);
	}
	public void update(int pid, int va, int pa) throws InterruptedException {
		int TLBI=va%setNumber;
		int next=getReplaceLocation(TLBI);
		TLBPTE tlbpte=ptes[next];
		if(tlbpte.dirtybit){
			int ptindex=(Kernal.getInstance().getPCBByPid(pid)).ptIndex;
			PageTable pageTable=(PageTable)Memory.getInstance().getPage(ptindex);
			pageTable.DrefreshPagetable(pid, va, pa);
			tlbpte.dirtybit=false;
		}else{
			tlbpte.pa=pa;
			tlbpte.tag=va/setNumber;
			tlbpte.validbit=true;
			tlbpte.dirtybit=true;
		}
	}
}
