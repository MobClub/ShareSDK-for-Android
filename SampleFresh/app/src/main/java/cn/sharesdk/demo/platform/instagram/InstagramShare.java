package cn.sharesdk.demo.platform.instagram;

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
import cn.sharesdk.instagram.Instagram;

import static cn.sharesdk.demo.entity.ResourcesManager.IMAGE_TEST_URL;

/**
 * Created by yjin on 2017/6/22.
 */

public class InstagramShare {
	public static final int INS_PHOTO = 21;

	private PlatformActionListener platformActionListener;

	public InstagramShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
		DemoUtils.isValidClient("com.instagram.android");
	}

	public void shareText() {
		Platform qqq = ShareSDK.getPlatform(Instagram.NAME);
		Platform.ShareParams sp = new Platform.ShareParams();
		sp.setTitle("title");
		sp.setText("text");
		sp.setShareType(Platform.INSTAGRAM_FRIEND);
		qqq.setPlatformActionListener(platformActionListener);
		qqq.share(sp);
	}

	public void shareTextImage(Activity activity){
		//openSystemGallery(activity);
		Platform platform = ShareSDK.getPlatform(Instagram.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText("Ins share test");
		shareParams.setImageUrl(IMAGE_TEST_URL);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);

	}

	public void shareInsImage(String path) {
		Platform platform = ShareSDK.getPlatform(Instagram.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText("Ins share test");
		//shareParams.setImagePath(path);
		//Log.e("QQQ", " ins选择的图片 " + path);
		shareParams.setImageUrl(IMAGE_TEST_URL);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	private void openSystemGallery(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("请选择图片").setPositiveButton("图片", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				//startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
				activity.startActivityForResult(intent, INS_PHOTO);
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void shareVideo(){
		Platform platform = ShareSDK.getPlatform(Instagram.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		//TODO
		Log.e("WWW", " INS分享视频的路径 " + ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
		shareParams.setShareType(Platform.SHARE_VIDEO);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}
}
