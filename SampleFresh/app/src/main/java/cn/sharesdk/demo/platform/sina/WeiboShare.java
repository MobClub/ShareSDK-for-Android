package cn.sharesdk.demo.platform.sina;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;

/**
 * Created by yjin on 2017/6/22.
 */

public class WeiboShare {
	private PlatformActionListener platformActionListener;

	public WeiboShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		if(!DemoUtils.isValidClientSina("com.sina.weibo")){
			shareParams.setUrl("http://www.mob.com");
		}
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageArray(ResourcesManager.getInstace(MobSDK.getContext()).randomPic());
		if(!DemoUtils.isValidClientSina("com.sina.weibo")){
			shareParams.setUrl("http://www.mob.com");
		}
		shareParams.setShareType(Platform.SHARE_IMAGE);
		shareParams.setLongitude(ResourcesManager.longitude);
		shareParams.setLatitude(ResourcesManager.latitude);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		if(!DemoUtils.isValidClientSina("com.sina.weibo")){
			shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		}
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		if(!DemoUtils.isValidClientSina("com.sina.weibo")){
			shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		}
		shareParams.setLongitude(ResourcesManager.longitude);
		shareParams.setLatitude(ResourcesManager.latitude);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
