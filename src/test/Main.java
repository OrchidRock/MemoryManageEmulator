package test;

import inter.Disk;
import inter.Kernal;
import inter.MMU;
import inter.Memory;
import inter.Process;
import inter.Processer;
import inter.TLB;

public class Main {

	public static void main(String[] args) {
		Kernal.createNewInstance();
		Memory.createNewInstance();
		MMU.cerateNewInstance();
		TLB.createNewInstance();
		Disk.createNewInstance();

		// start processer
		Runnable processerHandle = Processer.createNewInsatnce();
		Thread process = new Thread(processerHandle);
		process.start();

		try {// create new process
			// process 1
			Runnable process1Handle;
			process1Handle = new Process();
			Thread process1 = new Thread(process1Handle);

			//
			Runnable process2Handle = new Process();
			Thread process2 = new Thread(process2Handle);
			process2.start();
			process1.start();
			
			//System.out.println("TLB miss rate:"+TLB.getInstance().getMissRate());
		//	System.out.println("Page Fault rate :"+MMU.getInstance().getPageFaultRate());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		//
		//

	}

}
