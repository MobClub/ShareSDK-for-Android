package cn.sharesdk.demo.platform.reddit;

import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.reddit.Reddit;

import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_TEXT;
import static cn.sharesdk.demo.ShareMobLinkActivity.LINK_URL;

/**
 * Created by xiangli on 2018/9/13.
 */

public class RedditShare {
    private PlatformActionListener platformActionListener;

    public RedditShare(PlatformActionListener mListener) {
        this.platformActionListener = mListener;
    }

    /** redit 其中的title不能为空，否则无法正确提交  **/
    public void shareText(){
        Platform reddit = ShareSDK.getPlatform(Reddit.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("haha bu gao su ni , Title, please igonre, thank you................ 2020-03-23-1632");
        sp.setText("that's is ok all everything, thank you............... 2020-03-23-1632");
        sp.setSubreddit("AndroidWear");
        sp.setShareType(Platform.SHARE_TEXT);
        reddit.setPlatformActionListener(platformActionListener);
        reddit.share(sp);
    }

    /** 即时分享链接，setText也不能为空，虽然text并不会显示出来 **/
    public void shareUrl(){
        Platform reddit = ShareSDK.getPlatform(Reddit.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("ok all but i am bu hao le ");
        sp.setText("shi jie hai hao ma ");
        sp.setSubreddit("Coronavirus");
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setUrl("https://www.mob.com/");
        reddit.setPlatformActionListener(platformActionListener);
        reddit.share(sp);
    }
    public void shareImage() {
        Platform platform = ShareSDK.getPlatform(Reddit.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setSubreddit("aaa");
        shareParams.setTitle("分享链接");
        shareParams.setText("分享内容");
        String PIC1="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcambrian-images.cdn.bcebos.com%2Fb5e7f938e8233fdbe343817b5a2610bb_1513843872877.jpeg";
        shareParams.setImageUrl(PIC1);
        shareParams.setShareType(Platform.SHARE_IMAGE);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }
}
