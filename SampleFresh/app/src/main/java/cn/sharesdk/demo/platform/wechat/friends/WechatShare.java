package cn.sharesdk.demo.platform.wechat.friends;

import android.util.Log;

import com.mob.MobSDK;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;
import static cn.sharesdk.demo.entity.ResourcesManager.IMAGE_TEST_URL;

/**
 * Created by yjin on 2017/6/22.
 */

public class WechatShare {

private PlatformActionListener platformActionListener;

	public WechatShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
		DemoUtils.isValidClient("com.tencent.mm");
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImageUrl(IMAGE_TEST_URL);
		//shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		//Log.e("WWW", "imagePath ===> " + ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareMusic(){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(IMAGE_TEST_URL);
		shareParams.setMusicUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
		shareParams.setShareType(Platform.SHARE_MUSIC);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl("http://www.mob.com/video/ShareSDK.mp4");
		shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareWebpager(){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(LINK_URL);
		shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

//	public void shareApps(){
//		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
//		Platform.ShareParams shareParams = new  Platform.ShareParams();
//		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
//		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
//		shareParams.setShareType(Platform.SHARE_APPS);
//		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
//		shareParams.setExtInfo(ResourcesManager.EXT_INFO);
//		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
//		platform.setPlatformActionListener(platformActionListener);
//		platform.share(shareParams);
//	}

	/**
	 * 不展示分享文件，因为微信有可能废弃这个API
	 * **/
	public void shareFile(){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(IMAGE_TEST_URL);
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_FILE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareEmoji(){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		shareParams.setShareType(Platform.SHARE_EMOJI);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareMiniProgram(){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		shareParams.setShareType(Platform.SHARE_WXMINIPROGRAM);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareMusic(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setMusicUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
		shareParams.setShareType(Platform.SHARE_MUSIC);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareVideo(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl("http://www.mob.com/video/ShareSDK.mp4");
		if (ResourcesManager.getInstace(MobSDK.getContext()).getImagePath() == null) {
			shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		} else {
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		}
		shareParams.setScence(0);
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareWebpager(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

//	public void shareApps(PlatformActionListener mListener){
//		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
//		Platform.ShareParams shareParams = new  Platform.ShareParams();
//		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
//		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
//		shareParams.setShareType(Platform.SHARE_APPS);
//		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
//		shareParams.setExtInfo(ResourcesManager.EXT_INFO);
//		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
//		platform.setPlatformActionListener(mListener);
//		platform.share(shareParams);
//	}

	public void shareFile(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_FILE);
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareEmoji(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setShareType(Platform.SHARE_EMOJI);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareMiniProgram(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setShareType(Platform.SHARE_WXMINIPROGRAM);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
