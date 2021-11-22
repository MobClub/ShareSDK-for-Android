package cn.sharesdk.demo.platform.oasis;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.oasis.Oasis;


public class OasisShare {

    public static final int OASIS_PHOTO_REQUEST_GALLERY = 13;
    public static final int OASIS_PHOTO_REQUEST_GALLERY_NET = 18;

    public static final int OASIS_SHARE_VIDEO = 17;

    private PlatformActionListener platformActionListener;

    public OasisShare(PlatformActionListener listener) {
        this.platformActionListener = listener;
    }

    public void shareListUri(List<Uri> uriList){
        Platform platform = ShareSDK.getPlatform(Oasis.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_IMAGE);
        sp.setTitle("测试sharesdk");
        sp.setComment("分享到新浪绿洲");
        Log.d("AndyOn", "uriList=="+uriList.toString());

        sp.setImageUriList(uriList);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(sp);
    }

    public void shareListPath(List<String> pathList){
        Platform platform = ShareSDK.getPlatform(Oasis.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_IMAGE);
        sp.setTitle("测试sharesdk");
        sp.setComment("分享到新浪绿洲");
        sp.setImageUrlList(pathList);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(sp);
    }

    public void shareVideo(Uri path){
        Platform platform = ShareSDK.getPlatform(Oasis.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_IMAGE);
        sp.setTitle("测试sharesdk");
        sp.setComment("分享到新浪绿洲");
        sp.setVideoUriOasis(path);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(sp);
    }

    private void openSystemGallery(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("请选择图片").setPositiveButton("图片", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                activity.startActivityForResult(intent, OASIS_PHOTO_REQUEST_GALLERY);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showDialogSelectImg(final Activity activity){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setMessage("添加相片")
                .setNegativeButton("网络图片的本地绝对路径", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        activity.startActivityForResult(intent, OASIS_PHOTO_REQUEST_GALLERY_NET);
                    }
                })
                .setPositiveButton("本地图片", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        activity.startActivityForResult(intent, OASIS_PHOTO_REQUEST_GALLERY);
                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void shareVideo(final Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("请选择视频").setPositiveButton("视频", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                activity.startActivityForResult(intent, OASIS_SHARE_VIDEO);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void shareImagePath(Activity activity, String imagePath) {
        Platform oasis = ShareSDK.getPlatform(Oasis.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setImagePath(imagePath);
        sp.setShareType(Platform.SHARE_IMAGE);
        sp.setActivity(activity);
        oasis.setPlatformActionListener(platformActionListener);
        oasis.share(sp);
    }

}
