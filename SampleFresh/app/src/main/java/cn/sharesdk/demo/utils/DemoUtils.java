package cn.sharesdk.demo.utils;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import com.mob.MobSDK;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

public class DemoUtils {

	public static boolean canGetUserInfo(String platform) {
		return !("WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "email".equals(platform)
				|| "pinterest".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "Pocket".equals(platform) || "BaiduTieba".equals(platform)
				|| "Laiwang".equals(platform) || "LaiwangMoments".equals(platform)
				|| "Alipay".equals(platform) || "AlipayMoments".endsWith(platform)
				|| "FacebookMessenger".equals(platform) || "Dingding".equals(platform)
				|| "Youtube".equals(platform) || "Meipai".equals(platform));
	}

	public static boolean canAuthorize(String platform) {
		return !("WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "email".equals(platform)
				|| "pinterest".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "BaiduTieba".equals(platform) || "Laiwang".equals(platform)
				|| "LaiwangMoments".equals(platform) || "Alipay".equals(platform)
				|| "AlipayMoments".equals(platform) || "FacebookMessenger".equals(platform)
				|| "Dingding".equals(platform) || "Youtube".equals(platform)
				|| "Meipai".equals(platform));
	}

	public static boolean isUseClientToShare(String platform) {
		if ("Wechat".equals(platform) || "WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "email".equals(platform) || "googlePlus".equals(platform)
				|| "QQ".equals(platform) || "pinterest".equals(platform)
				|| "Instagram".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform) || "qzone".equals(platform)
				|| "Mingdao".equals(platform) || "Line".equals(platform)
				|| "KakaoStory".equals(platform) || "KakaoTalk".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "BaiduTieba".equals(platform) || "Laiwang".equals(platform)
				|| "LaiwangMoments".equals(platform) || "Alipay".equals(platform)
				|| "AlipayMoments".equals(platform) || "FacebookMessenger".equals(platform)
				|| "Dingding".equals(platform) || "Youtube".equals(platform)
				|| "Meipai".equals(platform) || "Douyin".equals(platform)) {
			return true;
		} else if ("evernote".equals(platform)) {
			Platform plat = ShareSDK.getPlatform(platform);
			if ("true".equals(plat.getDevinfo("ShareByAppClient"))) {
				return true;
			}
		} else if ("sinaWeibo".equals(platform)) {
			Platform plat = ShareSDK.getPlatform(platform);
			if ("true".equals(plat.getDevinfo("ShareByAppClient"))) {
				Intent test = new Intent(Intent.ACTION_SEND);
				test.setPackage("com.sina.weibo");
				test.setType("image/*");
				ResolveInfo ri = MobSDK.getContext().getPackageManager().resolveActivity(test, 0);
				return (ri != null);
			}
		}

		return false;
	}

	public static boolean isValidClient(String akp) {
		PackageInfo pi;
		try {
			pi = MobSDK.getContext().getPackageManager().getPackageInfo(
					akp, PackageManager.GET_RESOLVED_FILTER);
			return true;
		} catch (Throwable t) {
			Toast.makeText(MobSDK.getContext(), "client is not install or version low", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	public static boolean isValidClientSina(String akp) {
		PackageInfo pi;
		try {
			pi = MobSDK.getContext().getPackageManager().getPackageInfo(
					akp, PackageManager.GET_RESOLVED_FILTER);
			return true;
		} catch (Throwable t) {
		}
		return false;
	}

	public static void isValidClient(String[] akp) {
		int length = akp.length;
		PackageInfo pi = null;
		for (int i = 0; i < length; i++) {
			String packages = akp[i];
			try {
				pi = MobSDK.getContext().getPackageManager().getPackageInfo(
						packages, PackageManager.GET_RESOLVED_FILTER);
				if (pi != null) {
					break;
				}
			} catch (Throwable t) {

			}
		}
		if (pi == null) {
			Toast.makeText(MobSDK.getContext(), "client is not install or version low", Toast.LENGTH_SHORT).show();
		}

	}

}
