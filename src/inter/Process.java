package inter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import tool.AccessTableLoader;

public class Process {
	private PCB pcb=null;
	private ArrayList<Integer> accessTable=null;
	private ArrayList<Float>exetimeTable=new ArrayList<>();
	private ArrayList<Float>waittimeTable=new ArrayList<>();
	
	private int PC=0;
	private Date arrivetime=null;
	public static final int READ=0,WRITE=1;
	public Process(){
		//allocate pcb through kernal
		pcb=Kernal.getInstance().allocatePCB();
		//getAccessTable;
		accessTable=AccessTableLoader.loadAccessTableByPid(pcb.pid);
	    arrivetime=new Date();
	}
	public boolean accessNext(){
		Date pretime=new Date();
		long watetime=pretime.getTime()-arrivetime.getTime();
		Float watetimef=new Float(watetime);
		waittimeTable.add(PC, watetimef);
		
		int result=MMU.getInstance().addressTranslate(pcb.ptIndex,
				accessTable.get(PC), READ, null);
		if(result==0){ //success
			
		}else if(result==-1){//page fault
			MMU.getInstance().addressTranslate(pcb.ptIndex,
					accessTable.get(PC), READ, null);
		}
	

		/*Random rantime=new Random();
		float sleeptime=rantime.nextFloat()*5;
		try {
			Thread.sleep((long)sleeptime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		Date afttime=new Date();
		long exetime=afttime.getTime()-pretime.getTime();
		Float exetimef=new Float(exetime);
		exetimeTable.add(PC, exetimef);
		print(PC);
		PC++;
		
		return true;
	}

	
	public void print(int i){
		int j=i+1;
			System.out.println(j+" "+accessTable.get(i)+""
					+ " "+"execute time="+exetimeTable.get(i)+" "+"waittime="+waittimeTable.get(i));
			
		
	}
}
