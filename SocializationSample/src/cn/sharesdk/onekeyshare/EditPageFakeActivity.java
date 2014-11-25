/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.FakeActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.BitmapHelper;

import static cn.sharesdk.framework.utils.R.getStringRes;

public class EditPageFakeActivity extends FakeActivity {

	protected List<Platform> platforms;

	protected HashMap<String, Object> shareParamMap;
	// set to display as a dialog
	protected boolean dialogMode;
	protected View backgroundView;
	protected ArrayList<String> toFriendList;
	private ArrayList<ImageInfo> shareImageList;

	protected static class ImageInfo {
		public String paramName;
		public String srcValue;
		public Bitmap bitmap;
	}

	protected static interface ImageListResultsCallback {
		void onFinish(ArrayList<ImageInfo> results);
	}

	public void setShareData(HashMap<String, Object> data) {
		shareParamMap = data;
	}

	/** set to display as a dialog */
	public void setDialogMode() {
		dialogMode = true;
	}

	/**
	 * @param bgView
	 */
	public void setBackgroundView(View bgView) {
		this.backgroundView = bgView;
	}

	public void setPlatforms(List<Platform> supportEditPagePlatforms) {
		this.platforms = supportEditPagePlatforms;
	}

	public String getLogoName(String platform) {
		if (platform == null) {
			return "";
		}

		int resId = getStringRes(getContext(), platform);
		return getContext().getString(resId);
	}

	protected boolean isShowAtUserLayout(String platformName) {
		return "SinaWeibo".equals(platformName) || "TencentWeibo".equals(platformName)
				|| "Facebook".equals(platformName) || "Twitter".equals(platformName)
				|| "FacebookMessenger".equals(platformName);
	}

	protected String getAtUserButtonText(String platform) {
		return "FacebookMessenger".equals(platform) ? "To" : "@";
	}

	protected String getJoinSelectedUser(HashMap<String, Object> data) {
		if (data != null && data.containsKey("selected")) {
			@SuppressWarnings("unchecked")
			ArrayList<String> selected = (ArrayList<String>) data.get("selected");
			String platform = ((Platform)data.get("platform")).getName();
			if("FacebookMessenger".equals(platform)) {
				toFriendList = selected;
				return null;
			}
			StringBuilder sb = new StringBuilder();
			for (String sel : selected) {
				sb.append('@').append(sel).append(' ');
			}
			return sb.toString();
		}
		return null;
	}

	protected boolean initImageList(ImageListResultsCallback callback) {
		String imageUrl = (String) shareParamMap.get("imageUrl");
		String imagePath = (String) shareParamMap.get("imagePath");
		Bitmap viewToShare = (Bitmap) shareParamMap.get("viewToShare");
		String[] imageArray = (String[]) shareParamMap.get("imageArray");

		shareImageList = new ArrayList<ImageInfo>();
		if(!TextUtils.isEmpty(imagePath) && new File(imagePath).exists()) {
			ImageInfo imageInfo = new ImageInfo();
			imageInfo.paramName = "imagePath";
			imageInfo.srcValue = imagePath;
			shareImageList.add(imageInfo);
			shareParamMap.remove("imagePath");
		} else if(viewToShare != null && !viewToShare.isRecycled()){
			ImageInfo imageInfo = new ImageInfo();
			imageInfo.paramName = "viewToShare";
			imageInfo.bitmap = viewToShare;
			shareImageList.add(imageInfo);
			shareParamMap.remove("viewToShare");
		} else if (!TextUtils.isEmpty(imageUrl)) {
			ImageInfo imageInfo = new ImageInfo();
			imageInfo.paramName = "imageUrl";
			imageInfo.srcValue = imageUrl;
			shareImageList.add(imageInfo);
			shareParamMap.remove("imageUrl");
		} else if(imageArray != null && imageArray.length > 0) {
			for(String imageUri : imageArray) {
				if(TextUtils.isEmpty(imageUri))
					continue;
				ImageInfo imageInfo = new ImageInfo();
				imageInfo.paramName = "imageArray";
				imageInfo.srcValue = imageUri;
				shareImageList.add(imageInfo);
			}
			shareParamMap.remove("imageArray");
		}

		if(shareImageList.size() == 0) {
			return false;
		}

		new AsyncTask<Object, Void, ImageListResultsCallback>() {
			@Override
			protected ImageListResultsCallback doInBackground(Object... objects) {
				for(ImageInfo imageInfo : shareImageList) {
					if(imageInfo.bitmap == null) {
						try{
							Bitmap bitmap;
							String uri = imageInfo.srcValue;
							if(uri.startsWith("http://") || uri.startsWith("https://")) {
								uri = BitmapHelper.downloadBitmap(activity, uri);
							}
							bitmap = BitmapHelper.getBitmap(uri);
							if(bitmap == null)
								continue;

							imageInfo.bitmap = bitmap;
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
				}
				return (ImageListResultsCallback) objects[0];
			}

			@Override
			protected void onPostExecute(ImageListResultsCallback callback1) {
				callback1.onFinish(shareImageList);
			}
		}.execute(callback);
		return true;
	}

	protected void removeImage(ImageInfo imageInfo) {
		if(shareImageList == null || imageInfo == null)
			return;
		shareImageList.remove(imageInfo);
	}

	protected void setResultAndFinish() {
		ArrayList<String> imageArray = new ArrayList<String>();
		if(shareImageList != null) {
			for(ImageInfo imageInfo : shareImageList) {
				if("imagePath".equals(imageInfo.paramName) || "imageUrl".equals(imageInfo.paramName)) {
					shareParamMap.put(imageInfo.paramName, imageInfo.srcValue);
				} else if("viewToShare".equals(imageInfo.paramName)) {
					shareParamMap.put(imageInfo.paramName, imageInfo.bitmap);
				} else if("imageArray".equals(imageInfo.paramName)) {
					imageArray.add(imageInfo.srcValue);
				}
			}
			shareImageList.clear();
			if(imageArray.size() == 0) {
				shareParamMap.put("imageArray", null);
			} else {
				shareParamMap.put("imageArray", imageArray.toArray(new String[imageArray.size()]));
			}
		}

		HashMap<Platform, HashMap<String, Object>> editRes = new HashMap<Platform, HashMap<String,Object>>();

		for(Platform platform : platforms) {

			if("FacebookMessenger".equals(platform.getName())) {
				HashMap<String, Object> param = new HashMap<String, Object>(shareParamMap);
				if(toFriendList != null && toFriendList.size() > 0) {
					param.put("address", toFriendList.get(toFriendList.size() - 1));
				}
				if(param.get("address") == null) {
					int resId = getStringRes(activity, "select_a_friend");
					if (resId > 0) {
						Toast.makeText(getContext(), activity.getString(resId) + " - " + platform.getName(), Toast.LENGTH_SHORT).show();
					}
					return;
				}
				editRes.put(platform, param);
				// a statistics of Sharing
				ShareSDK.logDemoEvent(3, platform);
				continue;
			}
			// a statistics of Sharing
			ShareSDK.logDemoEvent(3, platform);
			editRes.put(platform, shareParamMap);
		}

		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("editRes", editRes);
		setResult(res);
		finish();
	}

	@Override
	public boolean onFinish() {
		shareImageList = null;
		return super.onFinish();
	}
}
