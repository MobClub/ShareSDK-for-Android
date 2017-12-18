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

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView.ScaleType;
import cn.sharesdk.onekeyshare.OnekeySharePage;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;

import com.mob.tools.gui.ScaledImageView;

//#if def{lang} == cn
/** 图片浏览的视图类 */
//#elif def{lang} == en
/** the Viewer of picture */
//#endif
public class PicViewerPage extends OnekeySharePage implements OnGlobalLayoutListener {
	private Bitmap pic;
	//#if def{lang} == cn
	/** 图片浏览的缩放控件 */
	//#elif def{lang} == en
	/** the Viewer of picture which can zoom control */
	//#endif
	private ScaledImageView sivViewer;
	
	public PicViewerPage(OnekeyShareThemeImpl impl) {
		super(impl);
	}
	
	//#if def{lang} == cn
	/** 设置图片用于浏览 */
	//#elif def{lang} == en
	/** set the picture */
	//#endif
	public void setImageBitmap(Bitmap pic) {
		this.pic = pic;
	}
	
	public void onCreate() {
		activity.getWindow().setBackgroundDrawable(new ColorDrawable(0x4c000000));

		sivViewer = new ScaledImageView(activity);
		sivViewer.setScaleType(ScaleType.MATRIX);
		activity.setContentView(sivViewer);
		if (pic != null) {
			sivViewer.getViewTreeObserver().addOnGlobalLayoutListener(this);
		}
	}

	public void onGlobalLayout() {
		sivViewer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		sivViewer.post(new Runnable() {
			public void run() {
				sivViewer.setBitmap(pic);
			}
		});
	}
	
}
