/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.evernote.Evernote;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.onekeyshare.res.R;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.utils.WechatClientNotExistException;
import cn.sharesdk.wechat.utils.WechatTimelineNotSupportedException;

/** 执行图文分享的页面，此页面不支持微信平台的分享 */
public class SharePage extends Activity implements Callback, Runnable, 
		OnClickListener, WeiboActionListener, TextWatcher {
	private Handler handler;
	private int notifIcon;
	private String notifTitle;
	private String platform;
	private LinearLayout llPage;
	private TitleLayout llTitle;
	private EditText etContent; // 文本编辑框
	private TextView tvCounter; // 字数计算器
	private LinearLayout llPlat;
	private AbstractWeibo[] weiboList; // 平台列表
	private View[] views;
	private String[] names;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler(this);
		notifIcon = getIntent().getIntExtra("notif_icon", 0);
		notifTitle = getIntent().getStringExtra("notif_title");
		notifTitle = notifTitle == null ? "" : notifTitle;
		platform = getIntent().getStringExtra("platform");
		
		initPageView();
		setContentView(llPage);
		onTextChanged(etContent.getText(), 0, etContent.length(), 0);
		
		// 获取平台列表并过滤微信
		new Thread(){
			public void run() {
				weiboList = AbstractWeibo.getWeiboList(SharePage.this);
				if (weiboList == null) {
					return;
				}
				
				ArrayList<AbstractWeibo> list = new ArrayList<AbstractWeibo>();
				for (AbstractWeibo weibo : weiboList) {
					String name = weibo.getName();
					if ("Wechat".equals(name) || "WechatMoments".equals(name)
							|| "ShortMessage".equals(name) || "Email".equals(name)) {
						continue;
					}
					list.add(weibo);
				}
				weiboList = new AbstractWeibo[list.size()];
				for (int i = 0; i < weiboList.length; i++) {
					weiboList[i] = list.get(i);
				}
				
				handler.post(SharePage.this);
			}
		}.start();
	}

	private void initPageView() {
		llPage = new LinearLayout(this);
		llPage.setBackgroundColor(0xff323232);
		llPage.setOrientation(LinearLayout.VERTICAL);
		
		llTitle = new TitleLayout(this);
		llTitle.setBackgroundDrawable(R.getDrawable(this, "title_back"));
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.getString(this, "multi_share"));
		llTitle.getBtnRight().setVisibility(View.VISIBLE);
		llTitle.getBtnRight().setText(R.getString(this, "share"));
		llTitle.getBtnRight().setOnClickListener(this);
		llTitle.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		llPage.addView(llTitle);
		
		FrameLayout flPage = new FrameLayout(this);
		LinearLayout.LayoutParams lpFl = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lpFl.weight = 1;
		flPage.setLayoutParams(lpFl);
		llPage.addView(flPage);
		
		LinearLayout llBody = new LinearLayout(this);
		llBody.setOrientation(LinearLayout.VERTICAL);
		FrameLayout.LayoutParams lpLl = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		lpLl.gravity = Gravity.LEFT | Gravity.TOP;
		llBody.setLayoutParams(lpLl);
		flPage.addView(llBody);
		
		ImageView ivPin = new ImageView(this);
		Bitmap bmPin = R.getBitmap(this, "pin");
		if (bmPin != null && !bmPin.isRecycled()) {
			int bmW = bmPin.getWidth();
			int bmH = bmPin.getHeight();
			int scrW = cn.sharesdk.framework.res.R.getScreenWidth(this);
			bmW = scrW * bmW / 480;
			bmH = scrW * bmH / 480;
			Bitmap scaledBm = Bitmap.createScaledBitmap(bmPin, bmW, bmH, true);
			ivPin.setImageBitmap(scaledBm == null ? bmPin : scaledBm);
		}
		int dp_80 = R.dipToPx(this, 80);
		int dp_36 = R.dipToPx(this, 36);
		FrameLayout.LayoutParams lpPin = new FrameLayout.LayoutParams(dp_80, dp_36);
		lpPin.topMargin = R.dipToPx(this, 6);
		lpPin.gravity = Gravity.RIGHT | Gravity.TOP;
		ivPin.setLayoutParams(lpPin);
		flPage.addView(ivPin);
		
		ImageView ivShadow = new ImageView(this);
		ivShadow.setBackgroundDrawable(R.getDrawable(this, "title_shadow"));
		ivShadow.setImageBitmap(R.getBitmap(this, "title_shadow"));
		FrameLayout.LayoutParams lpSd = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		ivShadow.setLayoutParams(lpSd);
		flPage.addView(ivShadow);
		
		LinearLayout llInput = new LinearLayout(this);
		llInput.setMinimumHeight(R.dipToPx(this, 150));
		llInput.setBackgroundDrawable(R.getDrawable(this, "edittext_back"));
		LinearLayout.LayoutParams lpInput = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		int dp_3 = R.dipToPx(this, 3);
		lpInput.setMargins(dp_3, dp_3, dp_3, dp_3);
		lpInput.weight = 1;
		llInput.setLayoutParams(lpInput);
		llBody.addView(llInput);
		
		LinearLayout llToolBar = new LinearLayout(this);
		llToolBar.setBackgroundDrawable(R.getDrawable(this, "share_tb_back"));
		LinearLayout.LayoutParams lpTb = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		int dp_4 = R.dipToPx(this, 4);
		lpTb.setMargins(dp_4, 0, dp_4, dp_4);
		llToolBar.setLayoutParams(lpTb);
		llBody.addView(llToolBar);
		
		etContent = new EditText(this);
		etContent.setGravity(Gravity.LEFT | Gravity.TOP);
		int dp_8 = R.dipToPx(this, 8);
		etContent.setPadding(dp_8, dp_8, dp_8, dp_8);
		etContent.setBackgroundDrawable(null);
		etContent.setText(getShownText());
		etContent.addTextChangedListener(this);
		LinearLayout.LayoutParams lpEt = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		lpEt.weight = 1;
		etContent.setLayoutParams(lpEt);
		llInput.addView(etContent);
		
		LinearLayout llRight = new LinearLayout(this);
		llRight.setOrientation(LinearLayout.VERTICAL);
		llRight.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
		llInput.addView(llRight);
		
		ImageView ivImage = new ImageView(this);
		ivImage.setBackgroundDrawable(R.getDrawable(this, "btn_back"));
		ivImage.setScaleType(ScaleType.CENTER_INSIDE);
		String image = getIntent().getStringExtra("imagePath");
		try {
			ivImage.setImageBitmap(R.getBitmap(image));
		} catch(Throwable t) {
			System.gc();
			try {
				ivImage.setImageBitmap(R.getBitmap(image, 2));
			} catch(Throwable t1) {
				t1.printStackTrace();
			}
		}
		ivImage.setPadding(dp_4, dp_4, dp_4, dp_4);
		int dp_74 = R.dipToPx(this, 74);
		LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(dp_74, dp_74);
		int dp_16 = R.dipToPx(this, 16);
		lpImage.setMargins(0, dp_16, dp_8, 0);
		ivImage.setLayoutParams(lpImage);
		llRight.addView(ivImage);
		
		tvCounter = new TextView(this);
		tvCounter.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
		int dp_18 = R.dipToPx(this, 18);
		tvCounter.setPadding(0, 0, dp_8, dp_18);
		tvCounter.setText("420");
		tvCounter.setTextColor(0xffcfcfcf);
		tvCounter.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		tvCounter.setTypeface(Typeface.DEFAULT_BOLD);
		LinearLayout.LayoutParams lpCounter = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lpCounter.weight = 1;
		tvCounter.setLayoutParams(lpCounter);
		llRight.addView(tvCounter);
		
		TextView tvShareTo = new TextView(this);
		tvShareTo.setText(R.getString(this, "share_to"));
		tvShareTo.setTextColor(0xffcfcfcf);
		tvShareTo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		int dp_9 = R.dipToPx(this, 9);
		LinearLayout.LayoutParams lpShareTo = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpShareTo.gravity = Gravity.CENTER_VERTICAL;
		lpShareTo.setMargins(dp_9, 0, 0, 0);
		tvShareTo.setLayoutParams(lpShareTo);
		llToolBar.addView(tvShareTo);
		
		HorizontalScrollView sv = new HorizontalScrollView(this);
		sv.setHorizontalScrollBarEnabled(false);
		sv.setHorizontalFadingEdgeEnabled(false);
		LinearLayout.LayoutParams lpSv = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpSv.setMargins(dp_9, dp_9, dp_9, dp_9);
		sv.setLayoutParams(lpSv);
		llToolBar.addView(sv);
		
		llPlat = new LinearLayout(this);
		llPlat.setLayoutParams(new HorizontalScrollView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
		sv.addView(llPlat);
	}
	
	private String getShownText() {
		if ("Twitter".equals(platform)) {
			return getString(cn.sharesdk.demo.R.string.share_content_short);
		}
		return getIntent().getStringExtra("text");
	}
	
	/** 显示平台列表 */
	public void run() {
		String name = getIntent().getStringExtra("platform");
		int size = weiboList == null ? 0 : weiboList.length;
		views = new View[size];
		names = new String[size];
		
		int dp_36 = R.dipToPx(this, 36);
		LinearLayout.LayoutParams lpItem = new LinearLayout.LayoutParams(dp_36, dp_36);
		int dp_9 = R.dipToPx(this, 9);
		lpItem.setMargins(0, 0, dp_9, 0);
		FrameLayout.LayoutParams lpMask = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		lpMask.gravity = Gravity.LEFT | Gravity.TOP;
		for (int i = 0; i < size; i++) {
			FrameLayout fl = new FrameLayout(this);
			fl.setLayoutParams(lpItem);
			if (i >= size - 1) {
				fl.setLayoutParams(new LinearLayout.LayoutParams(dp_36, dp_36));
			}
			llPlat.addView(fl);
			fl.setOnClickListener(this);
			
			ImageView iv = new ImageView(this);
			iv.setScaleType(ScaleType.CENTER_INSIDE);
			iv.setImageBitmap(getPlatLogo(weiboList[i]));
			iv.setLayoutParams(new FrameLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			fl.addView(iv);
			
			views[i] = new View(this);
			views[i].setBackgroundColor(0x7fffffff);
			views[i].setOnClickListener(this);
			names[i] = weiboList[i].getName();
			if (names[i].equals(name)) {
				views[i].setVisibility(View.INVISIBLE);
				
				// 编辑分享内容的统计
				AbstractWeibo.logDemoEvent(3, weiboList[i]);
			}
			views[i].setLayoutParams(lpMask);
			fl.addView(views[i]);
		}
	}
	
	private Bitmap getPlatLogo(AbstractWeibo weibo) {
		if (weibo == null) {
			return null;
		}
		
		String name = weibo.getName();
		if (name == null) {
			return null;
		}
		
		return R.getBitmap(this, "logo_" + weibo.getName().toLowerCase());
	}
	
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		int remain = 420 - etContent.length();
		tvCounter.setText(String.valueOf(remain));
		tvCounter.setTextColor(remain > 0 ? 0xffcfcfcf : 0xffff0000);
	}
	
	public void afterTextChanged(Editable s) {
		
	}
	
	public void onClick(View v) {
		if (v.equals(llTitle.getBtnBack())) {

			AbstractWeibo weibo = null;
			for (int i = 0; i < views.length; i++) {
				if (views[i].getVisibility() == View.INVISIBLE) {
					weibo = AbstractWeibo.getWeibo(SharePage.this, names[i]);
					break;
				}
				weibo = null;
			}
			
			// 取消分享的统计
			if (weibo != null) {
				AbstractWeibo.logDemoEvent(5, weibo);
			}
			finish();
			return;
		}
		
		// 执行分享
		if (v.equals(llTitle.getBtnRight())) {
			for (int i = 0; i < views.length; i++) {
				if (views[i].getVisibility() != View.VISIBLE) {
					AbstractWeibo weibo = AbstractWeibo.getWeibo(
							SharePage.this, names[i]);
					weibo.setWeiboActionListener(SharePage.this);
					share(weibo);
				}
			}
			showNotification(5000, R.getString(this, "sharing"));
			finish();
			return;
		}
		
		if (v instanceof FrameLayout) {
			((FrameLayout) v).getChildAt(1).performClick();
			return;
		}
		
		if (v.getVisibility() == View.INVISIBLE) {
			v.setVisibility(View.VISIBLE);
		}
		else {
			v.setVisibility(View.INVISIBLE);
		}
	}
	
	/** 分享的逻辑代码在这里 */
	private void share(AbstractWeibo weibo) {
		String text = etContent.getText().toString();
		String imagePath = getIntent().getStringExtra("imagePath");
		String title = getIntent().getStringExtra("title");
		String comment = getIntent().getStringExtra("comment");
		String imageUrl = getIntent().getStringExtra("imageUrl");
		String titleUrl = getIntent().getStringExtra("titleUrl");
		String site = getIntent().getStringExtra("site");
		String siteUrl = getIntent().getStringExtra("siteUrl");
		
		String platform = weibo.getName();
		AbstractWeibo.ShareParams shareParams = null;
		if ("Evernote".equals(platform)) {
			Evernote.ShareParams sp = new Evernote.ShareParams();
			shareParams = sp;
			sp.title = title;
			sp.text = text;
			sp.imagePath = imagePath;
		}
		else if ("Renren".equals(platform)) {
			Renren.ShareParams sp = new Renren.ShareParams();
			shareParams = sp;
			sp.title = title;
			sp.text = text;
			sp.titleUrl = titleUrl;
			sp.comment = comment;
			sp.imageUrl = imageUrl;
			sp.imagePath = imagePath;
		}
		else if ("QZone".equals(platform)) {
			QZone.ShareParams sp = new QZone.ShareParams();
			shareParams = sp;
			sp.title = title;
			sp.text = text;
			sp.titleUrl = titleUrl;
			sp.comment = comment;
			sp.imageUrl = imageUrl;
			sp.imagePath = imagePath;
			sp.site = site;
			sp.siteUrl = siteUrl;
		}else{
			shareParams = new AbstractWeibo.ShareParams();
			shareParams.text = text;
			shareParams.imagePath = imagePath;
		}
		
		if (shareParams != null) {
			weibo.share(shareParams);
		}
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
		t.printStackTrace();
		
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = t;
		handler.sendMessage(msg);
		
		// 分享失败的统计
		AbstractWeibo.logDemoEvent(4, weibo);
	}
	
	public boolean handleMessage(Message msg) {
		switch (msg.arg1) {
			case 1: { // 成功
				showNotification(2000, R.getString(this, "share_completed"));
			}
			break;
			case 2: { // 失败
				if (msg.obj instanceof WechatClientNotExistException) {
					showNotification(2000, R.getString(this, "wechat_client_not_install"));
				}
				else if (msg.obj instanceof WechatTimelineNotSupportedException) {
					showNotification(2000, R.getString(this, "wechat_timeline_not_support"));
				}
				else {
					showNotification(2000, R.getString(this, "share_failed"));
				}
			}
			break;
			case 3: { // 取消
				showNotification(2000, R.getString(this, "share_canceled"));
			}
			break;
		}
		
		return false;
	}
	
	private void showNotification(long cancelTime, String text) {
		try {
			Context app = getApplicationContext();
			final NotificationManager nm = (NotificationManager) app
					.getSystemService(Context.NOTIFICATION_SERVICE);
			nm.cancelAll();
			
			int icon = notifIcon;
			String title = notifTitle;
			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, text, when);
			PendingIntent pi = PendingIntent.getActivity(app, 0, new Intent(), 0);
			notification.setLatestEventInfo(app, title, text, pi);
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			final int id = Integer.MAX_VALUE / 11 + 1;
			nm.notify(id, notification);
			
			if (cancelTime > 0) {
				handler.postDelayed(new Runnable() {
					public void run() {
						if (nm != null) {
							nm.cancel(id);
						}
					}
				}, cancelTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finish() {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
		}
		super.finish();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AbstractWeibo weibo = null;
			for (int i = 0; i < views.length; i++) {
				if (views[i].getVisibility() == View.INVISIBLE) {
					weibo = AbstractWeibo.getWeibo(SharePage.this, names[i]);
					break;
				}
				weibo = null;
			}
			
			// 取消分享的统计
			if (weibo != null) {
				AbstractWeibo.logDemoEvent(5, weibo);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
