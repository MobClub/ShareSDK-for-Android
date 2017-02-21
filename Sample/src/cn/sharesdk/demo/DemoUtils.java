package cn.sharesdk.demo;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

public class DemoUtils {

	public static boolean canGetUserInfo(String platform) {
		return !("WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "Email".equals(platform)
				|| "Pinterest".equals(platform) || "Yixin".equals(platform)
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
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "Email".equals(platform)
				|| "Pinterest".equals(platform) || "Yixin".equals(platform)
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
				|| "Email".equals(platform) || "GooglePlus".equals(platform)
				|| "QQ".equals(platform) || "Pinterest".equals(platform)
				|| "Instagram".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform) || "QZone".equals(platform)
				|| "Mingdao".equals(platform) || "Line".equals(platform)
				|| "KakaoStory".equals(platform) || "KakaoTalk".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "BaiduTieba".equals(platform) || "Laiwang".equals(platform)
				|| "LaiwangMoments".equals(platform) || "Alipay".equals(platform)
				|| "AlipayMoments".equals(platform) || "FacebookMessenger".equals(platform)
				|| "Dingding".equals(platform) || "Youtube".equals(platform)
				|| "Meipai".equals(platform)) {
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

}
