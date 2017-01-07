package com.example.screenshop;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class ScreenShotIntentService extends IntentService {

	//截屏间隔时间ms
	private static final int sleepTime = 800;

	private static SimpleDateFormat dateFormat = null;
	private static String strDate = null;
	private static String pathImage = null;
	@SuppressWarnings("unused")
	private static String nameImage = null;

	private MediaProjection mMediaProjection = null;
	private VirtualDisplay mVirtualDisplay = null;

	public static int mResultCode = 0;
	public static Intent mResultData = null;
	public static MediaProjectionManager mMediaProjectionManager1 = null;

	private WindowManager mWindowManager1 = null;
	private int windowWidth = 0;
	private int windowHeight = 0;
	private static ImageReader mImageReader = null;
	private DisplayMetrics metrics = null;
	private int mScreenDensity = 0;

	private long pics = 0L;
	private int flag = 0;

	public ScreenShotIntentService() {
		super("");
	}

	public ScreenShotIntentService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("TAG", "start________________________________________________");
		flag = 0;
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		fixedThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				createVirtualEnvironment();
				while (flag == 0) {
					startVirtual();
					try {
						Thread.sleep(sleepTime);
						//long begin = System.currentTimeMillis();
						startCapture();
						//begin = System.currentTimeMillis() - begin;
						//BaseShell.sendNotificaition(getApplicationContext(), "截屏间隔", begin + "");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		/*flag = 0;
		createVirtualEnvironment();
		while (flag == 0) {
			startVirtual();
			try {
				Thread.sleep(sleepTime);
				//long begin = System.currentTimeMillis();
				startCapture();
				//begin = System.currentTimeMillis() - begin;
				//BaseShell.sendNotificaition(getApplicationContext(), "截屏间隔", begin + "");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		/*flag = 0;
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		fixedThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				createVirtualEnvironment();
				while (flag == 0) {
					startVirtual();
					try {
						Thread.sleep(sleepTime);
						//long begin = System.currentTimeMillis();
						startCapture();
						//begin = System.currentTimeMillis() - begin;
						//BaseShell.sendNotificaition(getApplicationContext(), "截屏间隔", begin + "");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});*/
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressWarnings("deprecation")
	@SuppressLint({ "SimpleDateFormat", "InlinedApi" })
	private void createVirtualEnvironment() {
		// TODO Auto-generated method stub
		dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		strDate = dateFormat.format(new java.util.Date());
		pathImage = Environment.getExternalStorageDirectory().getPath()
				+ "/Pictures/";
		nameImage = pathImage + strDate + ".png";
		mMediaProjectionManager1 = (MediaProjectionManager) getApplication()
				.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
		mWindowManager1 = (WindowManager) getApplication().getSystemService(
				Context.WINDOW_SERVICE);
		windowWidth = mWindowManager1.getDefaultDisplay().getWidth();
		windowHeight = mWindowManager1.getDefaultDisplay().getHeight();
		metrics = new DisplayMetrics();
		mWindowManager1.getDefaultDisplay().getMetrics(metrics);
		mScreenDensity = metrics.densityDpi;
		mImageReader = ImageReader.newInstance(windowWidth, windowHeight, 0x1,
				2);
		// ImageFormat.RGB_565
		Log.i("TAG", "prepared the virtual environment");
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void startVirtual() {
		if (mMediaProjection != null) {
			Log.i("TAG", "want to display virtual");
			virtualDisplay();
		} else {
			Log.i("TAG", "start screen capture intent");
			Log.i("TAG", "want to build mediaprojection and display virtual");
			setUpMediaProjection();
			virtualDisplay();
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void setUpMediaProjection() {
		mResultData = ((DemoApplication) getApplication()).getIntent();
		mResultCode = ((DemoApplication) getApplication()).getResult();
		mMediaProjectionManager1 = ((DemoApplication) getApplication())
				.getMediaProjectionManager();
		mMediaProjection = mMediaProjectionManager1.getMediaProjection(
				mResultCode, mResultData);
		Log.i("TAG", "mMediaProjection defined");
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void virtualDisplay() {
		mVirtualDisplay = mMediaProjection.createVirtualDisplay(
				"screen-mirror", windowWidth, windowHeight, mScreenDensity,
				DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
				mImageReader.getSurface(), null, null);
		Log.i("TAG", "virtual displayed");
	}

	//private Bitmap bitmap = null;
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void startCapture() {
		/*strDate = dateFormat.format(new java.util.Date());
		nameImage = pathImage + strDate + ".png";*/
		Image image = mImageReader.acquireLatestImage();
		//Image image = mImageReader.acquireNextImage();
		if (image == null) {
			return;
		}
		int width = image.getWidth();
		int height = image.getHeight();
		final Image.Plane[] planes = image.getPlanes();
		final ByteBuffer buffer = planes[0].getBuffer();
		int pixelStride = planes[0].getPixelStride();
		int rowStride = planes[0].getRowStride();
		int rowPadding = rowStride - pixelStride * width;
		Bitmap bitmap = null;
		bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride,
				height, Bitmap.Config.ARGB_8888);
		bitmap.copyPixelsFromBuffer(buffer);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
		buffer.clear();
		if (bitmap != null) {
			//


			//
			pics++;
			/*((DemoApplication) getApplication()).setImg64(Util.fromBitmapToBase64(bitmap, 10));
			((DemoApplication) getApplication()).setPics(pics);*/
			Log.d("WWW", pics + "张-------------------");
			//回收bitmap内存
			bitmap.recycle();
			bitmap = null;
			if (pics % 30 == 0) {
				System.gc();
			}
		}
		image.close();
		Log.i("TAG", "image data captured");
		//存入SDCard
		/*if (bitmap != null) {
			try {
				File fileImage = new File(nameImage);
				if (!fileImage.exists()) {
					fileImage.createNewFile();
					Log.i("TAG", "image file created");
				}
				FileOutputStream out = new FileOutputStream(fileImage);
				if (out != null) {
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
					out.flush();
					out.close();
					Log.i("TAG", "screen image saved");
					Intent media = new Intent(
							Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					Uri contentUri = Uri.fromFile(fileImage);
					media.setData(contentUri);
					this.sendBroadcast(media);
					Log.i("TAG", "screen image saved");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void tearDownMediaProjection() {
		if (mMediaProjection != null) {
			mMediaProjection.stop();
			mMediaProjection = null;
		}
		Log.i("TAG", "mMediaProjection undefined");
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void stopVirtual() {
		if (mVirtualDisplay == null)
			return;
		mVirtualDisplay.release();
		mVirtualDisplay = null;
		Log.i("TAG", "virtual display stopped");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i("stop", "stop----------");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		// to remove mFloatLayout from windowManager
		super.onDestroy();
		tearDownMediaProjection();
		stopVirtual();
		flag = 1;
		stopSelf();
		Log.i("TAG", "application destroy-----------------------------");
		/*Intent intent = new Intent();
		intent.setAction("ScreenShot");
		sendBroadcast(intent);*/
		/*Intent intent = new Intent(ScreenShotIntentService.this, ScreenShotIntentService.class);
		startService(intent);*/
		/*int key = Integer.parseInt(TaskService.serviceOpen.split(":")[0]);
		switch (key) {
		//close service
		case 0:{
			break;
		}
		//open service again
		case 1: {
			Log.i("TAG", "application open again-----------------------------");
			Message msg = MainActivity.handlerstatic.obtainMessage();
			msg.what = 18;
			MainActivity.handlerstatic.sendMessage(msg);
			Intent intent = new Intent();
			intent.setAction("ScreenShot");
			sendBroadcast(intent);
			break;
		}
		default:
			break;
		}*/
	}

}
