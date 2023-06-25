package com.anandhuarjunan.notesfx.entities;

public class NoteBook {

	private static int idGenerator;

	private int id;
	private String name;
	
	
	

	public NoteBook(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
