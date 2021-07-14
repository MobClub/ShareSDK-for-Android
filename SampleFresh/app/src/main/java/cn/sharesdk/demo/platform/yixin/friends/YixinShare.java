package cn.sharesdk.demo.platform.yixin.friends;

import android.content.pm.PackageInfo;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.yixin.friends.Yixin;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_TEXT;
import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;
import static cn.sharesdk.demo.entity.ResourcesManager.IMAGE_TEST_URL;

/**
 * Created by yjin on 2017/6/22.
 */

public class YixinShare {
	private PlatformActionListener platformActionListener;

	public YixinShare(PlatformActionListener platformActionListener) {
		this.platformActionListener = platformActionListener;
		PackageInfo pi;
		DemoUtils.isValidClient("im.yixin");
	}

	public void shareText() {
		Platform platform = ShareSDK.getPlatform(Yixin.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText("texttestettt");
		shareParams.setTitle("titletsdfsdf");
		shareParams.setShareType(Platform.SHARE_TEXT);
		shareParams.setScence(0);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage() {
		Platform platform = ShareSDK.getPlatform(Yixin.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText("yixinText");
		shareParams.setTitle("title");
		shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		//shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		//shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		shareParams.setScence(0);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareMusic() {
		Platform platform = ShareSDK.getPlatform(Yixin.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText("yixinText");
		shareParams.setTitle("title");
		shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		shareParams.setMusicUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
		shareParams.setUrl("https://www.baidu.com/");
		shareParams.setShareType(Platform.SHARE_MUSIC);
		shareParams.setScence(0);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareVideo() {
		Platform platform = ShareSDK.getPlatform(Yixin.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText("yixinText");
		shareParams.setTitle("title");
		shareParams.setImageUrl(IMAGE_TEST_URL);
		shareParams.setMusicUrl("https://y.qq.com/n/yqq/song/807557_num.html?ADTAG=h5_playsong&no_redirect=1");
		shareParams.setUrl("http://www.mob.com/video/ShareSDK.mp4");
		shareParams.setShareType(Platform.SHARE_VIDEO);
		shareParams.setScence(0);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareWebPager() {
		Platform platform = ShareSDK.getPlatform(Yixin.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText(LINK_TEXT);
		shareParams.setTitle("title");
		shareParams.setImageUrl(IMAGE_TEST_URL);
		shareParams.setMusicUrl("https://y.qq.com/n/yqq/song/807557_num.html?ADTAG=h5_playsong&no_redirect=1");
		shareParams.setUrl(LINK_URL);
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		shareParams.setScence(0);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
}
