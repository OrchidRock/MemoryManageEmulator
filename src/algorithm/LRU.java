package algorithm;

import inter.Memory;
import inter.TLB;
public class LRU extends ReplaceAlgorithm{

	@Override
	public int newPageReference(int startpn, int endpn,int type) {
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

	
	

}
