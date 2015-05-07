package cn.sharesdk.demo.widget;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;

public class BodyContainer extends HorizontalScrollView {
	private static final int MIN_FLING_VEL = 500; // 侧栏切换最低速度
	private static final int MENU_COVER_ALPHA = 230; // 0.9f
	private float downX = Integer.MAX_VALUE;
	private SlidingMenu menu;
	private int maxVelocity;
	private VelocityTracker tracker;

	public BodyContainer(SlidingMenu menu) {
		super(menu.getContext());
		this.menu = menu;
		ViewConfiguration conf = ViewConfiguration.get(menu.getContext());
		maxVelocity = conf.getScaledMaximumFlingVelocity();
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				downX = ev.getX();
				if (menu.isMenuShown()) {
					if (downX > menu.getMenuWidth()
							&& ev.getY() > menu.getMenuConfig().titleHeight) {
						super.onInterceptTouchEvent(ev);
						return true;
					}
				}
			}
			break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL: {
				downX = Integer.MAX_VALUE;
			}
			break;
			case MotionEvent.ACTION_MOVE: {
				if (!menu.isMenuShown()) {
					if (downX > menu.getShowMenuWidth()) {
						super.onInterceptTouchEvent(ev);
						return false;
					}
				}
			}
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	public boolean onTouchEvent(MotionEvent ev) {
		if (tracker == null) {
			tracker = VelocityTracker.obtain();
		}
		tracker.addMovement(ev);

		switch (ev.getAction()) {
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL: {
				if (menu.isMenuShown() && downX < menu.getMenuWidth()) {
					return false;
				}

				downX = Integer.MAX_VALUE;
				tracker.computeCurrentVelocity(1000, maxVelocity);
				float velX = tracker.getXVelocity();
				tracker.recycle();
				tracker = null;
				int scrX = getScrollX();

				if (velX - MIN_FLING_VEL > 0) {
					menu.showMenu();
				} else if (velX + MIN_FLING_VEL < 0) {
					menu.hideMenu();
				} else if (scrX > menu.getMenuWidth() / 2) {
					menu.hideMenu();
				} else {
					menu.showMenu();
				}
				return true;
			}
		}

		if (menu.isMenuShown() && downX < menu.getMenuWidth()) {
			return false;
		}
		return super.onTouchEvent(ev);
	}

	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		// 变黑
		int alpha = MENU_COVER_ALPHA * l / menu.getMenuWidth();
		int c = Color.argb(alpha, 0, 0, 0);
		menu.getMenuCover().setBackgroundColor(c);
	}

}
