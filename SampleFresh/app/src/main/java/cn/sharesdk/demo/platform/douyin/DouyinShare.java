package cn.sharesdk.demo.platform.douyin;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.mob.MobSDK;

import java.util.List;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.douyin.Douyin;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;


public class DouyinShare {

    public static final int PHOTO_REQUEST_GALLERY = 10;
    public static final int DOUYIN_VIDEO = 11;
    public static final int DOUYIN_IM_IMG = 111;

    private PlatformActionListener platformActionListener;

    public DouyinShare(PlatformActionListener listener) {
        this.platformActionListener = listener;
    }

    public void shareVideo(Activity activity) {
        openSystemGallery(activity, DOUYIN_VIDEO);
    }

    public void shareVideo(Activity activity, String[] videoPath) {
        Platform platform = ShareSDK.getPlatform(Douyin.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setVideoPathArray(videoPath);
        shareParams.setShareType(Platform.SHARE_VIDEO);
        shareParams.setActivity(activity);
        shareParams.setHashtags(new String[]{"tag1","tag2"});
        shareParams.setStack("stack");
        shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }

    public void shareImage(Activity activity) {
        openSystemGallery(activity);
    }

    public void shareImagePath(Activity activity, String imagePath) {
        Platform douyin = ShareSDK.getPlatform(Douyin.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setImageArray(new String[]{imagePath});
        sp.setShareType(Platform.SHARE_IMAGE);
        sp.setActivity(activity);
        sp.setHashtags(new String[]{});
        sp.setTitle("title");
        sp.setHashtag("addtag");
        sp.setTagPosition(1);
        sp.setStack("stack");
        douyin.setPlatformActionListener(platformActionListener);
        douyin.share(sp);
    }


    private void openSystemGallery(final Activity activity, final int shareType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("请选择视频").setPositiveButton("视频", new DialogInterface.OnClickListener() {
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

    private void openSystemGallery(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("请选择图片").setPositiveButton("图片", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void shareMixFile(Activity activity) {
        Platform douyin = ShareSDK.getPlatform(Douyin.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        ResourcesManager instace = ResourcesManager.getInstace(MobSDK.getContext());
        sp.setDYMixFileArray(new String[]{instace.getImagePath(),instace.getFilePath()});
        sp.setActivity(activity);
        sp.setHashtags(new String[]{"tag1","tag2"});
        sp.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
        sp.setHashtag("TestHashtagTitle");
        sp.setStack("mixfile");
        sp.setShareType(Platform.DY_MIXFILE);
        douyin.setPlatformActionListener(platformActionListener);
        douyin.share(sp);
    }

    public void shareImIMG(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("请选择图片").setPositiveButton("图片", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                activity.startActivityForResult(intent, DOUYIN_IM_IMG);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void shareIMIMG(Activity activity,String imgpath){
        Platform douyin = ShareSDK.getPlatform(Douyin.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setImagePath(imgpath);
        sp.setActivity(activity);
        sp.setShareType(Platform.SHARE_DYIM_IMG);
        douyin.setPlatformActionListener(platformActionListener);
        douyin.share(sp);
    }

    public void shareImWEBPAGE(Activity activity) {
        Platform douyin = ShareSDK.getPlatform(Douyin.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
        sp.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
        //链接需要在抖音后台认证
        sp.setUrl("https://www.mob.com");
        sp.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
        sp.setActivity(activity);
        sp.setShareType(Platform.SHARE_DYIM_WEBPAGE);
        douyin.setPlatformActionListener(platformActionListener);
        douyin.share(sp);
    }
}
