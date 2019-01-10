package cn.sharesdk.demo.platform.reddit;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.reddit.Reddit;

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
        sp.setTitle("If excuse , say sorry for you,Test shareText please ignore, ===> 2018-09-14-1430");
        sp.setText("I only test my code, please ignore this  ====> 2018.09.13.1904");
        sp.setSubreddit("AndroidWear");
        //sp.setUrl("https://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack");
        reddit.share(sp);
    }

    /** 即时分享链接，setText也不能为空，虽然text并不会显示出来 **/
    public void shareUrl(){
        Platform reddit = ShareSDK.getPlatform(Reddit.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("URL shareText please ignore,If excuse , say sorry for you ===> 2018.09.14.1228");
        sp.setText("How to test my code, I only test my code, please ignore this  ====> 2018.09.14.1228");
        sp.setSubreddit("dotnet");
        sp.setUrl("https://baike.baidu.com/item/SD%E5%8D%A1/122767?fr=aladdin");
        reddit.share(sp);
    }

}
