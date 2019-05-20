package cn.sharesdk.demo.platform.douyin;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import com.mob.MobSDK;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.douyin.Douyin;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by xiangli on 2018/12/27.
 */

public class DouyinShare {

    public static final int PHOTO_REQUEST_GALLERY = 10;

    private PlatformActionListener platformActionListener;

    public DouyinShare(PlatformActionListener listener) {
        this.platformActionListener = listener;
    }

    public void shareVideo(Activity activity) {
        Platform platform = ShareSDK.getPlatform(Douyin.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
        shareParams.setShareType(Platform.SHARE_VIDEO);
        shareParams.setActivity(activity);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }

    public void shareImage(Activity activity) {
        openSystemGallery(activity);
    }

    public void shareImagePath(Activity activity, String imagePath) {
        Platform douyin = ShareSDK.getPlatform(Douyin.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setImagePath(imagePath);
        sp.setShareType(Platform.SHARE_IMAGE);
        sp.setActivity(activity);
        douyin.setPlatformActionListener(platformActionListener);
        douyin.share(sp);
    }

    private void openSystemGallery(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("添加相片或视频")
                .setNegativeButton("视频", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("video/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        //startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                        activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                    }
                })
                .setPositiveButton("图片", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        //startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                        activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
