package inter;

import java.util.ArrayList;

public class Page {
	private ArrayList<Integer> ptAddress=new ArrayList();
	public boolean dirtyBit;
	public boolean freeBit;
	public boolean referenceBit;
	public Page(){
		dirtyBit=false;
		freeBit=true;
		referenceBit=false;
	}
	public void addNewPtAddress(int ptindex){
		ptAddress.add(new Integer(ptindex));
	}
}
