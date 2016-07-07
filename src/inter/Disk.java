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

import gui.Window;
import tool.ConfLoader;
import tool.ConfLoader.ConfType;
public class Disk {

	private final static String path="ThreeDayToSee.txt";
	private static int platterCount;
	private static int trackCount;
	private static int sectorCount;
	
	private Hashtable<Integer,String>dataTable=new Hashtable<>();
	
	
	private static Disk disk;
	private Disk(){}
	
	public static Disk createNewInstance(){
		if(disk==null)
			disk=new Disk();
		//loadConf
		Properties p=ConfLoader.getPropertiesByConfType(ConfType.Disk);
		platterCount=Integer.valueOf(p.getProperty("PlatterCount"));
		trackCount=Integer.valueOf(p.getProperty("TrackCount"));
		sectorCount=Integer.valueOf(p.getProperty("SectionCount"));
		
		//init disk data
		loadTestText();
	    return disk;
	}
	private static void loadTestText(){
		ArrayList<String> infos=new ArrayList<>();
		try {
			BufferedReader reader=new BufferedReader(new FileReader(path));
			String line;
			ArrayList<Integer> mapping=Kernal.getInstance().getAllDiskAddress();
			int index=0;
			while(index<mapping.size() && (line=reader.readLine())!=null){
				String info="";
				disk.dataTable.put(mapping.get(index), line);
				String location=disk.parserAddress(mapping.get(index));
				info=info+mapping.get(index)+"&"+location+"&"+line;
				infos.add(info);
				index++;
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(Window.getInstance()!=null)
			Window.getInstance().initDiskunits(infos);
	}
	public static Disk getInstance(){
		return disk;
	}
	private String parserAddress(int address){
		int platter=address/(trackCount*sectorCount);
		int track=(address/sectorCount)%trackCount;
		int sector=address%sectorCount;
		String imfor=platter+"&"+track+"&"+sector;
		return imfor;
	}	
	public String[] read(Integer[] addresses) throws InterruptedException{
		Thread.sleep(1000);
		ArrayList<String> blocks=new ArrayList<>();
		for(int i=0;i<addresses.length;i++){
			if(dataTable.containsKey(addresses[i])){
				blocks.add(dataTable.get(addresses[i]));
			}
			else{
				System.err.println("Disk.address : "+i+" is a empty block");
			}
		}
		String[] data=new String[blocks.size()];
		for(int i=0;i<data.length;i++)
			data[i]=blocks.get(i);
		return data;
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
