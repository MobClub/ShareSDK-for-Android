/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.R;

/**
 * ShareCore the actual export of OnekeyShare, which reflects
 * the pass in HashMap to a {@link ShareParams} of the target platform,
 * and shares it
 */
public class ShareCore {
	private ShareContentCustomizeCallback customizeCallback;

	/**
	 * add a customize share content callback which will be
	 * involved before the target platform sharing
	 */
	public void setShareContentCustomizeCallback(ShareContentCustomizeCallback callback) {
		customizeCallback = callback;
	}

	/** perform sharing */
	public boolean share(Platform plat, HashMap<String, Object> data) {
		if (plat == null || data == null) {
			return false;
		}

		try {
			String imagePath = (String) data.get("imagePath");
			Bitmap viewToShare = (Bitmap) data.get("viewToShare");
			if (TextUtils.isEmpty(imagePath) && viewToShare != null && !viewToShare.isRecycled()) {
				String path = R.getCachePath(plat.getContext(), "screenshot");
				File ss = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
				FileOutputStream fos = new FileOutputStream(ss);
				viewToShare.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
				data.put("imagePath", ss.getAbsolutePath());
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}

		ShareParams sp = new ShareParams(data);
		if (customizeCallback != null) {
			customizeCallback.onShare(plat, sp);
		}

		plat.share(sp);
		return true;
	}

	/** Determine whether the platform shares by its client or not */
	public static boolean isUseClientToShare(String platform) {
		if ("Wechat".equals(platform) || "WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "Email".equals(platform) || "GooglePlus".equals(platform)
				|| "QQ".equals(platform) || "Pinterest".equals(platform)
				|| "Instagram".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform) || "QZone".equals(platform)
				|| "Mingdao".equals(platform) || "Line".equals(platform)
				|| "KakaoStory".equals(platform) || "KakaoTalk".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "BaiduTieba".equals(platform) || "Laiwang".equals(platform)
				|| "LaiwangMoments".equals(platform)
				) {
			return true;
		} else if ("Evernote".equals(platform)) {
			Platform plat = ShareSDK.getPlatform(platform);
			if ("true".equals(plat.getDevinfo("ShareByAppClient"))) {
				return true;
			}
		} else if ("SinaWeibo".equals(platform)) {
			Platform plat = ShareSDK.getPlatform(platform);
			if ("true".equals(plat.getDevinfo("ShareByAppClient"))) {
				Intent test = new Intent(Intent.ACTION_SEND);
				test.setPackage("com.sina.weibo");
				test.setType("image/*");
				ResolveInfo ri = plat.getContext().getPackageManager().resolveActivity(test, 0);
				return (ri != null);
			}
		}

		return false;
	}

	/** Determine whether the platform can authorize */
	public static boolean canAuthorize(Context context, String platform) {
		return !("WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "Email".equals(platform)
				|| "Pinterest".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform) || "Line".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "BaiduTieba".equals(platform)) || "Laiwang".equals(platform)
				|| "LaiwangMoments".equals(platform);
	}


	/** Determine whether the platform can get user info */
	public static boolean canGetUserInfo(Context context, String platform) {
		return !("WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "Email".equals(platform)
				|| "Pinterest".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform) || "Line".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "Pocket".equals(platform) || "BaiduTieba".equals(platform)
				|| "Laiwang".equals(platform) || "LaiwangMoments".equals(platform));
	}

	/** Determine whether direct share */
	public static boolean isDirectShare(Platform platform) {
		return platform instanceof CustomPlatform || isUseClientToShare(platform.getName());
	}
}
