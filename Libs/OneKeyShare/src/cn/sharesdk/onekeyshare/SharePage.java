/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.WeiboActionListener;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/** 执行图文分享的页面，此页面不支持微信平台的分享 */
public class SharePage extends FakeActivity implements Callback, Runnable,
		OnClickListener, WeiboActionListener, TextWatcher {
	private Handler handler;
	private int notifIcon;
	private String notifTitle;
	private LinearLayout llPage;
	private TitleLayout llTitle;
	private EditText etContent; // 文本编辑框
	private TextView tvCounter; // 字数计算器
	private LinearLayout llPlat;
	private AbstractWeibo[] weiboList; // 平台列表
	private View[] views;
	private String[] names;
	private WeiboActionListener waListener;
	private LinearLayout llAt;

	/**图片区域*/
	private ImageView ivImage;
	/**文件夹图片*/
	private ImageView ivPin;
	/**是否分享图片*/
	private boolean isShareImg = false;

	public SharePage(Context context) {
		super(context);
	}

	protected void onCreate(final Context context) {
		handler = new Handler(this);
		notifIcon = getIntent().getIntExtra("notif_icon", 0);
		notifTitle = getIntent().getStringExtra("notif_title");
		notifTitle = notifTitle == null ? "" : notifTitle;
		getWAL(getIntent());

		initPageView();
		setContentView(llPage);
		onTextChanged(etContent.getText(), 0, etContent.length(), 0);

		// 获取平台列表并过滤微信
		new Thread(){
			public void run() {
				weiboList = AbstractWeibo.getWeiboList(context);
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

	// 检测是否设置了自定义的回调，如果有则使用之，否则使用默认的回调
	private void getWAL(Intent intent) {
		waListener = this;
		try {
			String callback = intent.getStringExtra("callback");
			Class<?> clz = Class.forName(callback);
			if (WeiboActionListener.class.isAssignableFrom(clz)) {
				waListener = (WeiboActionListener) clz.newInstance();
			}
			else {
				waListener = this;
			}
		} catch(Throwable t) {
			waListener = this;
		}
	}

	private void initPageView() {
		llPage = new LinearLayout(getContext());
		llPage.setBackgroundColor(0xff323232);
		llPage.setOrientation(LinearLayout.VERTICAL);

		// 标题栏
		llTitle = new TitleLayout(getContext());
		llTitle.setBackgroundResource(R.drawable.title_back);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.multi_share);
		llTitle.getBtnRight().setVisibility(View.VISIBLE);
		llTitle.getBtnRight().setText(R.string.share);
		llTitle.getBtnRight().setOnClickListener(this);
		llTitle.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		llPage.addView(llTitle);

		FrameLayout flPage = new FrameLayout(getContext());
		LinearLayout.LayoutParams lpFl = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lpFl.weight = 1;
		flPage.setLayoutParams(lpFl);
		llPage.addView(flPage);

		// 页面主体
		LinearLayout llBody = new LinearLayout(getContext());
		llBody.setOrientation(LinearLayout.VERTICAL);
		FrameLayout.LayoutParams lpLl = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		lpLl.gravity = Gravity.LEFT | Gravity.TOP;
		llBody.setLayoutParams(lpLl);
		flPage.addView(llBody);

		// 别针图片
		ivPin = new ImageView(getContext());
		ivPin.setImageResource(R.drawable.pin);
		int dp_80 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 80);
		int dp_36 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 36);
		FrameLayout.LayoutParams lpPin = new FrameLayout.LayoutParams(dp_80, dp_36);
		lpPin.topMargin = cn.sharesdk.framework.res.R.dipToPx(getContext(), 6);
		lpPin.gravity = Gravity.RIGHT | Gravity.TOP;
		ivPin.setLayoutParams(lpPin);
		flPage.addView(ivPin);

		ImageView ivShadow = new ImageView(getContext());
		ivShadow.setBackgroundResource(R.drawable.title_shadow);
		ivShadow.setImageResource(R.drawable.title_shadow);
		FrameLayout.LayoutParams lpSd = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		ivShadow.setLayoutParams(lpSd);
		flPage.addView(ivShadow);

		LinearLayout llInput = new LinearLayout(getContext());
		llInput.setMinimumHeight(cn.sharesdk.framework.res.R.dipToPx(getContext(), 150));
		llInput.setBackgroundResource(R.drawable.edittext_back);
		LinearLayout.LayoutParams lpInput = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		int dp_3 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 3);
		lpInput.setMargins(dp_3, dp_3, dp_3, dp_3);
		lpInput.weight = 1;
		llInput.setLayoutParams(lpInput);
		llBody.addView(llInput);

		// 底部工具栏
		LinearLayout llToolBar = new LinearLayout(getContext());
		llToolBar.setBackgroundResource(R.drawable.share_tb_back);
		LinearLayout.LayoutParams lpTb = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		int dp_4 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 4);
		lpTb.setMargins(dp_4, 0, dp_4, dp_4);
		llToolBar.setLayoutParams(lpTb);
		llBody.addView(llToolBar);

		LinearLayout llContent = new LinearLayout(getContext());
		llContent.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams lpEt = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		lpEt.weight = 1;
		llContent.setLayoutParams(lpEt);
		llInput.addView(llContent);

		// 文字输入区域
		etContent = new EditText(getContext());
		etContent.setGravity(Gravity.LEFT | Gravity.TOP);
		etContent.setBackgroundDrawable(null);
		etContent.setText(getIntent().getStringExtra("text"));
		etContent.addTextChangedListener(this);
		etContent.setLayoutParams(lpEt);
		llContent.addView(etContent);

		String platform = getIntent().getStringExtra("platform");
		checkAtMth(llContent, platform);

		int dp_74 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 74);
		int dp_16 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 16);
		String imagePath = getIntent().getStringExtra("imagePath");
		if(!TextUtils.isEmpty(imagePath) && new File(imagePath).exists()){
			LinearLayout llRight = new LinearLayout(getContext());
			llRight.setOrientation(LinearLayout.VERTICAL);
			llRight.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
			llInput.addView(llRight);

			// 输入区域的图片
			ivImage = new ImageView(getContext());
			ivImage.setBackgroundResource(R.drawable.btn_back_nor);
			ivImage.setScaleType(ScaleType.CENTER_INSIDE);
			ivImage.setVisibility(View.GONE);
			ivImage.setVisibility(View.VISIBLE);
			try {
				isShareImg = true;
				ivImage.setImageBitmap(cn.sharesdk.framework.res.R.getBitmap(imagePath));
			} catch(Throwable t) {
				System.gc();
				try {
					ivImage.setImageBitmap(cn.sharesdk.framework.res.R.getBitmap(imagePath, 2));
				} catch(Throwable t1) {
					t1.printStackTrace();
					isShareImg = false;
				}
			}

			ivImage.setPadding(dp_4, dp_4, dp_4, dp_4);
			LinearLayout.LayoutParams lpImage = new LinearLayout.LayoutParams(dp_74, dp_74);
			int dp_8 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 8);
			lpImage.setMargins(0, dp_16, dp_8, 0);
			ivImage.setLayoutParams(lpImage);
			llRight.addView(ivImage);
			if(!isShareImg){
				ivPin.setVisibility(View.GONE);
				ivImage.setVisibility(View.GONE);
			}
		}else {
			isShareImg = false;
			ivPin.setVisibility(View.GONE);
		}

		// 取消图片的btn
		if(isShareImg){
			Button btn = new Button(getContext());
			btn.setTag("img_cancel");
			btn.setOnClickListener(this);
			btn.setBackgroundResource(R.drawable.img_cancel);
			int dp_20 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 20);
			int dp_83 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 83);
			int dp_13 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 13);
			FrameLayout.LayoutParams lpBtn = new FrameLayout.LayoutParams(dp_20, dp_20);
			lpBtn.topMargin = dp_83;
			lpBtn.rightMargin = dp_13;
			lpBtn.gravity = Gravity.RIGHT | Gravity.TOP;
			btn.setPadding(dp_4, dp_4, dp_4, dp_4);
			btn.setLayoutParams(lpBtn);
			flPage.addView(btn);
		}

		// 字数统计
		tvCounter = new TextView(getContext());
		tvCounter.setText("420");
		tvCounter.setTextColor(0xffcfcfcf);
		tvCounter.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		tvCounter.setTypeface(Typeface.DEFAULT_BOLD);
		FrameLayout.LayoutParams lpCounter = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpCounter.bottomMargin = dp_74;
		lpCounter.gravity = Gravity.RIGHT | Gravity.BOTTOM;

		tvCounter.setPadding(0, 0, dp_16, 0);
		tvCounter.setLayoutParams(lpCounter);
		flPage.addView(tvCounter);

		TextView tvShareTo = new TextView(getContext());
		tvShareTo.setText(R.string.share_to);
		tvShareTo.setTextColor(0xffcfcfcf);
		tvShareTo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		int dp_9 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 9);
		LinearLayout.LayoutParams lpShareTo = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpShareTo.gravity = Gravity.CENTER_VERTICAL;
		lpShareTo.setMargins(dp_9, 0, 0, 0);
		tvShareTo.setLayoutParams(lpShareTo);
		llToolBar.addView(tvShareTo);

		HorizontalScrollView sv = new HorizontalScrollView(getContext());
		sv.setHorizontalScrollBarEnabled(false);
		sv.setHorizontalFadingEdgeEnabled(false);
		LinearLayout.LayoutParams lpSv = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpSv.setMargins(dp_9, dp_9, dp_9, dp_9);
		sv.setLayoutParams(lpSv);
		llToolBar.addView(sv);

		llPlat = new LinearLayout(getContext());
		llPlat.setLayoutParams(new HorizontalScrollView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
		sv.addView(llPlat);
	}

	/** 显示平台列表 */
	public void run() {
		String name = getIntent().getStringExtra("platform");
		int size = weiboList == null ? 0 : weiboList.length;
		views = new View[size];
		names = new String[size];

		int dp_36 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 36);
		LinearLayout.LayoutParams lpItem = new LinearLayout.LayoutParams(dp_36, dp_36);
		int dp_9 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 9);
		lpItem.setMargins(0, 0, dp_9, 0);
		FrameLayout.LayoutParams lpMask = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		lpMask.gravity = Gravity.LEFT | Gravity.TOP;
		for (int i = 0; i < size; i++) {
			FrameLayout fl = new FrameLayout(getContext());
			fl.setLayoutParams(lpItem);
			if (i >= size - 1) {
				fl.setLayoutParams(new LinearLayout.LayoutParams(dp_36, dp_36));
			}
			llPlat.addView(fl);
			fl.setOnClickListener(this);

			ImageView iv = new ImageView(getContext());
			iv.setScaleType(ScaleType.CENTER_INSIDE);
			iv.setImageBitmap(getPlatLogo(weiboList[i]));
			iv.setLayoutParams(new FrameLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			fl.addView(iv);

			views[i] = new View(getContext());
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

	// 进新浪微博、腾讯微博、Facebook和Twitter支持At功能
	private void checkAtMth(LinearLayout llInput, String platform) {
		if ("SinaWeibo".equals(platform) || "TencentWeibo".equals(platform)
				|| "Facebook".equals(platform) || "Twitter".equals(platform)) {
			llAt= new LinearLayout(getContext());
			FrameLayout.LayoutParams lpAt = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lpAt.leftMargin = cn.sharesdk.framework.res.R.dipToPx(getContext(), 10);
			lpAt.bottomMargin = cn.sharesdk.framework.res.R.dipToPx(getContext(), 10);
			lpAt.gravity = Gravity.LEFT | Gravity.BOTTOM;
			llAt.setLayoutParams(lpAt);
			llAt.setOnClickListener(this);
			llInput.addView(llAt);

			TextView tvAt = new TextView(getContext());
			tvAt.setBackgroundResource(R.drawable.btn_back_nor);
			int dp_32 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 32);
			tvAt.setLayoutParams(new LinearLayout.LayoutParams(dp_32, dp_32));
			tvAt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
			tvAt.setText("@");
			int dp_2 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 2);
			tvAt.setPadding(0, 0, 0, dp_2);
			tvAt.setTypeface(Typeface.DEFAULT_BOLD);
			tvAt.setTextColor(0xff000000);
			tvAt.setGravity(Gravity.CENTER);
			llAt.addView(tvAt);

			TextView tvName = new TextView(getContext());
			tvName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
			tvName.setTextColor(0xff000000);
			String text = getContext().getString(R.string.list_friends, getName(platform));
			tvName.setText(text);
			LinearLayout.LayoutParams lpName = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lpName.gravity = Gravity.CENTER_VERTICAL;
			tvName.setLayoutParams(lpName);
			llAt.addView(tvName);
		}
	}

	private String getName(String name) {
		if (name == null) {
			return "";
		}

		int resId = cn.sharesdk.framework.res.R.getStringRes(getContext(), name);
		return getContext().getString(resId);
	}

	private Bitmap getPlatLogo(AbstractWeibo weibo) {
		if (weibo == null) {
			return null;
		}

		String name = weibo.getName();
		if (name == null) {
			return null;
		}

		String resName = "logo_" + weibo.getName();
		int resId = cn.sharesdk.framework.res.R.getResId(R.drawable.class, resName);
		return BitmapFactory.decodeResource(getResources(), resId);
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
					weibo = AbstractWeibo.getWeibo(getContext(), names[i]);
					break;
				}
				weibo = null;
			}

			// 分享失败的统计
			if (weibo != null) {
				AbstractWeibo.logDemoEvent(5, weibo);
			}
			finish();
			return;
		}

		// 取消分享的统计
		if (v.equals(llTitle.getBtnRight())) {
			boolean showing = false;
			for (int i = 0; i < views.length; i++) {
				if (views[i].getVisibility() != View.VISIBLE) {
					AbstractWeibo weibo = AbstractWeibo.getWeibo(
							getContext(), names[i]);
					weibo.setWeiboActionListener(waListener);
					share(weibo);
					showing = true;
				}
			}
			if (showing) {
				showNotification(5000, getContext().getString(R.string.sharing));
				finish();
			}
			else {
				Toast.makeText(getContext(), R.string.select_one_plat_at_least,
						Toast.LENGTH_SHORT).show();
			}
			return;
		}

		if (v.equals(llAt)) {
			Intent i = new Intent();
			i.putExtra("platform", getIntent().getStringExtra("platform"));
			FollowList subPage = new FollowList(getContext());
			subPage.setBackPage(this);
			subPage.show(i);
			return;
		}

		// 取消分享图片
		if("img_cancel".equals(v.getTag())){
			v.setVisibility(View.GONE);
			ivPin.setVisibility(View.GONE);
			ivImage.setVisibility(View.GONE);
			isShareImg = false;
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

	public void onResult(ArrayList<String> selected) {
		StringBuilder sb = new StringBuilder();
		for (String sel : selected) {
			sb.append('@').append(sel).append(' ');
		}
		etContent.append(sb.toString());
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
		String venueName = getIntent().getStringExtra("venueName");
		String venueDescription = getIntent().getStringExtra("venueDescription");
		float latitude = getIntent().getFloatExtra("latitude", 0);
		float longitude = getIntent().getFloatExtra("longitude", 0);

		if(!isShareImg){
			imagePath = null;
		}

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("text", text);
		data.put("imagePath", imagePath);
		data.put("title", title);
		data.put("comment", comment);
		data.put("imageUrl", imageUrl);
		data.put("titleUrl", titleUrl);
		data.put("site", site);
		data.put("siteUrl", siteUrl);
		data.put("venueName", venueName);
		data.put("venueDescription", venueDescription);
		data.put("latitude", latitude);
		data.put("longitude", longitude);
		new ShareCore().share(weibo, data);
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
				showNotification(2000, getContext().getString(R.string.share_completed));
			}
			break;
			case 2: { // 取消
				String expName = msg.obj.getClass().getSimpleName();
				if ("WechatClientNotExistException".equals(expName)
						|| "WechatTimelineNotSupportedException".equals(expName)) {
					showNotification(2000, getContext().getString(R.string.wechat_client_inavailable));
				}
				else {
					showNotification(2000, getContext().getString(R.string.share_failed));
				}
			}
			break;
			case 3: { // 失败
				showNotification(2000, getContext().getString(R.string.share_canceled));
			}
			break;
		}

		return false;
	}

	private void showNotification(long cancelTime, String text) {
		try {
			Context app = getContext().getApplicationContext();
			final NotificationManager nm = (NotificationManager) app
					.getSystemService(Context.NOTIFICATION_SERVICE);
			final int id = Integer.MAX_VALUE / 13 + 1;
			nm.cancel(id);

			int icon = notifIcon;
			String title = notifTitle;
			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, text, when);
			PendingIntent pi = PendingIntent.getActivity(app, 0, new Intent(), 0);
			notification.setLatestEventInfo(app, title, text, pi);
			notification.flags = Notification.FLAG_AUTO_CANCEL;
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
		InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
		}
		super.finish();
	}

	public boolean onKeyEvent(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			AbstractWeibo weibo = null;
			for (int i = 0; i < views.length; i++) {
				if (views[i].getVisibility() == View.INVISIBLE) {
					weibo = AbstractWeibo.getWeibo(getContext(), names[i]);
					break;
				}
				weibo = null;
			}

			// 取消分享的统计
			if (weibo != null) {
				AbstractWeibo.logDemoEvent(5, weibo);
			}
		}
		return super.onKeyEvent(keyCode, event);
	}

}
