package cn.sharesdk.demo.platform.netease.microblog;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by yjin on 2017/6/22.
 */

public class NetEaseMicroBlog {
	private PlatformActionListener platformActionListener;

	public NetEaseMicroBlog(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform("NetEaseMicroBlog");
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform("NetEaseMicroBlog");
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform("NetEaseMicroBlog");
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform("NetEaseMicroBlog");
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
}
