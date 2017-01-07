package com.example.notepad;

import java.io.Serializable;

public class Note implements Serializable{

	private String noteID;		//����id
	private String noteContent;	//��������
	private String noteTime;	//���ʱ��
	
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
