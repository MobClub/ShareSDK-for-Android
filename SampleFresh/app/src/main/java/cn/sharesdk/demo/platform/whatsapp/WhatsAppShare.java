package cn.sharesdk.demo.platform.whatsapp;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.whatsapp.WhatsApp;

/**
 * Created by yjin on 2017/6/22.
 */

public class WhatsAppShare {
	private PlatformActionListener platformActionListener;

	public WhatsAppShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(WhatsApp.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(WhatsApp.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		platform.setPlatformActionListener(platformActionListener);
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.share(shareParams);
	}

	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(WhatsApp.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		platform.setPlatformActionListener(platformActionListener);
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.share(shareParams);
	}

	public void shareAddress(){
		Platform platform = ShareSDK.getPlatform(WhatsApp.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.ADDRESS);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
}
