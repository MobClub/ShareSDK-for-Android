/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import static cn.sharesdk.framework.utils.R.getBitmapRes;
import static cn.sharesdk.framework.utils.R.getStringRes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Message;
import android.os.Handler.Callback;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import cn.sharesdk.framework.FakeActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;

/**
 * 快捷分享的入口
 * <p>
 * 通过不同的setter设置参数，然后调用{@link #show(Context)}方法启动快捷分享
 */
public class OnekeyShare extends FakeActivity implements
		OnClickListener, PlatformActionListener, Callback {
	private static final int MSG_TOAST = 1;
	private static final int MSG_ACTION_CCALLBACK = 2;
	private static final int MSG_CANCEL_NOTIFY = 3;
	// 页面
	private FrameLayout flPage;
	// 宫格列表
	private PlatformGridView grid;
	// 取消按钮
	private Button btnCancel;
	// 滑上来的动画
	private Animation animShow;
	// 滑下去的动画
	private Animation animHide;
	private boolean finishing;
	private boolean canceled;
	private HashMap<String, Object> reqMap;
	private ArrayList<CustomerLogo> customers;
	private int notifyIcon;
	private String notifyTitle;
	private boolean silent;
	private PlatformActionListener callback;
	private ShareContentCustomizeCallback customizeCallback;
	private boolean dialogMode;

	public OnekeyShare() {
		reqMap = new HashMap<String, Object>();
		customers = new ArrayList<CustomerLogo>();
		callback = this;
	}

	public void show(Context context) {
		super.show(context, null);
	}

	/** 分享时Notification的图标和文字 */
	public void setNotification(int icon, String title) {
		notifyIcon = icon;
		notifyTitle = title;
	}

	/** address是接收人地址，仅在信息和邮件使用，否则可以不提供 */
	public void setAddress(String address) {
		reqMap.put("address", address);
	}

	/**
	 * title标题，在印象笔记、邮箱、信息、微信（包括好友、朋友圈和收藏）、
	 * 易信（包括好友、朋友圈）、人人网和QQ空间使用，否则可以不提供
	 */
	public void setTitle(String title) {
		reqMap.put("title", title);
	}

	/** titleUrl是标题的网络链接，仅在人人网和QQ空间使用，否则可以不提供 */
	public void setTitleUrl(String titleUrl) {
		reqMap.put("titleUrl", titleUrl);
	}

	/** text是分享文本，所有平台都需要这个字段 */
	public void setText(String text) {
		reqMap.put("text", text);
	}

	/** 获取text字段的值 */
	public String getText() {
		return reqMap.containsKey("text") ? String.valueOf(reqMap.get("text")) : null;
	}

	/** imagePath是本地的图片路径，除Linked-In外的所有平台都支持这个字段 */
	public void setImagePath(String imagePath) {
		if(!TextUtils.isEmpty(imagePath))
			reqMap.put("imagePath", imagePath);
	}

	/** imageUrl是图片的网络路径，新浪微博、人人网、QQ空间和Linked-In支持此字段 */
	public void setImageUrl(String imageUrl) {
		if(!TextUtils.isEmpty(imageUrl))
			reqMap.put("imageUrl", imageUrl);
	}

	/** url在微信（包括好友、朋友圈收藏）和易信（包括好友和朋友圈）中使用，否则可以不提供 */
 	public void setUrl(String url) {
		reqMap.put("url", url);
	}

	/** filePath是待分享应用程序的本地路劲，仅在微信（易信）好友和Dropbox中使用，否则可以不提供 */
	public void setFilePath(String filePath) {
		reqMap.put("filePath", filePath);
	}

	/** comment是我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供 */
	public void setComment(String comment) {
		reqMap.put("comment", comment);
	}

	/** site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供 */
	public void setSite(String site) {
		reqMap.put("site", site);
	}

	/** siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供 */
	public void setSiteUrl(String siteUrl) {
		reqMap.put("siteUrl", siteUrl);
	}

	/** foursquare分享时的地方名 */
	public void setVenueName(String venueName) {
		reqMap.put("venueName", venueName);
	}

	/** foursquare分享时的地方描述 */
	public void setVenueDescription(String venueDescription) {
		reqMap.put("venueDescription", venueDescription);
	}

	/** 分享地纬度，新浪微博、腾讯微博和foursquare支持此字段 */
	public void setLatitude(float latitude) {
		reqMap.put("latitude", latitude);
	}

	/** 分享地经度，新浪微博、腾讯微博和foursquare支持此字段 */
	public void setLongitude(float longitude) {
		reqMap.put("longitude", longitude);
	}

	/** 是否直接分享 */
	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	/** 设置编辑页的初始化选中平台 */
	public void setPlatform(String platform) {
		reqMap.put("platform", platform);
	}

	/** 设置自定义的外部回调 */
	public void setCallback(PlatformActionListener callback) {
		this.callback = callback;
	}

	/** 返回操作回调 */
	public PlatformActionListener getCallback() {
		return callback;
	}

	/** 设置用于分享过程中，根据不同平台自定义分享内容的回调 */
	public void setShareContentCustomizeCallback(ShareContentCustomizeCallback callback) {
		customizeCallback = callback;
	}

	/** 返回自定义分享内容的回调 */
	public ShareContentCustomizeCallback getShareContentCustomizeCallback() {
		return customizeCallback;
	}

	/** 设置自己图标和点击事件，可以重复调用添加多次 */
	public void setCustomerLogo(Bitmap logo, String label, OnClickListener ocListener) {
		CustomerLogo cl = new CustomerLogo();
		cl.label = label;
		cl.logo = logo;
		cl.listener = ocListener;
		customers.add(cl);
	}

	// 设置编辑页面的显示模式为Dialog模式
	public void setDialogMode() {
		dialogMode = true;
		reqMap.put("dialogMode", dialogMode);
	}

	public void onCreate() {
		// 显示方式是由platform和silent两个字段控制的
		// 如果platform设置了，则无须显示九宫格，否则都会显示；
		// 如果silent为true，表示不进入编辑页面，否则会进入。
		// 本类只判断platform，因为九宫格显示以后，事件交给PlatformGridView控制
		// 当platform和silent都为true，则直接进入分享；
		// 当platform设置了，但是silent为false，则判断是否是“使用客户端分享”的平台，
		// 若为“使用客户端分享”的平台，则直接分享，否则进入编辑页面
		HashMap<String, Object> copy = new HashMap<String, Object>();
		copy.putAll(reqMap);
		if (copy.containsKey("platform")) {
			String name = String.valueOf(copy.get("platform"));
			if (silent) {
				HashMap<Platform, HashMap<String, Object>> shareData
						= new HashMap<Platform, HashMap<String,Object>>();
				shareData.put(ShareSDK.getPlatform(activity, name), copy);
				share(shareData);
			} else if (ShareCore.isUseClientToShare(activity, name)) {
				HashMap<Platform, HashMap<String, Object>> shareData
						= new HashMap<Platform, HashMap<String,Object>>();
				shareData.put(ShareSDK.getPlatform(activity, name), copy);
				share(shareData);
			} else {
				EditPage page = new EditPage();
				page.setShareData(copy);
				page.setParent(this);
				if (dialogMode) {
					page.setDialogMode();
				}
				page.show(activity, null);
			}
			finish();
			return;
		}

		finishing = false;
		canceled = false;
		initPageView();
		initAnim();
		activity.setContentView(flPage);

		// 设置宫格列表数据
		grid.setData(copy, silent);
		grid.setCustomerLogos(customers);
		grid.setParent(this);
		btnCancel.setOnClickListener(this);

		// 显示列表
		flPage.clearAnimation();
		flPage.startAnimation(animShow);

		// 打开分享菜单的统计
		ShareSDK.logDemoEvent(1, null);
	}

	private void initPageView() {
		flPage = new FrameLayout(getContext());
		flPage.setOnClickListener(this);

		// 宫格列表的容器，为了“下对齐”，在外部包含了一个FrameLayout
		LinearLayout llPage = new LinearLayout(getContext()) {
			public boolean onTouchEvent(MotionEvent event) {
				return true;
			}
		};
		llPage.setOrientation(LinearLayout.VERTICAL);
		int resId = getBitmapRes(getContext(), "share_vp_back");
		if (resId > 0) {
			llPage.setBackgroundResource(resId);
		}
		FrameLayout.LayoutParams lpLl = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.BOTTOM;
		llPage.setLayoutParams(lpLl);
		flPage.addView(llPage);

		// 宫格列表
		grid = new PlatformGridView(getContext());
		LinearLayout.LayoutParams lpWg = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		grid.setLayoutParams(lpWg);
		llPage.addView(grid);

		// 取消按钮
		btnCancel = new Button(getContext());
		btnCancel.setTextColor(0xffffffff);
		btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		resId = getStringRes(getContext(), "cancel");
		if (resId > 0) {
			btnCancel.setText(resId);
		}
		btnCancel.setPadding(0, 0, 0, cn.sharesdk.framework.utils.R.dipToPx(getContext(), 5));
		resId = getBitmapRes(getContext(), "btn_cancel_back");
		if (resId > 0) {
			btnCancel.setBackgroundResource(resId);
		}
		LinearLayout.LayoutParams lpBtn = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, cn.sharesdk.framework.utils.R.dipToPx(getContext(), 45));
		int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(getContext(), 10);
		lpBtn.setMargins(dp_10, dp_10, dp_10, dp_10);
		btnCancel.setLayoutParams(lpBtn);
		llPage.addView(btnCancel);
	}

	private void initAnim() {
		animShow = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0);
		animShow.setDuration(300);

		animHide = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1);
		animHide.setDuration(300);
	}

	public void onClick(View v) {
		if (v.equals(flPage) || v.equals(btnCancel)) {
			canceled = true;
			finish();
		}
	}

	public boolean onKeyEvent(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			canceled = true;
		}
		return super.onKeyEvent(keyCode, event);
	}

	public void onConfigurationChanged(Configuration newConfig) {
		if (grid != null) {
			grid.onConfigurationChanged();
		}
	}

	public void finish() {
		if (finishing) {
			return;
		}

		if (animHide == null) {
			finishing = true;
			super.finish();
			return;
		}

		// 取消分享菜单的统计
		if (canceled) {
			ShareSDK.logDemoEvent(2, null);
		}
		finishing = true;
		animHide.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				flPage.setVisibility(View.GONE);
				OnekeyShare.super.finish();
			}
		});
		flPage.clearAnimation();
		flPage.startAnimation(animHide);
	}

	/** 循环执行分享 */
	public void share(HashMap<Platform, HashMap<String, Object>> shareData) {
		boolean started = false;
		for (Entry<Platform, HashMap<String, Object>> ent : shareData.entrySet()) {
			Platform plat = ent.getKey();
			String name = plat.getName();
			boolean isWechat = "WechatMoments".equals(name) || "Wechat".equals(name)
					|| "WechatFavorite".equals(name);
			if (isWechat && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				int resId = getStringRes(getContext(), "wechat_client_inavailable");
				msg.obj = activity.getString(resId);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			boolean isGooglePlus = "GooglePlus".equals(name);
			if (isGooglePlus && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				int resId = getStringRes(getContext(), "google_plus_client_inavailable");
				msg.obj = activity.getString(resId);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			boolean isQQ = "QQ".equals(name);
			if (isQQ && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				int resId = getStringRes(getContext(), "qq_client_inavailable");
				msg.obj = activity.getString(resId);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			boolean isPinterest = "Pinterest".equals(name);
			if (isPinterest && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				int resId = getStringRes(getContext(), "pinterest_client_inavailable");
				msg.obj = activity.getString(resId);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			boolean isInstagram = "Instagram".equals(name);
			if (isInstagram && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				int resId = getStringRes(getContext(), "instagram_client_inavailable");
				msg.obj = activity.getString(resId);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			boolean isYixin = "YixinMoments".equals(name) || "Yixin".equals(name);
			if (isYixin && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				int resId = getStringRes(getContext(), "yixin_client_inavailable");
				msg.obj = activity.getString(resId);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			HashMap<String, Object> data = ent.getValue();
			int shareType = Platform.SHARE_TEXT;
			String imagePath = String.valueOf(data.get("imagePath"));
			if (imagePath != null && (new File(imagePath)).exists()) {
				shareType = Platform.SHARE_IMAGE;
				if (imagePath.endsWith(".gif")) {
					shareType = Platform.SHARE_EMOJI;
				} else if (data.containsKey("url") && !TextUtils.isEmpty(data.get("url").toString())) {
					shareType = Platform.SHARE_WEBPAGE;
				}
			} else {
				Object imageUrl = data.get("imageUrl");
				if (imageUrl != null && !TextUtils.isEmpty(String.valueOf(imageUrl))) {
					shareType = Platform.SHARE_IMAGE;
					if (String.valueOf(imageUrl).endsWith(".gif")) {
						shareType = Platform.SHARE_EMOJI;
					} else if (data.containsKey("url") && !TextUtils.isEmpty(data.get("url").toString())) {
						shareType = Platform.SHARE_WEBPAGE;
					}
				}
			}
			data.put("shareType", shareType);

			if (!started) {
				started = true;
				if (equals(callback)) {
					int resId = getStringRes(getContext(), "sharing");
					if (resId > 0) {
						showNotification(2000, getContext().getString(resId));
					}
				}
				finish();
			}
			plat.setPlatformActionListener(callback);
			ShareCore shareCore = new ShareCore();
			shareCore.setShareContentCustomizeCallback(customizeCallback);
			shareCore.share(plat, data);
		}
	}

	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);
	}

	public void onError(Platform platform, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = t;
		UIHandler.sendMessage(msg, this);

		// 分享失败的统计
		ShareSDK.logDemoEvent(4, platform);
	}

	public void onCancel(Platform platform, int action) {
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);
	}

	public boolean handleMessage(Message msg) {
		switch(msg.what) {
			case MSG_TOAST: {
				String text = String.valueOf(msg.obj);
				Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
			}
			break;
			case MSG_ACTION_CCALLBACK: {
				switch (msg.arg1) {
					case 1: {
						// 成功
						int resId = getStringRes(getContext(), "share_completed");
						if (resId > 0) {
							showNotification(2000, getContext().getString(resId));
						}
					}
					break;
					case 2: {
						// 失败
						String expName = msg.obj.getClass().getSimpleName();
						if ("WechatClientNotExistException".equals(expName)
								|| "WechatTimelineNotSupportedException".equals(expName)
								|| "WechatFavoriteNotSupportedException".equals(expName)) {
							int resId = getStringRes(getContext(), "wechat_client_inavailable");
							if (resId > 0) {
								showNotification(2000, getContext().getString(resId));
							}
						} else if ("GooglePlusClientNotExistException".equals(expName)) {
							int resId = getStringRes(getContext(), "google_plus_client_inavailable");
							if (resId > 0) {
								showNotification(2000, getContext().getString(resId));
							}
						} else if ("QQClientNotExistException".equals(expName)) {
							int resId = getStringRes(getContext(), "qq_client_inavailable");
							if (resId > 0) {
								showNotification(2000, getContext().getString(resId));
							}
						} else if ("YixinClientNotExistException".equals(expName)
								|| "YixinTimelineNotSupportedException".equals(expName)) {
							int resId = getStringRes(getContext(), "yixin_client_inavailable");
							if (resId > 0) {
								showNotification(2000, getContext().getString(resId));
							}
						} else {
							int resId = getStringRes(getContext(), "share_failed");
							if (resId > 0) {
								showNotification(2000, getContext().getString(resId));
							}
						}
					}
					break;
					case 3: {
						// 取消
						int resId = getStringRes(getContext(), "share_canceled");
						if (resId > 0) {
							showNotification(2000, getContext().getString(resId));
						}
					}
					break;
				}
			}
			break;
			case MSG_CANCEL_NOTIFY: {
				NotificationManager nm = (NotificationManager) msg.obj;
				if (nm != null) {
					nm.cancel(msg.arg1);
				}
			}
			break;
		}
		return false;
	}

	// 在状态栏提示分享操作
	private void showNotification(long cancelTime, String text) {
		try {
			Context app = getContext().getApplicationContext();
			NotificationManager nm = (NotificationManager) app
					.getSystemService(Context.NOTIFICATION_SERVICE);
			final int id = Integer.MAX_VALUE / 13 + 1;
			nm.cancel(id);

			long when = System.currentTimeMillis();
			Notification notification = new Notification(notifyIcon, text, when);
			PendingIntent pi = PendingIntent.getActivity(app, 0, new Intent(), 0);
			notification.setLatestEventInfo(app, notifyTitle, text, pi);
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify(id, notification);

			if (cancelTime > 0) {
				Message msg = new Message();
				msg.what = MSG_CANCEL_NOTIFY;
				msg.obj = nm;
				msg.arg1 = id;
				UIHandler.sendMessageDelayed(msg, cancelTime, this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
