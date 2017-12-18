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

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.mob.MobApplication;
import com.mob.MobSDK;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

//#if def{lang} == cn
/**
* 快捷分享的入口
* <p>
* 通过不同的setter设置参数，然后调用{@link #show(Context)}方法启动快捷分享
*/
//#elif def{lang} == en
/**
* entrance of onekeyshare
* <p>
* by setting different setter parameter, then call the Show method to start the shortcut to share
*/
//#endif
public class OnekeyShare {
	private HashMap<String, Object> params;
	
	public OnekeyShare() {
		params = new HashMap<String, Object>();
		params.put("customers", new ArrayList<CustomerLogo>());
		params.put("hiddenPlatforms", new HashMap<String, String>());
	}
	
	//#if def{lang} == cn
	/** address是接收人地址，仅在信息和邮件使用，否则可以不提供 */
	//#elif def{lang} == en
	/** short message address or email address */
	//#endif
	public void setAddress(String address) {
		params.put("address", address);
	}

	//#if def{lang} == cn
	/** 
	 * title标题，在印象笔记、邮箱、信息、微信（包括好友、朋友圈和收藏）、
	 * 易信（包括好友、朋友圈）、人人网和QQ空间使用，否则可以不提供
	 */
	//#elif def{lang} == en
	/** title of share content */
	//#endif
	public void setTitle(String title) {
		params.put("title", title);
	}

	//#if def{lang} == cn
	/** titleUrl是标题的网络链接，仅在人人网和QQ空间使用，否则可以不提供 */
	//#elif def{lang} == en
	/** the url of title */
	//#endif
	public void setTitleUrl(String titleUrl) {
		params.put("titleUrl", titleUrl);
	}

	//#if def{lang} == cn
	/** text是分享文本，所有平台都需要这个字段 */
	//#elif def{lang} == en
	/** share content */
	//#endif
	public void setText(String text) {
		params.put("text", text);
	}

	//#if def{lang} == cn
	/** 获取text字段的值 */
	//#elif def{lang} == en
	/** returns share content */
	//#endif
	public String getText() {
		return params.containsKey("text") ? String.valueOf(params.get("text")) : null;
	}
	
	//#if def{lang} == cn
	/** imagePath是本地的图片路径，除Linked-In外的所有平台都支持这个字段 */
	//#elif def{lang} == en
	/** local path of the image to share */
	//#endif
	public void setImagePath(String imagePath) {
		if(!TextUtils.isEmpty(imagePath)) {
			params.put("imagePath", imagePath);
		}
	}

	//#if def{lang} == cn
	/** imageUrl是图片的网络路径，新浪微博、人人网、QQ空间和Linked-In支持此字段 */
	//#elif def{lang} == en
	/** url of the image to share */
	//#endif
	public void setImageUrl(String imageUrl) {
		if (!TextUtils.isEmpty(imageUrl)) {
			params.put("imageUrl", imageUrl);
		}
	}

	//#if def{lang} == cn
	/** imageData是bitmap图片，微信、易信支持此字段 */
	//#elif def{lang} == en
	/** bitmap of the image to share */
	//#endif
	public void setImageData(Bitmap iamgeData) {
		if(iamgeData != null) {
			params.put("imageData", iamgeData);
		}
	}

	//#if def{lang} == cn
	/** url在微信（包括好友、朋友圈收藏）和易信（包括好友和朋友圈）中使用，否则可以不提供 */
	//#elif def{lang} == en
	/** webpage link to share in wechat and yixin etc.*/
	//#endif
	public void setUrl(String url) {
		params.put("url", url);
	}

	//#if def{lang} == cn
	/** filePath是待分享应用程序的本地路劲，仅在微信（易信）好友和Dropbox中使用，否则可以不提供 */
	//#elif def{lang} == en
	/** local path of the file to share in wechat */
	//#endif
	public void setFilePath(String filePath) {
		params.put("filePath", filePath);
	}

	//#if def{lang} == cn
	/** comment是我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供 */
	//#elif def{lang} == en
	/** comment field of platform renren */
	//#endif
	public void setComment(String comment) {
		params.put("comment", comment);
	}

	//#if def{lang} == cn
	/** site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供 */
	//#elif def{lang} == en
	/** app name or site name of your program */
	//#endif
	public void setSite(String site) {
		params.put("site", site);
	}

	//#if def{lang} == cn
	/** siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供 */
	//#elif def{lang} == en
	/** the url of the website or appname */
	//#endif
	public void setSiteUrl(String siteUrl) {
		params.put("siteUrl", siteUrl);
	}

	//#if def{lang} == cn
	/** foursquare分享时的地方名 */
	//#elif def{lang} == en
	/** location name */
	//#endif
	public void setVenueName(String venueName) {
		params.put("venueName", venueName);
	}

	//#if def{lang} == cn
	/** foursquare分享时的地方描述 */
	//#elif def{lang} == en
	/** description of your sharing location */
	//#endif
	public void setVenueDescription(String venueDescription) {
		params.put("venueDescription", venueDescription);
	}

	//#if def{lang} == cn
	/** 分享地纬度，新浪微博、腾讯微博和foursquare支持此字段 */
	//#elif def{lang} == en
	/** latitude */
	//#endif
	public void setLatitude(float latitude) {
		params.put("latitude", latitude);
	}

	//#if def{lang} == cn
	/** 分享地经度，新浪微博、腾讯微博和foursquare支持此字段 */
	//#elif def{lang} == en
	/** longitude */
	//#endif
	public void setLongitude(float longitude) {
		params.put("longitude", longitude);
	}

	//#if def{lang} == cn
	/** 是否直接分享 */
	//#elif def{lang} == en
	/** determine whether to share directly */
	//#endif
	public void setSilent(boolean silent) {
		params.put("silent", silent);
	}

	public void setDialogMode(boolean isDialog) {
		params.put("dialogMode", isDialog);
	}

	//#if def{lang} == cn
	/** 设置编辑页的初始化选中平台 */
	//#elif def{lang} == en
	/** Setting the selected platform of EditPage when initializing */
	//#endif
	public void setPlatform(String platform) {
		params.put("platform", platform);
	}
	
	//#if def{lang} == cn
	/** 设置KakaoTalk的应用下载地址 */
	//#elif def{lang} == en
	/** Setting the selected platform of KakaoTalk ，go to the url when click the share msg */
	//#endif
	public void setInstallUrl(String installurl) {
		params.put("installurl", installurl);
	}
	
	//#if def{lang} == cn
	/** 设置KakaoTalk的应用打开地址 */
	//#elif def{lang} == en
	/** Setting the selected platform of KakaoTalk  ，open the app-url when click the share msg */
	//#endif
	public void setExecuteUrl(String executeurl) {
		params.put("executeurl", executeurl);
	}
	
	//#if def{lang} == cn
	/** 设置微信分享的音乐的地址 */
	//#elif def{lang} == en
	/** Setting the musicUrl when share msg using Wechat*/
	//#endif
	public void setMusicUrl(String musicUrl) {
		params.put("musicUrl", musicUrl);
	}		
	
	//#if def{lang} == cn
	/** 设置自定义的外部回调 */
	//#elif def{lang} == en
	/** setting custom external callback */
	//#endif
	public void setCallback(PlatformActionListener callback) {
		params.put("callback", callback);
	}
	
	//#if def{lang} == cn
	/** 返回操作回调 */
	//#elif def{lang} == en
	/** returns sharing callback */
	//#endif
	public PlatformActionListener getCallback() {
		return ResHelper.forceCast(params.get("callback"));
	}
	
	//#if def{lang} == cn
	/** 设置用于分享过程中，根据不同平台自定义分享内容的回调 */
	//#elif def{lang} == en
	/** setting the share content customizing callback for sharing process */
	//#endif
	public void setShareContentCustomizeCallback(ShareContentCustomizeCallback callback) {
		params.put("customizeCallback", callback);
	}
	
	//#if def{lang} == cn
	/** 自定义不同平台分享不同内容的回调 */
	//#elif def{lang} == en
	/** returns share content customizing callback */
	//#endif
	public ShareContentCustomizeCallback getShareContentCustomizeCallback() {
		return ResHelper.forceCast(params.get("customizeCallback"));
	}
	
	//#if def{lang} == cn
	/** 设置自己图标和点击事件，可以重复调用添加多次 */
	//#elif def{lang} == en
	/** add a custom icon and its click event listener */
	//#endif
	public void setCustomerLogo(Bitmap logo, String label, OnClickListener ocl) {
		CustomerLogo cl = new CustomerLogo();
		cl.logo = logo;
		cl.label = label;
		cl.listener = ocl;
		ArrayList<CustomerLogo> customers = ResHelper.forceCast(params.get("customers"));
		customers.add(cl);
	}
	
	//#if def{lang} == cn
	/** 设置一个总开关，用于在分享前若需要授权，则禁用sso功能 */
	//#elif def{lang} == en
	/** disable SSO authorizing before sharing */
	//#endif
	public void disableSSOWhenAuthorize() {
		params.put("disableSSO", true);
	}
	
	//#if def{lang} == cn
	/** 设置视频网络地址 */
	//#elif def{lang} == en
	/** set the url of video */
	//#endif
	public void setVideoUrl(String url) {
		params.put("url", url);
		params.put("shareType", Platform.SHARE_VIDEO);
	}
	
	//#if def{lang} == cn
	/** 添加一个隐藏的platform */
	//#elif def{lang} == en
	/** add a hidden platform */
	//#endif
	public void addHiddenPlatform(String platform) {
		HashMap<String, String> hiddenPlatforms = ResHelper.forceCast(params.get("hiddenPlatforms"));
		hiddenPlatforms.put(platform, platform);
	}
	
	//#if def{lang} == cn
	/** 设置一个将被截图分享的View , surfaceView是截不了图片的*/
	//#elif def{lang} == en
	/** add a view to be captured to share */
	//#endif
	public void setViewToShare(View viewToShare) {
		try {
			Bitmap bm = BitmapHelper.captureView(viewToShare, viewToShare.getWidth(), viewToShare.getHeight());
			params.put("viewToShare", bm);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	//#if def{lang} == cn
	/** 腾讯微博分享多张图片 */
	//#elif def{lang} == en
	/** share multi local pic to tencent weibo */
	//#endif
	public void setImageArray(String[] imageArray) {
		params.put("imageArray", imageArray);
	}
	
	//#if def{lang} == cn
	/** 设置在执行分享到QQ或QZone的同时，分享相同的内容腾讯微博 */
	//#elif def{lang} == en
	/** Share another copy to TencentWeibo when performing QQ or QZone Sharing */
	//#endif
	public void setShareToTencentWeiboWhenPerformingQQOrQZoneSharing() {
		params.put("isShareTencentWeibo", true);
	}
	
	//#if def{lang} == cn
	/** 设置分享界面的样式，目前只有一种，不需要设置 */
	//#elif def{lang} == en
	/** set the theme of share UI */
	//#endif
	public void setTheme(OnekeyShareTheme theme) {
		params.put("theme", theme.getValue());
	}
	
	@SuppressWarnings("unchecked")
	public void show(Context context) {
		HashMap<String, Object> shareParamsMap = new HashMap<String, Object>();
		shareParamsMap.putAll(params);

		if (!(context instanceof MobApplication)) {
			MobSDK.init(context.getApplicationContext());
		}
		
		//#if def{lang} == cn
		// 打开分享菜单的统计
		//#elif def{lang} == en
		// the statistics of opening the platform gridview
		//#endif
		ShareSDK.logDemoEvent(1, null);
		
		int iTheme = 0;
		try {
			iTheme = ResHelper.parseInt(String.valueOf(shareParamsMap.remove("theme")));
		} catch (Throwable t) {}
		OnekeyShareTheme theme = OnekeyShareTheme.fromValue(iTheme);
		OnekeyShareThemeImpl themeImpl = theme.getImpl();
		
		themeImpl.setShareParamsMap(shareParamsMap);
		themeImpl.setDialogMode(shareParamsMap.containsKey("dialogMode") ? ((Boolean) shareParamsMap.remove("dialogMode")) : false);
		themeImpl.setSilent(shareParamsMap.containsKey("silent") ? ((Boolean) shareParamsMap.remove("silent")) : false);
		themeImpl.setCustomerLogos((ArrayList<CustomerLogo>) shareParamsMap.remove("customers"));
		themeImpl.setHiddenPlatforms((HashMap<String, String>) shareParamsMap.remove("hiddenPlatforms"));
		themeImpl.setPlatformActionListener((PlatformActionListener) shareParamsMap.remove("callback"));
		themeImpl.setShareContentCustomizeCallback((ShareContentCustomizeCallback) shareParamsMap.remove("customizeCallback"));
		if (shareParamsMap.containsKey("disableSSO") ? ((Boolean) shareParamsMap.remove("disableSSO")) : false) {
			themeImpl.disableSSO();
		}
		
		themeImpl.show(context.getApplicationContext());
	}
	
}
