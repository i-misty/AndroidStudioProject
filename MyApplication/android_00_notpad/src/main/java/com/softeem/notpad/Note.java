package com.softeem.notpad;

import java.io.Serializable;

public class Note implements Serializable{

	private String noteID;		//备忘id
	private String noteContent;	//备忘内容
	private String noteTime;	//添加时间
	
	public Note() {
	}
	
	public Note(String noteID, String noteContent, String noteTime) {
		super();
		this.noteID = noteID;
		this.noteContent = noteContent;
		this.noteTime = noteTime;
	}

	public String getNoteID() {
		return noteID;
	}
	public void setNoteID(String noteID) {
		this.noteID = noteID;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	public String getNoteTime() {
		return noteTime;
	}
	public void setNoteTime(String noteTime) {
		this.noteTime = noteTime;
	}
}
