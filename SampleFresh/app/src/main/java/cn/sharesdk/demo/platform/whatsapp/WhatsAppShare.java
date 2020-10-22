package cn.sharesdk.demo.platform.whatsapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;


import com.mob.MobSDK;

import androidx.appcompat.app.AlertDialog;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.whatsapp.WhatsApp;

/**
 * Created by yjin on 2017/6/22.
 */

public class WhatsAppShare {
	public static final int PHOTO_WHATS_APP = 1033;
	public static final int VIDEO_WHATS_APP = 1034;

	private PlatformActionListener platformActionListener;

	public WhatsAppShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(WhatsApp.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(Activity activity) {
		openSystemGallery(activity);
	}

	/**
	 * 打开图片
	 * **/
	private void openSystemGallery(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("请选择图片").setPositiveButton("图片", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				//startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
				activity.startActivityForResult(intent, PHOTO_WHATS_APP);
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareImage(String imagePath){
		Platform platform = ShareSDK.getPlatform(WhatsApp.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImagePath(imagePath);
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		platform.setPlatformActionListener(platformActionListener);
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.share(shareParams);
	}


	public void shareVideo(Activity activity) {
		openSystemVideo(activity, VIDEO_WHATS_APP);
	}

	/**
	 * 打开相册视频
	 * **/
	private void openSystemVideo(final Activity activity, final int shareType) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("请选择视频").setPositiveButton("视频", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("video/*");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				activity.startActivityForResult(intent, shareType);
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareVideo(String videoPath){
		Platform platform = ShareSDK.getPlatform(WhatsApp.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(videoPath);
		platform.setPlatformActionListener(platformActionListener);
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.share(shareParams);
	}

	public void shareAddress(){
		Platform platform = ShareSDK.getPlatform(WhatsApp.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(ResourcesManager.ADDRESS);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
}
