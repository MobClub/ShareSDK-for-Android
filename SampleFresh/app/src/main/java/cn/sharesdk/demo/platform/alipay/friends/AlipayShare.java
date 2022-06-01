package cn.sharesdk.demo.platform.alipay.friends;

import com.mob.MobSDK;

import cn.sharesdk.alipay.friends.Alipay;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_TEXT;
import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;


public class AlipayShare {

	private PlatformActionListener platformActionListener;

	public AlipayShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
		DemoUtils.isValidClient("com.eg.android.AlipayGphone");
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(Alipay.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(Alipay.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareWebPage(){
		Platform platform = ShareSDK.getPlatform(Alipay.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(LINK_TEXT);
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(LINK_URL);
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Alipay.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Alipay.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareWebPage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Alipay.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
