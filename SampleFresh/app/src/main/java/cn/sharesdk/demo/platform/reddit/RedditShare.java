package cn.sharesdk.demo.platform.reddit;

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
        sp.setUrl("https://s.weibo.com/weibo?q=%23%E6%84%8F%E5%A4%A7%E5%88%A9%E8%BF%918000%E5%90%8D%E5%8C%BB%E7%94%9F%E7%94%B3%E8%AF%B7%E6%88%98%E7%96%AB%23");
        reddit.setPlatformActionListener(platformActionListener);
        reddit.share(sp);
    }

}
