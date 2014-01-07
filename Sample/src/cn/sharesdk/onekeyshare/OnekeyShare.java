/*
 * Offical Website:http://www.ShareSDK.cn
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 ShareSDK.cn. All rights reserved.
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
 * entrance of onekeyshare
 * <p>
 * by setting different setter parameter, then call the Show method to start the shortcut to share
 */
public class OnekeyShare extends FakeActivity implements
		OnClickListener, PlatformActionListener, Callback {
	private static final int MSG_TOAST = 1;
	private static final int MSG_ACTION_CCALLBACK = 2;
	private static final int MSG_CANCEL_NOTIFY = 3;
	// page container
	private FrameLayout flPage;
	// gridview of platform list
	private PlatformGridView grid;
	// cancel button
	private Button btnCancel;
	// sliding up animation
	private Animation animShow;
	// sliding down animation
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
	private boolean disableSSO;

	public OnekeyShare() {
		reqMap = new HashMap<String, Object>();
		customers = new ArrayList<CustomerLogo>();
		callback = this;
	}

	public void show(Context context) {
		super.show(context, null);
	}

	/** icon and text to notify after sharing */
	public void setNotification(int icon, String title) {
		notifyIcon = icon;
		notifyTitle = title;
	}

	/** short message address or email address */
	public void setAddress(String address) {
		reqMap.put("address", address);
	}

	/** title of share content */
	public void setTitle(String title) {
		reqMap.put("title", title);
	}

	/** the url of title */
	public void setTitleUrl(String titleUrl) {
		reqMap.put("titleUrl", titleUrl);
	}

	/** share content */
	public void setText(String text) {
		reqMap.put("text", text);
	}

	/** returns share content */
	public String getText() {
		return reqMap.containsKey("text") ? String.valueOf(reqMap.get("text")) : null;
	}

	/** local path of the image to share */
	public void setImagePath(String imagePath) {
		if(!TextUtils.isEmpty(imagePath))
			reqMap.put("imagePath", imagePath);
	}

	/** url of the image to share */
	public void setImageUrl(String imageUrl) {
		if (!TextUtils.isEmpty(imageUrl))
			reqMap.put("imageUrl", imageUrl);
	}

	/** webpage link to share in wechat and yixin etc.*/
 	public void setUrl(String url) {
		reqMap.put("url", url);
	}

 	/** local path of the file to share in wechat */
	public void setFilePath(String filePath) {
		reqMap.put("filePath", filePath);
	}

	/** comment field of platform renren */
	public void setComment(String comment) {
		reqMap.put("comment", comment);
	}

	/** app name or site name of your program */
	public void setSite(String site) {
		reqMap.put("site", site);
	}

	/** the url of the website or appname */
	public void setSiteUrl(String siteUrl) {
		reqMap.put("siteUrl", siteUrl);
	}

	/** location name */
	public void setVenueName(String venueName) {
		reqMap.put("venueName", venueName);
	}

	/** description of your sharing location */
	public void setVenueDescription(String venueDescription) {
		reqMap.put("venueDescription", venueDescription);
	}

	/** latitude */
	public void setLatitude(float latitude) {
		reqMap.put("latitude", latitude);
	}

	/** longitude */
	public void setLongitude(float longitude) {
		reqMap.put("longitude", longitude);
	}

	/** determine whether to share directly */
	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	/** Setting the selected platform of EditPage when initializing */
	public void setPlatform(String platform) {
		reqMap.put("platform", platform);
	}

	/** setting custom external callback */
	public void setCallback(PlatformActionListener callback) {
		this.callback = callback;
	}

	/** returns sharing callback */
	public PlatformActionListener getCallback() {
		return callback;
	}

	/** setting the share content customizing callback for sharing process */
	public void setShareContentCustomizeCallback(ShareContentCustomizeCallback callback) {
		customizeCallback = callback;
	}

	/** returns share content customizing callback */
	public ShareContentCustomizeCallback getShareContentCustomizeCallback() {
		return customizeCallback;
	}

	/** add a custom icon and its click event listener */
	public void setCustomerLogo(Bitmap logo, String label, OnClickListener ocListener) {
		CustomerLogo cl = new CustomerLogo();
		cl.label = label;
		cl.logo = logo;
		cl.listener = ocListener;
		customers.add(cl);
	}

	/** disable SSO authorizing before sharing */
	public void disableSSOWhenAuthorize() {
		disableSSO = true;
	}

	/** set the display mode of editpage to be the dialog mode */
	public void setDialogMode() {
		dialogMode = true;
		reqMap.put("dialogMode", dialogMode);
	}

	public void onCreate() {
		// display mode of onekeyshare is controled by the field of platform and silent,
		// if platform is set, platform gridview won't be display, onekeyshare will jump to editpage directly
		// if silent is true, onekeyshare will skip the editpage and shares directly
		// the class only determines the value of platform, because after PlatformGridView is shown, all events will be passed into it
		// when platform is set, and silent is true, onekeyshare will share the selected platform directly
		// when platform is set, and silent is false, onekeyshare will determine whether to share by the client of the platform or not
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

		// set the data for platform gridview
		grid.setData(copy, silent);
		grid.setCustomerLogos(customers);
		grid.setParent(this);
		btnCancel.setOnClickListener(this);

		// display gridviews
		flPage.clearAnimation();
		flPage.startAnimation(animShow);

		// a statistics of opening the platform gridview
		ShareSDK.logDemoEvent(1, null);
	}

	private void initPageView() {
		flPage = new FrameLayout(getContext());
		flPage.setOnClickListener(this);

		// container of the platform gridview
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

		// gridview
		grid = new PlatformGridView(getContext());
		LinearLayout.LayoutParams lpWg = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		grid.setLayoutParams(lpWg);
		llPage.addView(grid);

		// cancel button
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

		// a statistics of cancel sharing
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

	/** execute the loop of share */
	public void share(HashMap<Platform, HashMap<String, Object>> shareData) {
		boolean started = false;
		for (Entry<Platform, HashMap<String, Object>> ent : shareData.entrySet()) {
			Platform plat = ent.getKey();
			plat.SSOSetting(disableSSO);
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

		// a statistics of cancel sharing
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
						// success
						int resId = getStringRes(getContext(), "share_completed");
						if (resId > 0) {
							showNotification(2000, getContext().getString(resId));
						}
					}
					break;
					case 2: {
						// failed
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
						// canceled
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

	// notify the share result
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
