package cn.sharesdk.demo.platform.instapaper;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.instapaper.Instapaper;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_TEXT;
import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;

/**
 * Created by yjin on 2017/6/22.
 */

public class InstapaperShare {
	private PlatformActionListener platformActionListener;

	public InstapaperShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}
	public void shareTextUrl(){
		Platform platform = ShareSDK.getPlatform(Instapaper.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(LINK_TEXT);
		shareParams.setUrl(LINK_URL);
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareTextUrl(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Instapaper.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

}
