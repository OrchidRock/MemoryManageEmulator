package inter;

public class TLBPTE{
	public int tag=-1;
	public boolean dirtybit=false;
	public boolean validbit=false;
	public int pa=-1;
	public long referencetime=-1;
	
	public String toString(){
		return tag+"&"+pa+"&"+dirtybit+"&"+validbit;
	}
}
