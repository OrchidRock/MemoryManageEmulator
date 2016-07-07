package test;

import inter.InvertPTEKey;
import inter.Kernal;
import inter.Key;
import inter.PageTable;

public class PageTableTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InvertPTEKey kPteKey=new InvertPTEKey(0, 1);
		Key key=(Key)kPteKey;
		InvertPTEKey kPteKey2=new InvertPTEKey(0, 1);
		System.out.println(kPteKey2.equals(key));
		System.out.println(key.hashCode());
		System.out.println(kPteKey2.hashCode());
		
		
		
		Kernal.createNewInstance();
		PageTable pageTable=new PageTable();
		pageTable.DrefreshPagetable(1, 2345, 90);
		pageTable.DrefreshPagetable(1, 4628, 23);
		
		int result=pageTable.searchPPN(1, 2345);
		System.out.println(result);
	}

}
