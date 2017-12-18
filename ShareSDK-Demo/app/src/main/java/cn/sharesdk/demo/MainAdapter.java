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

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import cn.sharesdk.demo.widget.MenuAdapter;
import cn.sharesdk.demo.widget.SlidingMenu;
import cn.sharesdk.demo.widget.SlidingMenuItem;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

//#if def{lang} == cn
/** 
 * 侧栏实际逻辑类。
 * <p>
 * 负责完成侧栏目录的展示、事件的监听、页面主体的显示和切换等等业务。
 */
//#elif def{lang} == en
/** Adapter of sliding menu, handles ui events of sliding menu */
//#endif
public class MainAdapter extends MenuAdapter 
		implements Callback, PlatformActionListener {
	//#if def{lang} == cn
	/** "接口"分组 */
	//#elif def{lang} == en
	/** "interfaces" group */
	//#endif
	public static final int GROUP_DEMO = 1;
	//#if def{lang} == cn
	/** “更多”分组 */
	//#elif def{lang} == en
	/** "more" group*/
	//#endif
	public static final int GROUP_MORE = 2;
	
	//#if def{lang} == cn
	/** “接口”项 */
	//#elif def{lang} == en
	/** "interface" item */
	//#endif
	public static final int ITEM_DEMO = 1;
	//#if def{lang} == cn
	/** “授权”项 */
	//#elif def{lang} == en
	/** "authorize" item */
	//#endif
	public static final int ITEM_AUTH = 2;
	//#if def{lang} == cn
	/** “微信”项 */
	//#elif def{lang} == en
	/** "wechat" item */
	//#endif
	public static final int ITEM_WECHAT = 3;
	//#if def{lang} == cn
	/** “易信”项 */
	//#elif def{lang} == en
	/** "yixin" item */
	//#endif
	public static final int ITEM_YIXIN = 4;
	//#if def{lang} == cn
	/** “支付宝”项  */
	//#elif def{lang} == en
	/** "Alipay" item */
	//#endif
	public static final int ITEM_ALIPAY = 5;
	//#if def{lang} == cn
	/** “二维码”项  */
	//#elif def{lang} == en
	/** "QR Code" item */
	//#endif
	public static final int ITEM_QRCODE = 100;
		
	//#if def{lang} == cn
	/** “自定义接口”项 */
	//#elif def{lang} == en
	/** "custom interfaces" item */
	//#endif
	public static final int ITEM_CUSTOMER = 6;
	//#if def{lang} == cn
	/** “关注新浪微博”项 */
	//#elif def{lang} == en
	/** "follow our sina weibo" item */
	//#endif
	public static final int ITEM_FOLLOW_SINAWEIBO = 7;
	//#if def{lang} == cn
	/** “关注腾讯微博”项 */
	//#elif def{lang} == en
	/** "follow our tencent weibo" item */
	//#endif
	public static final int ITEM_FOLLOW_TECENTWEIBO = 8;
	//#if def{lang} == cn
	/** “关注官方网站”项 */
	//#elif def{lang} == en
	/** "visit our website" item */
	//#endif
	public static final int ITEM_VISIT_WEBSITE = 9;
	//#if def{lang} == cn
	/** “版本”项 */
	//#elif def{lang} == en
	/** "version" item */
	//#endif
	public static final int ITEM_ABOUT = 10;
	//#if def{lang} == cn
	/** “自定义分享内容”项 */
	//#elif def{lang} == en
	/** "custom share fields" item */
	//#endif
	public static final int ITEM_CUSTOM_FIELDS = 11;
	
	//#if def{lang} == cn
	/** 官方微信 */
	//#elif def{lang} == en
	/** official wecaht account */
	//#endif
	public static final String WECHAT_ADDR = "http://weixin.qq.com/r/HHURHl7EjmDxh099nyA4";
	//#if def{lang} == cn
	/** 官方网站 */
	//#elif def{lang} == en
	/** official website */
	//#endif
	public static final String WEBSITE_ADDR = "http://www.mob.com";
	//#if def{lang} == cn
	/** 官方新浪微博 */
	//#elif def{lang} == en
	/** official sina weibo */
	//#endif
	public static final String SDK_SINAWEIBO_UID = "3189087725";
	//#if def{lang} == cn
	/** 官方腾讯微博 */
	//#elif def{lang} == en
	/** official tencent weibo */
	//#endif
	public static final String SDK_TENCENTWEIBO_UID = "shareSDK";
	
	private SlidingMenu menu;
	private SlidingMenuItem curItem;

	public MainAdapter(SlidingMenu menu) {
		super(menu);
		this.menu = menu;
		initDate();
	}

	private void initDate() {
		setGroup(GROUP_DEMO, "");
		setGroup(GROUP_MORE, menu.getResources().getString(R.string.more));
		
		SlidingMenuItem item = new SlidingMenuItem();
		item.id = ITEM_DEMO;
		item.body = menu.getResources().getString(R.string.sm_item_demo);
		setItem(GROUP_DEMO, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_AUTH;
		item.body = menu.getResources().getString(R.string.sm_item_auth);
		setItem(GROUP_DEMO, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_WECHAT;
		item.body = menu.getResources().getString(R.string.sm_item_wechat);
		setItem(GROUP_DEMO, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_YIXIN;
		item.body = menu.getResources().getString(R.string.sm_item_yixin);
		setItem(GROUP_DEMO, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_ALIPAY;
		item.body = menu.getResources().getString(R.string.sm_item_alipay);
		setItem(GROUP_DEMO, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_CUSTOMER;
		item.body = menu.getResources().getString(R.string.sm_item_customer);
		setItem(GROUP_DEMO, item);

		item = new SlidingMenuItem();
		item.id = ITEM_CUSTOM_FIELDS;
		item.body = menu.getResources().getString(R.string.sm_item_custom_fields);
		setItem(GROUP_DEMO, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_FOLLOW_SINAWEIBO;
		item.body = menu.getResources().getString(R.string.sm_item_fl_weibo);
		setItem(GROUP_MORE, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_FOLLOW_TECENTWEIBO;
		item.body = menu.getResources().getString(R.string.sm_item_fl_tc);
		setItem(GROUP_MORE, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_VISIT_WEBSITE;
		item.body = menu.getResources().getString(R.string.sm_item_visit_website);
		setItem(GROUP_MORE, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_ABOUT;
		String appVer = getAppVersion();
		item.body = menu.getResources().getString(R.string.sm_item_about, appVer);
		setItem(GROUP_MORE, item);
	}
	
	//#if def{lang} == cn
	/** 造“组”标题 */
	//#elif def{lang} == en
	/** create group title */
	//#endif
	public View getGroupView(int position, ViewGroup menu) {
		String text = getTitle(position);
		if (text == null || text.length() <= 0) {
			return new LinearLayout(menu.getContext());
		}
		
		TextView tvTitle = new TextView(menu.getContext());
		tvTitle.setBackgroundResource(R.drawable.sidebar_titlt_back);
		int dp13 = dipToPx(menu.getContext(), 13);
		int dp5 = dipToPx(menu.getContext(), 3);
		tvTitle.setPadding(dp13, dp5, dp13, dp5);
		tvTitle.setText(text);
		tvTitle.setGravity(Gravity.CENTER_VERTICAL);
		tvTitle.setTextColor(0xff999999);
		tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
		tvTitle.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return tvTitle;
	}

	//#if def{lang} == cn
	/** 造“菜单项” */
	//#elif def{lang} == en
	/** create menu items */
	//#endif
	public View getItemView(SlidingMenuItem item, ViewGroup menu) {
		TextView tvItem = new TextView(menu.getContext());
		tvItem.setGravity(Gravity.CENTER_VERTICAL);
		tvItem.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		tvItem.setTextColor(tvItem.getResources().getColorStateList(
				R.color.normal_gray_pressed_white));
		tvItem.setText(String.valueOf(item.body));
		tvItem.setCompoundDrawablesWithIntrinsicBounds(
				0, 0, R.drawable.pointer, 0);
		int dp13 = dipToPx(menu.getContext(), 13);
		tvItem.setCompoundDrawablePadding(dp13);
		tvItem.setPadding(dp13, 0, dp13, 0);
		int dp52 = dipToPx(menu.getContext(), 52);
		tvItem.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dp52));
		return tvItem;
	}
	
	private int dipToPx(Context context, int dip) {
		return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
	}

	//#if def{lang} == cn
	/** 
	 * 当菜单项被点击的时候，此方法会被触发。用于处理菜单项的业务逻辑
	 *并加载对应的页面主体。
	 */
	//#elif def{lang} == en
	/** this methos will be called after the menu item is clicked */
	//#endif
	public boolean onItemTrigger(SlidingMenuItem item) {
		if (curItem != null && curItem.equals(item)
				&& item.group == GROUP_DEMO) {
			return false;
		}
		
		curItem = item;
		switch(item.group) {
			case GROUP_DEMO: {
				SlidingMenuPage page = null;
				switch(item.id) {
					case ITEM_DEMO: {
						page = new DemoPage(menu);
					} break;
					case ITEM_AUTH: {
						page = new AuthPage(menu);
					} break;
					case ITEM_WECHAT: {
						page = new WechatPage(menu);
					} break;
					case ITEM_YIXIN: {
						page = new YixinPage(menu);
					} break;
					case ITEM_ALIPAY: {
						page = new AlipayPage(menu);
					} break;
					case ITEM_CUSTOMER: {
						page = new CustomerPage(menu);
					} break;
					case ITEM_CUSTOM_FIELDS: {
						page = new CustomShareFieldsPage(menu);
					} break;
				}
				if (page != null) {
					menu.setBodyView(page.getPage());
				}
			} break;
			case GROUP_MORE: {
				switch(item.id) {
					case ITEM_FOLLOW_SINAWEIBO: {
						Platform plat = ShareSDK.getPlatform("SinaWeibo");
						plat.setPlatformActionListener(this);
						plat.followFriend(SDK_SINAWEIBO_UID);
					} break;
					case ITEM_FOLLOW_TECENTWEIBO: {
						Platform plat = ShareSDK.getPlatform("TencentWeibo");
						plat.setPlatformActionListener(this);
						plat.followFriend(SDK_TENCENTWEIBO_UID);
					} break;
					case ITEM_VISIT_WEBSITE: {
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(WEBSITE_ADDR));
						menu.getContext().startActivity(i);
					} break;
					case ITEM_ABOUT: {
						String msg = getAppName() + " " + getAppVersion();
						Toast.makeText(menu.getContext(), msg, Toast.LENGTH_SHORT).show();
					} break;
				}
				return true;
			}
		}
		return false;
	}
	
	private String getAppName() {
		String appName = menu.getContext().getApplicationInfo().name;
		if (appName != null) {
			return appName;
		}
		
		int appLbl = menu.getContext().getApplicationInfo().labelRes;
		if (appLbl > 0) {
			appName = menu.getContext().getString(appLbl);
		}
		
		return appName;
	}
	
	private String getAppVersion() {
		try {
			PackageManager pm = menu.getContext().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(menu.getContext().getPackageName(), 0);
			return pi.versionName;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}
	
	public void onCancel(Platform plat, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}
	
	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();
		
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}
	
	public boolean handleMessage(Message msg) {
		Platform palt = (Platform) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: {
				//#if def{lang} == cn
				// 成功
				//#elif def{lang} == en
				// success
				//#endif
				text = palt.getName() + " completed at " + text;
			} break;
			case 2: {
				//#if def{lang} == cn
				// 失败
				//#elif def{lang} == en
				// failed
				//#endif
				text = palt.getName() + " caught error at " + text;
			} break;
			case 3: {
				//#if def{lang} == cn
				// 取消
				//#elif def{lang} == en
				// canceled
				//#endif
				text = palt.getName() + " canceled at " + text;
			} break;
		}
		
		Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
		return false;
	}

}
