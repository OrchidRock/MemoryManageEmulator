package test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import tool.MappingLoader;

public class MLTest {

	public static void main(String[] args) {
		Hashtable<Integer, ArrayList<Integer>> mapping=MappingLoader.loadMappingTable();
		Set<Integer> keys=mapping.keySet();
		System.out.println("key.size="+keys.size());
		System.out.println("mapping.size="+mapping.size());
		int count=0;
		for(Integer key : keys){
			ArrayList<Integer> values=mapping.get(key);
			for(int i=0;i<values.size();i++){
				System.out.println(key.toString()+": "+values.get(i));
				count++;
			}
		}
		System.out.println("count = "+count);
		//mapping.
		//properties.list(new PrintStream(System.out));
	}

}
