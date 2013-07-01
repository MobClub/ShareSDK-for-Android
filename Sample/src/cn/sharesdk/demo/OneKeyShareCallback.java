/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.HashMap;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.WeiboActionListener;

/**
 * OneKeyShareCallback是快捷分享功能的一个“外部回调”示例。
 *演示了如何通过添加extra的方法，将快捷分享的分享结果回调到
 *外面来做自定义处理。
 */
public class OneKeyShareCallback implements WeiboActionListener {

	public void onComplete(AbstractWeibo weibo, int action,
			HashMap<String, Object> res) {
		System.out.println(res.toString());
		// 在这里添加分享成功的处理代码
	}

	public void onError(AbstractWeibo weibo, int action, Throwable t) {
		t.printStackTrace();
		// 在这里添加分享失败的处理代码
	}

	public void onCancel(AbstractWeibo weibo, int action) {
		// 在这里添加取消分享的处理代码
	}

}
