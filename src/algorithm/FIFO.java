package algorithm;


import inter.Memory;
import inter.TLB;

public class FIFO extends ReplaceAlgorithm{
	/*private  int head;
	private  int tail;
	private int num;
	private  int queue[]=null;	
	private  boolean isNull;
	private int offset=0;*/
	public FIFO(int num,int offset){
		/*head=0;
		tail=0;
		isNull=true;
		this.num=num;
		queue=new int[num];
		this.offset=offset;*/
	}
	public FIFO(){}
	@Override
	public int newPageReference(int startpn,int endpn,int type){
		int result=-1;
		long mintime=Long.MAX_VALUE;
		for(int i=startpn;i<endpn;i++){
			long time=-1;
			if(type==MEMORY)
				time=Memory.getInstance().getPageReferenceTimeByIndex(i);
			else
				time=TLB.getInstance().getPageReferenceTimeByIndex(i);
			if(time==0){ //null
				result= i;
				break;
			}else if(time<mintime){
				mintime=time;
				result=i;
			}
		}
		return result;
	}
	/*public int getReplacePageIndex() {
		return queue[head];
	}*/
	
}
