/*
 * Offical Website:http://www.ShareSDK.cn
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.HashMap;

import android.util.Log;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * onekeysharecallback is a example of "external callback" of onekeyshare.
 * Call {@link OnekeyShare#setCallback(PlatformActionListener)} method to
 * set an instance of this class into onekeyshare. Then after sharing,
 * onekeyshare will pass share result back to this class.
 */
public class OneKeyShareCallback implements PlatformActionListener {

	public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
		Log.d(getClass().getSimpleName(), res.toString());
		// add codes here to handle success result
	}

	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();
		// add codes here to handle failed result
	}

	public void onCancel(Platform plat, int action) {
		// add codes here to handle cancel result
	}

}
