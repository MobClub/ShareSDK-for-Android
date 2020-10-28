package cn.sharesdk.demo.platform.snapchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.mob.MobSDK;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.snapchat.Snapchat;

public class SnapChatShare {
    public static final int SNAP_IMAGE = 0x7628;
    public static final int SNAP_VIDEO = 0x2864;

    private PlatformActionListener platformActionListener;

    public SnapChatShare(PlatformActionListener listener) {
        this.platformActionListener = listener;
    }

    public void openSnapchatImage(Activity activity) {
        openSystemGallery(activity);
    }

    public void openSnapchatVideo(Activity activity) {
        openSystemGallery(activity);
    }

    private void openSystemGallery(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("请选择图片/视频").setPositiveButton("图片", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                activity.startActivityForResult(intent, SNAP_IMAGE);
            }
        }).setNegativeButton("视频", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
                activity.startActivityForResult(intent, SNAP_VIDEO);
            }
        }).show();
    }

    /**
     * 分享图片 打开相册
     * **/
    public void shareSnapImageOpen(Activity activity) {
        openSnapchatImage(activity);
    }

    /**
     * Snapchat 分享图片
     **/
    public void shareSnapchatImageIntent(Activity activity, Intent data) {
        File mSnapFile = new File(activity.getCacheDir(), "snap");
        saveContentLocally(activity, data, mSnapFile);

        File mStickerFile = new File(activity.getCacheDir(), "sticker");
        if (!mStickerFile.exists()) {
            try {
                InputStream inputStream = activity.getAssets().open("sticker.png");
                copyFile(inputStream, mStickerFile);
            } catch (IOException e) {
                Toast.makeText(MobSDK.getContext(), "Failed to copy sticker asset", Toast.LENGTH_SHORT).show();
            }
        }

        Platform platform = ShareSDK.getPlatform(Snapchat.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setFileImage(mSnapFile);
        shareParams.setFileSticker(mStickerFile);
        shareParams.setText("hahahahTestonly");
        shareParams.setUrl("https://www.baidu.com");
        shareParams.setShareType(Platform.SHARE_IMAGE);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
                Log.e("QQQ", " onComplete " + res);
            }

            @Override
            public void onError(Platform platform, int action, Throwable t) {
                Log.e("QQQ", " onError " + t);
            }

            @Override
            public void onCancel(Platform platform, int action) {
                Log.e("QQQ", " onCancel ");
            }
        });
        platform.share(shareParams);

    }

    /**
     * 分享视频 打开相册
     * **/
    public void shareSnapVideoOpen(Activity activity) {
        openSnapchatImage(activity);
    }

    /**
     * Snapchat 分享视频
     **/
    public void shareSnapchatVideo(Activity activity, Intent data) {
        File mSnapFile = new File(activity.getCacheDir(), "snap");
        saveContentLocally(activity, data, mSnapFile);

        File mStickerFile = new File(activity.getCacheDir(), "sticker");
        if (!mStickerFile.exists()) {
            try {
                InputStream inputStream = activity.getAssets().open("sticker.png");
                copyFile(inputStream, mStickerFile);
            } catch (IOException e) {
                Toast.makeText(activity, "Failed to copy sticker asset", Toast.LENGTH_SHORT).show();
            }
        }

        Platform platform = ShareSDK.getPlatform(Snapchat.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setFileVideo(mSnapFile);
        shareParams.setFileSticker(mStickerFile);
        shareParams.setText("hahahahTestonly");
        shareParams.setUrl("https://www.baidu.com");
        shareParams.setShareType(Platform.SHARE_VIDEO);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
                Log.e("QQQ", " onComplete " + res);
            }

            @Override
            public void onError(Platform platform, int action, Throwable t) {
                Log.e("QQQ", " onError " + t);
            }

            @Override
            public void onCancel(Platform platform, int action) {
                Log.e("QQQ", " onCancel ");
            }
        });
        platform.share(shareParams);
    }


    private boolean saveContentLocally(Activity activity, Intent intent, File snapFile) {
        if (intent == null || intent.getData() == null) {
            return false;
        }
        InputStream inputStream;

        try {
            inputStream = activity.getContentResolver().openInputStream(intent.getData());
        } catch (FileNotFoundException e) {
            Toast.makeText(activity, "Could not open file", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (inputStream == null) {
            Toast.makeText(MobSDK.getContext(), "File does not exist", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            copyFile(inputStream, snapFile);
        } catch (IOException e) {
            Toast.makeText(MobSDK.getContext(), "Failed save file locally", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private static void copyFile(InputStream inputStream, File file) throws IOException {
        byte[] buffer = new byte[1024];
        int length;

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Throwable t) {
            Log.e("QQQ", "copyFile catch " + t);
        }
    }

}
