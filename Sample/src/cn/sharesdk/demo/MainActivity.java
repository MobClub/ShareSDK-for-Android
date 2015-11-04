/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package cn.sharesdk.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import cn.sharesdk.demo.widget.SlidingMenu;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.UIHandler;

/**
 * 项目的入口类，是侧栏控件的外壳
 * <p>
 * 侧栏的UI或者逻辑控制基本上都在{@link MainAdapter}中进行
 */
public class MainActivity extends Activity implements Callback {
	private static final String FILE_NAME = "pic_lovely_cats.jpg";
	public static String TEST_IMAGE;
	public static String TEST_IMAGE_URL;
	public static HashMap<Integer, String> TEST_TEXT;
	private SlidingMenu menu;
	private int orientation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orientation = getResources().getConfiguration().orientation;

		menu = new SlidingMenu(this);
		menu.setMenuItemBackground(R.color.sliding_menu_item_down, R.color.sliding_menu_item_release);
		menu.setMenuBackground(R.color.sliding_menu_background);
		menu.setTtleHeight(com.mob.tools.utils.R.dipToPx(this, 44));
		menu.setBodyBackground(R.color.sliding_menu_body_background);
		menu.setShadowRes(R.drawable.sliding_menu_right_shadow);
		menu.setMenuDivider(R.drawable.sliding_menu_sep);
		menu.setAdapter(new MainAdapter(menu));
		setContentView(menu);

		ShareSDK.initSDK(this);
		ShareSDK.registerPlatform(LaiwangCustomize.class);
		ShareSDK.setConnTimeout(20000);
		ShareSDK.setReadTimeout(20000);

		new Thread() {
			public void run() {
				TEST_IMAGE_URL = "http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg";
				initImagePath();
				initTestText();
				UIHandler.sendEmptyMessageDelayed(1, 100, MainActivity.this);
			}
		}.start();

	}

	private void initImagePath() {
		try {
			String cachePath = com.mob.tools.utils.R.getCachePath(this, null);
			TEST_IMAGE = cachePath + FILE_NAME;
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
		Log.i("TEST_IMAGE path ==>>>", TEST_IMAGE);
	}

	private void initTestText() {
		TEST_TEXT = new HashMap<Integer, String>();
		try {
			NetworkHelper network = new NetworkHelper();
			NetworkTimeOut timeOut = null;
			String resp = network.httpGet("http://mob.com/Assets/snsplat.json", null, null, timeOut);
			JSONObject json = new JSONObject(resp);
			int status = json.optInt("status");
			if (status == 200) {
				JSONArray democont = json.optJSONArray("democont");
				if (democont != null && democont.length() > 0) {
					for (int i = 0, size = democont.length(); i < size; i++) {
						JSONObject plat = democont.optJSONObject(i);
						if (plat != null) {
							int snsplat = plat.optInt("snsplat", -1);
							String cont = plat.optString("cont");
							TEST_TEXT.put(snsplat, cont);
						}
					}
				}
			}
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case 1: {
				menu.triggerItem(MainAdapter.GROUP_DEMO, MainAdapter.ITEM_DEMO);
			} break;
			case 2: {
				String text = getString(R.string.receive_rewards, msg.arg1);
				Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			} break;
		}
		return false;
	}

	/** 屏幕旋转后，此方法会被调用，以刷新侧栏的布局 */
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (orientation != newConfig.orientation) {
			orientation = newConfig.orientation;
			menu.refresh();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN
				&& !menu.isMenuShown()) {
			menu.showMenu();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN
				&& menu.isMenuShown()) {
		}
		return super.onKeyDown(keyCode, event);
	}

	/** 将action转换为String */
	public static String actionToString(int action) {
		switch (action) {
			case Platform.ACTION_AUTHORIZING: return "ACTION_AUTHORIZING";
			case Platform.ACTION_GETTING_FRIEND_LIST: return "ACTION_GETTING_FRIEND_LIST";
			case Platform.ACTION_FOLLOWING_USER: return "ACTION_FOLLOWING_USER";
			case Platform.ACTION_SENDING_DIRECT_MESSAGE: return "ACTION_SENDING_DIRECT_MESSAGE";
			case Platform.ACTION_TIMELINE: return "ACTION_TIMELINE";
			case Platform.ACTION_USER_INFOR: return "ACTION_USER_INFOR";
			case Platform.ACTION_SHARE: return "ACTION_SHARE";
			default: {
				return "UNKNOWN";
			}
		}
	}

}
