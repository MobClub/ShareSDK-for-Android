/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package cn.sharesdk.demo;

import cn.sharesdk.demo.widget.SlidingMenu;
import android.content.Context;
import android.content.res.Resources;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;

/** 所有侧栏页面的父类 */
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

	/** 获取页面的View实例 */
	public View getPage() {
		return pageView;
	}

	public boolean handleMessage(Message msg) {
		return false;
	}

}
