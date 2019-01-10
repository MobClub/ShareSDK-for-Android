package cn.sharesdk.demo.platform.douyin;

import com.mob.MobSDK;

import cn.sharesdk.demo.MainActivity;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.douyin.Douyin;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by xiangli on 2018/12/27.
 */

public class DouyinShare {

    private PlatformActionListener platformActionListener;

    public DouyinShare(PlatformActionListener listener) {
        this.platformActionListener = listener;
    }

    public void shareVideo() {
        Platform platform = ShareSDK.getPlatform(Douyin.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
        shareParams.setShareType(Platform.SHARE_VIDEO);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }

}
