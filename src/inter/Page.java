package inter;

import java.util.ArrayList;

public class Page {
	private ArrayList<Integer> ptKey=new ArrayList();
	public boolean dirtyBit;
	public boolean freeBit;
	public boolean referenceBit;
	public Page(){
		dirtyBit=false;
		freeBit=true;
		referenceBit=false;
	}
	public void addNewPtAddress(Integer key){
		ptKey.add(key);
	}
	public ArrayList<Integer> getPtAddress(){
		return ptKey;
	}
	public String toString(){
		return dirtyBit+"&"+freeBit;
	}
}
