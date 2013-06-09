//#if def{lang} == cn
/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */
//#endif

package cn.sharesdk.onekeyshare;

import java.io.File;
import java.util.HashMap;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
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
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.WeiboActionListener;

//#if def{lang} == cn
/**
 * 平台宫格列表显示工具。
 * <p>
 * WeiboGridView对“android.support”包有依赖，因此请注意检查您项目中是
 *否已经集成了相应的jar包
 */
//#endif
public class WeiboGridView extends LinearLayout implements Runnable,
		OnPageChangeListener, OnClickListener, WeiboActionListener, Callback {
	private static final int PAGE_SIZE = 9; // def{note.WeiboGridView.9_gird_every_page.def{lang}}
	private ViewPager pager; // def{note.WeiboGridView.gird_container.def{lang}}
	private ImageView[] points; // def{note.WeiboGridView.page_indicator.def{lang}}
	private Bitmap grayPoint;
	private Bitmap whitePoint;
	private boolean silent; // def{note.WeiboGridView.flag_of_go_sharepage.def{lang}}
	private AbstractWeibo[] weiboList; // def{note.WeiboGridView.plat_data.def{lang}}
	private Handler handler;
	private Intent intent; // def{note.WeiboGridView.intent_from_outsize.def{lang}}
	private WeiboActionListener waListener;
	
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
		int dp_10 = cn.sharesdk.framework.res.R.dipToPx(context, 10);
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
	
	//#if def{lang} == cn
	/** 初始化宫格列表ui */
	//#endif
	public void run() {
		Context context = getContext();
		
		// def{note.WeiboGridView.start_thread_to_get_list.def{lang}}
		int pageSize = weiboList.length > PAGE_SIZE 
				? PAGE_SIZE : weiboList.length;
		int lines = pageSize / 3;
		if (pageSize % 3 > 0) {
			lines++;
		}
		ViewGroup.LayoutParams lp = pager.getLayoutParams();
		int dp_10 = cn.sharesdk.framework.res.R.dipToPx(context, 10);
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
		
		// def{note.WeiboGridView.set_page_indicator.def{lang}}
		LinearLayout llPoints = new LinearLayout(context);
		LayoutParams lpLl = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.CENTER_HORIZONTAL;
		llPoints.setLayoutParams(lpLl);
		addView(llPoints);
		
		int dp_5 = cn.sharesdk.framework.res.R.dipToPx(context, 5);
		grayPoint = BitmapFactory.decodeResource(getResources(), R.drawable.gray_point);
		whitePoint = BitmapFactory.decodeResource(getResources(), R.drawable.white_point);
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
	
	//#if def{lang} == cn
	/**
	 * 设置页面初始化和分享操作需要的数据
	 * <p>
	 * 此方法在{@link ShareAllGird}的UI初始化中被调用
	 * 
	 * @param intent 携带初始化数据的Intent
	 */
	//#endif
	public void setData(Intent intent) {
		this.intent = intent;
		silent = intent.getBooleanExtra("silent", false);
		getWAL(intent);
	}
	
	//#if def{lang} == cn
	// 检测是否设置了自定义的回调，如果有则使用之，否则使用默认的回调
	//#endif
	private void getWAL(Intent intent) {
		waListener = this;
		try {
			String callback = intent.getStringExtra("callback");
			Class<?> clz = Class.forName(callback);
			if (WeiboActionListener.class.isAssignableFrom(clz)) {
				waListener = (WeiboActionListener) clz.newInstance();
			}
		} catch(Throwable t) {
			waListener = this;
		}
	}
	
	public void onClick(View v) {
		AbstractWeibo weibo = (AbstractWeibo) v.getTag();
		if (weibo != null) {
			if (silent) { // def{note.WeiboGridView.share_derectly.def{lang}}
				weibo.setWeiboActionListener(waListener);
				if (!shareSilently(weibo)) {
					return;
				}
				showNotification(5000, getContext().getString(R.string.sharing));
			}
			else {
				String name = weibo.getName();
				// def{note.WeiboGridView.sharepage_not_suppor_these_plats.def{lang}}
				if ("Wechat".equals(name) || "WechatMoments".equals(name)
						|| "ShortMessage".equals(name) || "Email".equals(name)
						|| "GooglePlus".equals(name)) {
					weibo.setWeiboActionListener(waListener);
					if (!shareSilently(weibo)) {
						return;
					}
					showNotification(5000, getContext().getString(R.string.sharing));
				}
				else { // def{note.WeiboGridView.go_sharepage.def{lang}}
					Intent i = new Intent(getContext(), SharePage.class);
					i.putExtras(intent.getExtras());
					i.putExtra("platform", weibo.getName());
					getContext().startActivity(i);
				}
			}
		}
		
		((Activity) getContext()).finish();
	}
	
	//#if def{lang} == cn
	// 直接分享
	//#endif
	private boolean shareSilently(AbstractWeibo weibo) {
		if (intent == null) {
			return false;
		}
		
		String platform = weibo.getName();
		boolean isWechat = "WechatMoments".equals(platform) || "Wechat".equals(platform);
		if (isWechat && !weibo.isValid()) {
			String msg = getContext().getString(R.string.wechat_client_inavailable);
			Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
			return false;
		}
		boolean isGooglePlus = "GooglePlus".equals(platform);
		if (isGooglePlus && !weibo.isValid()) {
			String msg = getContext().getString(R.string.google_plus_client_inavailable);
			Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
			return false;
		}
		
		
		String address = intent.getStringExtra("address");
		String title = intent.getStringExtra("title");
		String titleUrl = intent.getStringExtra("titleUrl");
		String text = intent.getStringExtra("text");
		String imagePath = intent.getStringExtra("imagePath");
		String imageUrl = intent.getStringExtra("imageUrl");
		String url = intent.getStringExtra("url");
		String comment = intent.getStringExtra("comment");
		String site = intent.getStringExtra("site");
		String siteUrl = intent.getStringExtra("siteUrl");
		String venueName = intent.getStringExtra("venueName");
		String venueDescription = intent.getStringExtra("venueDescription");
		float latitude = intent.getFloatExtra("latitude", 0);
		float longitude = intent.getFloatExtra("longitude", 0);
		int shareType = AbstractWeibo.SHARE_TEXT;
		if (imagePath != null && (new File(imagePath)).exists()
				&& url != null && url.length() > 0) {
			shareType = AbstractWeibo.SHARE_WEBPAGE;
		}
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("address", address);
		data.put("url", url);
		data.put("text", text);
		data.put("imagePath", imagePath);
		data.put("title", title);
		data.put("comment", comment);
		data.put("imageUrl", imageUrl);
		data.put("titleUrl", titleUrl);
		data.put("site", site);
		data.put("siteUrl", siteUrl);
		data.put("shareType", shareType);
		data.put("venueName", venueName);
		data.put("venueDescription", venueDescription);
		data.put("latitude", latitude);
		data.put("longitude", longitude);
		return new ShareCore().share(weibo, data);
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
	}
	
	public boolean handleMessage(Message msg) {
		switch (msg.arg1) {
			case 1: { // def{note.complete.def{lang}}
				showNotification(2000, getContext().getString(R.string.share_completed));
			}
			break;
			case 2: { // def{note.error.def{lang}}
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
			case 3: { // def{note.cancel.def{lang}}
				showNotification(2000, getContext().getString(R.string.share_canceled));
			}
			break;
		}
		
		return false;
	}
	
	//#if def{lang} == cn
	// 在状态栏提示分享操作
	//#endif
	private void showNotification(long cancelTime, String text) {
		if (intent == null) {
			return;
		}
		
		try {
			Context app = getContext().getApplicationContext();
			final NotificationManager nm = (NotificationManager) app
					.getSystemService(Context.NOTIFICATION_SERVICE);
			final int id = Integer.MAX_VALUE / 13 + 1;
			nm.cancel(id);
			
			int icon = intent.getIntExtra("notif_icon", 0);
			String title = intent.getStringExtra("notif_title");
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
	
	//#if def{lang} == cn
	/** 宫格列表数据适配器 */
	//#endif
	private static class WeiboAdapter extends PagerAdapter {
		private GridView[] girds; // def{note.ShareAllGird.grid.def{lang}}
		private AbstractWeibo[] weibos; // def{note.WeiboGridView.plat_data.def{lang}}
		private OnClickListener callback;
		private int lines; // def{note.WeiboGridView.line_num.def{lang}}
		
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
	
	//#if def{lang} == cn
	/** 简易的宫格列表控件 */
	//#endif
	private static class GridView extends LinearLayout {
		private AbstractWeibo[] weibos; // def{note.SharePage.weiboList.def{lang}}
		private OnClickListener callback;
		private int lines;
		private int iconWidth; // def{note.WeiboGridView.icon_width.def{lang}}

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
			int dp_10 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 10);
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
			int dp_5 = cn.sharesdk.framework.res.R.dipToPx(context, 5);
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
			tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
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
			
			String resName = "logo_" + weibo.getName();
			int resId = cn.sharesdk.framework.res.R.getResId(R.drawable.class, resName);
			return BitmapFactory.decodeResource(getResources(), resId);
		}
		
		private String getName(AbstractWeibo weibo) {
			if (weibo == null) {
				return "";
			}
			
			String name = weibo.getName();
			if (name == null) {
				return "";
			}
			
			int resId = cn.sharesdk.framework.res.R.getResId(R.string.class, weibo.getName());
			return getContext().getString(resId);
		}
		
	}
	
}
