package cn.sharesdk.demo.platform.mingdao;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.mingdao.Mingdao;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;


public class MingdaoShare {
	private PlatformActionListener platformActionListener;

	public MingdaoShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(Mingdao.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setUrl(LINK_URL);
		shareParams.setText("shareText text text text hahahah");
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(Mingdao.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText("shareText only test no mult and more");
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImageText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Mingdao.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
