package br.edu.cefsa.ec6.laion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DolSystem {
	private String initiator;
	private HashMap<String, String> generator = new HashMap<String, String>();
	private int n;
	private int d;
	
	public DolSystem(String path)throws IOException {
		List<String> contentLines = Files.readAllLines(Paths.get(path));
		
		if(!this.validateFileContent(contentLines))
			throw new IOException("Invalid file");
		
		String[] firstLineData = contentLines.get(0).split(", ");
		this.n = Integer.parseInt(firstLineData[0].substring(2, firstLineData[0].length()));
		this.d = Integer.parseInt(firstLineData[1].substring(2, firstLineData[1].length()));
		this.initiator = contentLines.get(1);
		for(int i = 2; i < contentLines.size(); i++){
			String[] currentGenerator = contentLines.get(i).split("="); 
			this.generator.put(currentGenerator[0], currentGenerator[1]);
		}
	}

	public int getAngle(){
		return this.d;
	}

	public String getLSystem(){
		String lSystem = this.initiator;
		String[] keys = this.generator.keySet().toArray(new String[this.generator.size()]);
		Pattern p = Pattern.compile(this.stringJoin(keys, "|"));
		
		for(int i = 0; i < this.n; i++){
			Matcher m = p.matcher(lSystem);
			StringBuffer sb = new StringBuffer();
			
			while (m.find())
			    m.appendReplacement(sb, this.generator.get(m.group()));
			m.appendTail(sb);
			
			lSystem = sb.toString();
		}
		return lSystem;
	}
	
	private boolean validateFileContent(List<String> content){
		if(content.size() < 2)
			return false;
		else if(!Pattern.compile("^n=[0-9]+, d=[0-9]{1,2}$").matcher(content.get(0)).matches())
			return false;
		else if(!Pattern.compile("^[Ff+-]+$").matcher(content.get(1)).matches())
			return false;
		else{
			for(int i = 2; i < content.size(); i++){
				if(!Pattern.compile("^[Ff+-]{1}=[Ff+-]+$").matcher(content.get(i)).matches())
					return false;
			}
		}
		return true;
	}
	
	private String stringJoin(String[] array, String separator){
		String joined = "";
		
		if(array.length > 0)
			joined = array[0];
		
		for(int i = 1; i < array.length; i++)
			joined += separator + array[i];
		
		return joined;
	}
}