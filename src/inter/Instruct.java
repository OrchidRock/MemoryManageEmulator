package inter;

public class Instruct {
	public int pid;
	public int PC;
	public int la;
	public Instruct(int pid,int pc,int LA){
		this.pid=pid;
		this.PC=pc;
		this.la=LA;
	}
	
	public long exetime=0;
	public long arivetime=0;
	public long starttime=0;
	public String data;
	public String toString(){
		return pid+"&"+PC+"&"+la+"&"+(starttime-arivetime)+"&"+exetime;
	}
}
