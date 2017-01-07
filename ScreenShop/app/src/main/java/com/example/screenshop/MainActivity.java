package com.example.screenshop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	
	private MediaProjectionManager mMediaProjectionManager;
	private int REQUEST_MEDIA_PROJECTION = 1;
	private int result = 0;
	private Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startIntent();
		/*mMediaProjectionManager = (MediaProjectionManager)getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
		((ShotApplication)getApplication()).setMediaProjectionManager(mMediaProjectionManager);
		((ShotApplication)getApplication()).setIntent(mMediaProjectionManager.createScreenCaptureIntent());
		Intent intent = new Intent(MainActivity.this, ScreenShotService.class);
	    startService(intent);*/
		/*Intent intent = new Intent(getApplicationContext(), ScreenShotIntentService.class);
		stopService(intent);*/

	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startIntent(){
		mMediaProjectionManager = (MediaProjectionManager)getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if(intent != null && result != 0){
            Log.i("TAG", "user agree the application to capture screen");
            //Service1.mResultCode = resultCode;
            //Service1.mResultData = data;
            ((DemoApplication)getApplication()).setResult(result);
            ((DemoApplication)getApplication()).setIntent(intent);
            Intent intent = new Intent(this, ScreenShotService.class);
            startService(intent);
            Log.i("TAG", "start service Service1");
        }else{
            startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
            //Service1.mMediaProjectionManager1 = mMediaProjectionManager;
            ((DemoApplication)getApplication()).setMediaProjectionManager(mMediaProjectionManager);
        }
    }
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }else if(data != null && resultCode != 0){
                Log.i("TAG", "user agree the application to capture screen");
                //Service1.mResultCode = resultCode;
                //Service1.mResultData = data;
                result = resultCode;
                intent = data;
                ((DemoApplication)getApplication()).setResult(resultCode);
                ((DemoApplication)getApplication()).setIntent(data);
                /*Intent intent = new Intent(getApplicationContext(), ScreenShotIntentService.class);
                startService(intent);*/
                Log.i("TAG", "start service");
                //finish();
            }
        }
    }
}
