package cn.sharesdk.demo.platform.sohu.suishenkan;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;



public class SohuSuishenkanShare {
	private PlatformActionListener platformActionListener;

	public SohuSuishenkanShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform("SohuSuishenkan");
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform("SohuSuishenkan");
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
