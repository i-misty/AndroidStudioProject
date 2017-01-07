package com.example.notepad;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//��������
public class NoteAdapter extends BaseAdapter{

	private ArrayList<Note> notes;
	private Context context;
	
	public NoteAdapter(ArrayList<Note> notes,Context context) {
		this.notes = notes;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return notes.size();
	}

	@Override
	public Object getItem(int paramInt) {
		return null;
	}

	@Override
	public long getItemId(int paramInt) {
		return 0;
	}

	@Override
	public View getView(int index, View paramView, ViewGroup paramViewGroup) {
		View view = LayoutInflater.from(context).inflate(R.layout.note_item, null);
		
		TextView tv1 = (TextView)view.findViewById(R.id.note_item_content);
		TextView tv2 = (TextView)view.findViewById(R.id.note_item_time);
		
		tv1.setText(notes.get(index).getNoteContent());
		tv2.setText(notes.get(index).getNoteTime());
		
		return view;
	}

}
