/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.twitter.Twitter;

/**
 * 快捷分享项目现在添加为不同的平台添加不同分享内容的方法。
 *本类用于演示如何区别新浪微博的分享内容和其他平台分享内容。
 *本类会在{@link DemoPage#showShare(boolean, String)}方法
 *中被调用。
 */
public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {

	public void onShare(Platform platform, ShareParams paramsToShare) {
		// 改写twitter分享内容中的text字段，否则会超长，
		// 因为twitter会将图片地址当作文本的一部分去计算长度
		if (Twitter.NAME.equals(platform.getName())) {
			Twitter.ShareParams sp = (Twitter.ShareParams) paramsToShare;
			sp.text = platform.getContext().getString(R.string.share_content_short);
		}
	}

}
