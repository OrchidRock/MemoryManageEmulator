package inter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import tool.AccessTableLoader;

public class Processer implements Runnable {

	private BlockingQueue<Instruct> instructQueue = new ArrayBlockingQueue<>(100);
	private BlockingQueue<Instruct> instructresult = new ArrayBlockingQueue<>(100);

	private Date arrivetime = null;
	public static final int READ = 0, WRITE = 1;

	private static Processer processer;

	private Processer() {
	}

	public static Processer createNewInsatnce() {
		if (processer == null)
			processer = new Processer();
		return processer;
	}

	public static Processer getInsatnce() {
		return processer;
	}

	@Override
	public void run() {
		int count=0;
		try {
			while (true) {
				Instruct instruct = instructQueue.take();
				long pretime = new Date().getTime();
				instruct.starttime = pretime;
				accessNext(instruct);
				count++;
				long finishtime = new Date().getTime();
				instruct.exetime = finishtime - pretime;
				instructresult.put(instruct);
				System.out.println(instruct);
				if(count>=50){
					System.err.println("TLB miss rate:"+TLB.getInstance().getMissRate());
					System.err.println("Page Fault rate :"+MMU.getInstance().getPageFaultRate());
					count=0;
				}
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Instruct getOneInstructResult() throws InterruptedException{
		return instructresult.take();
	}
	public void exceOneInstruct(Instruct instruct) {
		try {
			instructQueue.put(instruct);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean accessNext(Instruct instruct) throws InterruptedException {
		int result = MMU.getInstance().addressTranslate(instruct.pid, instruct.la, READ, null);
		if (result > 0) { // success
			// get page
		} else if (result < 0) {// page fault
			MMU.getInstance().addressTranslate(instruct.pid, instruct.la, READ, null);
		} else {
			System.err.println("Access failed");
			return false;
		}

		Page page = Memory.getInstance().getPage(result);
		if (page == null) {
			System.err.println("Process access empty page");
			return false;
		}
		instruct.data = page.toString();
		return true;
	}

	/*
	 * public void print(int i){ int j=i+1; System.out.println(j+" "
	 * +accessTable.get(i)+"" + " "+"execute time="+exetimeTable.get(i)+" "
	 * +"waittime="+waittimeTable.get(i));
	 * 
	 * 
	 * }
	 */
}
