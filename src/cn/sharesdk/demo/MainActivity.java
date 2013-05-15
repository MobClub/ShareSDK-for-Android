/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.io.File;
import java.io.FileOutputStream;
import cn.sharesdk.framework.AbstractWeibo;
import m.framework.ui.SlidingMenu;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.KeyEvent;

/** 
 * 项目的入口类，是侧栏控件的外壳
 * <p>
 * 侧栏的UI或者逻辑控制基本上都在{@link MainAdapter}中进行
 */
public class MainActivity extends Activity implements Callback {
	public static String TEST_IMAGE;
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
		final Handler handler = new Handler(this);
		new Thread() {
			public void run() {
				initImagePath();
				handler.sendEmptyMessageDelayed(1, 100);
			}
		}.start();
	}
	
	private void initImagePath() {
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
					&& Environment.getExternalStorageDirectory().exists()) {
				TEST_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic.png";
			}
			else {
				TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath() + "/pic.png";
			}
			File file = new File(TEST_IMAGE);
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch(Throwable t) {
			t.printStackTrace();
			TEST_IMAGE = null;
		}
	}
	
	public boolean handleMessage(Message msg) {
		menu.triggerItem(MainAdapter.GROUP_DEMO, MainAdapter.ITEM_DEMO);
		return false;
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
		AbstractWeibo.stopSDK(this);
		super.onDestroy();
	}

	/** 将action转换为String */
	public static String actionToString(int action) {
		switch (action) {
			case AbstractWeibo.ACTION_AUTHORIZING: return "ACTION_AUTHORIZING";
			case AbstractWeibo.ACTION_GETTING_FRIEND_LIST: return "ACTION_GETTING_FRIEND_LIST";
			case AbstractWeibo.ACTION_FOLLOWING_USER: return "ACTION_FOLLOWING_USER";
			case AbstractWeibo.ACTION_SENDING_DIRECT_MESSAGE: return "ACTION_SENDING_DIRECT_MESSAGE";
			case AbstractWeibo.ACTION_TIMELINE: return "ACTION_TIMELINE";
			case AbstractWeibo.ACTION_USER_INFOR: return "ACTION_USER_INFOR";
			case AbstractWeibo.ACTION_SHARE: return "ACTION_SHARE";
			default: {
				return "UNKNOWN";
			}
		}
	}
	
}
