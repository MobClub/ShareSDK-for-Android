package cn.sharesdk.demo.platform.kuaishou;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;

import com.mob.MobSDK;

import cn.sharesdk.demo.R;
import cn.sharesdk.demo.UriUtil;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.ThreadPoolUtils;
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

	public void shareWebPage(final Activity activity) {
		final Platform platform = ShareSDK.getPlatform(Kuaishou.NAME);
		if (null == platform || null == activity) {
			return;
		}
		ThreadPoolUtils.execute(new Runnable() {
			@Override
			public void run() {
				Platform.ShareParams shareParams = new  Platform.ShareParams();
				shareParams.setText("text");//length<=1024
				shareParams.setTitle("title");//length<=512
				shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
				try {
					Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
					shareParams.setImageData(bitmap);//length<=65536
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
				shareParams.setActivity(activity);
				shareParams.setShareType(Platform.SHARE_WEBPAGE);
				platform.setPlatformActionListener(platformActionListener);
				platform.share(shareParams);
			}
		});
	}
}
