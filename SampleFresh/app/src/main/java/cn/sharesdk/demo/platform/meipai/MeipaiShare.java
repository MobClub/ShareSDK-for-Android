package cn.sharesdk.demo.platform.meipai;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.mob.MobSDK;

import androidx.appcompat.app.AlertDialog;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.littleredbook.Littleredbook;
import cn.sharesdk.meipai.Meipai;

/**
 * Created by yjin on 2017/6/22.
 */

public class MeipaiShare {

	public static final int MEIPAI_IMAGE = 0x9739;
	public static final int MEIPAI_VIDEO = 0x2847;

	private PlatformActionListener platformActionListener;

	public MeipaiShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
		DemoUtils.isValidClient("com.meitu.meipaimv");
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
				activity.startActivityForResult(intent, MEIPAI_VIDEO);

			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareVideo(Activity activity, String videoPath) {
		Platform platform = ShareSDK.getPlatform(Meipai.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setFilePath(videoPath);
		shareParams.setShareType(Platform.SHARE_VIDEO);
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
				activity.startActivityForResult(intent, MEIPAI_IMAGE);
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareImagePath(Activity activity, String imagePath) {
		Platform platform = ShareSDK.getPlatform(Meipai.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImagePath(imagePath);
		shareParams.setShareType(Platform.SHARE_IMAGE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

}
