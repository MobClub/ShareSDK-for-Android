package cn.sharesdk.demo.platform.tencent.qzone;

import android.app.Activity;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_TEXT;
import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;

/**
 * Created by yjin on 2017/6/22.
 */

public class QQZoneShare {
	private PlatformActionListener platformActionListener;

	public QQZoneShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
		String [] pks = {"com.tencent.mobileqq","com.tencent.mobileqqi","com.tencent.qqlite","com.tencent.minihd.qq","com.tencent.tim"};
		DemoUtils.isValidClient(pks);
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(QZone.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText("Share SDK QQ空间文字分享");
		/*shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
		shareParams.setSite(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setSiteUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());*/
		platform.setPlatformActionListener(platformActionListener);
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.share(shareParams);
	}

	public void shareWebPager(){
		Platform platform = ShareSDK.getPlatform(QZone.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(LINK_TEXT);
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(LINK_URL);
		shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		platform.setPlatformActionListener(platformActionListener);
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(QZone.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImageArray(ResourcesManager.getInstace(MobSDK.getContext()).randomPic());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setShareTencentWeibo(false);
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(QZone.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(QZone.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareWebPager(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(QZone.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(QZone.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareVideo(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(QZone.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareQQMiniProgram(Activity activity) {
		Platform platform = ShareSDK.getPlatform(QZone.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText("QQ小程序");
		shareParams.setTitle("QQ互联");
		shareParams.setTitleUrl("http://www.qq.com/");
		shareParams.setImageUrl("http://www.3wyu.com/wp-content/uploads/6e0eaf15gy1fvr5tnm2dfj20f108gtad.jpg");
		shareParams.setShareType(Platform.QQ_MINI_PROGRAM);
		shareParams.setQQMiniProgramAppid("1108318575");
		shareParams.setQQMiniProgramPath("page/share/index.html?share_name=QQ%E9%9F%B3%E4%B9%90&share_key=5aIqFGg&from=disk");
		shareParams.setQQMiniProgramType("");
		ShareSDK.setActivity(activity);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

}
