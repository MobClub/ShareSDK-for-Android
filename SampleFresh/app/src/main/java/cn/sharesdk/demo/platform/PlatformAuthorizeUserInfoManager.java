package cn.sharesdk.demo.platform;

import android.app.Activity;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.HashMap;

import cn.sharesdk.alipay.friends.Alipay;
import cn.sharesdk.dingding.friends.Dingding;
import cn.sharesdk.douban.Douban;
import cn.sharesdk.dropbox.Dropbox;
import cn.sharesdk.evernote.Evernote;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.facebookmessenger.FacebookMessenger;
import cn.sharesdk.flickr.Flickr;
import cn.sharesdk.foursquare.FourSquare;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.google.GooglePlus;
import cn.sharesdk.instagram.Instagram;
import cn.sharesdk.kaixin.KaiXin;
import cn.sharesdk.kakao.story.KakaoStory;
import cn.sharesdk.kakao.talk.KakaoTalk;
import cn.sharesdk.line.Line;
import cn.sharesdk.linkedin.LinkedIn;
import cn.sharesdk.meipai.Meipai;
import cn.sharesdk.mingdao.Mingdao;
import cn.sharesdk.pinterest.Pinterest;
import cn.sharesdk.pocket.Pocket;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
//import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.tumblr.Tumblr;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.vkontakte.VKontakte;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.whatsapp.WhatsApp;
import cn.sharesdk.yixin.friends.Yixin;
import cn.sharesdk.yixin.moments.YixinMoments;
import cn.sharesdk.youdao.YouDao;
import cn.sharesdk.youtube.Youtube;

/**
 * Created by yjin on 2017/6/21.
 */

public class PlatformAuthorizeUserInfoManager {
	private Activity activity;
	private MyPlatformActionListener myPlatformActionListener = null;

	public PlatformAuthorizeUserInfoManager(Activity mACt) {
		this.activity = mACt;
		this.myPlatformActionListener = new MyPlatformActionListener();
	}

	public void WeiXinAuthorize() {
		Platform weiXin = ShareSDK.getPlatform(Wechat.NAME);
		doAuthorize(weiXin);
	}

	public void sinaAuthorize() {
		Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
		doAuthorize(sina);
	}

	public void tenWeiboAuthorize() {
		Platform weibo = ShareSDK.getPlatform(TencentWeibo.NAME);
		doAuthorize(weibo);
	}

	public void qqZoneAuthorize() {
		Platform qqZone = ShareSDK.getPlatform(QZone.NAME);
		doAuthorize(qqZone);
	}

	public void whatMomentsAuthorize() {
		Platform moments = ShareSDK.getPlatform(WechatMoments.NAME);
		doAuthorize(moments);
	}

	public void wechatFavoriteAuthorize() {
		Platform wechatFavorite = ShareSDK.getPlatform(WechatFavorite.NAME);
		doAuthorize(wechatFavorite);
	}

	public void qqShareAuthorize() {
//		Platform qqShare = ShareSDK.getPlatform(QQ.NAME);
//		doAuthorize(qqShare);
	}

	public void facebookAuthorize() {
		Platform facebookShare = ShareSDK.getPlatform(Facebook.NAME);
		doAuthorize(facebookShare);
	}

	public void twitterAuthorize() {
		Platform twitterShare = ShareSDK.getPlatform(Twitter.NAME);
		doAuthorize(twitterShare);
	}

	public void renrenAuthorize() {
		Platform renrenShare = ShareSDK.getPlatform(Renren.NAME);
		doAuthorize(renrenShare);
	}

	public void kaixinAuthorize() {
		Platform kaixinShare = ShareSDK.getPlatform(KaiXin.NAME);
		doAuthorize(kaixinShare);
	}

	public void emailAuthorize() {
		Platform emailShare = ShareSDK.getPlatform(Email.NAME);
		doAuthorize(emailShare);
	}

	public void textAuthorize() {
		Platform shortMessageShare = ShareSDK.getPlatform(ShortMessage.NAME);
		doAuthorize(shortMessageShare);
	}

	public void doubanAuthorize() {
		Platform doubanShare = ShareSDK.getPlatform(Douban.NAME);
		doAuthorize(doubanShare);
	}

	public void youdaoAuthorize() {
		Platform youdaoShare = ShareSDK.getPlatform(YouDao.NAME);
		doAuthorize(youdaoShare);
	}

	public void yinxiangAuthorize() {
		Platform yingxiangShare = ShareSDK.getPlatform(Evernote.NAME);
		doAuthorize(yingxiangShare);
	}

	public void linkedInAuthorize() {
		Platform linkedInShare = ShareSDK.getPlatform(LinkedIn.NAME);
		doAuthorize(linkedInShare);
	}

	public void googlePlusAuthorize() {
		Platform googlePlusShare = ShareSDK.getPlatform(GooglePlus.NAME);
		doAuthorize(googlePlusShare);
	}

	public void fourSquareAuthorize() {
		Platform fourSquareShare = ShareSDK.getPlatform(FourSquare.NAME);
		doAuthorize(fourSquareShare);
	}

	public void printerestAuthorize() {
		Platform printerestShare = ShareSDK.getPlatform(Pinterest.NAME);
		doAuthorize(printerestShare);
	}

	public void flickrAuthorize() {
		Platform flickrShare = ShareSDK.getPlatform(Flickr.NAME);
		doAuthorize(flickrShare);
	}

	public void tumblrAuthorize() {
		Platform tumblrShare = ShareSDK.getPlatform(Tumblr.NAME);
		doAuthorize(tumblrShare);
	}

	public void droxboxAuthorize() {
		Platform droxboxShare = ShareSDK.getPlatform(Dropbox.NAME);
		doAuthorize(droxboxShare);
	}

	public void vkAuthorize() {
		Platform vkShare = ShareSDK.getPlatform(VKontakte.NAME);
		doAuthorize(vkShare);
	}

	public void instagramAuthorize() {
		Platform instagrameShare = ShareSDK.getPlatform(Instagram.NAME);
		doAuthorize(instagrameShare);
	}

	public void yinxinAuthorize() {
		Platform yinxinShare = ShareSDK.getPlatform(Yixin.NAME);
		doAuthorize(yinxinShare);
	}

	public void yixinMomentsAuthorize() {
		Platform yixinMomentsShare = ShareSDK.getPlatform(YixinMoments.NAME);
		doAuthorize(yixinMomentsShare);
	}

	public void mingDaoAuthorize() {
		Platform mingDaoShare = ShareSDK.getPlatform(Mingdao.NAME);
		doAuthorize(mingDaoShare);
	}

	public void lineAuthorize() {
		Platform lineShare = ShareSDK.getPlatform(Line.NAME);
		doAuthorize(lineShare);
	}

	public void kakaoTalkAuthorize() {
		Platform kakaoTalkShare = ShareSDK.getPlatform(KakaoTalk.NAME);
		doAuthorize(kakaoTalkShare);
	}

	public void kakaoStoryAuthorize() {
		Platform kakaoStory = ShareSDK.getPlatform(KakaoStory.NAME);
		doAuthorize(kakaoStory);
	}

	public void whatsAppAuthorize() {
		Platform whatsApp = ShareSDK.getPlatform(WhatsApp.NAME);
		doAuthorize(whatsApp);
	}

	public void pocketAuthorize() {
		Platform pocketShare = ShareSDK.getPlatform(Pocket.NAME);
		doAuthorize(pocketShare);
	}

	public void instapaperAuthorize() {
		Platform instapaerShare = ShareSDK.getPlatform(Instagram.NAME);
		doAuthorize(instapaerShare);
	}

	public void facebookMessageAuthorize() {
		Platform facebookMessageShare = ShareSDK.getPlatform(FacebookMessenger.NAME);
		doAuthorize(facebookMessageShare);
	}

	public void aliyAuthorize() {
		Platform aliyShare = ShareSDK.getPlatform(Alipay.NAME);
		doAuthorize(aliyShare);
	}

	public void dingDingAuthorize() {
		Platform dingdingShare = ShareSDK.getPlatform(Dingding.NAME);
		doAuthorize(dingdingShare);
	}

	public void youtubeAuthorize() {
		Platform youtubeShare = ShareSDK.getPlatform(Youtube.NAME);
		doAuthorize(youtubeShare);
	}

	public void meiPaiAuthorize() {
		Platform meipaiShare = ShareSDK.getPlatform(Meipai.NAME);
		doAuthorize(meipaiShare);
	}


	/**
	 * 授权的代码
	 */
	public void doAuthorize(Platform platform) {
		if (platform != null) {
			platform.setPlatformActionListener(myPlatformActionListener);
			if (platform.isAuthValid()) {
				platform.removeAccount(true);
				return;
			}
			platform.SSOSetting(true);
			platform.authorize();
		}
	}

	/**
	 * 授权的代码
	 */
	public void doAuthorize(Platform platform, PlatformActionListener listener) {
		if (platform != null) {
			platform.setPlatformActionListener(listener);
			platform.removeAccount(true);
			platform.authorize();
		}
	}

	/**
	 * 用户信息的代码
	 */
	public void doUserInfo(Platform platform) {
		if (platform != null) {
			platform.showUser(null);
		}
	}

	/**
	 * 用户信息的代码
	 */
	public void doUserInfo(Platform platform, PlatformActionListener listener) {
		if (platform != null) {
			platform.setPlatformActionListener(listener);
			platform.showUser(null);
		}
	}

	/**
	 *
	 * @param platform 平台名称
	 * @param shareType 分享类型
	 */
	/**
	 * 用户信息的代码
	 */
	public void doUserInfo(Platform platform, String account) {
		if (platform != null) {
			platform.showUser(account);
		}
	}

	/**
	 *
	 * @param platform 平台名称
	 * @param shareType 分享类型
	 */
	/**
	 * 用户信息的代码
	 */
	public void doUserInfo(Platform platform, String account, PlatformActionListener listener) {
		if (platform != null) {
			platform.setPlatformActionListener(listener);
			platform.showUser(account);
		}
	}

	/**
	 * 回调代码
	 */
	class MyPlatformActionListener implements PlatformActionListener {
		@Override
		public void onComplete(final Platform platform, int i, final HashMap<String, Object> hashMap) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(MobSDK.getContext(), "Authorize Complete.", Toast.LENGTH_SHORT).show();
					if(platform.getName().equals("ShortMessage") && hashMap != null) {
						Toast.makeText(MobSDK.getContext(), "ShoreMessage Login Info:" + hashMap.toString(), Toast.LENGTH_LONG).show();
					}
				}
			});
		}

		@Override
		public void onError(Platform platform, int i, Throwable throwable) {
			throwable.printStackTrace();
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(MobSDK.getContext(), "Authorize Failure", Toast.LENGTH_SHORT).show();
				}
			});
		}

		@Override
		public void onCancel(Platform platform, int i) {
			Toast.makeText(MobSDK.getContext(), "Cancel Authorize", Toast.LENGTH_SHORT).show();
		}
	}
}
