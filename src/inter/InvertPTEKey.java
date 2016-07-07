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
	public int getPid(){
		return pid;
	}
	public String toString(){
		return pid+"&"+la;
	}
	@Override
	public boolean equals(Object otherobject) {
		if(this==otherobject)
			return true;
		if(otherobject==null)
			return false;
		if(getClass()!=otherobject.getClass()) return false;
		InvertPTEKey invertPTEKey=(InvertPTEKey)otherobject;
		return (invertPTEKey.la==la) && (invertPTEKey.pid==pid);
	}
}
