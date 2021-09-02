package cn.sharesdk.demo.manager.share;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.HashMap;

import cn.sharesdk.demo.activitys.SharePlatformTypeActivity;
import cn.sharesdk.demo.platform.PlatformShareManager;
import cn.sharesdk.demo.platform.wechat.friends.WechatShare;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import static cn.sharesdk.demo.utils.CommomDialog.dialog;

/**
 * Created by yjin on 2017/5/17.
 * 分享的操作类，各个平台的分享代码写在这里。
 * 这里可以直接拷贝代码，粘贴到合适的位置。
 */

public class ShareTypeManager {
    private SharePlatformTypeActivity context = null;
    private Platform platform = null;
    private MyPlatformActionListener myPlatformActionListener = null;
    private Context contextThis;

    public ShareTypeManager(SharePlatformTypeActivity context, Platform platform) {
        this.context = context;
        this.platform = platform;
        myPlatformActionListener = new MyPlatformActionListener();
        this.contextThis = context;
    }

    public void shareShow(int platform, Activity activity) {
        switch (platform) {
            case Platform.SHARE_TEXT: {
                shareText();
            } break;
            case Platform.SHARE_VIDEO: {
                shareVideo(activity);
            } break;
            case Platform.SHARE_IMAGE: {
                shareImage(activity);
            } break;
            case Platform.SHARE_APPS: {
                shareApp();
            } break;
            case Platform.SHARE_FILE: {
                shareFiles(activity);
            } break;
            case Platform.SHARE_EMOJI: {
                shareEmoji();
            } break;
            case Platform.SHARE_WXMINIPROGRAM: {
                shareMiniProgram();
            } break;
            case Platform.SHARE_WEBPAGE: {
                shareWebPage(activity);
            } break;
            case Platform.SHARE_MUSIC: {
                shareMusic();
            } break;

            case Platform.SHARE_LINKCARD: {
                shareLinkCard();
            } break;

            case Platform.QQ_MINI_PROGRAM: {
                shareQQMiniProgram(activity);
            } break;

        }
    }

    public void shareText() {
        PlatformShareManager platformShareManager = new PlatformShareManager();
        platformShareManager.setPlatformActionListener(myPlatformActionListener);
        platformShareManager.shareText(platform.getName());
    }

    public void shareVideo(Activity activity) {
        PlatformShareManager platformShareManager = new PlatformShareManager();
        platformShareManager.setPlatformActionListener(myPlatformActionListener);
        platformShareManager.shareVideo(platform.getName(), activity);
    }

    public void shareImage(Activity activity) {
        PlatformShareManager platformShareManager = new PlatformShareManager();
        platformShareManager.setPlatformActionListener(myPlatformActionListener);
        platformShareManager.shareImage(platform.getName(), activity);
    }

    public void shareApp() {
        PlatformShareManager platformShareManager = new PlatformShareManager();
        platformShareManager.setPlatformActionListener(myPlatformActionListener);
        platformShareManager.shareApp(platform.getName());
    }

    public void shareFiles(Activity activity) {
        PlatformShareManager platformShareManager = new PlatformShareManager();
        platformShareManager.setPlatformActionListener(myPlatformActionListener);
        platformShareManager.shareFile(platform.getName(), activity);
    }

    public void shareEmoji() {
        WechatShare wechatShare = new WechatShare(myPlatformActionListener);
        wechatShare.shareEmoji();
    }

    public void shareMiniProgram() {
        WechatShare wechatShare = new WechatShare(myPlatformActionListener);
        wechatShare.shareMiniProgram();
    }

    public void shareWebPage(Activity activity) {
        PlatformShareManager platformShareManager = new PlatformShareManager();
        platformShareManager.setPlatformActionListener(myPlatformActionListener);
        platformShareManager.shareWebPager(platform.getName(), activity);
    }

    public void shareMusic() {
        PlatformShareManager platformShareManager = new PlatformShareManager();
        platformShareManager.setPlatformActionListener(myPlatformActionListener);
        platformShareManager.shareMusic(platform.getName());
    }

    public void shareLinkCard() {
        PlatformShareManager platformShareManager = new PlatformShareManager();
        platformShareManager.setPlatformActionListener(myPlatformActionListener);
        platformShareManager.shareLinkCard(platform.getName());
    }

    /**
     * share QQ mini program
     * **/
    public void shareQQMiniProgram(Activity activity) {
        PlatformShareManager platformShareManager = new PlatformShareManager();
        platformShareManager.setPlatformActionListener(myPlatformActionListener);
        platformShareManager.shareQQMiniProgram(platform.getName(), activity);
    }


    class MyPlatformActionListener implements PlatformActionListener {
        @Override
        public void onComplete(final Platform platform, int i, HashMap<String, Object> hashMap) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //if (platform.getName().equals("Douyin")) {
                            //Toast.makeText(MobSDK.getContext(), "Share Complete", Toast.LENGTH_LONG).show();
                        //} else if (contextThis != null) {
                            //dialog(contextThis, "Share Complete");
                        //} else {
                            Toast.makeText(MobSDK.getContext(), "Share Complete", Toast.LENGTH_LONG).show();
                        //}
                    } catch (Throwable t) {
                        Log.e("QQQ", " ShareTypeManager  onComplete===> " + t);
                    }
                }
            });
        }

        @Override
        public void onError(final Platform platform, int i, Throwable throwable) {
            throwable.printStackTrace();
            final String error = throwable.toString();
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //if (platform.getName().equals("Douyin")) {
                            //Toast.makeText(MobSDK.getContext(), "Share Failure" + error, Toast.LENGTH_LONG).show();
                        //} else if (contextThis != null) {
                            //dialog(contextThis, "Share Failure" + error);
                        //} else {
                            Toast.makeText(MobSDK.getContext(), "Share Failure" + error, Toast.LENGTH_LONG).show();
                        //}
                    } catch (Throwable t) {
                        Log.e("QQQ", " ShareTypeManager  onError===> " + t);
                    }
                }
            });
        }

        @Override
        public void onCancel(Platform platform, int i) {
            try {
                //if (platform.getName().equals("Douyin")) {
                    //Toast.makeText(MobSDK.getContext(), "Cancel Share", Toast.LENGTH_LONG).show();
                //} else if (contextThis != null) {
                    //dialog(contextThis, "Cancel Share");
                //} else {
                    Toast.makeText(MobSDK.getContext(), "Cancel Share", Toast.LENGTH_LONG).show();
                //}
            } catch (Throwable t) {
                Log.e("QQQ", " ShareTypeManager  onCancel===> " + t);
            }
        }
    }

}
