package algorithm;

import inter.Memory;

public class LRU extends ReplaceAlgorithm{

	@Override
	public int newPageReference(int startpn, int endpn) {
		int result=-1;
		long mintime=Long.MAX_VALUE;
		for(int i=startpn;i<endpn;i++){
			long time=Memory.getInstance().getPageReferenceTimeByIndex(i);
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
