package cn.sharesdk.demo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ActivityHook {
    /**
     * java.lang.IllegalStateException: Only fullscreen opaque activities can request orientation
     * 修复android 8.0存在的问题
     * 在Activity中onCreate()中super之前调用
     * @param activity
     */
    public static void hookOrientation(Activity activity) {
        //目标版本8.0及其以上
        if (activity.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.O) {
            if (isTranslucentOrFloating(activity)) {
                fixOrientation(activity);
            }
        }
    }

    /**
     * 设置屏幕不固定，绕过检查
     *
     * @param activity
     */
    private static void fixOrientation(Activity activity) {
        try {
            Class<Activity> activityClass = Activity.class;
            Field mActivityInfoField = activityClass.getDeclaredField("mActivityInfo");
            mActivityInfoField.setAccessible(true);
            ActivityInfo activityInfo = (ActivityInfo) mActivityInfoField.get(activity);
            //设置屏幕不固定
            activityInfo.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查屏幕 横竖屏或者锁定就是固定
     *
     * @param activity
     * @return
     */
    private static boolean isTranslucentOrFloating(Activity activity) {
        boolean isTranslucentOrFloating = false;
        try {
            Class<?> styleableClass = Class.forName("com.android.internal.R$styleable");
            Field WindowField = styleableClass.getDeclaredField("Window");
            WindowField.setAccessible(true);
            int[] styleableRes = (int[]) WindowField.get(null);
            //先获取到TypedArray
            final TypedArray typedArray = activity.obtainStyledAttributes(styleableRes);
            Class<?> ActivityInfoClass = ActivityInfo.class;
            //调用检查是否屏幕旋转
            Method isTranslucentOrFloatingMethod = ActivityInfoClass.getDeclaredMethod("isTranslucentOrFloating", TypedArray.class);
            isTranslucentOrFloatingMethod.setAccessible(true);
            isTranslucentOrFloating = (boolean) isTranslucentOrFloatingMethod.invoke(null, typedArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }
}
