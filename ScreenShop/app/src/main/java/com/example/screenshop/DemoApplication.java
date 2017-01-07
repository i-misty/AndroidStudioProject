package com.example.screenshop;

import android.app.Application;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;

/**
 * Created by user10 on 2016/11/19.
 */

public class DemoApplication extends Application {
    // //////////////////////////截屏////////////////////////////////////////////
    private Intent intent;
    private MediaProjectionManager mMediaProjectionManager;
    private int result;
    private int flag = 0;
    private long pics = 0L;

    private String img64; // 图片64编码

    public long getPics() {
        return pics;
    }

    public void setPics(long pics) {
        this.pics = pics;
    }

    public String getImg64() {
        return img64;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
    }

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

    public void setResult(int result1) {
        this.result = result1;
    }

    public int getResult() {
        return result;
    }
}
