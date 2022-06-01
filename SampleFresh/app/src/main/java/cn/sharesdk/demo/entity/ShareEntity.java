package cn.sharesdk.demo.entity;

import android.text.TextUtils;

import cn.sharesdk.framework.Platform;


public class ShareEntity {
	private static volatile Platform.ShareParams param;
	private ShareEntity(){}
	public static  Platform.ShareParams createShareEntity(){
		if(param == null){
			synchronized (ShareEntity.class){
				if(param == null){
					param = new Platform.ShareParams();
				}
			}
		}
		return param;
	}
	public void setTitle(String title){
		param.setTitle(title);
	}

	public void setText(String text){
		param.setText(text);
	}

	public Platform.ShareParams getWebPage(String url){
		param.setShareType(Platform.SHARE_WEBPAGE);
		if(TextUtils.isEmpty(url)){
			return getDefaultWebPage();
		}
		return param;
	}

	public Platform.ShareParams getDefaultWebPage(){
		param.setShareType(Platform.SHARE_WEBPAGE);
		return param;
	}

	public Platform.ShareParams getBitmapImage(String imagePath){
		param.setShareType(Platform.SHARE_IMAGE);
		if(TextUtils.isEmpty(imagePath)){
			return getDefaultBitmapImage();
		}
		return param;
	}

	public Platform.ShareParams getDefaultBitmapImage(){
		param.setShareType(Platform.SHARE_IMAGE);
		return param;
	}

	public Platform.ShareParams getVideo(String filePath){
		param.setShareType(Platform.SHARE_VIDEO);
		if(TextUtils.isEmpty(filePath)){
			return getDefaultVideo();
		}
		return param;
	}
	public Platform.ShareParams getDefaultVideo(){
		param.setShareType(Platform.SHARE_VIDEO);
		return param;
	}
}
