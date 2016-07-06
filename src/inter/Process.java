package inter;

import java.util.ArrayList;

import tool.AccessTableLoader;

public class Process implements Runnable{
	private PCB pcb=null;
	private ArrayList<Integer> accessTable=null;
	private ArrayList<Float>exetimeTable=new ArrayList<>();
	private ArrayList<Float>waittimeTable=new ArrayList<>();
	
	
	private int PC=0;
	public Process() {
		//allocate pcb through kernal
		pcb=Kernal.getInstance().allocatePCB();
		//getAccessTable;
		accessTable=AccessTableLoader.loadAccessTableByPid(pcb.pid);
	   // arrivetime=new Date();
	}
	@Override
	public void run() {
		while(PC<accessTable.size()){
			Instruct instruct=new Instruct(pcb.pid, PC, accessTable.get(PC));
			Processer.getInsatnce().exceOneInstruct(instruct);
			PC++;
		}
		
	}
	
}
