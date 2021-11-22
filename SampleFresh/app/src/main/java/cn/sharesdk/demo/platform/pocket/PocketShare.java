package cn.sharesdk.demo.platform.pocket;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.pocket.Pocket;



public class PocketShare {
	private PlatformActionListener platformActionListener;

	public PocketShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareWebPager(){
		Platform platform = ShareSDK.getPlatform(Pocket.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setTags(ResourcesManager.TAGS);
		shareParams.setExtInfo(ResourcesManager.EXT_INFO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareWebPager(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Pocket.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setTags(ResourcesManager.TAGS);
		shareParams.setExtInfo(ResourcesManager.EXT_INFO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
