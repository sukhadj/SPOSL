package com.sukhad;

import java.util.*;


class Proc
{
	String name;
	int burst_time;
	int tw;
	int tat;
	int remaining_burst;
	int last_visited;
	
	public Proc(String name, int burst_time) {
		super();
		this.name = name;
		this.burst_time = burst_time;
		this.tw = 0;
		this.tat = 0;
		this.remaining_burst = this.burst_time;
		this.last_visited = 0;
	}

	@Override
	public String toString() {
		return "Proc [name=" + name + ", burst_time=" + burst_time + ", tw=" + tw + ", tat=" + tat + "]";
	}
	
	
	
}

public class RoundRobin {

	ArrayList<Proc> processes; 
	int time_slice;
	int num_processes;
	int num_completed;
	
	RoundRobin()
	{
		this.processes = new ArrayList<>();
		this.time_slice = 0;
		this.num_processes = 0;
		this.num_completed = 0;
	}
	
	public void get_processes()
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the time slice:");
		this.time_slice = sc.nextInt();
		
		System.out.println("Enter the number of processes:");
		this.num_processes = sc.nextInt();
		
		String name;
		int  burst_time;
		
		System.out.println("Enter the process name and burst time");
		
		for(int i=0;i<num_processes;i++)
		{
			name = sc.next();
			burst_time = sc.nextInt();
			
			this.processes.add(new Proc(name,burst_time));
		}
	}
	
	public void display()
	{
		for(Proc p:this.processes)
		{
			System.out.println(p);
		}
	}
	
	public void schedule()
	{
		int current_time = 0;
		while(this.num_completed!=this.num_processes)
		{
			for(Proc p : this.processes)
			{
				if(p.remaining_burst==0)
				{
					// do nothing
					continue;
				}
				if(p.remaining_burst<=this.time_slice)
				{
					System.out.println("Executing process "+p.name+" for "+p.remaining_burst);
					p.tw += current_time-p.last_visited;
					p.tat = p.tw+p.burst_time;
					
					this.num_completed++;
					
					current_time += p.remaining_burst; 
					p.remaining_burst = 0;
				}
				else
				{
					System.out.println("Executing process "+p.name+" for "+this.time_slice);
					
					p.tw += current_time - p.last_visited;
					p.tat = p.tw + p.burst_time;
					
					p.remaining_burst -= this.time_slice;
					
					current_time += this.time_slice;
					p.last_visited = current_time;
				}
			}
		}
	}
	

	
	public static void main(String args[])
	{
		RoundRobin RR = new RoundRobin();
		RR.get_processes();
		RR.display();
		RR.schedule();
		RR.display();
	}
}
