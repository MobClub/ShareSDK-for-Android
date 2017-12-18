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
 *  If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 * 
 * Copyright (c) 2013 mob.com. All rights reserved.
 */
//#endif

package cn.sharesdk.demo;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.demo.widget.SlidingMenu;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

//#if def{lang} == cn
/**
 * 项目的入口类，是侧栏控件的外壳
 * <p>
 * 侧栏的UI或者逻辑控制基本上都在{@link MainAdapter}中进行
 */
//#elif def{lang} == en

/**
 * entrance of the project, UI shell of sliding menu
 * ui logics and events are handled by {@link MainAdapter}
 */
//#endif
public class MainActivity extends Activity implements Callback {
	public static String testImage;
	public static String testVideo;
	public static String testImageUrl;
	public static HashMap<Integer, String> testText;
	private SlidingMenu menu;
	private int orientation;
	private String platformName;

	public static final int REQUEST_CODE_GO_VIDEO = 10100;
	public static final int REQUEST_CODE_GO_PIC = 10101;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orientation = getResources().getConfiguration().orientation;

		menu = new SlidingMenu(this);
		menu.setMenuItemBackground(R.color.sliding_menu_item_down, R.color.sliding_menu_item_release);
		menu.setMenuBackground(R.color.sliding_menu_background);
		menu.setTtleHeight(ResHelper.dipToPx(this, 44));
		menu.setBodyBackground(R.color.sliding_menu_body_background);
		menu.setShadowRes(R.drawable.sliding_menu_right_shadow);
		menu.setMenuDivider(R.drawable.sliding_menu_sep);
		menu.setAdapter(new MainAdapter(menu));
		setContentView(menu);

		ShareSDK.registerPlatform(LaiwangCustomize.class);
		ShareSDK.setConnTimeout(20000);
		ShareSDK.setReadTimeout(20000);
		new Thread() {
			public void run() {
				String[] urls = randomPic();
				testImageUrl = urls[1];
				try {
					testImage = BitmapHelper.downloadBitmap(MainActivity.this, urls[1]);
				} catch (Throwable t) {
					t.printStackTrace();
					testImage = null;
				}
				initTestText();
				
				String videoUrl = "http://f1.webshare.mob.com/dvideo/demovideos.mp4";
				try {
					testVideo = new NetworkHelper().downloadCache(MainActivity.this, videoUrl, "videos", true, null);
				} catch (Throwable e) {
					e.printStackTrace();
				}
				
				UIHandler.sendEmptyMessageDelayed(1, 100, MainActivity.this);
			}
		}.start();
	}

	private void initTestText() {
		testText = new HashMap<Integer, String>();
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
							testText.put(snsplat, cont);
						}
					}
				}
			}
		} catch (Throwable t) {
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

	//#if def{lang} == cn
	/** 屏幕旋转后，此方法会被调用，以刷新侧栏的布局 */
	//#elif def{lang} == en

	/** this method will be called after the screen rotation to refresh the sliding menu */
	//#endif
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

	//#if def{lang} == cn
	/** 将action转换为String */
	//#elif def{lang} == en

	/** converts ShareSDK actions into string */
	//#endif
	public static String actionToString(int action) {
		switch (action) {
			case Platform.ACTION_AUTHORIZING: {
				return "ACTION_AUTHORIZING";
			}
			case Platform.ACTION_GETTING_FRIEND_LIST: {
				return "ACTION_GETTING_FRIEND_LIST";
			}
			case Platform.ACTION_FOLLOWING_USER: {
				return "ACTION_FOLLOWING_USER";
			}
			case Platform.ACTION_SENDING_DIRECT_MESSAGE: {
				return "ACTION_SENDING_DIRECT_MESSAGE";
			}
			case Platform.ACTION_TIMELINE: {
				return "ACTION_TIMELINE";
			}
			case Platform.ACTION_USER_INFOR: {
				return "ACTION_USER_INFOR";
			}
			case Platform.ACTION_SHARE: {
				return "ACTION_SHARE";
			}
			default: {
				return "UNKNOWN";
			}
		}
	}
	public static String[] randomPic() {
		String url = "http://git.oschina.net/alexyu.yxj/MyTmpFiles/raw/master/kmk_pic_fld/";
		String urlSmall = "http://git.oschina.net/alexyu.yxj/MyTmpFiles/raw/master/kmk_pic_fld/small/";
		String[] pics = new String[]{
				"120.JPG",
				"127.JPG",
				"130.JPG",
				"18.JPG",
				"184.JPG",
				"22.JPG",
				"236.JPG",
				"237.JPG",
				"254.JPG",
				"255.JPG",
				"263.JPG",
				"265.JPG",
				"273.JPG",
				"37.JPG",
				"39.JPG",
				"IMG_2219.JPG",
				"IMG_2270.JPG",
				"IMG_2271.JPG",
				"IMG_2275.JPG",
				"107.JPG"
		};
		int index = (int) (System.currentTimeMillis() % pics.length);
		return new String[]{
				url + pics[index],
				urlSmall + pics[index]
		};
	}

//	public void startSelectImages() {
//		Intent intent = new Intent(Intent.ACTION_PICK, null);
//		intent.setType("image/*");
//		startActivityForResult(intent, REQUEST_CODE_GO_PIC);
//	}
//
//	public void startSelectVideo(String platformName) {
//		this.platformName = platformName;
//		if (Build.VERSION.SDK_INT < 19) {
//			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//			intent.setType("image/* video/*");
//			startActivityForResult(Intent.createChooser(intent, "视频选择"), REQUEST_CODE_GO_VIDEO);
//		} else {
//			Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//			intent.addCategory(Intent.CATEGORY_OPENABLE);
//			intent.setType("image/*");
//			intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
//			startActivityForResult(intent, REQUEST_CODE_GO_VIDEO);
//		}
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == RESULT_OK) {
//			if (requestCode == REQUEST_CODE_GO_VIDEO || requestCode == REQUEST_CODE_GO_PIC) {
//				if (data != null) {
//					String filePath = MeipaiUtils.getImagePathFromUri(this, data.getData());
//					String filePa = data.getData().getPath();
//					if (!TextUtils.isEmpty(filePath)) {
//						if (requestCode == REQUEST_CODE_GO_PIC) {
//							mediaSelected.onResult(1,data.getData());
//						} else {
//							mediaSelected.onResult(0,filePath);
//
//						}
//					}
//				}
//			}
//		}
//	}
//
//	public void setMediaSelected(iSelectMeidaSouces mediaSelected) {
//		this.mediaSelected = mediaSelected;
//	}
//
//	public interface iSelectMeidaSouces {
//		void onResult(int type, Uri uri);
//		void onResult(int type,String filePath);
//	}
}
