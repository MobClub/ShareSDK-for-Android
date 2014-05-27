/*
 * Offical Website:http://www.ShareSDK.cn
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import m.framework.ui.widget.slidingmenu.SlidingMenu;

/** super-class of all sliding menu page */
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

	/** returns body view of the page */
	public View getPage() {
		return pageView;
	}

	public boolean handleMessage(Message msg) {
		return false;
	}

}
