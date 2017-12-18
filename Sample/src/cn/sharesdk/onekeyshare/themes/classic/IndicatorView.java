//#if def{lang} == cn
/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */
//#elif def{lang} == en
/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. 
 * If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 * 
 * Copyright (c) 2013 mob.com. All rights reserved.
 */
//#endif

package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

//#if def{lang} == cn
/** 九宫格滑动时，下面显示的圆圈 */
//#elif def{lang} == en
/** the point view below the gridview */
//#endif
public class IndicatorView extends View {
	private static final int DESIGN_INDICATOR_RADIUS = 6;
	private static final int DESIGN_INDICATOR_DISTANCE = 14;
	private static final int DESIGN_BOTTOM_HEIGHT = 52;
	//#if def{lang} == cn
	/** 九格宫有多少页数 */
	//#elif def{lang} == en
	/** the count of pages in gridview */
	//#endif
	private int count;
	//#if def{lang} == cn
	/** 当前显示的是九格宫中的第几页 */
	//#elif def{lang} == en
	/** the page-number showing currently in gridview */
	//#endif
	private int current;

	public IndicatorView(Context context) {
		super(context);
	}
	
	public void setScreenCount(int count) {
		this.count = count;
	}
	
	public void onScreenChange(int currentScreen, int lastScreen) {
		if (currentScreen != current) {
			current = currentScreen;
			postInvalidate();
		}
	}
	
	protected void onDraw(Canvas canvas) {
		if (count <= 1) {
			this.setVisibility(View.GONE);
			return;
		}
		float height = getHeight();
		float radius = height * DESIGN_INDICATOR_RADIUS / DESIGN_BOTTOM_HEIGHT;
		float distance = height * DESIGN_INDICATOR_DISTANCE / DESIGN_BOTTOM_HEIGHT;
		float windowWidth = radius * 2 * count + distance * (count - 1);
		float left = (getWidth() - windowWidth) / 2;
		float cy = height / 2;
		
		canvas.drawColor(0xffffffff);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		for (int i = 0; i < count; i++) {
			if (i == current) {
				paint.setColor(0xff5d71a0);
			} else {
				paint.setColor(0xffafb1b7);
			}
			float cx = left + (radius * 2 + distance) * i;
			canvas.drawCircle(cx, cy, radius, paint);
		}
	}
	
}
