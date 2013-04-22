package cn.sharesdk.onekeyshare.res;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

/** 快捷分享模块的资源处理器 */
public final class R {
	private static final int BITMAP_DPI = 240;
	private static HashMap<String, String> strings;
	private static float density;
	
	public static String getString(Context context, String name) {
		if (strings == null) {
			initString();
		}
		
		String res = null;
		if (strings != null) {
			res = strings.get(name);
		}
		
		return res == null ? "" : res;
	}
	
	public static Drawable getDrawable(Context context, String name) {
		Bitmap bm = getBitmap(context, name);
		if (bm == null || bm.isRecycled()) {
			return null;
		}
		
		if (bm.getNinePatchChunk() == null) {
			BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);
			return bitmapDrawable;
		}
		else {
			Rect padding = new Rect();
			NinePatchTool.readPaddingFromChunk(bm.getNinePatchChunk(), padding);
			NinePatchDrawable d = new NinePatchDrawable(context.getResources(),
					bm, bm.getNinePatchChunk(), padding, null);
			return d;
		}
	}
	
	public static Bitmap getBitmap(Context context, String name) {
		try {
			String packageName = "/" + R.class.getName().replace('.', '/');
	        packageName = packageName.substring(0, packageName.length() - 1);
	        InputStream is = R.class.getResourceAsStream(packageName + name + ".png");
	        if (is == null) {
	        	is = R.class.getResourceAsStream(packageName + name + ".9.png");
	        }
	        if (is == null) {
	        	is = R.class.getResourceAsStream(packageName + name + ".jpg");
	        }
	        Bitmap bm = NinePatchTool.decodeFromStream(is);
			is.close();
			return bm;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	private static void initString() {
		try {
			strings = new HashMap<String, String>();
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            String packageName = "/" + R.class.getName().replace('.', '/');
            packageName = packageName.substring(0, packageName.length() - 1);
            InputStream is = R.class.getResourceAsStream(packageName + "strings.xml");
            parser.setInput(is, "utf-8");
            int event = parser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT) {
            	if (event == XmlPullParser.START_TAG
            			&& "string".equals(parser.getName())) {
            		String name = parser.getAttributeValue(0);
            		event = parser.next();
            		String text = null;
            		if (event == XmlPullParser.TEXT) {
            			text = parser.getText();
            		}
            		strings.put(name, text);
            	}
            	event = parser.next();
            }
			is.close();
		} catch (Throwable t) {
			t.printStackTrace();
			strings = null;
		}
	}
	
	public static float getBitmapScale(Context context) {
		float density = context.getResources().getDisplayMetrics().density;
		return density * 160 / BITMAP_DPI;
	}
	
	private static Bitmap getBitmap(InputStream is, int inSampleSize) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inSampleSize = inSampleSize;
		return BitmapFactory.decodeStream(is, null, opt);
	}
	
	public static Bitmap getBitmap(File file) throws Throwable {
		return getBitmap(file, 1);
	}
	
	public static Bitmap getBitmap(File file, int inSampleSize) throws Throwable {
		FileInputStream fis = new FileInputStream(file);
		Bitmap bm = getBitmap(fis, inSampleSize);
		fis.close();
		return bm;
	}
	
	public static Bitmap getBitmap(String path) throws Throwable {
		return getBitmap(path, 1);
	}
	
	public static Bitmap getBitmap(String path, int inSampleSize) throws Throwable {
		return getBitmap(new File(path), inSampleSize);
	}
	
	public static int dipToPx(Context context, int dip) {
		if (density <= 0) {
			density = context.getResources().getDisplayMetrics().density;
		}
		return (int) (dip * density + 0.5f);
	}
	
}
