package com.softeem.notpad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
//工具类实现集合的写入和读取
public class NoteTools {

	public static void writeData(File rootFile,ArrayList<Note> notes) throws IOException{
		//包装节点流获取对象输出流
		FileOutputStream fos = new FileOutputStream(rootFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(notes);
		oos.flush();
		oos.close();
	}
	
	public static ArrayList<Note> readData(File rootFile) throws StreamCorruptedException, IOException, ClassNotFoundException{
		
		ArrayList<Note> notes = new ArrayList<Note>();
		FileInputStream fis = new FileInputStream(rootFile);
		
		if(fis.available() > 0){
			//包装节点流获取对象输入流
			ObjectInputStream ois = new ObjectInputStream(fis);
			notes = (ArrayList<Note>)ois.readObject();
			ois.close();
			return notes;
		}
			return notes;
		
	}
	
	
}
