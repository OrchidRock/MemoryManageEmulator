package inter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Scanner;


import tool.ConfLoader;
import tool.ConfLoader.ConfType;
public class Disk {

	private final static String path="ThreeDayToSee.txt";
	private static int platterCount;
	private static int trackCount;
	private static int sectorCount;
	
	private Hashtable<Integer,String>dataTable;
	
	
	private static Disk disk;
	private Disk(){}
	
	public static Disk createNewInstance(){
		if(disk==null)
			disk=new Disk();
		//loadConf
		Properties p=ConfLoader.getPropertiesByConfType(ConfType.Disk);
		String platterCountS=p.getProperty("PlatterCount");
		platterCount=Integer.valueOf(platterCountS);
		trackCount=Integer.valueOf(p.getProperty("TrackCount"));
		sectorCount=Integer.valueOf(p.getProperty("SectionCount"));
		
		//init disk data
		loadTestText();
	    return disk;
	}
	private static void loadTestText(){
		try {
			BufferedReader reader=new BufferedReader(new FileReader(path));
			String line;
			ArrayList<Integer> mapping=Kernal.getInstance().getAllDiskAddress();
			int index=0;
			while(index<mapping.size() && (line=reader.readLine())!=null){
				disk.dataTable.put(mapping.get(index), line);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Disk getInstance(){
		return disk;
	}
	private void print(int address){
		int platter=address/(trackCount*sectorCount);
		int track=(address/sectorCount)%trackCount;
		int sector=address%sectorCount;
		String imfor="data in platter:"+platter+" track:"+track+" sector:"+sector;
		System.out.println(imfor);
		
	}	
	public String[] read(Integer[] addresses){
		ArrayList<String> blocks=new ArrayList<>();
		for(int i=0;i<addresses.length;i++){
			if(dataTable.containsKey(addresses[i])){
				blocks.add(dataTable.get(addresses[i]));
			}
			else{
				System.err.println("Disk.address : "+i+" is a empty block");
			}
		}
		
		return (String[])blocks.toArray();
	}
	public void writeBack(Integer[] addresses,String[] newData){
		if(dataTable==null)
			dataTable=new Hashtable<>();
		if(newData.length != addresses.length){
			System.err.println("Disk : writeback data inconformity");
		}
		int size=Math.min(newData.length, addresses.length);
		
		for(int i=0;i<size;i++){
			if(dataTable.containsKey(addresses[i])){
				dataTable.replace(addresses[i], newData[i]);
			}else{
				dataTable.put(addresses[i], newData[i]);
			}
		}
	}
}
