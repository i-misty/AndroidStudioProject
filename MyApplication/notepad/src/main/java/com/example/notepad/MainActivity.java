package com.example.notepad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends Activity implements OnClickListener,OnItemClickListener,OnItemLongClickListener{

	private ListView listView;
	private ImageButton imgBtn;
	public static final String FILENAME = "note.dat";
	private File rootFile; 
	private ArrayList<Note> list;
	private NoteAdapter adapter ;
	private int currentIndex;//��¼��������ѡ���±�
	
	public void initFile(){
		 rootFile = new File(Environment.getExternalStorageDirectory(),FILENAME);
		if(!rootFile.exists()){
			try {
				boolean flag = rootFile.createNewFile();
				if(flag){
					Log.v("flag", "�ļ��������");
				}else{
					Log.w("flag", "�ļ�����ʧ��");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			Log.v("flag", "�ļ��Ѵ���");
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initFile();
		
		listView = (ListView)findViewById(R.id.not_list);
		imgBtn = (ImageButton)findViewById(R.id.add_btn);
		
		try {
			//��ȡ�����������ݲ���ȡ���϶���
			list = NoteTools.readData(rootFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Ϊ��ť���õ���¼�
		imgBtn.setOnClickListener(this);
		
		if(list != null && list.size() > 0){
			//Ϊ�б�����������
			adapter = new NoteAdapter(list, this); 
			listView.setAdapter(adapter);
			//Ϊ�б�����ѡ�����¼�
			listView.setOnItemClickListener(this);
			//Ϊ�б�����ѡ����¼�(�����˵�)
			listView.setOnItemLongClickListener(this);
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		try {
			list = NoteTools.readData(rootFile);
			//֪ͨ���������ݼ��Ѹ���
			adapter = new NoteAdapter(list,this);
//			adapter.notifyDataSetChanged();
			listView.setAdapter(adapter);
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View paramView) {
		//��ת����ӱ�����¼ҳ��
		Intent i = new Intent(this,NotAddActivity.class);
		startActivity(i);
	}

	//��ѡ����ʱ����
	@Override
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			int index, long paramLong) {
		//��ȡ������������
		Note note = list.get(index);
		Intent intent = new Intent(this,EditNoteActivity.class);
		intent.putExtra("note", note);
		startActivity(intent);
	}
	//ѡ�����ʱ����
	@Override
	public boolean onItemLongClick(AdapterView<?> paramAdapterView,
			View paramView, int index, long paramLong) {
		currentIndex = index;
		startActionMode(new MyActionMode());
		return true;
	}
	
	class MyActionMode implements Callback{

		@Override
		public boolean onCreateActionMode(ActionMode paramActionMode,
				Menu paramMenu) {
			getMenuInflater().inflate(R.menu.main, paramMenu);
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode actionMode,
				MenuItem menuItem) {
			
			switch(menuItem.getItemId()){
			case R.id.action_delete:
				//ɾ������
				list.remove(currentIndex);
				try {
					//����������д��
					NoteTools.writeData(rootFile, list);
					//֪ͨ���ݼ�����
					adapter.notifyDataSetChanged();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			actionMode.finish();
			return false;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode paramActionMode,
				Menu paramMenu) {
			return false;
		}
		@Override
		public void onDestroyActionMode(ActionMode paramActionMode) {
		}
		
	}
}
