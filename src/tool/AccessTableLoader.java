package tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class AccessTableLoader {
	private static String rootpath="trace/";
	public static ArrayList<Integer> loadAccessTableByPid(int pid){
		ArrayList<Integer> integers=new ArrayList<>();
		String path=rootpath+"trace"+Integer.toString(pid)+".txt";
		try {
			File file=new File(path);
			InputStream inputStream=Files.newInputStream(file.toPath());
			Scanner scanner=new Scanner(inputStream);
			boolean isquit=false;
			int linecount=0;
			while(scanner.hasNext() && !isquit){
				String line=scanner.nextLine().trim();
				linecount++;
				if(line.length()<1 || line.charAt(0)=='#')
					continue;
				String[] info=line.split("[ \t]+");
				if(info.length>2){
					System.err.println(path+" has invaild data at "+linecount);
				}
				integers.add(new Integer(info[1]));
			}
			scanner.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return integers;
	}
}
