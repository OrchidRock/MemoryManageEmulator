package tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class MappingLoader {
	private static String path="trace/mapping.txt";
	public static Hashtable<Integer, ArrayList<Integer>> loadMappingTable(){
		
		Hashtable<Integer, ArrayList<Integer> >table=new Hashtable<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			int linecount=0;
			while ((line = reader.readLine()) != null){
				//parserLine(line);
				line=line.trim();linecount++;
				if(line.length()<1 || line.charAt(0)=='#')
					continue;
				String[] info=line.split("[ \t]+");
				
				if(info.length!=2){
					System.err.println(path+" has invaild data at "+linecount);
				}
				Integer key=new Integer(info[0].trim());
				Integer value=new Integer(info[1].trim());
				ArrayList<Integer> values=table.get(key);
				if(values==null)
					values=new ArrayList<>();
				values.add(value);
				table.put(key, values);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return table;
	}
}
