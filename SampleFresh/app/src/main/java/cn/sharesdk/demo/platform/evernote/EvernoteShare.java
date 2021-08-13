package cn.sharesdk.demo.platform.evernote;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.evernote.Evernote;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by yjin on 2017/6/22.
 */

public class EvernoteShare {

	private PlatformActionListener platformActionListener;

	public EvernoteShare(PlatformActionListener mListener) {
		this.platformActionListener = mListener;
	}

	public void shareVideo() {
			Platform platform = ShareSDK.getPlatform(Evernote.NAME);
			Platform.ShareParams shareParams = new Platform.ShareParams();
			shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
			shareParams.setShareType(Platform.SHARE_VIDEO);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
	}

	public void shareText() {
		Platform platform = ShareSDK.getPlatform(Evernote.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage() {
			Platform platform = ShareSDK.getPlatform(Evernote.NAME);
			Platform.ShareParams shareParams = new Platform.ShareParams();
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
			shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setShareType(Platform.SHARE_IMAGE);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
	}

	public void shareVideo(PlatformActionListener mListener) {
		Platform platform = ShareSDK.getPlatform(Evernote.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareTextImage(PlatformActionListener mListener) {
		Platform platform = ShareSDK.getPlatform(Evernote.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setTags(ResourcesManager.TAGS);
		shareParams.setStack(ResourcesManager.STACK);
		shareParams.setNotebook(ResourcesManager.NOTEBOOK);
		shareParams.setPublic(ResourcesManager.IS_PUBLIC);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

}
