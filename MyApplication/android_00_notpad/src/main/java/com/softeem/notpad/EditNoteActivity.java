package com.softeem.notpad;

import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;

public class EditNoteActivity extends Activity{

	private EditText et;
	private Note note;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notadd);
		
		et = (EditText)findViewById(R.id.note_content);
		Intent i = getIntent();
		note = (Note)i.getSerializableExtra("note");
		et.setText(note.getNoteContent());
		//设置将光标定义到指定位置
		et.setSelection(note.getNoteContent().length());
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		try {
			File rootFile = new File(Environment.getExternalStorageDirectory(),MainActivity.FILENAME);
			ArrayList<Note> notes = NoteTools.readData(rootFile);
			for (Note n : notes) {
				//判断当前编辑到的note项是否是正在编辑的这项
				if(n.getNoteID().equals(note.getNoteID())){
					n.setNoteContent(et.getText().toString().trim());
					break;
				}
			}
			NoteTools.writeData(rootFile, notes);
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
