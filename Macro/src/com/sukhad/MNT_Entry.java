package com.sukhad;

public class MNT_Entry {
	
	String name;
	int argc;
	int start;
	
	public MNT_Entry()
	{
		// pass
	}
	
	public MNT_Entry(String name, int argc, int start) {
		super();
		this.name = name;
		this.argc = argc;
		this.start = start;
	}

	@Override
	public String toString() {
		return "MNT_Entry [name=" + name + ", argc=" + argc + ", start=" + start + "]";
	}
	
	
}