/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import cn.sharesdk.framework.AuthorizeAdapter;

/** 一个用于演示{@link AuthorizeAdapter}的例子  */
public class MyAdapter extends AuthorizeAdapter {
	
	public void onCreate() {
		System.out.println("============== onCreate");
		System.out.println("==============     Activity: " + getActivity());
		System.out.println("==============     WeiboName: " + getWeiboName());
		System.out.println("==============     TitleLayout: " + getTitleLayout());
		System.out.println("==============     WebBody: " + getWebBody());
	}
	
	public void onDestroy() {
		System.out.println("============== onDestroy");
	}
	
}
