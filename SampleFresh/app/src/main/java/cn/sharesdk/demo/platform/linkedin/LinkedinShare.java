package cn.sharesdk.demo.platform.linkedin;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.linkedin.LinkedIn;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;


public class LinkedinShare {
	private PlatformActionListener platformActionListener;

	public LinkedinShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(LinkedIn.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setComment(ResourcesManager.getInstace(MobSDK.getContext()).getComment());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareWebPage() {
		Platform platform = ShareSDK.getPlatform(LinkedIn.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setTitle("asd title can not repeat");
		shareParams.setText("assf text str can not repeat");
		shareParams.setLinkedinDescription("asdg web descri can not repeat ");
		shareParams.setUrl(LINK_URL);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(LinkedIn.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setComment(ResourcesManager.getInstace(MobSDK.getContext()).getComment());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

}
