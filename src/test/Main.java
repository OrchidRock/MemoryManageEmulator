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
		Runnable processerHandle=Processer.createNewInsatnce();
		Thread process=new Thread(processerHandle);
		process.start();
		
		// create new process
		//process 1
		Runnable process1Handle=new Process();
		Thread process1=new Thread(process1Handle);
		process1.start();
		
		
	}

}
