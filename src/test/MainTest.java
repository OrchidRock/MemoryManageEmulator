package test;

import gui.Window;
import gui.Window.ProcessExeOneInstruct;
import inter.Disk;
import inter.Kernal;
import inter.MMU;
import inter.Memory;
import inter.Process;
import inter.Processer;
import inter.TLB;

public class MainTest implements ProcessExeOneInstruct{
	private final static int processCount = 5;
	private static Process[] processes = new Process[processCount];

	public static void main(String[] args) {
		new MainTest();
	}
	public MainTest() {
		try {
			
			// GUI Window start up
			Window.createNewInstance(this);

			
			// Equipments and kernel start up
			Kernal.createNewInstance();
			Memory.createNewInstance();
			MMU.cerateNewInstance();
			TLB.createNewInstance();
			Disk.createNewInstance();
			
			//processer
			Processer processer=Processer.createNewInsatnce();
			Thread threadprocesser=new Thread(processer);
			threadprocesser.start();
			
			
			// create process
			for (int i = 0; i < processCount; i++) {
				processes[i] = new Process();
			//	Thread thread=new Thread(processes[i]);
				//thread.start();
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * // start processer Runnable processerHandle =
		 * Processer.createNewInsatnce(); Thread process = new
		 * Thread(processerHandle); process.start();
		 * 
		 * try {// create new process // process 1 Process process1Handle;
		 * process1Handle = new Process(); for(int i=0;i<20;i++){
		 * process1Handle.issueOneInstruct(); } //Thread process1 = new
		 * Thread(process1Handle);
		 * 
		 * // Process process2Handle = new Process(); //Thread process2 = new
		 * Thread(process2Handle); //process2.start();
		 * 
		 * //process1.start();
		 * 
		 * //System.out.println("TLB miss rate:"
		 * +TLB.getInstance().getMissRate()); // System.out.println(
		 * "Page Fault rate :"+MMU.getInstance().getPageFaultRate()); } catch
		 * (InterruptedException e) { e.printStackTrace(); }
		 */

	}
	@Override
	public boolean ProcessExeInstruct(int processindex,boolean isallprocess) {
		if(isallprocess){
			startupallprocess();
			return true;
		}
		if(processindex>processCount || processindex<1)
			return false;
		if(processes==null)
			return true;
		Process process=processes[processindex-1];
		if(process==null)
			return true;
		return process.issueOneInstruct();
	}
	private void startupallprocess() {
		for(int i=0;i<processCount;i++){
			Thread thread=new Thread(processes[i]);
			thread.start();
		}
	}
}
