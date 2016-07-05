package inter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Scanner;


import tool.ConfLoader;
import tool.ConfLoader.ConfType;
public class Disk {

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
	    return disk;
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
	public String read(int address){
		String data;
		data=dataTable.get(new Integer(address));
		print(address);
		return data;
	}
	public boolean writeBack(int address,String newData){
		if(dataTable==null)
			dataTable=new Hashtable<>();
		dataTable.remove(new Integer(address));
		if(dataTable.put(new Integer(address), newData)!=null)
		{
			print(address);
			return true;
		}
		else return false;
	}
}
