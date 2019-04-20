package com.sukhad;

import java.util.*;
import java.io.*;

public class Pass1 {
	
	ArrayList<String> source_code;

	ArrayList<MNT_Entry> MNT;
	ArrayList<String> MDT;
	HashMap<String, String> formVsPos;
	String intermediate_code;
	
	Pass1()
	{
		this.source_code = new ArrayList<>();
		this.MNT = new ArrayList<>();
		this.MDT = new ArrayList<>();
		this.formVsPos = new HashMap<>();
		this.intermediate_code = "";
	}
	
	public void read_source_code(File file)
	{
		try {
		BufferedReader br = new BufferedReader(new FileReader(file));
	
		String line;
		while((line=br.readLine())!=null)
		{
			this.source_code.add(line);
		}	
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void handle_macro_signature(String line)
	{
		String[] words = line.split(" |,|\t");
		
		// -2 because First two words are MACRO and name
		MNT_Entry mnt_entry = new MNT_Entry(words[1],words.length-2,this.MDT.size());
		this.MNT.add(mnt_entry);
		
		for(int i=2;i<words.length;i++)
		{
			this.formVsPos.put(words[i], "#"+Integer.toString(i-1));
		}
	}
	
	public void macro_process_pass1()
	{
		boolean flag = false;
		for(String line : this.source_code)
		{
			if(line.contains("MACRO")) // start of the macro
			{
				flag = true;
				this.handle_macro_signature(line);
				continue;
			}
			if(line.contains("MEND")) // end of macro
			{
				this.formVsPos.clear();
				flag = false;
				this.MDT.add(line);
				continue;
			}
			if(flag)
			{
				for(String key: this.formVsPos.keySet())
				{
					if(line.contains(key))
					{
						line = line.replaceAll(key, this.formVsPos.get(key));
					}
				}
				this.MDT.add(line);
			}
			else
			{
				this.intermediate_code += line+"\n";
			}
		}
	}
	
	public void print_output(File mnt, File mdt, File ic)
	{
	try {	
		FileWriter MNTW = new FileWriter(mnt);
		FileWriter MDTW = new FileWriter(mdt);
		FileWriter IC = new FileWriter(ic);

		System.out.println("Intermediate code");
		System.out.println(this.intermediate_code);
		IC.write(intermediate_code);
		
		System.out.println("MNT");
		for(MNT_Entry mnt_entry : this.MNT)
		{
			System.out.println(mnt_entry);
			MNTW.write(mnt_entry.name+"\t"+mnt_entry.argc+"\t"+mnt_entry.start+"\n");
		}
		
		System.out.println("MDT");
		for(String line:this.MDT)
		{
			System.out.println(line);
			MDTW.write(line+"\n");
		}
		
		MNTW.close();
		MDTW.close();
		IC.close();
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	}
	
	public static void main(String args[])
	{
		String RES_DIR = "/home/sukhad/Workspace/College/SPOS/Macro/res/";
		
		Pass1 pass1 = new Pass1();
		File code = new File(RES_DIR+"code.txt");
	
		File mnt = new File(RES_DIR+"mnt.txt");
		File mdt = new File(RES_DIR+"mdt.txt");
		File ic = new File(RES_DIR+"ic.txt");
		
		pass1.read_source_code(code);
		pass1.macro_process_pass1();
		pass1.print_output(mnt,mdt,ic);
		

 	}

}