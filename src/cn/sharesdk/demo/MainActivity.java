/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import cn.sharesdk.framework.AbstractWeibo;
import m.framework.ui.SlidingMenu;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

/** 
 * 项目的入口类，是侧栏控件的外壳
 * <p>
 * 侧栏的UI或者逻辑控制基本上都在{@link MainAdapter}中进行
 */
public class MainActivity extends Activity {
	private SlidingMenu menu;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		menu = new SlidingMenu(this);
		menu.setTtleHeight(cn.sharesdk.framework.res.R.dipToPx(this, 44));
		menu.setMenuDivider(R.drawable.sidebar_seperator);
		menu.setShadowRes(R.drawable.sidebar_right_shadow);
		menu.setAdapter(new MainAdapter(menu));
		setContentView(menu);
		
		AbstractWeibo.initSDK(this);
		menu.postDelayed(new Runnable() {
			public void run() {
				menu.triggerItem(MainAdapter.GROUP_DEMO, MainAdapter.ITEM_DEMO);
			}
		}, 100);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN
				&& !menu.isMenuShown()) {
			menu.showMenu();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0); // 结束进程
	}
	
}
