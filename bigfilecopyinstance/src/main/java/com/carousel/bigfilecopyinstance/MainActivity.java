package com.carousel.bigfilecopyinstance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public  void readFile(){
        try {
            File file = new File("test.txt"); //如果文件不存在，则自动创建
            BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeUtf8("Hello, World");
            sink.writeString("测试信息", Charset.forName("UTF-8"));
            sink.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
