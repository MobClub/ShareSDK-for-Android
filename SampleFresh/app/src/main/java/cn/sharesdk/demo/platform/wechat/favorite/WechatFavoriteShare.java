package cn.sharesdk.demo.platform.wechat.favorite;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.mob.MobSDK;

import androidx.appcompat.app.AlertDialog;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.favorite.WechatFavorite;


public class WechatFavoriteShare {
	private PlatformActionListener platformActionListener;
	public static final int WECHAT_FAV_FILE = 00221;

	public WechatFavoriteShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
		DemoUtils.isValidClient("com.tencent.mm");
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		if (ResourcesManager.getInstace(MobSDK.getContext()).getImagePath() == null) {
			shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		} else {
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		}
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareMusic(){
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		if (ResourcesManager.getInstace(MobSDK.getContext()).getImagePath() == null) {
			shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		} else {
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		}
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
		shareParams.setShareType(Platform.SHARE_MUSIC);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	/**
	 * url 视频连接
	 */
	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl("https://cdn-static-oss.mob.com/video/ShareSDK.mp4");
		if (ResourcesManager.getInstace(MobSDK.getContext()).getImagePath() == null) {
			shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
		} else {
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		}
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}



	public void shareWebpager(){
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareFile(Activity activity){
		openSystemGallery(activity, WECHAT_FAV_FILE);
	}

	public void shareFile(String videoPath){
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(videoPath);
		Log.e("WWW", " 收藏文件路径 " + ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_FILE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	private void openSystemGallery(final Activity activity, final int shareType) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("请选择图片").setPositiveButton("图片", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				activity.startActivityForResult(intent, shareType);
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_TEXT);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
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
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
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
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareWebpager(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
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

	public void shareFile(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
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
}
