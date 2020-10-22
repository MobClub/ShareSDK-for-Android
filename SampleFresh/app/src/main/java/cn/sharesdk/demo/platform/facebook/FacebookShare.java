package cn.sharesdk.demo.platform.facebook;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.mob.MobSDK;
import com.mob.tools.utils.BitmapHelper;

import androidx.appcompat.app.AlertDialog;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_TEXT;
import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;

/**
 * Created by yjin on 2017/6/22.
 */

public class FacebookShare {
	public static final int FACEBOOK_VIDEO = 0X0520;

	private PlatformActionListener platformActionListener;

	public FacebookShare(PlatformActionListener mListener) {
		this.platformActionListener = mListener;
	}

	public void shareImage() {
		try {
			final Platform platform = ShareSDK.getPlatform(Facebook.NAME);
			if (platform.isClientValid()) {
				//使用原生SDK分享Facebook
				Thread thread = new Thread() {
					@Override
					public void run() {
						super.run();
						try {
							String path = BitmapHelper.downloadBitmap(MobSDK.getContext(), "http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
							Log.d("ShareSDK", "path: " + path);

							Platform.ShareParams shareParams = new Platform.ShareParams();
							shareParams.setImagePath(path);
							shareParams.setHashtag("Test Share Image HashTag");
							shareParams.setShareType(Platform.SHARE_IMAGE);
							platform.setPlatformActionListener(platformActionListener);
							platform.share(shareParams);
						} catch (Throwable t) {
							Log.d("ShareSDK", "catch:  " + t);
						}
					}
				};
				thread.start();

			} else {
				Platform.ShareParams shareParams = new Platform.ShareParams();
				shareParams.setText("elseelsetestesdfsdttwetwe");
				shareParams.setUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
				platform.setPlatformActionListener(platformActionListener);
				platform.share(shareParams);
			}
		} catch (Throwable t) {
			Log.e("ShareSDK", "FacebookShare shareImage catch: " + t);
			Toast.makeText(MobSDK.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public void shareVideo(Activity activity) {
		openVideo(activity);
	}

	private void openVideo(final Activity activity) {
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage("请选择视频").setPositiveButton("视频", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Intent.ACTION_PICK);
					intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
					activity.startActivityForResult(intent, FACEBOOK_VIDEO);

				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		} catch (Throwable t) {
			Log.e("QQQ", " Facebook Open video catch " + t);
		}
	}

	public void shareFacebookVideo(Uri videoUri) {
		try {
			Platform platform = ShareSDK.getPlatform(Facebook.NAME);
			if (platform.isClientValid()) {
				Platform.ShareParams shareParams = new Platform.ShareParams();
				shareParams.setVideoUri(videoUri);
				shareParams.setHashtag("Test share HashTag for video");
				shareParams.setShareType(Platform.SHARE_VIDEO);
				platform.setPlatformActionListener(platformActionListener);
				platform.share(shareParams);
			} else {
				Toast.makeText(MobSDK.getContext(),
						" Web Share not support share video", Toast.LENGTH_LONG).show();
			}
		} catch (Throwable t) {
			Log.e("ShareSDK", "shareFacebookVideo catch: " + t);
			Toast.makeText(MobSDK.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

	public void shareWebPage() {
		try {
			Platform platform = ShareSDK.getPlatform(Facebook.NAME);
			Platform.ShareParams shareParams = new Platform.ShareParams();
			shareParams.setUrl("https://developers.facebook.com");
			shareParams.setShareType(Platform.SHARE_WEBPAGE);
			shareParams.setQuote(LINK_TEXT);
			shareParams.setHashtag("测试话题分享");
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		} catch (Throwable t) {
			Log.e("ShareSDK", "shareWebPage catch: " + t);
			Toast.makeText(MobSDK.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

}
