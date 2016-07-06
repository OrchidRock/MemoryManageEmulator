package inter;

import java.util.ArrayList;
import java.util.Date;

import tool.AccessTableLoader;

public class Process implements Runnable{
	private PCB pcb=null;
	private ArrayList<Integer> accessTable=null;
	private ArrayList<Float>exetimeTable=new ArrayList<>();
	private ArrayList<Float>waittimeTable=new ArrayList<>();
	
	
	private int PC=0;
	public Process() throws InterruptedException {
		//allocate pcb through kernal
		pcb=Kernal.getInstance().allocatePCB();
		//getAccessTable;
		accessTable=AccessTableLoader.loadAccessTableByPid(pcb.pid+1);
	   // arrivetime=new Date();
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
	}
	
}
