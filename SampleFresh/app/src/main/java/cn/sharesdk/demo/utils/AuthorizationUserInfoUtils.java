package cn.sharesdk.demo.utils;


public class AuthorizationUserInfoUtils {

	public static boolean canGetUserInfo(String platform) {
		return !("WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "Email".equals(platform) || "Pinterest".equals(platform)
				|| "Yixin".equals(platform) || "YixinMoments".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "Pocket".equals(platform) || "BaiduTieba".equals(platform)
				|| "Laiwang".equals(platform) || "LaiwangMoments".equals(platform)
				|| "Alipay".equals(platform) || "AlipayMoments".endsWith(platform)
				|| "FacebookMessenger".equals(platform) || "Youtube".equals(platform)
				|| "Meipai".equals(platform)
		);
	}

	public static boolean canAuthorize(String platform) {
		return !("WechatMoments".equals(platform) || "WechatFavorite".equals(platform)
				|| "Pinterest".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform) || "Email".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "BaiduTieba".equals(platform) || "Laiwang".equals(platform)
				|| "LaiwangMoments".equals(platform) || "Alipay".equals(platform)
				|| "AlipayMoments".equals(platform) || "FacebookMessenger".equals(platform)
				|| "Youtube".equals(platform) || "Meipai".equals(platform));
	}
}
