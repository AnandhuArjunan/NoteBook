package com.anandhuarjunan.notesfx.entities;

import java.util.Date;

public class Note {


    private int id;
    private String note;
    private final NoteBook notebook;
    private final Date writeDate;
    private final String title;
    

    // App constructor
    public Note(String title,String note,NoteBook notebook) {
        this.note = note;
        this.notebook = notebook;
        this.writeDate = new Date();
        this.title = title;
    }

    // Database constructor
    public Note(int id, String title, String note,NoteBook notebook, Date writeDate) {
        this.id = id;
        this.note = note;
		this.notebook = notebook;
        this.writeDate = writeDate;
        this.title = title;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

  

    public NoteBook getNotebook() {
		return notebook;
	}

	public String getTitle() {
		return title;
	}

	public Date getWriteDate() {
        return writeDate;
    }

    public int getId() {
        return id;
    }
}
