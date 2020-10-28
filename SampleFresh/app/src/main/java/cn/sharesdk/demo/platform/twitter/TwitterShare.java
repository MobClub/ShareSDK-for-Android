package cn.sharesdk.demo.platform.twitter;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mob.MobSDK;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.twitter.Twitter;

import static cn.sharesdk.demo.entity.ResourcesManager.IMAGE_TEST_URL;
import static cn.sharesdk.demo.entity.ResourcesManager.IMAGE_TEST_URL_TWO;

/**
 * Created by yjin on 2017/6/22.
 */

public class TwitterShare {
    private PlatformActionListener platformActionListener;
    public static final int TWITTER_VIDEO = 10009;
    private final String toast = "Please install twitter client";

    public TwitterShare(PlatformActionListener mListener) {
        this.platformActionListener = mListener;
    }

    public void shareText() {
        Platform platform = ShareSDK.getPlatform(Twitter.NAME);
        boolean install = platform.isClientValid();
        if (install) {
            Platform.ShareParams shareParams = new Platform.ShareParams();
            shareParams.setText("only test good shareText");
            platform.setPlatformActionListener(platformActionListener);
            platform.share(shareParams);
        } else {
            Toast.makeText(MobSDK.getContext(), toast, Toast.LENGTH_LONG).show();
        }
    }

    public void shareImage() {
        Platform platform = ShareSDK.getPlatform(Twitter.NAME);
        boolean install = platform.isClientValid();
        if (install) {
            Platform.ShareParams shareParams = new Platform.ShareParams();
            shareParams.setText("only test good shareImage");
            //shareParams.setImageArray(ResourcesManager.getInstace(MobSDK.getContext()).randomPic());
            //shareParams.setImageUrl(IMAGE_TEST_URL);
            String[] arr = new String[]{IMAGE_TEST_URL, IMAGE_TEST_URL_TWO};
            shareParams.setImageArray(arr);
            platform.setPlatformActionListener(platformActionListener);
            platform.share(shareParams);
        } else {
            Toast.makeText(MobSDK.getContext(), toast, Toast.LENGTH_LONG).show();
        }
    }

    public void shareVideo() {
        Platform platform = ShareSDK.getPlatform(Twitter.NAME);
        boolean install = platform.isClientValid();
        if (install) {
            Platform.ShareParams shareParams = new Platform.ShareParams();
            shareParams.setText("only test good shareVideo");
            String filePath = ResourcesManager.getInstace(MobSDK.getContext()).getFilePath();
            if (!TextUtils.isEmpty(filePath)) {
                shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
                Log.e("qqq", " twitter=====> " + ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
                shareParams.setShareType(Platform.SHARE_VIDEO);
                platform.setPlatformActionListener(platformActionListener);
                platform.share(shareParams);
            } else {
                Toast.makeText(MobSDK.getContext(), "网络不好，视频在DEMO初始化的时候没有下载成功，请重新启动应用下载。", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MobSDK.getContext(), toast, Toast.LENGTH_LONG).show();
        }
    }

    public void shareText(PlatformActionListener mListener) {
        Platform platform = ShareSDK.getPlatform(Twitter.NAME);
        boolean install = platform.isClientValid();
        if (install) {
            Platform.ShareParams shareParams = new Platform.ShareParams();
            //shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
            shareParams.setText("only test good shareText shareText");
            platform.setPlatformActionListener(mListener);
            platform.share(shareParams);
        } else {
            Toast.makeText(MobSDK.getContext(), toast, Toast.LENGTH_LONG).show();
        }
    }

    public void shareImage(PlatformActionListener mListener) {
        Platform platform = ShareSDK.getPlatform(Twitter.NAME);
        boolean install = platform.isClientValid();
        if (install) {
            Platform.ShareParams shareParams = new Platform.ShareParams();
            //shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
            shareParams.setText("only test good shareImage shareImage");
            shareParams.setImageArray(ResourcesManager.getInstace(MobSDK.getContext()).randomPic());
            shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
            shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
            platform.setPlatformActionListener(mListener);
            platform.share(shareParams);
        } else {
            Toast.makeText(MobSDK.getContext(), toast, Toast.LENGTH_LONG).show();
        }
    }

//	public void shareVideo(PlatformActionListener mListener){
//		Platform platform = ShareSDK.getPlatform(Twitter.NAME);
//		Platform.ShareParams shareParams = new  Platform.ShareParams();
//		shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
//		shareParams.setShareType(Platform.SHARE_VIDEO);
//		platform.setPlatformActionListener(mListener);
//		platform.share(shareParams);
//	}


}
