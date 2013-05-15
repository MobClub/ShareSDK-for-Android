package cn.sharesdk.onekeyshare.res;

import java.io.File;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/** 快捷分享模块的资源处理器 */
public final class R {
	private static String packageName;
	static {
		packageName = "/" + R.class.getName().replace('.', '/');
        packageName = packageName.substring(0, packageName.length() - 1);
	}
	
	public static String getString(Context context, String name) {
        return cn.sharesdk.framework.res.R.getString(context, packageName, name);
	}
	
	public static Drawable getDrawable(Context context, String name) {
		return cn.sharesdk.framework.res.R.getDrawable(context, packageName, name);
	}
	
	public static Bitmap getBitmap(Context context, String name) {
		return cn.sharesdk.framework.res.R.getBitmap(context, packageName, name);
	}
	
	public static int dipToPx(Context context, int dip) {
		return cn.sharesdk.framework.res.R.dipToPx(context, dip);
	}
	
	public static Bitmap getBitmap(String path) throws Throwable {
		return cn.sharesdk.framework.res.R.getBitmap(path);
	}
	
	public static Bitmap getBitmap(String path, int inSampleSize) throws Throwable {
		return cn.sharesdk.framework.res.R.getBitmap(path, inSampleSize);
	}
	
	public static Bitmap getBitmap(File file) throws Throwable {
		return cn.sharesdk.framework.res.R.getBitmap(file);
	}
	
	public static Bitmap getBitmap(File file, int inSampleSize) throws Throwable {
		return cn.sharesdk.framework.res.R.getBitmap(file, inSampleSize);
	}
	
}
