package com.example.screenshop;

import android.app.Application;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;

public class ShotApplication extends Application {

	private Intent intent;
	private MediaProjectionManager mMediaProjectionManager;
	private int result;
	private int flag = 0;

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Intent getIntent() {
		return intent;
	}

	public MediaProjectionManager getMediaProjectionManager() {
		return mMediaProjectionManager;
	}

	public void setIntent(Intent intent1) {
		this.intent = intent1;
	}

	public void setMediaProjectionManager(
			MediaProjectionManager mMediaProjectionManager) {
		this.mMediaProjectionManager = mMediaProjectionManager;
	}
	
	public void setResult(int result1){
        this.result = result1;
    }
	
	public int getResult(){
        return result;
    }
}
