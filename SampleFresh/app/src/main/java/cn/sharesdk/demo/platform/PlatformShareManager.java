package cn.sharesdk.demo.platform;

/**
 * Created by yjin on 2017/6/19.
 */

import android.app.Activity;

import com.mob.MobSDK;

import cn.sharesdk.alipay.friends.Alipay;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.platform.alipay.friends.AlipayShare;
import cn.sharesdk.demo.platform.dingding.friends.DingdingShare;
import cn.sharesdk.demo.platform.douban.DoubanShare;
import cn.sharesdk.demo.platform.douyin.DouyinShare;
import cn.sharesdk.demo.platform.dropbox.DropboxShare;
import cn.sharesdk.demo.platform.evernote.EvernoteShare;
import cn.sharesdk.demo.platform.facebook.FacebookShare;
import cn.sharesdk.demo.platform.facebookmessenger.FacebookMessengerShare;
import cn.sharesdk.demo.platform.flickr.FlickerShare;
import cn.sharesdk.demo.platform.foursquare.FourSquareShare;
import cn.sharesdk.demo.platform.instagram.InstagramShare;
import cn.sharesdk.demo.platform.instapaper.InstapaperShare;
import cn.sharesdk.demo.platform.kaixin.KaiXinShare;
import cn.sharesdk.demo.platform.kakao.story.KakaoStoryShare;
import cn.sharesdk.demo.platform.kakao.talk.KakaoTalkShare;
import cn.sharesdk.demo.platform.line.LineShare;
import cn.sharesdk.demo.platform.linkedin.LinkedinShare;
import cn.sharesdk.demo.platform.meipai.MeipaiShare;
import cn.sharesdk.demo.platform.mingdao.MingdaoShare;
import cn.sharesdk.demo.platform.pinterest.PinterestShare;
import cn.sharesdk.demo.platform.reddit.RedditShare;
import cn.sharesdk.demo.platform.renren.RenrenShare;
import cn.sharesdk.demo.platform.sina.WeiboShare;
import cn.sharesdk.demo.platform.system.email.EmailShare;
import cn.sharesdk.demo.platform.system.text.ShortMessageShare;
import cn.sharesdk.demo.platform.telegram.TelegramShare;
import cn.sharesdk.demo.platform.tencent.qq.QQShare;
import cn.sharesdk.demo.platform.tencent.qzone.QQZoneShare;
import cn.sharesdk.demo.platform.tumblr.TumblrShare;
import cn.sharesdk.demo.platform.twitter.TwitterShare;
import cn.sharesdk.demo.platform.vkontakte.VKontakteShare;
import cn.sharesdk.demo.platform.wechat.favorite.WechatFavoriteShare;
import cn.sharesdk.demo.platform.wechat.friends.WechatShare;
import cn.sharesdk.demo.platform.wechat.moments.WechatMomentsShare;
import cn.sharesdk.demo.platform.wework.WeworkShare;
import cn.sharesdk.demo.platform.whatsapp.WhatsAppShare;
import cn.sharesdk.demo.platform.yixin.friends.YixinShare;
import cn.sharesdk.demo.platform.yixin.moments.YixinMomentsShare;
import cn.sharesdk.demo.platform.youdao.YouDaoShare;
import cn.sharesdk.demo.platform.youtub.YoutubeShare;
import cn.sharesdk.dingding.friends.Dingding;
import cn.sharesdk.douban.Douban;
import cn.sharesdk.douyin.Douyin;
import cn.sharesdk.dropbox.Dropbox;
import cn.sharesdk.evernote.Evernote;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.facebookmessenger.FacebookMessenger;
import cn.sharesdk.flickr.Flickr;
import cn.sharesdk.foursquare.FourSquare;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.instagram.Instagram;
import cn.sharesdk.instapaper.Instapaper;
import cn.sharesdk.kaixin.KaiXin;
import cn.sharesdk.kakao.story.KakaoStory;
import cn.sharesdk.kakao.talk.KakaoTalk;
import cn.sharesdk.line.Line;
import cn.sharesdk.linkedin.LinkedIn;
import cn.sharesdk.meipai.Meipai;
import cn.sharesdk.mingdao.Mingdao;
import cn.sharesdk.pinterest.Pinterest;
import cn.sharesdk.reddit.Reddit;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.telegram.Telegram;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.tumblr.Tumblr;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.vkontakte.VKontakte;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wework.Wework;
import cn.sharesdk.whatsapp.WhatsApp;
import cn.sharesdk.yixin.friends.Yixin;
import cn.sharesdk.yixin.moments.YixinMoments;
import cn.sharesdk.youdao.YouDao;
import cn.sharesdk.youtube.Youtube;

/**
 * 获取各个平台后进行分享的操作。
 */

public class PlatformShareManager {
	private PlatformActionListener platformActionListener;

	public void setPlatformActionListener(PlatformActionListener platformActionListener) {
		this.platformActionListener = platformActionListener;
	}

	public void shareText(String name) {
		if (name.equals(Alipay.NAME)) {
			AlipayShare alipay = new AlipayShare(platformActionListener);
			alipay.shareText();
		} else if (name.equals(Dingding.NAME)) {
			DingdingShare dingdingShare = new DingdingShare(platformActionListener);
			dingdingShare.shareText();
		} else if (name.equals(Douban.NAME)) {
			DoubanShare doubanShare = new DoubanShare(platformActionListener);
			doubanShare.shareText();
		} else if (name.equals(Evernote.NAME)) {
			EvernoteShare evernoteShare = new EvernoteShare(platformActionListener);
			evernoteShare.shareText();
		} else if (name.equals(Instapaper.NAME)) {
			InstapaperShare instapaperShare = new InstapaperShare(platformActionListener);
			instapaperShare.shareTextUrl();
		} else if (name.equals(KaiXin.NAME)) {
			KaiXinShare kaiXinShare = new KaiXinShare(platformActionListener);
			kaiXinShare.shareText();
		} else if (name.equals(KakaoStory.NAME)) {
			KakaoStoryShare kakaoStoryShare = new KakaoStoryShare(platformActionListener);
			kakaoStoryShare.shareText();
		} else if (name.equals(KakaoTalk.NAME)) {
			KakaoTalkShare kakaoTalkShare = new KakaoTalkShare(platformActionListener);
			kakaoTalkShare.shareText();
		} else if (name.equals(Line.NAME)) {
			LineShare lineShare = new LineShare(platformActionListener);
			lineShare.shareText();
		} else if (name.equals(LinkedIn.NAME)) {
			LinkedinShare linkedinShare = new LinkedinShare(platformActionListener);
			linkedinShare.shareText();
		} else if (name.equals(Mingdao.NAME)) {
			MingdaoShare mingdaoShare = new MingdaoShare(platformActionListener);
			mingdaoShare.shareText();
		} else if (name.equals(Renren.NAME)) {
			RenrenShare renrenShare = new RenrenShare(platformActionListener);
			renrenShare.shareText();
		} else if (name.equals(SinaWeibo.NAME)) {
			WeiboShare weiboShare = new WeiboShare(platformActionListener);
			weiboShare.shareText();
		} else if (name.equals(QZone.NAME)) {
			QQZoneShare qqZoneShare = new QQZoneShare(platformActionListener);
			qqZoneShare.shareText();
		} else if (name.equals(TencentWeibo.NAME)) {
			cn.sharesdk.demo.platform.tencent.WeiboShare qqZoneShare = new cn.sharesdk.demo.platform.tencent.WeiboShare(platformActionListener);
			qqZoneShare.shareText();
		} else if (name.equals(Tumblr.NAME)) {
			TumblrShare tumblrShare = new TumblrShare(platformActionListener);
			tumblrShare.shareText();
		} else if (name.equals(Twitter.NAME)) {
			TwitterShare twitterShare = new TwitterShare(platformActionListener);
			twitterShare.shareText();
		} else if (name.equals(Wechat.NAME)) {
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareText();
		} else if (name.equals(WechatFavorite.NAME)) {
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareText();
		} else if (name.equals(WechatMoments.NAME)) {
			WechatMomentsShare wechatMomentsShare = new WechatMomentsShare(platformActionListener);
			wechatMomentsShare.shareText();
		} else if (name.equals(WhatsApp.NAME)) {
			WhatsAppShare whatsAppShare = new WhatsAppShare(platformActionListener);
			whatsAppShare.shareText();
		} else if (name.equals(Instagram.NAME)) {
			InstagramShare instagramShare = new InstagramShare(platformActionListener);
			instagramShare.shareText();
		} else if (name.equals(YouDao.NAME)) {
			YouDaoShare whatsAppShare = new YouDaoShare(platformActionListener);
			whatsAppShare.shareText();
		} else if (name.equals(Yixin.NAME)) {
			YixinShare yixinShare = new YixinShare(platformActionListener);
			yixinShare.shareText();
		} else if (name.equals(Email.NAME)) {
			EmailShare yixinShare = new EmailShare(platformActionListener);
			yixinShare.shareText();
		} else if (name.equals(ShortMessage.NAME)) {
			ShortMessageShare yixinShare = new ShortMessageShare(platformActionListener);
			yixinShare.shareText();
		} else if (name.equals(Reddit.NAME)) {
			RedditShare redditShare = new RedditShare(platformActionListener);
			redditShare.shareText();
		} else if (name.equals(Telegram.NAME)) {
			TelegramShare telegramShare = new TelegramShare(platformActionListener);
			telegramShare.shareText();
		} else if (name.equals(Wework.NAME)) {
			WeworkShare weworkShare = new WeworkShare(platformActionListener);
			weworkShare.shareText();
		} else {
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setShareType(Platform.SHARE_TEXT);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareImage(String name, Activity activity) {
		if (name.equals(Alipay.NAME)) {
			AlipayShare alipay = new AlipayShare(platformActionListener);
			alipay.shareImage();
		} else if (name.equals(Dingding.NAME)) {
			DingdingShare dingdingShare = new DingdingShare(platformActionListener);
			dingdingShare.shareImage();
		} else if (name.equals(Douban.NAME)) {
			DoubanShare doubanShare = new DoubanShare(platformActionListener);
			doubanShare.shareImage();
		} else if (name.equals(Dropbox.NAME)) {
			DropboxShare dropboxShare = new DropboxShare(platformActionListener);
			dropboxShare.shareImage();
		} else if (name.equals(Evernote.NAME)) {
			EvernoteShare evernoteShare = new EvernoteShare(platformActionListener);
			evernoteShare.shareImage();
		} else if (name.equals(Facebook.NAME)) {
			FacebookShare facebookShare = new FacebookShare(platformActionListener);
			facebookShare.shareImage();
		} else if (name.equals(Renren.NAME)) {
			RenrenShare renrenShare = new RenrenShare(platformActionListener);
			renrenShare.shareImage();
		} else if (name.equals(YouDao.NAME)) {
			YouDaoShare whatsAppShare = new YouDaoShare(platformActionListener);
			whatsAppShare.shareImage();
		} else if (name.equals(FacebookMessenger.NAME)) {
			FacebookMessengerShare facebookMessengerShare = new FacebookMessengerShare(platformActionListener);
			facebookMessengerShare.shareImage();
		} else if (name.equals(Flickr.NAME)) {
			FlickerShare flickerShare = new FlickerShare(platformActionListener);
			flickerShare.shareImage();
		} else if (name.equals(FourSquare.NAME)) {
			FourSquareShare fourSquareShare = new FourSquareShare(platformActionListener);
			fourSquareShare.shareImage();
		} else if (name.equals(Instagram.NAME)) {
			InstagramShare instagramShare = new InstagramShare(platformActionListener);
			instagramShare.shareTextImage();
		} else if (name.equals(KaiXin.NAME)) {
			KaiXinShare kaiXinShare = new KaiXinShare(platformActionListener);
			kaiXinShare.shareImage();
		} else if (name.equals(KakaoStory.NAME)) {
			KakaoStoryShare kakaoStoryShare = new KakaoStoryShare(platformActionListener);
			kakaoStoryShare.shareImage();
		} else if (name.equals(KakaoTalk.NAME)) {
			KakaoTalkShare kakaoStoryShare = new KakaoTalkShare(platformActionListener);
			kakaoStoryShare.shareImage();
		} else if (name.equals(Line.NAME)) {
			LineShare lineShare = new LineShare(platformActionListener);
			lineShare.shareImage();
		}/* else if (name.equals(LinkedIn.NAME)) {
			LinkedinShare linkedinShare = new LinkedinShare(platformActionListener);
			linkedinShare.shareImage();
		}*/ else if (name.equals(Meipai.NAME)) {
			MeipaiShare meipaiShare = new MeipaiShare(platformActionListener);
			meipaiShare.shareImage();
		} else if (name.equals(Mingdao.NAME)) {
			MingdaoShare mingdaoShare = new MingdaoShare(platformActionListener);
			mingdaoShare.shareImage();
		} else if (name.equals(SinaWeibo.NAME)) {
			WeiboShare weiboShare = new WeiboShare(platformActionListener);
			weiboShare.shareImage();
		} else if (name.equals(QQ.NAME)) {
			QQShare qqShare = new QQShare(platformActionListener);
			qqShare.shareImage();
		} else if (name.equals(QZone.NAME)) {
			QQZoneShare qqZoneShare = new QQZoneShare(platformActionListener);
			qqZoneShare.shareImage();
		} else if (name.equals(TencentWeibo.NAME)) {
			cn.sharesdk.demo.platform.tencent.WeiboShare qqZoneShare = new cn.sharesdk.demo.platform.tencent.WeiboShare(platformActionListener);
			qqZoneShare.shareImage();
		} else if (name.equals(Tumblr.NAME)) {
			TumblrShare tumblrShare = new TumblrShare(platformActionListener);
			tumblrShare.shareImage();
		} else if (name.equals(Twitter.NAME)) {
			TwitterShare twitterShare = new TwitterShare(platformActionListener);
			twitterShare.shareImage();
		} else if (name.equals(Wechat.NAME)) {
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareImage();
		} else if (name.equals(WechatFavorite.NAME)) {
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareImage();
		} else if (name.equals(WechatMoments.NAME)) {
			WechatMomentsShare wechatMomentsShare = new WechatMomentsShare(platformActionListener);
			wechatMomentsShare.shareImage();
		} else if (name.equals(WhatsApp.NAME)) {
			WhatsAppShare whatsAppShare = new WhatsAppShare(platformActionListener);
			whatsAppShare.shareImage();
		} else if (name.equals(Yixin.NAME)) {
			YixinShare yixinShare = new YixinShare(platformActionListener);
			yixinShare.shareImage();
		} else if (name.equals(Email.NAME)) {
			EmailShare yixinShare = new EmailShare(platformActionListener);
			yixinShare.shareImage();
		} else if (name.equals(ShortMessage.NAME)) {
			ShortMessageShare yixinShare = new ShortMessageShare(platformActionListener);
			yixinShare.shareImage();
		} else if (name.equals(VKontakte.NAME)) {
			VKontakteShare vKontakteShare = new VKontakteShare(platformActionListener);
			vKontakteShare.shareMessage();
		} else if (name.equals(Pinterest.NAME)) {
			PinterestShare wechatShare = new PinterestShare(platformActionListener);
			wechatShare.shareImage();
		} else if (name.equals(Telegram.NAME)) {
			TelegramShare telegramShare = new TelegramShare(platformActionListener);
			telegramShare.shareImage();
		} else if (name.equals(Douyin.NAME)) {
			DouyinShare douyinShare = new DouyinShare(platformActionListener);
			douyinShare.shareImage(activity);
		} else if (name.equals(YixinMoments.NAME)) {
			YixinMomentsShare wechatShare = new YixinMomentsShare(platformActionListener);
			wechatShare.shareImage();
		} else if (name.equals(Wework.NAME)) {
			WeworkShare weworkShare = new WeworkShare(platformActionListener);
			weworkShare.shareImage(activity);
		} else {
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
			shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
			shareParams.setShareType(Platform.SHARE_IMAGE);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareWebPager(String name, Activity activity) {
		if (name.equals(Alipay.NAME)) {
			AlipayShare alipay = new AlipayShare(platformActionListener);
			alipay.shareWebPage();
		} else if (name.equals(Dingding.NAME)) {
			DingdingShare dingdingShare = new DingdingShare(platformActionListener);
			dingdingShare.shareWebPage();
		} else if (name.equals(Facebook.NAME)) {
			FacebookShare facebookShare = new FacebookShare(platformActionListener);
			facebookShare.shareWebPage();
		} else if (name.equals(QQ.NAME)) {
			QQShare qqShare = new QQShare(platformActionListener);
			qqShare.shareWebPager();
		} else if (name.equals(QZone.NAME)) {
			QQZoneShare mingdaoShare = new QQZoneShare(platformActionListener);
			mingdaoShare.shareWebPager();
		} else if (name.equals(FacebookMessenger.NAME)) {
			FacebookMessengerShare facebookMessengerShare = new FacebookMessengerShare(platformActionListener);
			facebookMessengerShare.shareWebPage();
		} else if (name.equals(Wechat.NAME)) {
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareWebpager();
		} else if (name.equals(WechatMoments.NAME)) {
			WechatMomentsShare wechatShare = new WechatMomentsShare(platformActionListener);
			wechatShare.shareWebpager();
		} else if (name.equals(WechatFavorite.NAME)) {
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareWebpager();
		} else if (name.equals(Yixin.NAME)) {
			YixinShare wechatShare = new YixinShare(platformActionListener);
			wechatShare.shareWebPager();
		} else if (name.equals(YixinMoments.NAME)) {
			YixinMomentsShare wechatShare = new YixinMomentsShare(platformActionListener);
			wechatShare.shareWebPager();
		} else if (name.equals(Reddit.NAME)) {
			RedditShare redditShare = new RedditShare(platformActionListener);
			redditShare.shareUrl();
		} else if (name.equals(LinkedIn.NAME)) {
			LinkedinShare linkedinShare = new LinkedinShare(platformActionListener);
			linkedinShare.shareWebPage();
		} else if (name.equals(Wework.NAME)) {
			WeworkShare weworkShare = new WeworkShare(platformActionListener);
			weworkShare.shareWebPage();
		} else {
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
			shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
			shareParams.setShareType(Platform.SHARE_WEBPAGE);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareVideo(String name, Activity activity) {
		if(name.equals(SinaWeibo.NAME)){
			WeiboShare weiboShare = new WeiboShare(platformActionListener);
			weiboShare.shareVideo();
		} else if (name.equals(Dropbox.NAME)) {
			DropboxShare dropboxShare = new DropboxShare(platformActionListener);
			dropboxShare.shareVideo();
		} else if (name.equals(Evernote.NAME)) {
			EvernoteShare evernoteShare = new EvernoteShare(platformActionListener);
			evernoteShare.shareVideo();
		} else if (name.equals(Facebook.NAME)) {
			FacebookShare facebookShare = new FacebookShare(platformActionListener);
			facebookShare.shareVideo();
		} else if (name.equals(Instagram.NAME)) {
			InstagramShare instagramShare = new InstagramShare(platformActionListener);
			instagramShare.shareVideo();
		} else if (name.equals(KakaoStory.NAME)) {
			KakaoStoryShare kakaoStoryShare = new KakaoStoryShare(platformActionListener);
			kakaoStoryShare.shareVideo();
		} else if (name.equals(Meipai.NAME)) {
			MeipaiShare meipaiShare = new MeipaiShare(platformActionListener);
			meipaiShare.shareVideo();
		} else if (name.equals(Email.NAME)) {
			EmailShare emailShare = new EmailShare(platformActionListener);
			emailShare.shareVideo();
		} else if (name.equals(ShortMessage.NAME)) {
			ShortMessageShare shortMessageShare = new ShortMessageShare(platformActionListener);
			shortMessageShare.shareVideo();
		} else if (name.equals(QZone.NAME)) {
			QQZoneShare qqZoneShare = new QQZoneShare(platformActionListener);
			qqZoneShare.shareVideo();
		} else if (name.equals(Twitter.NAME)) {
			TwitterShare twitterShare = new TwitterShare(platformActionListener);
			twitterShare.shareVideo();
		} else if (name.equals(Wechat.NAME)) {
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareVideo();
		} else if (name.equals(WechatFavorite.NAME)) {
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareVideo();
		} else if (name.equals(WechatMoments.NAME)) {
			WechatMomentsShare wechatMomentsShare = new WechatMomentsShare(platformActionListener);
			wechatMomentsShare.shareVideo();
		} else if (name.equals(WhatsApp.NAME)) {
			WhatsAppShare whatsAppShare = new WhatsAppShare(platformActionListener);
			whatsAppShare.shareVideo();
		} else if (name.equals(Yixin.NAME)) {
			YixinShare yixinShare = new YixinShare(platformActionListener);
			yixinShare.shareVideo();
		} else if (name.equals(YixinMoments.NAME)) {
			YixinMomentsShare yixinShare = new YixinMomentsShare(platformActionListener);
			yixinShare.shareVideo();
		} else if (name.equals(Youtube.NAME)) {
			YoutubeShare youtubeShare = new YoutubeShare(platformActionListener);
			youtubeShare.shareVideo();
		} else if (name.equals(Douyin.NAME)) {
			DouyinShare douyinShare = new DouyinShare(platformActionListener);
			douyinShare.shareVideo(activity);
		} else if (name.equals(Wework.NAME)) {
			WeworkShare weworkShare = new WeworkShare(platformActionListener);
			weworkShare.shareVideo(activity);
		} else {
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
			shareParams.setShareType(Platform.SHARE_VIDEO);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareFile(String name, Activity activity) {
		if (name.equals(Dropbox.NAME)) {
			DropboxShare dropboxShare = new DropboxShare(platformActionListener);
			dropboxShare.shareFile();
		} else if (name.equals(QZone.NAME)) {
			QQZoneShare qqZoneShare = new QQZoneShare(platformActionListener);
			qqZoneShare.shareVideo();
		} else if (name.equals(Wechat.NAME)) {
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareFile();
		} else if (name.equals(WechatFavorite.NAME)) {
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareFile();
		} else if (name.equals(Wework.NAME)) {
			WeworkShare weworkShare = new WeworkShare(platformActionListener);
			weworkShare.shareFile(activity);
		} else {
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
			shareParams.setShareType(Platform.SHARE_FILE);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareMusic(String name) {
		if (name.equals(QQ.NAME)) {
			QQShare qqShare = new QQShare(platformActionListener);
			qqShare.shareMusic();
		} else if (name.equals(Wechat.NAME)) {
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareMusic();
		} else if (name.equals(WechatFavorite.NAME)) {
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareMusic();
		} else if (name.equals(WechatMoments.NAME)) {
			WechatMomentsShare wechatMomentsShare = new WechatMomentsShare(platformActionListener);
			wechatMomentsShare.shareMusic();
		} else if (name.equals(Yixin.NAME)) {
			YixinShare yixinShare = new YixinShare(platformActionListener);
			yixinShare.shareMusic();
		} else if (name.equals(YixinMoments.NAME)) {
			YixinMomentsShare yixinShare = new YixinMomentsShare(platformActionListener);
			yixinShare.shareMusic();
		} else {
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
			shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
			shareParams.setMusicUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
			shareParams.setShareType(Platform.SHARE_MUSIC);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareApp(String name) {
		if (name.equals(Wechat.NAME)) {
			WechatShare wechatShare = new WechatShare(platformActionListener);
			//wechatShare.shareApps();
		}
	}

	public void shareLinkCard(String name) {
		if (name.equals(SinaWeibo.NAME)) {
			WeiboShare sinaWeibo = new WeiboShare(platformActionListener);
			sinaWeibo.shareLinkCard();
		}
	}


}
