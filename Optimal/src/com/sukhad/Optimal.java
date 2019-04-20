package com.sukhad;

import java.util.*;

public class Optimal {
	
	int frame_capacity;
	
	Optimal()
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the page frame capacity");
		this.frame_capacity = sc.nextInt();
		
		
	}
	
	// helper function to find the page not used for the longest period of time 
	private int findPageToReplace(ArrayList<Integer> frame,ArrayList<Integer> sequence, 
									int startIndex)
	{
		int index = 0;
		int longestIndex = 0;
		
		for(int i=0;i<frame.size();i++)
		{
			int page = frame.get(i);
			int j = startIndex;
			while(j<sequence.size())
			{
				if(page==sequence.get(j))
				{
					break;
				}
				j++;
			}
			
			if(j>longestIndex)
			{
				longestIndex = j;
				index = i;
			}
		}
		
		
		return index;
	}
	
	// helper function to print the frame
	void printFrame(ArrayList<Integer> frame)
	{
		for(int page: frame)
		{
			System.out.println(page);
		}
		
		System.out.println("====");
	}
	
	public void optimal_page_replacement(ArrayList<Integer> sequence, int seqLen)
	{
		int page_fault=0;
		int page_hit=0;
		
		float page_ratio=0;
		
		ArrayList<Integer> frame = new ArrayList<Integer>();
		
		int page;
		
		for(int i=0;i<sequence.size();i++)
		{
			this.printFrame(frame);
			
			page = sequence.get(i);
			
			if(frame.contains(page))
			{
				// no need to change
				page_hit++;
				continue;
			}
			
			if(frame.size()<this.frame_capacity)
			{
				// the frame still has space to add new page
				frame.add(page);
				page_fault++;
				continue;
			}
			
			// the frame is full and replace
			int index = this.findPageToReplace(frame, sequence, i);
			frame.set(index, page); 
			page_fault++;	
			
		}
		
		System.out.println("Page hits: "+page_hit);
		System.out.println("Page fault: "+page_fault);
		
		page_ratio = (float)page_fault/seqLen;
		System.out.println("Page fault ratio: "+page_ratio);
	}
	
	public static void main(String args[])
	{
		
		Optimal opt = new Optimal();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the number of pages in the sequence");
		int seqLen = sc.nextInt();
		
		ArrayList<Integer> sequence = new ArrayList<Integer>();
		
		System.out.println("Enter the page sequence");
		for(int i=0;i<seqLen;i++)
		{
			sequence.add(sc.nextInt());
		}
		
		opt.optimal_page_replacement(sequence, seqLen);
		
	}
	
	
}
