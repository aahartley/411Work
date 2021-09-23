/**
 * Demonstrating process creation in Java.
 */

import java.io.*;
import java.util.*;

public class OSprocess {
	public static void main(String[] args) throws IOException {
		
		boolean exit = false;
		Map<String,String[]> commands = new HashMap<>();
		commands.put("dir", new String[]{"cmd","/c","dir"});
		commands.put("ls", new String[]{"cmd","/c","dir"});
		commands.put("cd", new String[]{"cmd","/c","cd"});
		commands.put("pwd", new String[]{"cmd","/c","cd"});
		commands.put("cp",new String[]{"cmd","/c","copy"});
		commands.put("copy",new String[]{"cmd","/c","copy"});
		commands.put("del", new String[]{"cmd","/c","del"});
		commands.put("rm", new String[]{"cmd","/c","del"});
		commands.put("more", new String[]{"cmd","/c","more"});
		commands.put("man", new String[]{"help"});
		commands.put("nano", new String[]{"notepad"});
		commands.put("grep", new String[]{"find"});
		commands.put("mkdir", new String[]{"cmd","/c","mkdir"});
		commands.put("rmdir", new String[]{"cmd","/c","rmdir"});
		commands.put("set", new String[]{"cmd","/c","set"});
		commands.put("env", new String[]{"cmd","/c","set"});
		commands.put("ifconfig", new String[]{"ipconfig"});
		
		List<String> history = new ArrayList<>();
		List<String> directory = new ArrayList<>();
		String past="";



		// show system environment
		Map<String, String> enviro = System.getenv();
		for (String key : enviro.keySet())
			System.out.println(key + "  :  " + enviro.get(key));

		// show current directory
		String workingDirectory = System.getProperty("user.dir") + "\\";
		System.out.println(workingDirectory);
		directory.add(workingDirectory);
		while(!exit) {
		// Create a command line window

		// Read the command ** A command without parameter **
		Scanner console = new Scanner((System.in));

		// Read the command **A command with parameter **
		// Run cmd /c dir
		System.out.print("bash>");
		List<String> commandlist = new ArrayList<String>();
		Scanner toker = new Scanner(console.nextLine());
		while (toker.hasNext()) {
			commandlist.add(toker.next());
		}
		history.addAll(commandlist);
		String[] cmd = new String[100];
		boolean split = false;
		int index =0;
		if(commandlist.get(0).equals("history")) {
			System.out.println(history);
			commandlist.clear();
		}
		else if(commandlist.get(0).equals("exit")) {
			commandlist.clear();
			exit=true;
		}
		for(String s: commandlist) {
			if(commands.containsKey(s)) {
				index = commandlist.indexOf(s);
				cmd = commands.get(s);
				split=true;
			}
		}
		
		if(split) {
			List<String> newCommandList = new ArrayList<String>(Arrays.asList(cmd));
			commandlist.addAll(index, newCommandList);
			commandlist.remove(index+newCommandList.size());
		}
		//being able to change directories and save the path
		if(!commandlist.isEmpty()) {
			
			  String current ="";
			  int cdIndex=0; 
			  if(commandlist.contains("cd")) { 
				  for(int i=0; i<commandlist.size();i++) {
					  if(commandlist.get(i).equals("cd")) {
						  cdIndex = i; 
			  }
					  } 
			  
			  if(commandlist.size()>3) {
			  
			  current=commandlist.get(cdIndex+1);
			  directory.set(0,past+current);
			 
			  
			  } 
			  } past=workingDirectory; 
			  if(!directory.isEmpty()) 
				  past=directory.get(0);
		
		try {
			ProcessBuilder pb1 = new ProcessBuilder(commandlist).directory(new File(past));
			past="";

			Process proc1 = pb1.start();

			// obtain the input and output streams -- only for text stream
			Scanner br1 = new Scanner(proc1.getInputStream());

			while (br1.hasNextLine()) {
				System.out.println(br1.nextLine());
			}

			br1.close();
			toker.close();
			// end
		}catch(Exception e) {
			System.out.println("Command is not supported");
			directory.set(0, workingDirectory);
		}
		}
	}
	    

	}
}