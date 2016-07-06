package inter;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class MMU {
	
	private int acceecount=0;
	private int pagefaultcount=0;
	
	private ReentrantLock lock=new ReentrantLock();
	
	private static MMU mmu;
	private MMU(){}
	public static MMU cerateNewInstance(){
		if(mmu==null)
			mmu=new MMU();
		return mmu;
	}
	public static MMU getInstance(){
		return mmu;
	}
	/*
	 * @return 
	 */
	public int addressTranslate(int pid,int va,int accessType,String data){
		int result=0;
		//search TLB
		lock.lock();
		acceecount++;
		try {
			result=TLB.getInstance().searchTLB(va);
		if(result==-1){ //TLB miss
			PCB pcb=Kernal.getInstance().getPCBByPid(pid);
			PageTable pt=(PageTable)Memory.getInstance().getPage(pcb.ptIndex);
			result=pt.searchPPN(pid, va);
			if(result==-1){ // page fault
				pagefaultcount++;
				int pa=Kernal.getInstance().pagefaultExceptionRoutine(pid,va);
				if(pa==-1){
					return 0;
				}
				TLB.getInstance().update(pid,va,pa);
				result=pa;
			}else{
				if(Kernal.getInstance().replacePolicy==Kernal.LRU){
					Memory.getInstance().setPageReferenceTimeByIndex(result, new Date().getTime());
				}
				TLB.getInstance().update(pid, va, result);
			}
		}else{
			if(Kernal.getInstance().replacePolicy==Kernal.LRU){
				Memory.getInstance().setPageReferenceTimeByIndex(result, new Date().getTime());
			}
		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock.unlock();
		return result;
	}
	public double getPageFaultRate(){
		lock.lock();
		Double rate=(double)(pagefaultcount)/(double)acceecount;
		lock.unlock();
		return rate;
	} 
}
