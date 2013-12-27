/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import static cn.sharesdk.framework.utils.R.getBitmapRes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import m.framework.ui.widget.viewpager.ViewPagerAdapter;
import m.framework.ui.widget.viewpager.ViewPagerClassic;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler.Callback;
import android.os.Message;
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
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;

/** 平台宫格列表显示工具。 */
public class PlatformGridView extends LinearLayout implements
		OnClickListener, Callback {
	private static final int MSG_PLATFORM_LIST_GOT = 1;
	// 每行显示的格数
	private int LINE_PER_PAGE;
	// 每页显示的行数
	private int COLUMN_PER_LINE;
	// 每页显示的格数
	private int PAGE_SIZE;
	// 宫格容器
	private ViewPagerClassic pager;
	// 页面指示器
	private ImageView[] points;
	private Bitmap grayPoint;
	private Bitmap whitePoint;
	// 是否不跳转EditPage而直接分享
	private boolean silent;
	// 平台数据
	private Platform[] platformList;
	// 从外部传进来的分享数据（含初始化数据）
	private HashMap<String, Object> reqData;
	private OnekeyShare parent;
	private ArrayList<CustomerLogo> customers;

	public PlatformGridView(Context context) {
		super(context);
		init(context);
	}

	public PlatformGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(final Context context) {
		calPageSize();
		setOrientation(VERTICAL);

		pager = new ViewPagerClassic(context);
		disableOverScrollMode(pager);
		pager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		addView(pager);

		// 为了更好的ui效果，开启子线程获取平台列表
		new Thread(){
			public void run() {
				platformList = ShareSDK.getPlatformList(context);
				UIHandler.sendEmptyMessage(MSG_PLATFORM_LIST_GOT, PlatformGridView.this);
			}
		}.start();
	}

	private void calPageSize() {
		float scrW = cn.sharesdk.framework.utils.R.getScreenWidth(getContext());
		float scrH = cn.sharesdk.framework.utils.R.getScreenHeight(getContext());
		float whR = scrW / scrH;
		if (whR < 0.6) {
			COLUMN_PER_LINE = 3;
			LINE_PER_PAGE = 3;
		} else if (whR < 0.75) {
			COLUMN_PER_LINE = 3;
			LINE_PER_PAGE = 2;
		} else {
			LINE_PER_PAGE = 1;
			if (whR >= 1.75) {
				COLUMN_PER_LINE = 6;
			} else if (whR >= 1.5) {
				COLUMN_PER_LINE = 5;
			} else if (whR >= 1.3) {
				COLUMN_PER_LINE = 4;
			} else {
				COLUMN_PER_LINE = 3;
			}
		}
		PAGE_SIZE = COLUMN_PER_LINE * LINE_PER_PAGE;
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
		PlatformAdapter adapter = new PlatformAdapter(this);
		pager.setAdapter(adapter);
		int pageCount = 0;
		if (platformList != null) {
			int cusSize = customers == null ? 0 : customers.size();
			int platSize = platformList == null ? 0 : platformList.length;
			int size = platSize + cusSize;
			pageCount = size / PAGE_SIZE;
			if (size % PAGE_SIZE > 0) {
				pageCount++;
			}
		}
		points = new ImageView[pageCount];
		if (points.length <= 0) {
			return;
		}

		Context context = getContext();
		LinearLayout llPoints = new LinearLayout(context);
		// 如果页面总是超过1，则设置页面指示器
		llPoints.setVisibility(pageCount > 1 ? View.VISIBLE: View.GONE);
		LayoutParams lpLl = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.CENTER_HORIZONTAL;
		llPoints.setLayoutParams(lpLl);
		addView(llPoints);

		int dp_5 = cn.sharesdk.framework.utils.R.dipToPx(context, 5);
		int resId = getBitmapRes(getContext(), "gray_point");
		if (resId > 0) {
			grayPoint = BitmapFactory.decodeResource(getResources(), resId);
		}
		resId = getBitmapRes(getContext(), "white_point");
		if (resId > 0) {
			whitePoint = BitmapFactory.decodeResource(getResources(), resId);
		}
		for (int i = 0; i < pageCount; i++) {
			points[i] = new ImageView(context);
			points[i].setScaleType(ScaleType.CENTER_INSIDE);
			points[i].setImageBitmap(grayPoint);
			LayoutParams lpIv = new LayoutParams(dp_5, dp_5);
			lpIv.setMargins(dp_5, dp_5, dp_5, 0);
			points[i].setLayoutParams(lpIv);
			llPoints.addView(points[i]);
		}
		int curPage = pager.getCurrentScreen();
		points[curPage].setImageBitmap(whitePoint);
	}

	/** 屏幕旋转后，此方法会被调用，以刷新宫格列表的布局 */
	public void onConfigurationChanged() {
		int curFirst = pager.getCurrentScreen() * PAGE_SIZE;
		calPageSize();
		int newPage = curFirst / PAGE_SIZE;

		removeViewAt(1);
		afterPlatformListGot();

		pager.setCurrentScreen(newPage);
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

	/** 设置自己图标和点击事件 */
	public void setCustomerLogos(ArrayList<CustomerLogo> customers) {
		this.customers = customers;
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
			reqData.put("platform", name);
			// EditPage不支持微信平台、Google+、QQ分享、Pinterest、信息和邮件，总是执行直接分享
			if (ShareCore.isUseClientToShare(getContext(), name)) {
				HashMap<Platform, HashMap<String, Object>> shareData
						= new HashMap<Platform, HashMap<String,Object>>();
				shareData.put(plat, reqData);
				parent.share(shareData);
				return;
			}

			// 跳转EditPage分享
			EditPage page = new EditPage();
			page.setShareData(reqData);
			page.setParent(parent);
			if ("true".equals(String.valueOf(reqData.get("dialogMode")))) {
				page.setDialogMode();
			}
			page.show(parent.getContext(), null);
			parent.finish();
		}
	}

	// 禁用ViewPage OverScroll的“发光”效果
	private void disableOverScrollMode(View view) {
		if (Build.VERSION.SDK_INT < 9) {
			return;
		}
		try {
			Method m = View.class.getMethod("setOverScrollMode",
					new Class[] { Integer.TYPE });
			m.setAccessible(true);
			m.invoke(view, new Object[] { Integer.valueOf(2) });
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/** 宫格列表数据适配器 */
	private static class PlatformAdapter extends ViewPagerAdapter {
		// 宫格列表元素
		private GridView[] girds;
		private List<Object> logos;
		private OnClickListener callback;
		// 行数
		private int lines;
		private PlatformGridView platformGridView;

		public PlatformAdapter(PlatformGridView platformGridView) {
			this.platformGridView = platformGridView;
			logos = new ArrayList<Object>();
			Platform[] platforms = platformGridView.platformList;
			if (platforms != null) {
				logos.addAll(Arrays.asList(platforms));
			}
			ArrayList<CustomerLogo> customers = platformGridView.customers;
			if (customers != null) {
				logos.addAll(customers);
			}
			this.callback = platformGridView;
			girds = null;

			if (logos != null) {
				int size = logos.size();
				int PAGE_SIZE = platformGridView.PAGE_SIZE;
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

		public View getView(int position, ViewGroup parent) {
			if (girds[position] == null) {
				int pageSize = platformGridView.PAGE_SIZE;
				int curSize = pageSize * position;
				int listSize = logos == null ? 0 : logos.size();
				if (curSize + pageSize > listSize) {
					pageSize = listSize - curSize;
				}
				Object[] gridBean = new Object[pageSize];
				for (int i = 0; i < pageSize; i++) {
					gridBean[i] = logos.get(curSize + i);
				}

				if (position == 0) {
					int COLUMN_PER_LINE = platformGridView.COLUMN_PER_LINE;
					lines = gridBean.length / COLUMN_PER_LINE;
					if (gridBean.length % COLUMN_PER_LINE > 0) {
						lines++;
					}
				}
				girds[position] = new GridView(this);
				girds[position].setData(lines, gridBean);
			}

			return girds[position];
		}

		/** 屏幕滑动后，此方法会被调用 */
		public void onScreenChange(int currentScreen, int lastScreen) {
			ImageView[] points = platformGridView.points;
			for (int i = 0; i < points.length; i++) {
				points[i].setImageBitmap(platformGridView.grayPoint);
			}

			points[currentScreen].setImageBitmap(platformGridView.whitePoint);
		}

	}

	/** 简易的宫格列表控件 */
	private static class GridView extends LinearLayout {
		private Object[] beans;
		private OnClickListener callback;
		private int lines;
		private PlatformAdapter platformAdapter;

		public GridView(PlatformAdapter platformAdapter) {
			super(platformAdapter.platformGridView.getContext());
			this.platformAdapter = platformAdapter;
			this.callback = platformAdapter.callback;
		}

		public void setData(int lines, Object[] beans) {
			this.lines = lines;
			this.beans = beans;
			init();
		}

		private void init() {
			int dp_5 = cn.sharesdk.framework.utils.R.dipToPx(getContext(), 5);
			setPadding(0, dp_5, 0, dp_5);
			setOrientation(VERTICAL);

			int size = beans == null ? 0 : beans.length;
			int COLUMN_PER_LINE = platformAdapter.platformGridView.COLUMN_PER_LINE;
			int lineSize = size / COLUMN_PER_LINE;
			if (size % COLUMN_PER_LINE > 0) {
				lineSize++;
			}
			LayoutParams lp = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			lp.weight = 1;
			for (int i = 0; i < lines; i++) {
				LinearLayout llLine = new LinearLayout(getContext());
				llLine.setLayoutParams(lp);
				llLine.setPadding(dp_5, 0, dp_5, 0);
				addView(llLine);

				if (i >= lineSize) {
					continue;
				}

				for (int j = 0; j < COLUMN_PER_LINE; j++) {
					final int index = i * COLUMN_PER_LINE + j;
					if (index >= size) {
						LinearLayout llItem = new LinearLayout(getContext());
						llItem.setLayoutParams(lp);
						llLine.addView(llItem);
						continue;
					}

					final LinearLayout llItem = getView(index, callback, getContext());
					llItem.setTag(beans[index]);
					llItem.setLayoutParams(lp);
					llLine.addView(llItem);
				}
			}
		}

		private LinearLayout getView(int position, OnClickListener ocL, Context context) {
			Bitmap logo;
			String label;
			OnClickListener listener;
			if (beans[position] instanceof Platform) {
				logo = getIcon((Platform) beans[position]);
				label = getName((Platform) beans[position]);
				listener = ocL;
			} else {
				logo = ((CustomerLogo) beans[position]).logo;
				label = ((CustomerLogo) beans[position]).label;
				listener = ((CustomerLogo) beans[position]).listener;
			}

			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.VERTICAL);

			ImageView iv = new ImageView(context);
			int dp_5 = cn.sharesdk.framework.utils.R.dipToPx(context, 5);
			iv.setPadding(dp_5, dp_5, dp_5, dp_5);
			iv.setScaleType(ScaleType.CENTER_INSIDE);
			LinearLayout.LayoutParams lpIv = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lpIv.setMargins(dp_5, dp_5, dp_5, dp_5);
			lpIv.gravity = Gravity.CENTER_HORIZONTAL;
			iv.setLayoutParams(lpIv);
			iv.setImageBitmap(logo);
			ll.addView(iv);

			TextView tv = new TextView(context);
			tv.setTextColor(0xffffffff);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			tv.setSingleLine();
			tv.setIncludeFontPadding(false);
			LinearLayout.LayoutParams lpTv = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lpTv.gravity = Gravity.CENTER_HORIZONTAL;
			lpTv.weight = 1;
			lpTv.setMargins(dp_5, 0, dp_5, dp_5);
			tv.setLayoutParams(lpTv);
			tv.setText(label);
			ll.addView(tv);
			ll.setOnClickListener(listener);

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
			int resId = getBitmapRes(getContext(), resName);
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
