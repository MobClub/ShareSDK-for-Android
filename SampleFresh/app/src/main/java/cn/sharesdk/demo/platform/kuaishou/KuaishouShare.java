package cn.sharesdk.demo.platform.kuaishou;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.mob.MobSDK;

import androidx.appcompat.app.AlertDialog;
import cn.sharesdk.demo.UriUtil;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.kuaishou.Kuaishou;

public class KuaishouShare {

	public static final int KUAISHOU_IMAGE = 0x1592;
	public static final int KUAISHOU_VIDEO = 0x1593;

	private PlatformActionListener platformActionListener;

	public KuaishouShare(PlatformActionListener listener) {
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
				activity.startActivityForResult(intent, KUAISHOU_VIDEO);

			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareVideo(Activity activity, Uri uri) {
		Platform platform = ShareSDK.getPlatform(Kuaishou.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		//shareParams.setVideoUri(uri);
		shareParams.setFilePath(UriUtil.convertUriToPath(MobSDK.getContext(), uri));
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
				activity.startActivityForResult(intent, KUAISHOU_IMAGE);
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareImagePath(Activity activity, String imagePath) {
		Platform douyin = ShareSDK.getPlatform(Kuaishou.NAME);
		Platform.ShareParams sp = new Platform.ShareParams();
		sp.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
		sp.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		sp.setImagePath(imagePath);
		sp.setShareType(Platform.SHARE_IMAGE);
		sp.setActivity(activity);
		douyin.setPlatformActionListener(platformActionListener);
		douyin.share(sp);
	}

}
