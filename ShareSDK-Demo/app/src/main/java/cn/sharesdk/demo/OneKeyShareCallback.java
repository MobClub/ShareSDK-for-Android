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

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

//#if def{lang} == cn
/**
 * OneKeyShareCallback是快捷分享功能的一个“外部回调”示例。通过
 *{@link OnekeyShare#setCallback(PlatformActionListener)}将
 *本类的示例传递进快捷分享，分享操作结束后，快捷分享会将分享结果
 *回调到本类中来做自定义处理。
 */
//#elif def{lang} == en
/**
 * onekeysharecallback is a example of "external callback" of onekeyshare.
 * Call {@link OnekeyShare#setCallback(PlatformActionListener)} method to
 * set an instance of this class into onekeyshare. Then after sharing,
 * onekeyshare will pass share result back to this class.
 */
//#endif
public class OneKeyShareCallback implements PlatformActionListener {

	public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
		//#if def{lang} == cn
		// 在这里添加分享成功的处理代码
		//#elif def{lang} == en
		// add codes here to handle success result
		//#endif
	}

	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();
		//#if def{lang} == cn
		// 在这里添加分享失败的处理代码
		//#elif def{lang} == en
		// add codes here to handle failed result
		//#endif
	}

	public void onCancel(Platform plat, int action) {
		//#if def{lang} == cn
		// 在这里添加取消分享的处理代码
		//#elif def{lang} == en
		// add codes here to handle cancel result
		//#endif
	}

}
