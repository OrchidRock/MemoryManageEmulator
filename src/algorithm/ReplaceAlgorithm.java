package algorithm;

public abstract class ReplaceAlgorithm {
	public final static int Tlb=100,MEMORY=101;
	public abstract  int newPageReference(int startpn,int endpn,int type);
}
