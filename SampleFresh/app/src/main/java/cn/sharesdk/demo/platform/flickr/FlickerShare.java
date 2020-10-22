package cn.sharesdk.demo.platform.flickr;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.flickr.Flickr;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by yjin on 2017/6/22.
 */

public class FlickerShare {
	private PlatformActionListener platformActionListener;

	public FlickerShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(Flickr.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setTags(ResourcesManager.TAGS);
		shareParams.setPublic(ResourcesManager.IS_PUBLIC);
		shareParams.setFriend(ResourcesManager.IS_FRIEND);
		shareParams.setFamily(ResourcesManager.IS_FAMILY);
		shareParams.setSafetyLevel(1);
		shareParams.setContentType(1);
		shareParams.setHidden(1);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Flickr.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setTags(ResourcesManager.TAGS);
		shareParams.setPublic(ResourcesManager.IS_PUBLIC);
		shareParams.setFriend(ResourcesManager.IS_FRIEND);
		shareParams.setFamily(ResourcesManager.IS_FAMILY);
		shareParams.setSafetyLevel(1);
		shareParams.setContentType(1);
		shareParams.setHidden(1);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
