package inter;

import java.util.Objects;

public class InvertPTEKey extends Key{
	private int pid;
	private int la;
	public InvertPTEKey(int pid,int la) {
		// TODO Auto-generated constructor stub
		this.pid=pid;
		this.la=la;
	}
	@Override
	public int hashCode() {
		return Objects.hash(pid,la);
	}
	
}
