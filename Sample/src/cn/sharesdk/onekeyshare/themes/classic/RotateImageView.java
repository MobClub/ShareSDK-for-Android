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
import android.widget.ImageView;

//#if def{lang} == cn
/** 在At好友页面中，下拉刷新列表头部的旋转箭头 */
//#elif def{lang} == en
/** the rotating arrow icon of the header view of pull-to-refresh list in the “at” page */
//#endif
public class RotateImageView extends ImageView {
	private float rotation;

	public RotateImageView(Context context) {
		super(context);
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
		invalidate();
	}

	protected void onDraw(Canvas canvas) {
		canvas.rotate(rotation, getWidth() / 2, getHeight() / 2);
		super.onDraw(canvas);
	}

}