/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import java.util.HashMap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import cn.sharesdk.demo.R;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;

/**
 * 平台宫格列表显示工具。
 * <p>
 * PlatformGridView对“android.support”包有依赖，因此请注意检查您项目中是
 *否已经集成了相应的jar包
 */
public class PlatformGridView extends LinearLayout implements
		OnPageChangeListener, OnClickListener, Callback {
	private static final int MSG_PLATFORM_LIST_GOT = 1;
	private static final int PAGE_SIZE = 9; // 每页显示9格
	private ViewPager pager; // 宫格容器
	private ImageView[] points; // 页面指示器
	private Bitmap grayPoint;
	private Bitmap whitePoint;
	private boolean silent; // 是否不跳转EditPage而直接分享
	private Platform[] platformList; // 平台数据
	private HashMap<String, Object> reqData; // 从外部传进来的分享数据（含初始化数据）
	private OnekeyShare parent;

	public PlatformGridView(Context context) {
		super(context);
		init(context);
	}

	public PlatformGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(final Context context) {
		setOrientation(VERTICAL);
		int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(context, 10);
		setPadding(dp_10, dp_10, dp_10, dp_10);

		pager = new ViewPager(context);
		pager.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		pager.setOnPageChangeListener(this);
		addView(pager);

		// 为了更好的ui效果，开启子线程获取平台列表
		new Thread(){
			public void run() {
				platformList = ShareSDK.getPlatformList(context);
				UIHandler.sendEmptyMessage(MSG_PLATFORM_LIST_GOT, PlatformGridView.this);
			}
		}.start();
	}

	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case MSG_PLATFORM_LIST_GOT: {
				afterPlatformListGot();
			}
			break;
		}
		return false;
	}

	/** 初始化宫格列表ui */
	public void afterPlatformListGot() {
		Context context = getContext();

		// 为了更好的ui效果，开启子线程获取平台列表
		int pageSize = platformList.length > PAGE_SIZE
				? PAGE_SIZE : platformList.length;
		int lines = pageSize / 3;
		if (pageSize % 3 > 0) {
			lines++;
		}
		ViewGroup.LayoutParams lp = pager.getLayoutParams();
		int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(context, 10);
		int scrW = getResources().getDisplayMetrics().widthPixels;
		lp.height = (scrW - dp_10 * 2) * lines / 3;
		pager.setLayoutParams(lp);
		PlatformAdapter adapter = new PlatformAdapter(platformList, this);
		pager.setAdapter(adapter);
		int pageCount = 0;
		if (platformList != null) {
			int size = platformList.length;
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

		int dp_5 = cn.sharesdk.framework.utils.R.dipToPx(context, 5);
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

	/**
	 * 设置页面初始化和分享操作需要的数据
	 * <p>
	 * 此方法在{@link OnekeyShare}的UI初始化中被调用
	 *
	 * @param intent 携带初始化数据的Intent
	 */
	public void setData(HashMap<String, Object> data, boolean silent) {
		reqData = data;
		this.silent = silent;
	}

	/** 设置分享操作的回调页面 */
	public void setParent(OnekeyShare parent) {
		this.parent = parent;
	}

	public void onClick(View v) {
		Platform plat = (Platform) v.getTag();
		if (plat != null) {
			if (silent) {
				HashMap<Platform, HashMap<String, Object>> shareData
						= new HashMap<Platform, HashMap<String,Object>>();
				shareData.put(plat, reqData);
				parent.share(shareData);
				return;
			}

			String name = plat.getName();
			parent.setPlatform(name);
			// EditPage不支持微信平台、Google+、QQ分享、信息和邮件，总是执行直接分享
			if ("Wechat".equals(name) || "WechatMoments".equals(name)
					|| "ShortMessage".equals(name) || "Email".equals(name)
					|| "GooglePlus".equals(name) || "QQ".equals(name)) {
				HashMap<Platform, HashMap<String, Object>> shareData
						= new HashMap<Platform, HashMap<String,Object>>();
				shareData.put(plat, reqData);
				parent.share(shareData);
				return;
			}

			// 跳转SharePage分享
			EditPage page = new EditPage();
			page.setShareData(reqData);
			page.setParent(parent);
			page.show(parent.getContext(), null);
			parent.finish();
		}
	}

	/** 宫格列表数据适配器 */
	private static class PlatformAdapter extends PagerAdapter {
		private GridView[] girds; // 宫格列表
		private Platform[] platforms; // 平台数据
		private OnClickListener callback;
		private int lines; // 行数

		public PlatformAdapter(Platform[] platforms, OnClickListener callback) {
			this.platforms = platforms;
			this.callback = callback;
			girds = null;

			if (platforms != null) {
				int size = platforms.length;
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
				int listSize = platforms == null ? 0 : platforms.length;
				if (curSize + pageSize > listSize) {
					pageSize = listSize - curSize;
				}
				Platform[] gridPlatforms = new Platform[pageSize];
				for (int i = 0; i < pageSize; i++) {
					gridPlatforms[i] = platforms[curSize + i];
				}

				if (position == 0) {
					lines = gridPlatforms.length / 3;
					if (gridPlatforms.length % 3 > 0) {
						lines++;
					}
				}
				girds[position] = new GridView(container.getContext(), callback);
				girds[position].setData(lines, gridPlatforms);
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
		private Platform[] platforms; // 平台列表
		private OnClickListener callback;
		private int lines;
		private int iconWidth; // 格子宽度

		public GridView(Context context, OnClickListener callback) {
			super(context);
			this.callback = callback;
		}

		public void setData(int lines, Platform[] platforms) {
			this.lines = lines;
			this.platforms = platforms;
			init();
		}

		private void init() {
			int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(getContext(), 10);
			int scrW = getResources().getDisplayMetrics().widthPixels;
			iconWidth = (scrW - dp_10 * 2) / 3 - dp_10 * 4;

			setOrientation(VERTICAL);

			int size = platforms == null ? 0 : platforms.length;
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
					llItem.setTag(platforms[index]);
					llItem.setOnClickListener(callback);
					llItem.setLayoutParams(lp);
					llLine.addView(llItem);
				}
			}
		}

		private LinearLayout getView(int position, Context context) {
			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.VERTICAL);
			int dp_5 = cn.sharesdk.framework.utils.R.dipToPx(context, 5);
			ll.setPadding(dp_5, dp_5, dp_5, dp_5);

			ImageView iv = new ImageView(context);
			iv.setScaleType(ScaleType.CENTER_INSIDE);
			LinearLayout.LayoutParams lpIv = new LinearLayout.LayoutParams(
					iconWidth, iconWidth);
			lpIv.gravity = Gravity.CENTER_HORIZONTAL;
			iv.setLayoutParams(lpIv);
			iv.setImageBitmap(getIcon(platforms[position]));
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
			tv.setText(getName(platforms[position]));
			ll.addView(tv);

			return ll;
		}

		private Bitmap getIcon(Platform plat) {
			if (plat == null) {
				return null;
			}

			String name = plat.getName();
			if (name == null) {
				return null;
			}

			String resName = "logo_" + plat.getName();
			int resId = cn.sharesdk.framework.utils.R.getResId(R.drawable.class, resName);
			return BitmapFactory.decodeResource(getResources(), resId);
		}

		private String getName(Platform plat) {
			if (plat == null) {
				return "";
			}

			String name = plat.getName();
			if (name == null) {
				return "";
			}

			int resId = cn.sharesdk.framework.utils.R.getStringRes(getContext(), plat.getName());
			return getContext().getString(resId);
		}

	}

}
