package cn.sharesdk.demo.platform.yixin.moments;

import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.yixin.moments.YixinMoments;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_TEXT;
import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;

/**
 * Created by yjin on 2017/6/25.
 */

public class YixinMomentsShare {
	private PlatformActionListener platformActionListener;

	public YixinMomentsShare(PlatformActionListener platformActionListener){
		this.platformActionListener = platformActionListener;
		DemoUtils.isValidClient("im.yixin");
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(YixinMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText("texttestettt");
		shareParams.setTitle("titletsdfsdf");
		shareParams.setImageUrl("https://t3.ftcdn.net/jpg/02/01/25/00/240_F_201250053_xMFe9Hax6w01gOiinRLEPX0Wt1zGCzYz.jpg");
		shareParams.setShareType(Platform.SHARE_TEXT);
		shareParams.setScence(1);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(YixinMoments.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText("yixinText");
		shareParams.setTitle("title");
		shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		//shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		//shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		shareParams.setScence(1);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareMusic(){
		Platform platform = ShareSDK.getPlatform(YixinMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText("yixinText");
		shareParams.setTitle("title");
		shareParams.setImageUrl("https://t3.ftcdn.net/jpg/02/01/25/00/240_F_201250053_xMFe9Hax6w01gOiinRLEPX0Wt1zGCzYz.jpg");
		shareParams.setMusicUrl("https://y.qq.com/n/yqq/song/807557_num.html?ADTAG=h5_playsong&no_redirect=1");
		shareParams.setUrl("https://www.baidu.com/");
		shareParams.setShareType(Platform.SHARE_MUSIC);
		shareParams.setScence(1);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(YixinMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText("yixinText");
		shareParams.setTitle("title");
		shareParams.setImageUrl("https://t3.ftcdn.net/jpg/02/01/25/00/240_F_201250053_xMFe9Hax6w01gOiinRLEPX0Wt1zGCzYz.jpg");
		shareParams.setMusicUrl("https://y.qq.com/n/yqq/song/807557_num.html?ADTAG=h5_playsong&no_redirect=1");
		shareParams.setUrl("http://f1.webshare.mob.com/dvideo/demovideos.mp4");
		shareParams.setShareType(Platform.SHARE_VIDEO);
		shareParams.setScence(1);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareWebPager(){
		Platform platform = ShareSDK.getPlatform(YixinMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(LINK_TEXT);
		shareParams.setTitle("title");
		shareParams.setImageUrl("https://t3.ftcdn.net/jpg/02/01/25/00/240_F_201250053_xMFe9Hax6w01gOiinRLEPX0Wt1zGCzYz.jpg");
		shareParams.setMusicUrl("https://y.qq.com/n/yqq/song/807557_num.html?ADTAG=h5_playsong&no_redirect=1");
		//shareParams.setUrl("http://f1.webshare.mob.com/dvideo/demovideos.mp4");
		shareParams.setUrl(LINK_URL);
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		shareParams.setScence(1);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
}
