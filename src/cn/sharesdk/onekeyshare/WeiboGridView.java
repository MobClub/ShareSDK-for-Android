/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import java.util.HashMap;

import cn.sharesdk.evernote.Evernote;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.onekeyshare.res.R;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qzone.QZone;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

/**
 * 平台宫格列表显示工具。
 * <p>
 * WeiboGridView对“android.support”包有依赖，因此请注意检查您项目中是
 *否已经集成了相应的jar包
 */
public class WeiboGridView extends LinearLayout implements Runnable,
		OnPageChangeListener, OnClickListener, WeiboActionListener, Callback {
	private static final int PAGE_SIZE = 9; // 每页显示9格
	private ViewPager pager; // 宫格容器
	private ImageView[] points; // 页面指示器
	private Bitmap grayPoint;
	private Bitmap whitePoint;
	private boolean silent; // 是否不跳转SharePage而直接分享
	private AbstractWeibo[] weiboList; // 平台列表
	private Handler handler;
	private Intent intent; // 从外部传进来的Intent（含初始化数据）
	
	public WeiboGridView(Context context) {
		super(context);
		init(context);
	}

	public WeiboGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(final Context context) {
		handler = new Handler(this);
		setOrientation(VERTICAL);
		int dp_10 = R.dipToPx(context, 10);
		setPadding(dp_10, dp_10, dp_10, dp_10);
		
		pager = new ViewPager(context);
		pager.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		pager.setOnPageChangeListener(this);
		addView(pager);
		
		// 为了更好的ui效果，开启子线程获取平台列表
		new Thread(){
			public void run() {
				weiboList = AbstractWeibo.getWeiboList(context);
				handler.post(WeiboGridView.this);
			}
		}.start();
	}
	
	/** 初始化宫格列表ui */
	public void run() {
		Context context = getContext();
		
		// 计算pager的页数和高度
		int pageSize = weiboList.length > PAGE_SIZE 
				? PAGE_SIZE : weiboList.length;
		int lines = pageSize / 3;
		if (pageSize % 3 > 0) {
			lines++;
		}
		ViewGroup.LayoutParams lp = pager.getLayoutParams();
		int dp_10 = R.dipToPx(context, 10);
		int scrW = getResources().getDisplayMetrics().widthPixels;
		lp.height = (scrW - dp_10 * 2) * lines / 3;
		pager.setLayoutParams(lp);
		WeiboAdapter adapter = new WeiboAdapter(weiboList, this);
		pager.setAdapter(adapter);
		int pageCount = 0;
		if (weiboList != null) {
			int size = weiboList.length;
			pageCount = size / PAGE_SIZE;
			if (size % PAGE_SIZE > 0) {
				pageCount++;
			}
		}
		points = new ImageView[pageCount];
		if (points.length <= 0) {
			return;
		}
		
		// 设置页面指示器
		LinearLayout llPoints = new LinearLayout(context);
		LayoutParams lpLl = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.CENTER_HORIZONTAL;
		llPoints.setLayoutParams(lpLl);
		addView(llPoints);
		
		int dp_5 = R.dipToPx(context, 5);
		grayPoint = R.getBitmap(getContext(), "gray_point");
		whitePoint = R.getBitmap(getContext(), "white_point");
		for (int i = 0; i < pageCount; i++) {
			points[i] = new ImageView(context);
			points[i].setScaleType(ScaleType.CENTER_INSIDE);
			points[i].setImageBitmap(grayPoint);
			LayoutParams lpIv = new LayoutParams(dp_5, dp_5);
			lpIv.setMargins(dp_5, dp_5, dp_5, 0);
			points[i].setLayoutParams(lpIv);
			llPoints.addView(points[i]);
		}
		int curPage = pager.getCurrentItem();
		points[curPage].setImageBitmap(whitePoint);
	}
	
	public void onPageScrollStateChanged(int state) {
		if (ViewPager.SCROLL_STATE_IDLE == state) {
			for (int i = 0; i < points.length; i++) {
				points[i].setImageBitmap(grayPoint);
			}
			
			int curPage = pager.getCurrentItem();
			points[curPage].setImageBitmap(whitePoint);
		}
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	public void onPageSelected(int position) {
		
	}
	
	/**
	 * 设置页面初始化和分享操作需要的数据
	 * <p>
	 * 此方法在{@link ShareAllGird}的UI初始化中被调用
	 * 
	 * @param intent 携带初始化数据的Intent
	 */
	public void setData(Intent intent) {
		this.intent = intent;
		silent = intent.getBooleanExtra("silent", false);
	}
	
	public void onClick(View v) {
		AbstractWeibo weibo = (AbstractWeibo) v.getTag();
		if (weibo != null) {
			if (silent) { // 直接执行分享
				weibo.setWeiboActionListener(this);
				shareSilently(weibo);
				showNotification(5000, R.getString(getContext(), "sharing"));
			}
			else {
				String name = weibo.getName();
				// SharePage不支持微信平台、信息和邮件，总是执行直接分享
				if ("Wechat".equals(name) || "WechatMoments".equals(name)
						|| "ShortMessage".equals(name) || "Email".equals(name)) {
					weibo.setWeiboActionListener(this);
					shareSilently(weibo);
					showNotification(5000, R.getString(getContext(), "sharing"));
				}
				else { // 跳转SharePage分享
					Intent i = new Intent(getContext(), SharePage.class);
					i.putExtras(intent.getExtras());
					i.putExtra("platform", weibo.getName());
					getContext().startActivity(i);
				}
			}
		}
		
		((Activity) getContext()).finish();
	}
	
	// 直接分享
	private void shareSilently(final AbstractWeibo weibo) {
		new Thread() {
			public void run() {
				if (intent == null) {
					return;
				}
				
				String address = intent.getStringExtra("address");
				String title = intent.getStringExtra("title");
				String text = intent.getStringExtra("text");
				String image = intent.getStringExtra("image");
				String imageUrl = intent.getStringExtra("image_url");
				String site = intent.getStringExtra("site");
				String siteUrl = intent.getStringExtra("siteUrl");
				
				if ("QZone".equals(weibo.getName())) {
					if (title != null && imageUrl != null 
							&& site != null && siteUrl != null) {
						// qq空间使用“add_share”接口
						((QZone) weibo).share(title, null, null, text, imageUrl, site, siteUrl);
					}
					else {
						weibo.share(text, image);
					}
				}
				else if ("Evernote".equals(weibo.getName())) {
					if (title != null) {
						// 印象笔记使用"save"接口
						((Evernote) weibo).save(title, text, image);
					}
					else {
						weibo.share(text, image);
					}
				}
				else if ("Twitter".equals(weibo.getName())) {
					text = getContext().getString(cn.sharesdk.demo.R.string.share_content_short);
					weibo.share("@Share SDK", image);
				}
				else if ("ShortMessage".equals(weibo.getName())) {
					((ShortMessage) weibo).send(address, title, "1"/*, image*/);
				}
				else if ("Email".equals(weibo.getName())) {
					((Email) weibo).send(address, title, text, image);
				}
				else {
					weibo.share(text, image);
				}
			}
		}.start();
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
		switch (msg.arg1) {
			case 1: { // 成功
				showNotification(2000, R.getString(getContext(), "share_completed"));
			}
			break;
			case 2: { // 失败
				showNotification(2000, R.getString(getContext(), "share_failed"));
			}
			break;
			case 3: { // 取消
				showNotification(2000, R.getString(getContext(), "share_canceled"));
			}
			break;
		}
		
		return false;
	}
	
	// 在状态栏提示分享操作
	private void showNotification(long cancelTime, String text) {
		if (intent == null) {
			return;
		}
		
		try {
			Context app = getContext().getApplicationContext();
			final NotificationManager nm = (NotificationManager) app
					.getSystemService(Context.NOTIFICATION_SERVICE);
			nm.cancelAll();
			
			int icon = intent.getIntExtra("notif_icon", 0);
			String title = intent.getStringExtra("notif_title");
			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, text, when);
			PendingIntent pi = PendingIntent.getActivity(app, 0, new Intent(), 0);
			notification.setLatestEventInfo(app, title, text, pi);
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify(1, notification);
			
			if (cancelTime > 0) {
				handler.postDelayed(new Runnable() {
					public void run() {
						if (nm != null) {
							nm.cancelAll();
						}
					}
				}, cancelTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 宫格列表数据适配器 */
	private static class WeiboAdapter extends PagerAdapter {
		private GridView[] girds; // 宫格列表
		private AbstractWeibo[] weibos; // 平台数据
		private OnClickListener callback;
		private int lines; // 行数
		
		public WeiboAdapter(AbstractWeibo[] weibos, OnClickListener callback) {
			this.weibos = weibos;
			this.callback = callback;
			girds = null;
			
			if (weibos != null) {
				int size = weibos.length;
				int pageCount = size / PAGE_SIZE;
				if (size % PAGE_SIZE > 0) {
					pageCount++;
				}
				girds = new GridView[pageCount];
			}
		}

		public int getCount() {
			return girds == null ? 0 : girds.length;
		}

		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}
		
		public Object instantiateItem(ViewGroup container, int position) {
			if (girds[position] == null) {
				int pageSize = PAGE_SIZE;
				int curSize = pageSize * position;
				int listSize = weibos == null ? 0 : weibos.length;
				if (curSize + pageSize > listSize) {
					pageSize = listSize - curSize;
				}
				AbstractWeibo[] gridWeibos = new AbstractWeibo[pageSize];
				for (int i = 0; i < pageSize; i++) {
					gridWeibos[i] = weibos[curSize + i];
				}
				
				if (position == 0) {
					lines = gridWeibos.length / 3;
					if (gridWeibos.length % 3 > 0) {
						lines++;
					}
				}
				girds[position] = new GridView(container.getContext(), callback);
				girds[position].setData(lines, gridWeibos);
			}
			container.addView(girds[position]);
			return girds[position];
		}
		
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
	}
	
	/** 简易的宫格列表控件 */
	private static class GridView extends LinearLayout {
		private AbstractWeibo[] weibos; // 平台列表
		private OnClickListener callback;
		private int lines;
		private int iconWidth; // 格子宽度

		public GridView(Context context, OnClickListener callback) {
			super(context);
			this.callback = callback;
		}
		
		public void setData(int lines, AbstractWeibo[] weibos) {
			this.lines = lines;
			this.weibos = weibos;
			init();
		}
		
		private void init() {
			int dp_10 = R.dipToPx(getContext(), 10);
			int scrW = getResources().getDisplayMetrics().widthPixels;
			iconWidth = (scrW - dp_10 * 2) / 3 - dp_10 * 4;
			
			setOrientation(VERTICAL);
			
			int size = weibos == null ? 0 : weibos.length;
			int lineSize = size / 3;
			if (size % 3 > 0) {
				lineSize++;
			}
			LayoutParams lp = new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			lp.weight = 1;
			for (int i = 0; i < lines; i++) {
				LinearLayout llLine = new LinearLayout(getContext());
				llLine.setLayoutParams(lp);
				addView(llLine);
				
				if (i >= lineSize) {
					continue;
				}
				
				for (int j = 0; j < 3; j++) {
					final int index = i * 3 + j;
					if (index >= size) {
						LinearLayout llItem = new LinearLayout(getContext());
						llItem.setLayoutParams(lp);
						llLine.addView(llItem);
						continue;
					}
					
					final LinearLayout llItem = getView(index, getContext());
					llItem.setTag(weibos[index]);
					llItem.setOnClickListener(callback);
					llItem.setLayoutParams(lp);
					llLine.addView(llItem);
				}
			}
		}
		
		private LinearLayout getView(int position, Context context) {
			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.VERTICAL);
			int dp_5 = R.dipToPx(context, 5);
			ll.setPadding(dp_5, dp_5, dp_5, dp_5);
			
			ImageView iv = new ImageView(context);
			iv.setScaleType(ScaleType.CENTER_INSIDE);
			LinearLayout.LayoutParams lpIv = new LinearLayout.LayoutParams(
					iconWidth, iconWidth);
			lpIv.gravity = Gravity.CENTER_HORIZONTAL;
			iv.setLayoutParams(lpIv);
			iv.setImageBitmap(getIcon(weibos[position]));
			ll.addView(iv);
			
			TextView tv = new TextView(context);
			tv.setTextColor(0xffffffff);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			tv.setSingleLine();
			tv.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams lpTv = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			lpTv.weight = 1;
			tv.setLayoutParams(lpTv);
			tv.setText(getName(weibos[position]));
			ll.addView(tv);
			
			return ll;
		}
		
		private Bitmap getIcon(AbstractWeibo weibo) {
			if (weibo == null) {
				return null;
			}
			
			String name = weibo.getName();
			if (name == null) {
				return null;
			}
			
			return R.getBitmap(getContext(), "logo_" + weibo.getName().toLowerCase());
		}
		
		private String getName(AbstractWeibo weibo) {
			if (weibo == null) {
				return "";
			}
			
			String name = weibo.getName();
			if (name == null) {
				return "";
			}
			
			return R.getString(getContext(), weibo.getName().toLowerCase());
		}
		
	}
	
}
