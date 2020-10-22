//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.sharesdk.demo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@TargetApi(17)
public class ScreenShotListenManager {
	private static final String TAG = "ScreenShotListenManager";
	private static final String[] MEDIA_PROJECTIONS = new String[]{"_data", "datetaken"};
	private static final String[] MEDIA_PROJECTIONS_API_16 = new String[]{"_data", "datetaken", "width", "height"};
	private static final String[] KEYWORDS = new String[]{"screenshot", "screen_shot", "screen-shot", "screen shot", "screencapture", 
			"screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", "screen-cap", "screen cap"};
	private static Point sScreenRealSize;
	private final List<String> sHasCallbackPaths = new ArrayList();
	private Context context;
	private OnScreenShotListener listener;
	private long startListenTime;
	private MediaContentObserver internalObserver;
	private MediaContentObserver externalObserver;
	private final Handler uiHandler = new Handler(Looper.getMainLooper());

	private ScreenShotListenManager(Context context) {
		if(context == null) {
			throw new IllegalArgumentException("The context must not be null.");
		} else {
			this.context = context;
			if(sScreenRealSize == null) {
				sScreenRealSize = this.getRealScreenSize();
				if(sScreenRealSize != null) {
					Log.d("ScreenShotListenManager", "Screen Real Size: " + sScreenRealSize.x + " * " + sScreenRealSize.y);
				} else {
					Log.w("ScreenShotListenManager", "Get screen real size failed.");
				}
			}

		}
	}

	public static ScreenShotListenManager newInstance(Context context) {
		assertInMainThread();
		return new ScreenShotListenManager(context);
	}

	public void startListen() {
		assertInMainThread();
		this.sHasCallbackPaths.clear();
		this.startListenTime = System.currentTimeMillis();
		this.internalObserver = new MediaContentObserver(Media.INTERNAL_CONTENT_URI, this.uiHandler);
		this.externalObserver = new MediaContentObserver(Media.EXTERNAL_CONTENT_URI, this.uiHandler);
		this.context.getContentResolver().registerContentObserver(Media.INTERNAL_CONTENT_URI, false, this.internalObserver);
		this.context.getContentResolver().registerContentObserver(Media.EXTERNAL_CONTENT_URI, false, this.externalObserver);
	}

	public void stopListen() {
		assertInMainThread();
		if(this.internalObserver != null) {
			try {
				this.context.getContentResolver().unregisterContentObserver(this.internalObserver);
			} catch (Exception var3) {
				var3.printStackTrace();
			}

			this.internalObserver = null;
		}

		if(this.externalObserver != null) {
			try {
				this.context.getContentResolver().unregisterContentObserver(this.externalObserver);
			} catch (Exception var2) {
				var2.printStackTrace();
			}

			this.externalObserver = null;
		}

		this.startListenTime = 0L;
		this.sHasCallbackPaths.clear();
	}

	private void handleMediaContentChange(Uri contentUri) {
		Cursor cursor = null;
		try {
			cursor = this.context.getContentResolver().query(contentUri, VERSION.SDK_INT < 16 ? MEDIA_PROJECTIONS : MEDIA_PROJECTIONS_API_16,
					(String)null, (String[])null, "date_added desc limit 1");
			if(cursor == null) {
				Log.e("ScreenShotListenManager", "Deviant logic.");
				return;
			}

			if(cursor.moveToFirst()) {
				int e = cursor.getColumnIndex("_data");
				int dateTakenIndex = cursor.getColumnIndex("datetaken");
				int widthIndex = -1;
				int heightIndex = -1;
				if(VERSION.SDK_INT >= 16) {
					widthIndex = cursor.getColumnIndex("width");
					heightIndex = cursor.getColumnIndex("height");
				}

				String data = cursor.getString(e);
				long dateTaken = cursor.getLong(dateTakenIndex);
				boolean width = false;
				boolean height = false;
				int width1;
				int height1;
				if(widthIndex >= 0 && heightIndex >= 0) {
					width1 = cursor.getInt(widthIndex);
					height1 = cursor.getInt(heightIndex);
				} else {
					Point size = this.getImageSize(data);
					width1 = size.x;
					height1 = size.y;
				}

				this.handleMediaRowData(data, dateTaken, width1, height1);
				return;
			}

			Log.d("ScreenShotListenManager", "Cursor no data.");
		} catch (Exception var16) {
			var16.printStackTrace();
			return;
		} finally {
			if(cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
	}

	private Point getImageSize(String imagePath) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, options);
		return new Point(options.outWidth, options.outHeight);
	}

	private void handleMediaRowData(String data, long dateTaken, int width, int height) {
		if(this.checkScreenShot(data, dateTaken, width, height)) {
			Log.d("ScreenShotListenManager", "ScreenShot: path = " + data + "; size = " + width + " * " + height + "; date = " + dateTaken);
			if(this.listener != null && !this.checkCallback(data)) {
				this.listener.onShot(data);
			}
		} else {
			Log.w("ScreenShotListenManager", "Media content changed, but not screenshot: path = " + data + "; size = " + width + " * " 
					+ height + "; date = " + dateTaken);
		}

	}

	private boolean checkScreenShot(String data, long dateTaken, int width, int height) {
		long currentTime = System.currentTimeMillis() - dateTaken;
		if(dateTaken >= this.startListenTime && currentTime <= 20000L) {
			if(sScreenRealSize == null || width <= sScreenRealSize.x && height <= sScreenRealSize.y || height <= sScreenRealSize.x 
					&& width <= sScreenRealSize.y) {
				if(TextUtils.isEmpty(data)) {
					return false;
				} else {
					data = data.toLowerCase();
					String[] var11 = KEYWORDS;
					int var10 = KEYWORDS.length;

					for(int var9 = 0; var9 < var10; var9++) {
						String keyWork = var11[var9];
						if(data.contains(keyWork)) {
							return true;
						}
					}

					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean checkCallback(String imagePath) {
		if(this.sHasCallbackPaths.contains(imagePath)) {
			return true;
		} else {
			if(this.sHasCallbackPaths.size() >= 20) {
				for(int i = 0; i < 5; i++) {
					this.sHasCallbackPaths.remove(0);
				}
			}

			this.sHasCallbackPaths.add(imagePath);
			return false;
		}
	}

	private Point getRealScreenSize() {
		Point screenSize = null;

		try {
			screenSize = new Point();
			WindowManager e = (WindowManager)this.context.getSystemService("window");
			Display defaultDisplay = e.getDefaultDisplay();
			if(VERSION.SDK_INT >= 17) {
				defaultDisplay.getRealSize(screenSize);
			} else {
				try {
					Method e1 = Display.class.getMethod("getRawWidth", new Class[0]);
					Method getRawH = Display.class.getMethod("getRawHeight", new Class[0]);
					screenSize.set(((Integer)e1.invoke(defaultDisplay, new Object[0])).intValue(), 
							((Integer)getRawH.invoke(defaultDisplay, new Object[0])).intValue());
				} catch (Exception var6) {
					screenSize.set(defaultDisplay.getWidth(), defaultDisplay.getHeight());
					var6.printStackTrace();
				}
			}
		} catch (Exception var7) {
			var7.printStackTrace();
		}

		return screenSize;
	}

	public void setListener(ScreenShotListenManager.OnScreenShotListener listener) {
		this.listener = listener;
	}

	private static void assertInMainThread() {
		if(Looper.myLooper() != Looper.getMainLooper()) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			String methodMsg = null;
			if(elements != null && elements.length >= 4) {
				methodMsg = elements[3].toString();
			}

			throw new IllegalStateException("Call the method must be in main thread: " + methodMsg);
		}
	}

	private class MediaContentObserver extends ContentObserver {
		private Uri contentUri;

		public MediaContentObserver(Uri contentUri, Handler handler) {
			super(handler);
			this.contentUri = contentUri;
		}

		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			handleMediaContentChange(this.contentUri);
		}
	}

	public interface OnScreenShotListener {
		void onShot(String var1);
	}
}
