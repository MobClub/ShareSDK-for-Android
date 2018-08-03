package cn.sharesdk.demo.platform.renren;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.renren.Renren;

/**
 * Created by yjin on 2017/6/22.
 */

public class RenrenShare {
	private PlatformActionListener platformActionListener;

	public RenrenShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(Renren.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setComment(ResourcesManager.getInstace(MobSDK.getContext()).getComment());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(Renren.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setComment(ResourcesManager.getInstace(MobSDK.getContext()).getComment());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareFeed(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Renren.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setComment(ResourcesManager.getInstace(MobSDK.getContext()).getComment());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
		shareParams.setExtInfo(ResourcesManager.EXT_INFO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
