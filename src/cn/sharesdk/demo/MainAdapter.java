/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.HashMap;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import m.framework.ui.MenuAdapter;
import m.framework.ui.SlidingMenu;
import m.framework.ui.SlidingMenuItem;

/** 侧栏实际逻辑类。
 * <p>
 * 复制完成侧栏目录的展示、事件的监听、页面主体的显示和切换等等业务。
 */
public class MainAdapter extends MenuAdapter 
		implements Callback, WeiboActionListener {
	/** 接口分组 */
	public static final int GROUP_DEMO = 1;
	/** “更多”分组 */
	public static final int GROUP_MORE = 2;
	
	/** “接口”项 */
	public static final int ITEM_DEMO = 1;
	/** “授权”项 */
	public static final int ITEM_AUTH = 2;
	/** “微信”项 */
	public static final int ITEM_WECHAT = 3;
	/** “关注新浪微博”项 */
	public static final int ITEM_FOLLOW_SINAWEIBO = 4;
	/** “关注腾讯微博”项 */
	public static final int ITEM_FOLLOW_TECENTWEIBO = 5;
	/** “关注官方微信”项 */
	public static final int ITEM_VISIT_WECHAT = 6;
	/** “关注官方网站”项 */
	public static final int ITEM_VISIT_WEBSITE = 7;
	/** “版本”项 */
	public static final int ITEM_ABOUT = 8;
	
	/** 官方微信 */
	public static final String WECHAT_ADDR = "http://weixin.qq.com/r/HHURHl7EjmDxh099nyA4";
	/** 官方网站 */
	public static final String WEBSITE_ADDR = "http://www.shareSDK.cn";
	/** 官方新浪微博 */
	public static final String SDK_SINAWEIBO_UID = "3189087725";
	/** 官方腾讯微博 */
	public static final String SDK_TENCENTWEIBO_UID = "shareSDK";
	
	private SlidingMenu menu;
	private Handler handler;
	private SlidingMenuItem curItem;

	public MainAdapter(SlidingMenu menu) {
		super(menu);
		this.menu = menu;
		handler = new Handler(this);
		initDate();
	}

	private void initDate() {
		setGroup(GROUP_DEMO, "");
		setGroup(GROUP_MORE, menu.getResources().getString(R.string.more));
		
		SlidingMenuItem item = new SlidingMenuItem();
		item.id = ITEM_DEMO;
		item.text = menu.getResources().getString(R.string.sm_item_demo);
		setItem(GROUP_DEMO, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_AUTH;
		item.text = menu.getResources().getString(R.string.sm_item_auth);
		setItem(GROUP_DEMO, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_WECHAT;
		item.text = menu.getResources().getString(R.string.sm_item_wechat);
		setItem(GROUP_DEMO, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_FOLLOW_SINAWEIBO;
		item.text = menu.getResources().getString(R.string.sm_item_fl_weibo);
		setItem(GROUP_MORE, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_FOLLOW_TECENTWEIBO;
		item.text = menu.getResources().getString(R.string.sm_item_fl_tc);
		setItem(GROUP_MORE, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_VISIT_WECHAT;
		item.text = menu.getResources().getString(R.string.sm_item_visit_wechat);
		setItem(GROUP_MORE, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_VISIT_WEBSITE;
		item.text = menu.getResources().getString(R.string.sm_item_visit_website);
		setItem(GROUP_MORE, item);
		
		item = new SlidingMenuItem();
		item.id = ITEM_ABOUT;
		String appVer = menu.getResources().getString(R.string.app_version);
		item.text = menu.getResources().getString(R.string.sm_item_about, appVer);
		setItem(GROUP_MORE, item);
	}
	
	/** 造“组”标题 */
	public View getGroupView(int position, ViewGroup menu) {
		String text = getTitle(position);
		if (text == null || text.length() <= 0) {
			return new LinearLayout(menu.getContext());
		}
		
		TextView tvTitle = new TextView(menu.getContext());
		tvTitle.setBackgroundResource(R.drawable.sidebar_titlt_back);
		int dp_13 = dipToPx(menu.getContext(), 13);
		int dp_5 = dipToPx(menu.getContext(), 3);
		tvTitle.setPadding(dp_13, dp_5, dp_13, dp_5);
		tvTitle.setText(text);
		tvTitle.setGravity(Gravity.CENTER_VERTICAL);
		tvTitle.setTextColor(0xff999999);
		tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
		tvTitle.setLayoutParams(new ScrollView.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		return tvTitle;
	}

	/** 造“菜单项” */
	public View getItemView(SlidingMenuItem item, ViewGroup menu) {
		// 标题
		TextView tvItem = new TextView(menu.getContext());
		tvItem.setGravity(Gravity.CENTER_VERTICAL);
		tvItem.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		tvItem.setTextColor(tvItem.getResources().getColorStateList(
				R.color.normal_gray_pressed_white));
		tvItem.setText(item.text);
		tvItem.setCompoundDrawablesWithIntrinsicBounds(
				0, 0, R.drawable.pointer, 0);
		int dp_13 = dipToPx(menu.getContext(), 13);
		tvItem.setCompoundDrawablePadding(dp_13);
		tvItem.setPadding(dp_13, 0, dp_13, 0);
		int dp_52 = dipToPx(menu.getContext(), 52);
		tvItem.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, dp_52));
		return tvItem;
	}
	
	private int dipToPx(Context context, int dip) {
		return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
	}

	/** 
	 * 当菜单项被点击的时候，此方法会被触发。用于处理菜单项的业务逻辑
	 *并加载对应的页面主体。
	 */
	public boolean onItemTrigger(SlidingMenuItem item) {
		if (curItem != null && curItem.equals(item)
				&& item.group == GROUP_DEMO) {
			return false;
		}
		
		curItem = item;
		switch(item.group) {
			case GROUP_DEMO: {
				switch(item.id) {
					case ITEM_DEMO: {
						DemoPage page = new DemoPage(menu);
						menu.setBodyView(page.getPage());
					}
					break;
					case ITEM_AUTH: {
						AuthPage page = new AuthPage(menu);
						menu.setBodyView(page.getPage());
					}
					break;
					case ITEM_WECHAT: {
						WechatPage page = new WechatPage(menu);
						menu.setBodyView(page.getPage());
					}
					break;
				}
			}
			break;
			case GROUP_MORE: {
				switch(item.id) {
					case ITEM_FOLLOW_SINAWEIBO: {
						AbstractWeibo weibo = AbstractWeibo.getWeibo(
								menu.getContext(), SinaWeibo.NAME);
						weibo.setWeiboActionListener(this);
						weibo.followFriend(SDK_SINAWEIBO_UID);
					}
					break;
					case ITEM_FOLLOW_TECENTWEIBO: {
						AbstractWeibo weibo = AbstractWeibo.getWeibo(
								menu.getContext(), TencentWeibo.NAME);
						weibo.setWeiboActionListener(this);
						weibo.followFriend(SDK_TENCENTWEIBO_UID);
					}
					break;
					case ITEM_VISIT_WECHAT: {
						try {
							Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri.parse(WECHAT_ADDR));
							i.setPackage("com.tencent.mm");
							menu.getContext().startActivity(i);
						} catch(Throwable t) {
							t.printStackTrace();
							
							Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri.parse(WECHAT_ADDR));
							String title = menu.getContext().getString(R.string.plz_choose_wechat);
							menu.getContext().startActivity(Intent.createChooser(i, title));
						}
					}
					break;
					case ITEM_VISIT_WEBSITE: {
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(WEBSITE_ADDR));
						menu.getContext().startActivity(i);
					}
					break;
				}
				return true;
			}
		}
		return false;
	}
	
	public void onComplete(AbstractWeibo weibo, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}
	
	public void onCancel(AbstractWeibo weibo, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}
	
	public void onError(AbstractWeibo weibo, int action, Throwable t) {
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}
	
	public boolean handleMessage(Message msg) {
		AbstractWeibo weibo = (AbstractWeibo) msg.obj;
		String text = AbstractWeibo.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: { // 成功
				text = weibo.getName() + " completed at " + text;
			}
			break;
			case 2: { // 失败
				text = weibo.getName() + " caught error at " + text;
			}
			break;
			case 3: { // 取消
				text = weibo.getName() + " canceled at " + text;
			}
			break;
		}
		
		Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
		return false;
	}

}
