package cn.sharesdk.demo.platform.wechat.moments;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.moments.WechatMoments;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;


public class WechatMomentsShare {
	private PlatformActionListener platformActionListener;

	public WechatMomentsShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
		DemoUtils.isValidClient("com.tencent.mm");
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		if (ResourcesManager.getInstace(MobSDK.getContext()).getImagePath() == null) {
			shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		} else {
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		}
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareMusic(){
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		if (ResourcesManager.getInstace(MobSDK.getContext()).getImagePath() == null) {
			shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		} else {
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		}
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
		shareParams.setShareType(Platform.SHARE_MUSIC);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl("https://cdn-static-oss.mob.com/video/ShareSDK.mp4");
		if (ResourcesManager.getInstace(MobSDK.getContext()).getImagePath() == null) {
			shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		} else {
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		}
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareWebpager(){
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(LINK_URL);//ResourcesManager.getInstace(MobSDK.getContext()).getUrl()
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		if (ResourcesManager.getInstace(MobSDK.getContext()).getImagePath() == null) {
			shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		} else {
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		}
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareMusic(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setMusicUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
		shareParams.setShareType(Platform.SHARE_MUSIC);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareVideo(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl("http://www.mob.com/video/ShareSDK.mp4");
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareWebpager(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
