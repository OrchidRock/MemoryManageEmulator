package inter;

import java.util.ArrayList;
import java.util.Date;

import tool.AccessTableLoader;

public class Process implements Runnable{
	private PCB pcb=null;
	private ArrayList<Integer> accessTable=null;
	
	private int PC=0;
	public Process() throws InterruptedException {
		//allocate pcb through kernal
		pcb=Kernal.getInstance().allocatePCB();
		//getAccessTable;
		accessTable=AccessTableLoader.loadAccessTableByPid(pcb.pid);
	   // arrivetime=new Date();
	}
	public boolean issueOneInstruct(){
		if(PC>=accessTable.size())
			return false;
		if(Processer.getInsatnce()==null)
			return false;
		Instruct instruct=new Instruct(pcb.pid, PC, accessTable.get(PC));
		instruct.arivetime=new Date().getTime();
		Processer.getInsatnce().exceOneInstruct(instruct);
		PC++;
		if(PC>=accessTable.size())
			return false;
		else
			return true;
	}
	@Override
	public void run() {
		while(PC<accessTable.size()){
			Instruct instruct=new Instruct(pcb.pid, PC, accessTable.get(PC));
			instruct.arivetime=new Date().getTime();
			Processer.getInsatnce().exceOneInstruct(instruct);
			PC++;
		}
		/*System.err.println("TLB miss rate:"+TLB.getInstance().getMissRate());
		System.err.println("Page Fault rate :"+MMU.getInstance().getPageFaultRate());*/
		/*try {
			Kernal.getInstance().freePCBByPid(pcb.pid);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
}
