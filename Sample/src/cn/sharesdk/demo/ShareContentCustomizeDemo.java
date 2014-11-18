/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 mob.com. All rights reserved.
 */

package cn.sharesdk.demo;

import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

/**
 * OnekeyShare allows you to customize share content according to
 * the target platform by implements ShareContentCustomizeCallback
 * and override its {@link #onShare(Platform, ShareParams)} method
 *
 * This demo defines a callback to shorten share content of Twitter.
 */
public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {

	public void onShare(Platform platform, ShareParams paramsToShare) {
		if(platform instanceof CustomPlatform){
			return;
		}
		int id = ShareSDK.platformNameToId(platform.getName());
		if (MainActivity.TEST_TEXT != null && MainActivity.TEST_TEXT.containsKey(id)) {
			String text = MainActivity.TEST_TEXT.get(id);
			paramsToShare.setText(text);
		} else if ("Twitter".equals(platform.getName())) {
			// shorten the text field of twitter share content
			String text = platform.getContext().getString(R.string.share_content_short);
			paramsToShare.setText(text);
		}
	}

}
