package inter;

import java.util.ArrayList;

public class Memory {
	
	private Page[] pages=null;
	
	private static Memory memory;
	private Memory(){}
	public static Memory createNewInstance(){
		if(memory==null)
			memory=new Memory();
		int count=(int)Math.pow(2, Kernal.PABN);
		memory.pages=new Page[count];
		return memory;
	}
	public static Memory getInstance(){
		return memory;
	}
	public Page getPage(int index) throws InterruptedException{
		Thread.sleep(10);
		return pages[index];
	}
	public void addPage(Page page,int index){
		pages[index]=page;
	}
	public void setPageReferenceTimeByIndex(int index,long time){
		NormalPage page=(NormalPage)pages[index];
		if(page!=null){
			page.setNewReferenceTime(time);
		}
	}
	public long getPageReferenceTimeByIndex(int index){
		NormalPage page=(NormalPage)pages[index];
		if(page!=null){
			return page.getNewReferenceTime();
		}
		return 0;
	}
}
