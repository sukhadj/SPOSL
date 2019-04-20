package com.sukhad;

import java.io.*;
import java.util.*;

public class Pass2 {

	ArrayList<MNT_Entry> MNT;
	ArrayList<String> intermediate_code;
	ArrayList<String> MDT;
	HashMap<String, String> posVsAct;
	
	String output;
	
	Pass2()
	{
		this.MNT = new ArrayList<>();
		this.MDT = new ArrayList<>();
		this.intermediate_code = new ArrayList<>();
		this.output = "";
		this.posVsAct = new HashMap<>();
	}
	
	public void read_input(File mnt, File mdt, File ic)
	{
		try {
			BufferedReader mntr = new BufferedReader(new FileReader(mnt));
			BufferedReader mdtr = new BufferedReader(new FileReader(mdt));
			BufferedReader icr = new BufferedReader(new FileReader(ic));
			
			// read intermediate code
			System.out.println("===Intermediate code===S");
			String line = icr.readLine(); 
			while(line!=null)
			{
				System.out.println(line);
				this.intermediate_code.add(line);
				line = icr.readLine();
			}
			
			// read mdt
			System.out.println("===MDT===");
			line = mdtr.readLine(); 
			while(line!=null)
			{
				System.out.println(line);
				this.MDT.add(line);
				line = mdtr.readLine();
			}

			// read MNT
			System.out.println("===MNT===");
			line = mntr.readLine(); 
			while(line!=null)
			{
				System.out.println(line);
				String[] words = line.split("\t");
 				this.MNT.add(new MNT_Entry(words[0],Integer.parseInt(words[1]),Integer.parseInt(words[2])));
				line = mntr.readLine();
			}

			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void expand_macro(String line, MNT_Entry mnte)
	{
		String[] words = line.split(" |\t|,");
		if((words.length-1)!=mnte.argc)
		{
			System.out.println("Not enough parameters");
			return;
		}
		for(int i=1;i<words.length;i++)
		{
			this.posVsAct.put("#"+Integer.toString(i),words[i]);
		}
		int i = mnte.start;
		while(true)
		{
			String l = this.MDT.get(i);
			if(l.trim().compareTo("MEND")==0)
			{
				posVsAct.clear();
				break;
			}
			for(String key : this.posVsAct.keySet())
			{
				l = l.replaceAll(key, this.posVsAct.get(key));

			}
			this.output += l+"\n";
			i++;
		}
	}
	
	public void generate_output()
	{
		boolean flag = false;
		for(String line : this.intermediate_code)
		{
			flag = false;
			for(MNT_Entry mnte : this.MNT)
			{
				if(line.contains(mnte.name))
				{
					this.expand_macro(line, mnte);
					flag = true;
				}
			}
			if(!flag)
			{
				this.output += line+"\n";
			}	
		}
		System.out.println("===output===");
		System.out.println(this.output);
	}
	
	public static void main(String args[])
	{
		String RES_DIR = "/home/sukhad/Workspace/College/SPOS/Macro/res/";
		
		File mnt = new File(RES_DIR+"mnt.txt");
		File mdt = new File(RES_DIR+"mdt.txt");
		File ic = new File(RES_DIR+"ic.txt");

		Pass2 pass2 = new Pass2();
		pass2.read_input(mnt, mdt, ic);
		pass2.generate_output();
	}
}
