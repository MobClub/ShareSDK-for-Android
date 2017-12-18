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

package cn.sharesdk.onekeyshare;

import cn.sharesdk.onekeyshare.themes.classic.ClassicTheme;

//#if def{lang} == cn
/** 快捷分享的主题样式  */
//#elif def{lang} == en
/** the theme of onekeyshare */
//#endif
public enum OnekeyShareTheme {
	//#if def{lang} == cn
	/** 九格宫的主题样式 ,对应的实现类ClassicTheme */
	//#elif def{lang} == en
	/** the theme of gridview */
	//#endif
	CLASSIC(0, new ClassicTheme());
	
	private final int value;
	private final OnekeyShareThemeImpl impl;
	
	private OnekeyShareTheme(int value, OnekeyShareThemeImpl impl) {
		this.value = value;
		this.impl = impl;
	}
	
	public int getValue() {
		return value;
	}
	
	public OnekeyShareThemeImpl getImpl() {
		return impl;
	}
	
	public static OnekeyShareTheme fromValue(int value) {
		for (OnekeyShareTheme theme : OnekeyShareTheme.values()) {
			if (theme.value == value) {
				return theme;
			}
		}
		return CLASSIC;
	}
	
}
