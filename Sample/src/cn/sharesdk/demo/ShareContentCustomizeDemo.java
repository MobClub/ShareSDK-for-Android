//#if def{lang} == cn
/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */
//#elif def{lang} == en
/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. 
 * If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 * 
 * Copyright (c) 2013 mob.com. All rights reserved.
 */
//#endif

package cn.sharesdk.demo;

import com.mob.MobSDK;

import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

//#if def{lang} == cn
/**
 * 快捷分享项目现在添加为不同的平台添加不同分享内容的方法。
 *本类用于演示如何区别Twitter的分享内容和其他平台分享内容。
 *本类会在{@link DemoPage#showShare(boolean, String)}方法
 *中被调用。
 */
//#elif def{lang} == en
/**
 * OnekeyShare allows you to customize share content according to 
 * the target platform by implements ShareContentCustomizeCallback 
 * and override its {@link #onShare(Platform, ShareParams)} method
 * 
 * This demo defines a callback to shorten share content of Twitter. 
 */
//#endif
public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {

	public void onShare(Platform platform, ShareParams paramsToShare) {
		if(platform instanceof CustomPlatform){
			return;
		}
		int id = ShareSDK.platformNameToId(platform.getName());
		if (MainActivity.testText != null && MainActivity.testText.containsKey(id)) {
			String text = MainActivity.testText.get(id);
			paramsToShare.setText(text);
		} else if ("Twitter".equals(platform.getName())) {
			//#if def{lang} == cn
			// 改写twitter分享内容中的text字段，否则会超长，
			// 因为twitter会将图片地址当作文本的一部分去计算长度
			//#elif def{lang} == en
			// shorten the text field of twitter share content 
			//#endif
			String text = MobSDK.getContext().getString(R.string.share_content_short);
			paramsToShare.setText(text);
		} else if ("QQ".equals(platform.getName())) {
			paramsToShare.setHidden(1);
		} else if ("Facebook".equals(platform.getName())) {
			paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
		}
	}

}
