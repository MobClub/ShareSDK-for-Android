package cn.sharesdk.demo.platform.tumblr;

import android.text.TextUtils;
import android.util.Log;

import com.mob.MobSDK;
import com.mob.tools.network.NetworkHelper;

import java.util.HashMap;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.ThreadPoolUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tumblr.Tumblr;

/**
 * Created by yjin on 2017/6/22.
 */

public class TumblrShare {
	private PlatformActionListener platformActionListener;

	public TumblrShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(Tumblr.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(Tumblr.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Tumblr.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Tumblr.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
	public void shareWebPager() {
		Platform platform = ShareSDK.getPlatform(Tumblr.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setAuthor("author...");
		shareParams.setQuote("quote...");
		shareParams.setImageUrl(ResourcesManager.PIC1);
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.share(shareParams);
	}
	public void shareMusic() {
		ThreadPoolUtils.execute(new Runnable() {
			@Override
			public void run() {
				String musicPath = null;
				try {
					musicPath = new NetworkHelper().downloadCache(MobSDK.getContext(), ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl(), "music", true, null);
				} catch (Throwable throwable) {
					Log.e("mob","download Music e="+throwable.getMessage());
				}
				Platform platform = ShareSDK.getPlatform(Tumblr.NAME);
				Platform.ShareParams shareParams = new Platform.ShareParams();
				shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
				if (!TextUtils.isEmpty(musicPath)) {
					shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getMusicPath());
				}else {
					shareParams.setMusicUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
				}
				shareParams.setShareType(Platform.SHARE_MUSIC);
				platform.setPlatformActionListener(platformActionListener);
				platform.share(shareParams);
			}
		});

	}
	public void shareVideo() {
		Platform platform = ShareSDK.getPlatform(Tumblr.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setTitle("我喜欢的视频");
		shareParams.setUrl("https://www.youtube.com/watch?v=T6AsbTCejmg");
//		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

}
