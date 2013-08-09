/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.TypedValue;
import android.view.Gravity;
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
import cn.sharesdk.demo.R;
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
	private FrameLayout flPage; // 页面
	private PlatformGridView grid; // 宫格列表
	private Button btnCancel; // 取消按钮
	private Animation animShow; // 滑上来的动画
	private Animation animHide; // 滑下去的动画
	private boolean finishing;
	private HashMap<String, Object> reqMap;
	private int notifyIcon;
	private String notifyTitle;
	private boolean silent;
	private PlatformActionListener callback;
	private ShareContentCustomizeCallback customizeCallback;

	public OnekeyShare() {
		reqMap = new HashMap<String, Object>();
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

	/** title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供 */
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

	/** imagePath是本地的图片路径，除Linked-In外的所有平台都支持这个字段 */
	public void setImagePath(String imagePath) {
		reqMap.put("imagePath", imagePath);
	}

	/** imageUrl是图片的网络路径，新浪微博、人人网、QQ空间和Linked-In支持此字段 */
	public void setImageUrl(String imageUrl) {
		reqMap.put("imageUrl", imageUrl);
	}

	/** musicUrl仅在微信（及朋友圈）中使用，是音乐文件的直接地址 */
	public void serMusicUrl(String musicUrl) {
		reqMap.put("musicUrl", musicUrl);
	}

	/** url仅在微信（包括好友和朋友圈）中使用，否则可以不提供 */
 	public void setUrl(String url) {
		reqMap.put("url", url);
	}

	/** appPath是待分享应用程序的本地路劲，仅在微信（包括好友和朋友圈）中使用，否则可以不提供 */
	public void setAppPath(String appPath) {
		reqMap.put("appPath", appPath);
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

	/** appName在QQ客户端分享时使用 */
	public void setAppName(String appName) {
		reqMap.put("appName", appName);
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

	/** 设置用于分享过程中，根据不同平台自定义分享内容的回调 */
	public void setShareContentCustomizeCallback(ShareContentCustomizeCallback callback) {
		customizeCallback = callback;
	}

	public void onCreate() {
		if (silent && reqMap.containsKey("platform")) {
			EditPage page = new EditPage();
			page.setShareData(reqMap);
			page.setParent(this);
			page.show(activity, null);

			finish();
		}

		initPageView();
		initAnim();
		activity.setContentView(flPage);

		// 设置宫格列表数据
		grid.setData(reqMap, silent);
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
		llPage.setBackgroundResource(R.drawable.share_vp_back);
		FrameLayout.LayoutParams lpLl = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.BOTTOM;
		llPage.setLayoutParams(lpLl);
		flPage.addView(llPage);

		// 宫格列表
		grid = new PlatformGridView(getContext());
		LinearLayout.LayoutParams lpWg = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(getContext(), 10);
		lpWg.setMargins(0, dp_10, 0, 0);
		grid.setLayoutParams(lpWg);
		llPage.addView(grid);

		// 取消按钮
		btnCancel = new Button(getContext());
		btnCancel.setTextColor(0xffffffff);
		btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		btnCancel.setText(R.string.cancel);
		btnCancel.setPadding(0, 0, 0, cn.sharesdk.framework.utils.R.dipToPx(getContext(), 5));
		btnCancel.setBackgroundResource(R.drawable.btn_cancel_back);
		LinearLayout.LayoutParams lpBtn = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, cn.sharesdk.framework.utils.R.dipToPx(getContext(), 45));
		lpBtn.setMargins(dp_10, 0, dp_10, dp_10 * 2);
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
			finish();
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
		ShareSDK.logDemoEvent(2, null);
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
			boolean isWechat = "WechatMoments".equals(name) || "Wechat".equals(name);
			if (isWechat && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				msg.obj = activity.getString(R.string.wechat_client_inavailable);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			boolean isGooglePlus = "GooglePlus".equals(name);
			if (isGooglePlus && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				msg.obj = activity.getString(R.string.google_plus_client_inavailable);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			boolean isQQ = "QQ".equals(name);
			if (isQQ && !plat.isValid()) {
				Message msg = new Message();
				msg.what = MSG_TOAST;
				msg.obj = activity.getString(R.string.qq_client_inavailable);
				UIHandler.sendMessage(msg, this);
				continue;
			}

			HashMap<String, Object> data = ent.getValue();
			int shareType = Platform.SHARE_TEXT;
			String imagePath = String.valueOf(data.get("imagePath"));
			if (imagePath != null && (new File(imagePath)).exists()) {
				shareType = Platform.SHARE_IMAGE;
				String url = String.valueOf(data.get("url"));
				if (url != null && url.length() > 0) {
					shareType = Platform.SHARE_WEBPAGE;
				}
			}
			data.put("shareType", shareType);

			if (!started) {
				started = true;
				if (equals(callback)) {
					showNotification(2000, getContext().getString(R.string.sharing));
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
					case 1: { // 成功
						showNotification(2000, getContext().getString(R.string.share_completed));
					}
					break;
					case 2: { // 失败
						String expName = msg.obj.getClass().getSimpleName();
						if ("WechatClientNotExistException".equals(expName)
								|| "WechatTimelineNotSupportedException".equals(expName)) {
							showNotification(2000, getContext().getString(R.string.wechat_client_inavailable));
						}
						else if ("GooglePlusClientNotExistException".equals(expName)) {
							showNotification(2000, getContext().getString(R.string.google_plus_client_inavailable));
						}
						else if ("QQClientNotExistException".equals(expName)) {
							showNotification(2000, getContext().getString(R.string.qq_client_inavailable));
						}
						else {
							showNotification(2000, getContext().getString(R.string.share_failed));
						}
					}
					break;
					case 3: { // 取消
						showNotification(2000, getContext().getString(R.string.share_canceled));
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
