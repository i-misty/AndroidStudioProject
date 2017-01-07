package com.softeem.notpad;

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
	private int currentIndex;//记录被长按的选项下标
	
	public void initFile(){
		 rootFile = new File(Environment.getExternalStorageDirectory(),FILENAME);
		if(!rootFile.exists()){
			try {
				boolean flag = rootFile.createNewFile();
				if(flag){
					Log.v("flag", "文件创建完成");
				}else{
					Log.w("flag", "文件创建失败");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			Log.v("flag", "文件已存在");
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
			//读取输入流中内容并获取集合对象
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
		//为按钮设置点击事件
		imgBtn.setOnClickListener(this);
		
		if(list != null && list.size() > 0){
			//为列表设置适配器
			adapter = new NoteAdapter(list, this); 
			listView.setAdapter(adapter);
			//为列表设置选项被点击事件
			listView.setOnItemClickListener(this);
			//为列表设置选项长按事件(呼出菜单)
			listView.setOnItemLongClickListener(this);
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		try {
			list = NoteTools.readData(rootFile);
			//通知适配器数据集已更新
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
		//跳转到添加备忘记录页面
		Intent i = new Intent(this,NotAddActivity.class);
		startActivity(i);
	}

	//当选项被点击时触发
	@Override
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			int index, long paramLong) {
		//获取被点击项的内容
		Note note = list.get(index);
		Intent intent = new Intent(this,EditNoteActivity.class);
		intent.putExtra("note", note);
		startActivity(intent);
	}
	//选项被长按时触发
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
				//删除该项
				list.remove(currentIndex);
				try {
					//将集合重新写入
					NoteTools.writeData(rootFile, list);
					//通知数据集更新
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
