/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import m.framework.ui.widget.slidingmenu.SlidingMenu;

/** 所有侧栏页面的父类。抽象类，已经集成了{@link Handler}可直接调用。 */
public abstract class SlidingMenuPage implements Callback {
	protected SlidingMenu menu;
	protected Handler handler;
	private View pageView;

	public SlidingMenuPage(SlidingMenu menu) {
		handler = new Handler(this);
		this.menu = menu;
		pageView = initPage();
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
