package cn.sharesdk.demo.widget;

import java.lang.reflect.Method;
import java.util.HashMap;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.ImageView.ScaleType;

/**
 * 侧栏
 *
 * @author 余勋杰
 */
public class SlidingMenu extends RelativeLayout {
	private MenuConfig config;
	private HashMap<SlidingMenuItem, View> itemToView;
	private OnClickListener ocListener;
	private OnTouchListener otListener;
	private MenuAdapter adapter;
	private int screenWidth;
	private int menuWidth;
	private int showMenuWidth;
	private boolean menuShown;
	private FrameLayout flMenu;
	private LinearLayout llMenu; // 侧栏
	private View vCover;
	private BodyContainer svBody;
	private LinearLayout llBody; // 主体
	private View curBody;

	public SlidingMenu(Context context) {
		super(context);
		init(context);
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		config = new MenuConfig();

		itemToView = new HashMap<SlidingMenuItem, View>();
		ocListener = new OnClickListener() {
			public void onClick(View v) {
				SlidingMenuItem item = (SlidingMenuItem) v.getTag();
				if (item == null) {
					return;
				}

				if (adapter == null) {
					return;
				}

				if (!adapter.onItemTrigger(item)) {
					postDelayed(new Runnable() {
						public void run() {
							hideMenu();
						}
					}, 500);
				}
			}
		};
		otListener = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						v.setBackgroundResource(config.itemDownBack);
					}
					break;
					case MotionEvent.ACTION_CANCEL:
					case MotionEvent.ACTION_UP: {
						v.setBackgroundResource(config.itemDownRelease);
					}
					break;
				}
				return false;
			}
		};

		screenWidth = context.getResources().getDisplayMetrics().widthPixels;
		menuWidth = (int) (screenWidth * config.menuWeight);
		showMenuWidth = (screenWidth - menuWidth) / 2;

		setBackgroundResource(config.menuBackground);
		initMenu(context);
		initBody(context);

		getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@SuppressWarnings("deprecation")
					public void onGlobalLayout() {
						getViewTreeObserver().removeGlobalOnLayoutListener(this);
						post(new Runnable() {
							public void run() {
								hideMenu();
							}
						});
					}
				});
	}

	private void initMenu(Context context) {
		// 外层容器
		flMenu = new FrameLayout(context) {
			public boolean onInterceptTouchEvent(MotionEvent ev) {
				if (!menuShown) {
					return true;
				}
				return super.onInterceptTouchEvent(ev);
			}
		};
		flMenu.setLayoutParams(new FrameLayout.LayoutParams(
				menuWidth, LayoutParams.MATCH_PARENT));
		addView(flMenu);

		LinearLayout llMenuCtn = new LinearLayout(context);
		llMenuCtn.setOrientation(LinearLayout.VERTICAL);
		llMenuCtn.setLayoutParams(new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		flMenu.addView(llMenuCtn);

		// 菜单滚动条
		ScrollView svMenu = new ScrollView(context);
		svMenu.setVerticalScrollBarEnabled(false);
		svMenu.setVerticalFadingEdgeEnabled(false);
		disableOverScrollMode(svMenu);
		LinearLayout.LayoutParams lpSv = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lpSv.weight = 1;
		svMenu.setLayoutParams(lpSv);
		llMenuCtn.addView(svMenu);

		// 菜单内层容器
		llMenu = new LinearLayout(context);
		llMenu.setOrientation(LinearLayout.VERTICAL);
		llMenu.setLayoutParams(new ScrollView.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		svMenu.addView(llMenu);

		// 上层渐变遮挡
		vCover = new View(context);
		vCover.setBackgroundColor(0x00000000);
		vCover.setLayoutParams(new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		flMenu.addView(vCover);
	}

	private void initBody(Context context) {
		// 滚动条
		svBody = new BodyContainer(this);
		svBody.setHorizontalScrollBarEnabled(false);
		svBody.setHorizontalFadingEdgeEnabled(false);
		disableOverScrollMode(svBody);
		svBody.setLayoutParams(new FrameLayout.LayoutParams(
				screenWidth, LayoutParams.MATCH_PARENT));
		addView(svBody);

		// 顶层容器
		LinearLayout rlBodyContainer = new LinearLayout(context);
		rlBodyContainer.setLayoutParams(new FrameLayout.LayoutParams(
				screenWidth + menuWidth, LayoutParams.MATCH_PARENT));
		svBody.addView(rlBodyContainer);

		FrameLayout flPlh = new FrameLayout(getContext());
		LinearLayout.LayoutParams lpPlh = new LinearLayout.LayoutParams(
				menuWidth, LayoutParams.MATCH_PARENT);
		flPlh.setLayoutParams(lpPlh);
		rlBodyContainer.addView(flPlh);

		// 阴影
		ImageView ivShadow = new ImageView(context);
		ivShadow.setImageResource(config.rightShadow);
		ivShadow.setScaleType(ScaleType.FIT_XY);
		FrameLayout.LayoutParams lpShadow = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lpShadow.gravity = Gravity.RIGHT;
		ivShadow.setLayoutParams(lpShadow);
		flPlh.addView(ivShadow);

		// 实体容器
		llBody = new LinearLayout(context) {
			public boolean onTouchEvent(MotionEvent event) {
				return true;
			}
		};
		llBody.setBackgroundResource(config.bodyBackground);
		LinearLayout.LayoutParams lpBody = new LinearLayout.LayoutParams(
				screenWidth, LayoutParams.MATCH_PARENT);
		llBody.setLayoutParams(lpBody);
		rlBodyContainer.addView(llBody);
	}

	public void setAdapter(MenuAdapter adapter) {
		this.adapter = adapter;
		refresh();
	}

	public void refresh() {
		if (adapter != null) {
			reInit(getContext());
			invalidateMenu();
		}
	}

	private void reInit(Context context) {
		screenWidth = context.getResources().getDisplayMetrics().widthPixels;
		menuWidth = (int) (screenWidth * config.menuWeight);
		showMenuWidth = (screenWidth - menuWidth) / 2;
		setBackgroundResource(config.menuBackground);

		reInitMenu(context);
		reInitBody(context);
	}

	private void reInitMenu(Context context) {
		ViewGroup.LayoutParams lpMenu = flMenu.getLayoutParams();
		lpMenu.width = menuWidth;
		flMenu.setLayoutParams(lpMenu);
		llMenu.setPadding(config.paddingLeft, config.paddingTop,
				config.paddingRight, config.paddingBottom);

		if (adapter != null) {
			View vTitle = adapter.getMenuTitle();
			if (vTitle != null) {
				ViewGroup.LayoutParams lp = vTitle.getLayoutParams();
				int height = LayoutParams.WRAP_CONTENT;
				if (lp != null) {
					height = lp.height;
				}
				vTitle.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, height));
				LinearLayout llMenuCtn = (LinearLayout) flMenu.getChildAt(0);
				llMenuCtn.addView(vTitle);
			}
		}
	}

	private void reInitBody(Context context) {
		ViewGroup.LayoutParams lpBody = svBody.getLayoutParams();
		lpBody.width = screenWidth;
		svBody.setLayoutParams(lpBody);

		LinearLayout rlBodyContainer = (LinearLayout) svBody.getChildAt(0);
		ViewGroup.LayoutParams lp = rlBodyContainer.getLayoutParams();
		lp.width = screenWidth + menuWidth;
		rlBodyContainer.setLayoutParams(lp);

		FrameLayout flPlh = (FrameLayout) rlBodyContainer.getChildAt(0);
		lp = flPlh.getLayoutParams();
		lp.width = menuWidth;
		flPlh.setLayoutParams(lp);

		lp = llBody.getLayoutParams();
		lp.width = screenWidth;
		llBody.setLayoutParams(lp);
		llBody.setBackgroundResource(config.bodyBackground);

		ImageView ivShadow = (ImageView) flPlh.getChildAt(0);
		ivShadow.setImageResource(config.rightShadow);

		if (!menuShown) {
			getViewTreeObserver().addOnGlobalLayoutListener(
					new OnGlobalLayoutListener() {
						public void onGlobalLayout() {
							getViewTreeObserver().removeGlobalOnLayoutListener(this);
							post(new Runnable() {
								public void run() {
									svBody.scrollTo(menuWidth, 0);
								}
							});
						}
					});
		}
	}

	public void setBodyView(View body) {
		curBody = body;
		llBody.removeAllViews();
		if (curBody != null) {
			curBody.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			llBody.addView(curBody);
		}
	}

	public View getBodyView() {
		return curBody;
	}

	private void invalidateMenu() {
		Context context = getContext();
		llMenu.removeAllViews();
		int count = adapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			// 造标题
			View title = adapter.getGroupView(i, llMenu);
			llMenu.addView(title);

			// 造菜单项
			int groupCount = adapter.getGroup(i).getCount();
			for (int j = 0; j < groupCount; j++) {
				SlidingMenuItem data = adapter.getItem(i, j);
				View item = adapter.getItemView(data, llMenu);
				llMenu.addView(item);
				llMenu.addView(getSepView(context));

				itemToView.put(data, item);
				item.setTag(data);
				item.setOnClickListener(ocListener);
				item.setOnTouchListener(otListener);
			}

			// 去掉最后的分割线
			int viewCount = llMenu.getChildCount();
			if (viewCount > 0) {
				llMenu.removeViewAt(viewCount - 1);
			}
		}
	}

	private View getSepView(Context context) {
		View vSep = new View(context);
		vSep.setBackgroundResource(config.menuSep);
		vSep.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 2));
		return vSep;
	}

	/** 切换侧栏状态 */
	public void switchMenu() {
		if (menuShown) {
			hideMenu();
		}
		else {
			showMenu();
		}
	}

	public void showMenu() {
		menuShown = true;
		svBody.smoothScrollTo(0, 0);
		if(adapter != null) {
			adapter.onMenuSwitch(menuShown);
		}
	}

	public void hideMenu() {
		menuShown = false;
		svBody.smoothScrollTo(menuWidth, 0);
		if(adapter != null) {
			adapter.onMenuSwitch(menuShown);
		}
	}

	public boolean isMenuShown() {
		return menuShown;
	}

	int getMenuWidth() {
		return menuWidth;
	}

	int getShowMenuWidth() {
		return showMenuWidth;
	}

	MenuConfig getMenuConfig() {
		return config;
	}

	View getMenuCover() {
		return vCover;
	}

	public void setMenuItemBackground(int down, int release) {
		config.itemDownBack = down;
		config.itemDownRelease = release;
	}

	public void setMenuWeight(float weight) {
		config.menuWeight = weight;
	}

	public void setMenuBackground(int resId) {
		config.menuBackground = resId;
	}

	public void setMenuPadding(int left, int top, int right, int bottom) {
		config.paddingLeft = left;
		config.paddingTop = top;
		config.paddingRight = right;
		config.paddingBottom = bottom;
	}

	public void setTtleHeight(int height) {
		config.titleHeight = height;
	}

	public void setShadowRes(int resId) {
		config.rightShadow = resId;
	}

	public void setBodyBackground(int resId) {
		config.bodyBackground = resId;
	}

	public void setMenuDivider(int resId) {
		config.menuSep = resId;
	}

	/* 禁止滑动到头以后的发光效果 */
	private void disableOverScrollMode(View view) {
		if (Build.VERSION.SDK_INT < 9) {
			return;
		}

		try {
			Method m = View.class.getMethod("setOverScrollMode", int.class);
			m.setAccessible(true);
			m.invoke(view, 2); // OVER_SCROLL_NEVER = 2
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	View itemToView(SlidingMenuItem item) {
		return itemToView.get(item);
	}

	public void triggerItem(int groupId, int itemId) {
		if (adapter == null) {
			return;
		}

		SlidingMenuItem item = adapter.getMenuItem(groupId, itemId);
		if (item != null) {
			adapter.onItemTrigger(item);
		}
	}

	public void triggerItem(SlidingMenuItem item) {
		if (adapter == null) {
			return;
		}

		if (item != null) {
			adapter.onItemTrigger(item);
		}
	}

}
