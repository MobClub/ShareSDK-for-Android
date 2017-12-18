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

package cn.sharesdk.demo.yxapi;
 
import cn.sharesdk.yixin.utils.YXMessage;
import cn.sharesdk.yixin.utils.YixinHandlerActivity;

//#if def{lang} == cn
/** 易信客户端回调activity示例 */
//#elif def{lang} == en
/** Callback activity demo of Yixin */
//#endif
public class YXEntryActivity extends YixinHandlerActivity {
	
	//#if def{lang} == cn
	/** 处理易信向第三方应用发起的消息 */
	//#elif def{lang} == en
	/** Handling message which sent from yixin */
	//#endif
	public void onReceiveMessageFromYX(YXMessage msg) {
		//#if def{lang} == cn
		// 从易信网第三方应用发送消息的处理，从这个方法开始，不过当前易信似乎还没有
		// 提供这样子的功能入口，因此这个方法暂时没有作用，但是如果易信客户端开放了
		// 入口，这个方法就可以接收到消息
		//#elif def{lang} == en
		// Yixin has not provided this interface currently, but when it provide, 
		// this method will receive message from yixin
		//#endif
	}
	
}
