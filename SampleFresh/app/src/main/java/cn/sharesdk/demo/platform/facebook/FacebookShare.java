package cn.sharesdk.demo.platform.facebook;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_TEXT;
import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;

/**
 * Created by yjin on 2017/6/22.
 */

public class FacebookShare {
	private PlatformActionListener platformActionListener;

	public FacebookShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(Facebook.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl("https://t3.ftcdn.net/jpg/02/01/25/00/240_F_201250053_xMFe9Hax6w01gOiinRLEPX0Wt1zGCzYz.jpg");
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(Facebook.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareWebPage(){
		Platform platform = ShareSDK.getPlatform(Facebook.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setUrl(LINK_URL);
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		shareParams.setQuote(LINK_TEXT);
		shareParams.setHashtag("测试话题分享");
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareApps(){
		Platform platform = ShareSDK.getPlatform(Facebook.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setShareType(Platform.SHARE_APPS);
		shareParams.setUrl("https://fb.me/1654865544566386");//Facebook开放平台创建后得到的短链接
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Facebook.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageArray(ResourcesManager.getInstace(MobSDK.getContext()).randomPic());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareVideo(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Facebook.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareWebPage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Facebook.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

}
