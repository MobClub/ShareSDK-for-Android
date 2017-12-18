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

package cn.sharesdk.onekeyshare;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;

import com.mob.tools.FakeActivity;

//#if def{lang} == cn
/** 快捷分享的基类 */
//#elif def{lang} == en
/** the basic class of onekeyshare */
//#endif
public class OnekeySharePage extends FakeActivity {
	private OnekeyShareThemeImpl impl;
	
	public OnekeySharePage(OnekeyShareThemeImpl impl) {
		this.impl = impl;
	}
	
	//#if def{lang} == cn
	/** 分享界面是否弹窗模式 */
	//#elif def{lang} == en
	/** show dialog to share*/
	//#endif

	protected final boolean isDialogMode() {
		return impl.dialogMode;
	}
	
	protected final HashMap<String, Object> getShareParamsMap() {
		return impl.shareParamsMap;
	}
	
	//#if def{lang} == cn
	/** 静默分享开关（没有界面，直接分享 ）*/
	//#elif def{lang} == en
	/** using api to share there is not UI*/
	//#endif
	protected final boolean isSilent() {
		return impl.silent;
	}
	
	protected final ArrayList<CustomerLogo> getCustomerLogos() {
		return impl.customerLogos;
	}
	
	protected final HashMap<String, String> getHiddenPlatforms() {
		return impl.hiddenPlatforms;
	}
	
	protected final PlatformActionListener getCallback() {
		return impl.callback;
	}
	
	protected final ShareContentCustomizeCallback getCustomizeCallback() {
		return impl.customizeCallback;
	}
	
	protected final boolean isDisableSSO() {
		return impl.disableSSO;
	}
	
	protected final void shareSilently(Platform platform) {
		impl.shareSilently(platform);
	}
	
	protected final ShareParams formateShareData(Platform platform) {
		if (impl.formateShareData(platform)) {
			return impl.shareDataToShareParams(platform);
		}
		return null;
	}
	
	protected final boolean isUseClientToShare(Platform platform) {
		return impl.isUseClientToShare(platform);
	}
	
}
