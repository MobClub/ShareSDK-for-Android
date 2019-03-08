package cn.sharesdk.demo.manager.platform;

import java.lang.reflect.Field;

import cn.sharesdk.framework.Platform;

/**
 * Created by yjin on 2017/5/17.
 */

/**
 * 各个平台具体可以实现的分享类型的定义类。
 */
public class PlatformShareConstant {
	private static PlatformShareConstant instance = null;
	private Platform platform;
	public static Integer[] sinaWeibo;
	public static Integer[] tencentWeibo;
	public static Integer[] qzone;
	public static Integer[] wechat;
	public static Integer[] wechatMoments;
	public static Integer[] wechatFavorite;
	public static Integer[] qq;
	public static Integer[] facebook;
	public static Integer[] twitter;
	public static Integer[] renren;
	public static Integer[] kaiXin;
	public static Integer[] email;
	public static Integer[] shortMessage;
	public static Integer[] douban;
	public static Integer[] youDao;
	public static Integer[] evernote;
	public static Integer[] linkedIn;
	public static Integer[] fourSquare;
	public static Integer[] pinterest;
	public static Integer[] flickr;
	public static Integer[] tumblr;
	public static Integer[] dropbox;
	public static Integer[] vkontakte;
	public static Integer[] instagram;
	public static Integer[] yixin;
	public static Integer[] yixinMoments;
	public static Integer[] mingdao;
	public static Integer[] line;
	public static Integer[] kakaoTalk;
	public static Integer[] kakaoStory;
	public static Integer[] whatsApp;
	public static Integer[] bluetooth;
	public static Integer[] pocket;
	public static Integer[] instapaper;
	public static Integer[] facebookMessenger;
	public static Integer[] alipay;
	public static Integer[] alipayMoments;
	public static Integer[] dingding;
	public static Integer[] youtube;
	public static Integer[] meipai;
	public static Integer[] telegram;
	public static Integer[] reddit;
	public static Integer[] douyin;
	
	private PlatformShareConstant(){
		sinaWeibo = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_VIDEO, Platform.SHARE_LINKCARD};
		tencentWeibo = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT};
		qzone = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT,Platform.SHARE_WEBPAGE, Platform.SHARE_VIDEO};
		wechat = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_FILE, Platform.SHARE_WEBPAGE, Platform.SHARE_MUSIC, 
				Platform.SHARE_VIDEO, Platform.SHARE_EMOJI, Platform.SHARE_WXMINIPROGRAM};
		wechatMoments = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_WEBPAGE, Platform.SHARE_MUSIC, Platform.SHARE_VIDEO};
		wechatFavorite = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_FILE, Platform.SHARE_WEBPAGE, 
				Platform.SHARE_MUSIC, Platform.SHARE_VIDEO};
		qq = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_WEBPAGE, Platform.SHARE_MUSIC};
		facebook = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_WEBPAGE, Platform.SHARE_VIDEO};
		twitter = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_VIDEO};
		renren = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT};
		kaiXin = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT};
		email = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_VIDEO};
		shortMessage = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_VIDEO};
		douban = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT};
		youDao = new Integer[]{Platform.SHARE_TEXT, Platform.SHARE_IMAGE};
		evernote = new Integer[]{Platform.SHARE_IMAGE,Platform.SHARE_TEXT, Platform.SHARE_VIDEO};
		linkedIn = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT	};
		fourSquare = new Integer[]{Platform.SHARE_IMAGE};
		pinterest = new Integer[]{Platform.SHARE_IMAGE};
		flickr = new Integer[]{Platform.SHARE_IMAGE};
		tumblr = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT};
		dropbox = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_FILE, Platform.SHARE_VIDEO};
		vkontakte = new Integer[]{Platform.SHARE_IMAGE};
		instagram = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_VIDEO, Platform.SHARE_TEXT};
		yixin = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_MUSIC,Platform.SHARE_VIDEO,Platform.SHARE_WEBPAGE};
		yixinMoments = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_MUSIC,Platform.SHARE_VIDEO,Platform.SHARE_WEBPAGE};
		mingdao = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT};
		line = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT	};
		kakaoTalk = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT};
		kakaoStory = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_VIDEO};
		whatsApp = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_VIDEO};
		bluetooth = new Integer[]{Platform.SHARE_FILE};
		pocket = new Integer[]{Platform.SHARE_WEBPAGE};
		instapaper = new Integer[]{Platform.SHARE_TEXT, Platform.SHARE_WEBPAGE	};
		facebookMessenger = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_WEBPAGE};
		alipay = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_WEBPAGE};
		alipayMoments = new Integer[]{Platform.SHARE_WEBPAGE};
		dingding = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_TEXT, Platform.SHARE_WEBPAGE};
		youtube = new Integer[]{Platform.SHARE_VIDEO};
		meipai = new Integer[]{Platform.SHARE_IMAGE, Platform.SHARE_VIDEO};
		telegram = new Integer[]{Platform.SHARE_TEXT, Platform.SHARE_IMAGE};
		reddit = new Integer[]{Platform.SHARE_TEXT, Platform.SHARE_WEBPAGE};
		douyin = new Integer[]{Platform.SHARE_VIDEO};
	}
	public synchronized static PlatformShareConstant getInstance(){
		if(instance == null){
			instance = new PlatformShareConstant();
		}
		return instance;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public static Integer[] byNamePlatforms(String name) {
		PlatformShareConstant platformShare = new PlatformShareConstant();
		Class cls = platformShare.getClass();
		Field[] fields = cls.getFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().toUpperCase().equals(name.toUpperCase())) {
					Object obj = fields[i].get(platformShare);
					if (obj != null && obj instanceof Integer[]) {
						Integer[] lists = (Integer[]) obj;
						return lists;
					}
				}
			}
		} catch (Exception e) {

		}
		return new Integer[]{};
	}

}
