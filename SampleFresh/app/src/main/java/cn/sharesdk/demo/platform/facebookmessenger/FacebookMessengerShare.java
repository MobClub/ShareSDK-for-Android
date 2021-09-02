package cn.sharesdk.demo.platform.facebookmessenger;

import android.os.Build;
import android.util.Log;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.facebookmessenger.FacebookMessenger;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.instagram.Instagram;
import cn.sharesdk.system.text.ShortMessage;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_TEXT;
import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;

/**
 * Created by yjin on 2017/6/22.
 */

public class FacebookMessengerShare {
	private PlatformActionListener platformActionListener;

	public FacebookMessengerShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
		DemoUtils.isValidClient("com.facebook.orca");
	}

	public void shareWebPage(){
		Platform platform = ShareSDK.getPlatform(FacebookMessenger.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(LINK_TEXT);
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setUrl(LINK_URL);
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(FacebookMessenger.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
//		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageArray(ResourcesManager.getInstace(MobSDK.getContext()).randomPic());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareWebPage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(FacebookMessenger.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(FacebookMessenger.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageArray(ResourcesManager.getInstace(MobSDK.getContext()).randomPic());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
	public void shareText(){
		Platform platform = ShareSDK.getPlatform(FacebookMessenger.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText("http://ahmn.t4m.cn/ziqMNf " +"loopShare 重磅上线！一键实现分享闭环！错过它，就错过了全世界~  ahmn.t4m.cn/ziqMNf 点击立即使用");
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(FacebookMessenger.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
}
