package com.sukhad;

import java.util.*;
import java.io.*;


public class Pass2 {
	
	String intermediate_code;
	HashMap<String, String> symTab, litTab;
	ArrayList<Integer> poolTab;
	
	MachineOpcodes mop;
	
	String machine_code;
	
	Pass2()
	{
		this.intermediate_code = "";
		this.symTab = new HashMap<>();
		this.litTab = new HashMap<>();
		
		this.poolTab = new ArrayList<>();
		
		mop = new MachineOpcodes();
		
		this.machine_code = "";
	}
	
	private void parseTable(String tabString, HashMap<String, String> tab)
	{
		String[] words;
		for(String line : tabString.split("\n"))
		{
			words = line.split("\t");
			tab.put(words[0], words[1]);
		}
	}
	
	private void printTables()
	{
		System.out.println("Symbol table");
		for(String key : this.symTab.keySet())
		{
			System.out.println(key+":"+this.symTab.get(key));
		}
	
		System.out.println("Literal table");
		for(String key : this.litTab.keySet())
		{
			System.out.println(key+":"+this.litTab.get(key));
		}

	}
	
	private String parseOperand(String operand)
	{
		if(this.symTab.containsKey(operand))
		{
			// symbol table
			return this.symTab.get(operand);
		}
		else if(this.litTab.containsKey(operand))
		{
			// literal table
			return this.litTab.get(operand);
		}
		else if(operand.split(",|\\(|\\)")[1].compareTo("REG")==0)
		{
			// register operand
			return operand.split(",|\\(|\\)")[2];
		}
		else
		{
			System.out.println("I haven't seen this operand: "+operand);
			return "00";
		}
	}

	public void read_code_table(File intermediate_code, File symTab, File litTab, File poolTab)
	{
		String line;
		String symTabString="", litTabString="";
		
		try {
		// read the intermediate code
		BufferedReader br = new BufferedReader(new FileReader(intermediate_code)); 
		
		while((line=br.readLine())!=null)
		{
			this.intermediate_code += line+"\n";
		}
		System.out.println(this.intermediate_code);

		// read the symbol table
		br = new BufferedReader(new FileReader(symTab));
		
		while((line=br.readLine())!=null)
		{
			symTabString += line+"\n";
		}
		
		this.parseTable(symTabString, this.symTab);
		
		// read the literal table
		br = new BufferedReader(new FileReader(litTab));
		
		while((line=br.readLine())!=null)
		{
			litTabString += line+"\n";
		}
		this.parseTable(litTabString, this.litTab);

		// read the pool table
		br = new BufferedReader(new FileReader(poolTab));
		
		while((line=br.readLine())!=null)
		{
			this.poolTab.add(Integer.parseInt(line.trim()));
		}
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void generate_machine_code()
	{
		for(String line : this.intermediate_code.split("\n"))
		{
			// first one is lc; second is opcode, third is operand 1, fourth is operand 2
			String[] words = line.split("\t");
			String instruction_type = words[1].split(",|\\(|\\)")[1]; 
			if(instruction_type.compareTo("AD")==0)
			{
				// pass
			}
			else if(instruction_type.compareTo("IS")==0)
			{
				this.machine_code += words[0]+"\t";
				this.machine_code += words[1].split(",|\\(|\\)")[2]+"\t";
				
				String op1 = "00",op2="00";
				
				if(words.length>2) // first operand exists
				{
					op1 = this.parseOperand(words[2]);
					
				}
				if(words.length>3) // second operand exists
				{
					op2 = this.parseOperand(words[3]);
				}
				this.machine_code += op1+"\t"+op2+"\t";
				this.machine_code += "\n";
			}
			else if(instruction_type.compareTo("DS")==0)
			{
				if(words[1].split(",|\\(|\\)")[2].compareTo("02")==0)//DC wala case
				{
					this.machine_code += words[0]+"\t";
					this.machine_code += "00"+"\t"+"00"+"\t"+words[2].split("=|'|'")[2]+"\t";
					this.machine_code += "\n";
				}
				
			}
		}
		System.out.println(this.machine_code);
	}
	
	
	public static void main(String args[])
	{
		String RES_DIR = "/home/sukhad/Workspace/College/SPOS/Assembler/res/";
		
		File intermediate_code = new File(RES_DIR+"intermediate_code.txt");
		File symTab = new File(RES_DIR+"symTab.txt");
		File litTab = new File(RES_DIR+"litTab.txt");
		File poolTab = new File(RES_DIR+"poolTab.txt");
		
		Pass2 pass2 = new Pass2();
		pass2.read_code_table(intermediate_code, symTab, litTab, poolTab);
		pass2.generate_machine_code();
		
	}
}