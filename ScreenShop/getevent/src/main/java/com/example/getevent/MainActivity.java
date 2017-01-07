package com.example.getevent;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private ContentResolver resolver ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPoint();

    }

    public void getPoint(){
        Log.d("count", "start----->");
        resolver = getContentResolver();
       Cursor cursor =  resolver.query(Uri.parse("content://settings/system/pointer_location"),null,null,null,null);
        Log.d("count", "curror");

           Log.d("count", cursor.getColumnCount()+"");
            Log.d("ColumnNamesLenght", cursor.getColumnNames().length+"");
          /* for (String s : cursor.getColumnNames()){
               Log.d("ColumnNames", s+"");
           }*/
        while(cursor.moveToNext()){
           // cursor.getColumnIndex(cursor.getColumnName(0));

            Log.d("count",cursor.getString(1)+"----->"+ cursor.getString(1)+"--------------->"+ cursor.getString(2));

        }
            cursor.close();
    }
}
