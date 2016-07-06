package inter;

public class MMU {
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
		result=TLB.getInstance().searchTLB(va);
		if(result==-1){ //TLB miss
			PCB pcb=Kernal.getInstance().getPCBByPid(pid);
			PageTable pt=(PageTable)Memory.getInstance().getPage(pcb.ptIndex);
			result=pt.searchPPN(pid, va);
			if(result==-1){ // page fault
				int pa=Kernal.getInstance().pagefaultExceptionRoutine(pid,va);
				if(pa==-1){
					return 0;
				}
				TLB.getInstance().update(pid,va,pa);
				result=pa;
			}else{
				TLB.getInstance().update(pid, va, result);
			}
		}
		return result;
	}
}
