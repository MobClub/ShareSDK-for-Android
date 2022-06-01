package cn.sharesdk.demo.platform.dropbox;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.dropbox.Dropbox;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;


public class DropboxShare {
	private PlatformActionListener platformActionListener;

	public DropboxShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(Dropbox.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareFile(){
		Platform platform = ShareSDK.getPlatform(Dropbox.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(Dropbox.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Dropbox.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareFile(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Dropbox.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
	public void shareVideo(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Dropbox.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
