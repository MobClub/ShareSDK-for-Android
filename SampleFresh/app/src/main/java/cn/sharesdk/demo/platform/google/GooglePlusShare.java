package cn.sharesdk.demo.platform.google;

import android.widget.Toast;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.google.GooglePlus;

/**
 * Created by yjin on 2017/6/22.
 */

public class GooglePlusShare {
	private PlatformActionListener platformActionListener;

	public GooglePlusShare(PlatformActionListener mListener) {
		this.platformActionListener = mListener;
	}

	public void shareText() {
		Platform platform = ShareSDK.getPlatform(GooglePlus.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage() {
		Platform platform = ShareSDK.getPlatform(GooglePlus.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareVideo() {
		Platform platform = ShareSDK.getPlatform(GooglePlus.NAME);
		if (platform != null && platform.isClientValid()) {
			Platform.ShareParams shareParams = new Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
			shareParams.setShareType(Platform.SHARE_VIDEO);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		} else {
			Toast.makeText(MobSDK.getContext(), "Client is not found or version low.", Toast.LENGTH_SHORT).show();
		}

	}

	public void shareVideo(PlatformActionListener mListener) {
		Platform platform = ShareSDK.getPlatform(GooglePlus.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
