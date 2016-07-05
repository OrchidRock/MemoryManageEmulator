package inter;
public class FIFO {
	int head;
	int tail;
	static int num=10;
	int queue[]=new int[num];	
	boolean isNull;

	public FIFO(){
		head=0;
		tail=0;
		isNull=true;
	}
	public void newPageReference(int pn){
		if((head-tail+num)%num==1){
			int temp=head;
			head=(++head)%num;
			queue[temp]=pn;
			tail=(++tail)%num;
		}
		else{
			if(isNull==true)
				isNull=false;
			else
				tail=(++tail)%num;
			queue[tail]=pn;					
		}
		//System.out.println("head:"+head+"  tail:"+tail);
		print();
	}
	public void print(){
		for(int i=0;i<queue.length;i++){
			System.out.print(queue[i]+" ");}
		System.out.println("");
	}
	public int getReplacePage(){
		return queue[head];
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FIFO f=new FIFO();
		for(int i=0;i<26;i++){f.newPageReference(i);}
	}
}
