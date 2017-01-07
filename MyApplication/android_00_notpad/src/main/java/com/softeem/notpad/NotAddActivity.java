package com.softeem.notpad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.widget.EditText;

public class NotAddActivity extends Activity {

	private EditText et_content;
	private ArrayList<Note> notes;
	private File rootFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notadd);
		
		rootFile = new File(Environment.getExternalStorageDirectory(),MainActivity.FILENAME);
		et_content = (EditText)findViewById(R.id.note_content);
		
		try {
			//读取文件中的集合
			notes = NoteTools.readData(rootFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		String content = et_content.getText().toString();
		content = content.trim();
		if(!"".equals(content)){
			Note note = new Note();
			note.setNoteID(System.currentTimeMillis()+"");
			note.setNoteContent(content);
			//yyyyMMdd HH:mm:ss SSS
			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
			note.setNoteTime(sdf.format(new Date()));
			//记录到文件中
			notes.add(note);
			try {
				NoteTools.writeData(rootFile, notes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
