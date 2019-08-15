package cn.sharesdk.demo.platform.wework;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wework.Wework;

/**
 * Created by xiangli on 2019/7/16.
 */

public class WeworkShare {

    public static final int WEWORK_SHARE_IMAGE = 6001;
    public static final int WEWORK_SHARE_FILE = 6002;
    public static final int WEWORK_SHARE_VIDEO = 6003;


    private PlatformActionListener platformActionListener;

    public WeworkShare(PlatformActionListener mListener) {
        this.platformActionListener = mListener;
    }

    /** 企业微信文本 **/
    public void shareText() {
        Platform platform = ShareSDK.getPlatform(Wework.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("TESTESTETS");
        sp.setShareType(Platform.SHARE_TEXT);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(sp);
    }



    /** 企业微信图片 **/
    public void shareImage(String path) {
        Platform platform = ShareSDK.getPlatform(Wework.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("TESTESTETS");
        sp.setImagePath(path);
        sp.setShareType(Platform.SHARE_IMAGE);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(sp);
    }

    public void shareImage(Activity activity) {
        openSystemGallery(activity, WEWORK_SHARE_IMAGE);
    }



    /**
     * 企业微信文件分享
     * **/
    public void shareFile(String path) {
        Platform platform = ShareSDK.getPlatform(Wework.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("文件分享");
        sp.setFilePath(path);
        sp.setShareType(Platform.SHARE_FILE);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(sp);
    }

    public void shareFile(Activity activity) {
        openSystemGallery(activity, WEWORK_SHARE_FILE);
    }



    /**
     * 企业微信视频分享
     * **/
    public void shareVideo(String path) {
        Platform platform = ShareSDK.getPlatform(Wework.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("视频test");
        sp.setFilePath(path);
        sp.setShareType(Platform.SHARE_VIDEO);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(sp);
    }

    public void shareVideo(Activity activity) {
        openSystemGallery(activity, WEWORK_SHARE_VIDEO);
    }




    /**
     * 企业微信网页分享
     *
     * **/
    public void shareWebPage() {
        Platform platform = ShareSDK.getPlatform(Wework.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setImageUrl("www.cctv.com");
        sp.setUrl("http://www.gov.cn/");
        sp.setTitle("webPage Title test");
        sp.setText("description");
        sp.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(sp);
    }

    private void openSystemGallery(final Activity activity, final int shareType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("请根据分享的类型选择对应的分享内容")
                .setNegativeButton("图片", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        activity.startActivityForResult(intent, shareType);
                    }
                });
        builder.setMessage("请根据分享的类型选择对应的分享内容")
                .setPositiveButton("视频", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("video/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        activity.startActivityForResult(intent, shareType);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
