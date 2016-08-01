package pop2016.openservice.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static void unWar(String workPath, String fileName){
		List<String> command = new ArrayList<String>();
	
		command.add("jar");
		command.add("xvf");
		command.add(fileName);
		
	
		ProcessBuilder pb = new ProcessBuilder(command); 
		pb.directory(new File(workPath));
		Process p;
		try {
			p = pb.start();
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( InterruptedException e){
			e.printStackTrace();
		}
		
			
	
	}
	public static void unZip(String workPath, String fileName){
		List<String> command = new ArrayList<String>();
	
		command.add("unzip");
		command.add(fileName);
		
	
		ProcessBuilder pb = new ProcessBuilder(command); 
		pb.directory(new File(workPath));
		Process p;
		try {
			p = pb.start();
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( InterruptedException e){
			e.printStackTrace();
		}
		
			
	
	}

}
