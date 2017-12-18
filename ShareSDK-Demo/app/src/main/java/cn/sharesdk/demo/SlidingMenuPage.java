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

import cn.sharesdk.demo.widget.SlidingMenu;
import android.content.Context;
import android.content.res.Resources;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;

//#if def{lang} == cn
/** 所有侧栏页面的父类 */
//#elif def{lang} == en
/** super-class of all sliding menu page */
//#endif
public abstract class SlidingMenuPage implements Callback {
	private SlidingMenu menu;
	private View pageView;

	public SlidingMenuPage(SlidingMenu menu) {
		this.menu = menu;
		pageView = initPage();
	}
	
	public Context getContext() {
		return menu.getContext();
	}
	
	public Resources getResources() {
		return menu.getResources();
	}
	
	public boolean isMenuShown() {
		return menu.isMenuShown();
	}
	
	public void hideMenu() {
		menu.hideMenu();
	}
	
	public void showMenu() {
		menu.showMenu();
	}
	
	public View findViewById(int id) {
		return menu.findViewById(id);
	}
	
	protected abstract View initPage();
	
	//#if def{lang} == cn
	/** 获取页面的View实例 */
	//#elif def{lang} == en
	/** returns body view of the page */
	//#endif
	public View getPage() {
		return pageView;
	}
	
	public boolean handleMessage(Message msg) {
		return false;
	}
	
}
