package cn.sharesdk.demo.platform.watermelonvideo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.watermelonvideo.Watermelonvideo;

public class WatermelonvideoShare {

	public static final int WATERM_VIDEO = 0x1561;

	private PlatformActionListener platformActionListener;

	public WatermelonvideoShare(PlatformActionListener listener) {
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
				activity.startActivityForResult(intent, WATERM_VIDEO);

			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareVideo(Activity activity, Uri uri) {
		Platform platform = ShareSDK.getPlatform(Watermelonvideo.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setVideoUri(uri);
		shareParams.setShareType(Platform.SHARE_VIDEO);
		shareParams.setActivity(activity);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}


}
