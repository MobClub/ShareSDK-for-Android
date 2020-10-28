package cn.sharesdk.demo.platform.littleredbook;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.littleredbook.Littleredbook;

public class LittleredbookShare {

	public static final int LITTLEREDBOOK_IMAGE = 0x1594;
	public static final int LITTLEREDBOOK_VIDEO = 0x1595;

	private PlatformActionListener platformActionListener;

	public LittleredbookShare(PlatformActionListener listener) {
		this.platformActionListener = listener;
	}

	public void shareVideo(Activity activity) {
		openVideo(activity);
	}

	private void openVideo(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("请选择视频").setPositiveButton("视频", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
				activity.startActivityForResult(intent, LITTLEREDBOOK_VIDEO);

			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareVideo(Activity activity, Uri uri) {
		Platform platform = ShareSDK.getPlatform(Littleredbook.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setVideoUri(uri);
		shareParams.setShareType(Platform.SHARE_VIDEO);
		shareParams.setActivity(activity);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}




	public void shareImage(Activity activity) {
		openImage(activity);
	}

	private void openImage(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("请选择图片").setPositiveButton("图片", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				activity.startActivityForResult(intent, LITTLEREDBOOK_IMAGE);
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareImagePath(Activity activity, String imagePath) {
		Platform douyin = ShareSDK.getPlatform(Littleredbook.NAME);
		Platform.ShareParams sp = new Platform.ShareParams();
		sp.setImagePath(imagePath);
		sp.setShareType(Platform.SHARE_IMAGE);
		sp.setActivity(activity);
		douyin.setPlatformActionListener(platformActionListener);
		douyin.share(sp);
	}

}
