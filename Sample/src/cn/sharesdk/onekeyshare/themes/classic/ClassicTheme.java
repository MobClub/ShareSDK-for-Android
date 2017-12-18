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
import android.content.res.Configuration;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.themes.classic.land.EditPageLand;
import cn.sharesdk.onekeyshare.themes.classic.land.PlatformPageLand;
import cn.sharesdk.onekeyshare.themes.classic.port.EditPagePort;
import cn.sharesdk.onekeyshare.themes.classic.port.PlatformPagePort;

//#if def{lang} == cn
/** 九宫格经典主题样式的实现类*/
//#elif def{lang} == en
/** the implement of classic theme */
//#endif
public class ClassicTheme extends OnekeyShareThemeImpl {

	//#if def{lang} == cn
	/** 展示平台列表*/
	//#elif def{lang} == en
	/** show the platform list */
	//#endif

	// 两次弹出九宫格间隔不能少于1000毫秒
	private static final int MIN_CLICK_DELAY_TIME = 1000;
	private static long lastTime;

	protected void showPlatformPage(Context context) {
		PlatformPage page;
		int orientation = context.getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			page = new PlatformPagePort(this);
		} else {
			page = new PlatformPageLand(this);
		}
		long currentTime = System.currentTimeMillis();
		if ((currentTime - lastTime) >= MIN_CLICK_DELAY_TIME) {
			page.show(context, null);
		}
		lastTime = currentTime;

	}

	//#if def{lang} == cn
	/** 展示编辑界面*/
	//#elif def{lang} == en
	/** show the edit page */
	//#endif
	protected void showEditPage(Context context, Platform platform, ShareParams sp) {
		EditPage page;
		int orientation = context.getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			page = new EditPagePort(this);
		} else {
			page = new EditPageLand(this);
		}
		page.setPlatform(platform);
		page.setShareParams(sp);
		page.show(context, null);
	}

}
