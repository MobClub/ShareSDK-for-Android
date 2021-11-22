package cn.sharesdk.demo.platform.tiktok;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tiktok.Tiktok;

public class TiktokShare {
	public static final int KT_IMAGE = 0x1252;
	public static final int KT_VIDEO = 0x2961;

	private PlatformActionListener platformActionListener;


	public TiktokShare(PlatformActionListener listener) {
		this.platformActionListener = listener;
	}

	public void shareVideo(Activity activity) {
		openAlbumVideo(activity);
	}

	public void shareVideo(Activity activity, String[] videoPath) {
		Platform platform = ShareSDK.getPlatform(Tiktok.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setVideoPathArray(videoPath);
		shareParams.setHashtags(new String[]{"a","b"});
		shareParams.setShareType(Platform.SHARE_VIDEO);
		shareParams.setActivity(activity);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
	public void shareImage(Activity activity) {
		openAlbumImage(activity);
	}

	public void shareImagePath(Activity activity, String[] imagePath) {
		Platform tiktok = ShareSDK.getPlatform(Tiktok.NAME);
		Platform.ShareParams sp = new Platform.ShareParams();
		sp.setImageArray(imagePath);
		sp.setHashtags(new String[]{"a","b"});
		sp.setShareType(Platform.SHARE_IMAGE);
		sp.setActivity(activity);
		tiktok.setPlatformActionListener(platformActionListener);
		tiktok.share(sp);
	}

	/**
	 * 打开图片相册
	 **/
	private void openAlbumImage(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("请选择图片").setPositiveButton("图片", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				activity.startActivityForResult(intent, KT_IMAGE);
			}
		}).show();
	}

	/**
	 * 打开视频相册
	 **/
	private void openAlbumVideo(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("请选择视频").setPositiveButton("视频", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("video/*");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				activity.startActivityForResult(intent, KT_VIDEO);
			}
		}).show();
	}

}
