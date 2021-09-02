package cn.sharesdk.demo.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import cn.sharesdk.demo.App;
import cn.sharesdk.demo.MainActivity;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.UriUtil;
import cn.sharesdk.demo.adapter.SharePlatformAdapter;
import cn.sharesdk.demo.entity.BaseEntity;
import cn.sharesdk.demo.entity.ShareListItemInEntity;
import cn.sharesdk.demo.platform.facebook.FacebookShare;
import cn.sharesdk.demo.platform.littleredbook.LittleredbookShare;
import cn.sharesdk.demo.manager.BasePresenter;
import cn.sharesdk.demo.manager.platform.PlatformShareConstant;
import cn.sharesdk.demo.manager.share.ShareTypeManager;
import cn.sharesdk.demo.manager.ui.SharePlatformPresenter;
import cn.sharesdk.demo.platform.douyin.DouyinShare;
import cn.sharesdk.demo.platform.instagram.InstagramShare;
import cn.sharesdk.demo.platform.kuaishou.KuaishouShare;
import cn.sharesdk.demo.platform.meipai.MeipaiShare;
import cn.sharesdk.demo.platform.oasis.OasisShare;
import cn.sharesdk.demo.platform.snapchat.SnapChatShare;
import cn.sharesdk.demo.platform.tencent.qzone.QQZoneShare;
import cn.sharesdk.demo.platform.tiktok.TiktokShare;
import cn.sharesdk.demo.platform.watermelonvideo.WatermelonvideoShare;
import cn.sharesdk.demo.platform.wechat.favorite.WechatFavoriteShare;
import cn.sharesdk.demo.platform.wework.WeworkShare;
import cn.sharesdk.demo.platform.whatsapp.WhatsAppShare;
import cn.sharesdk.demo.ui.BaseActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import static cn.sharesdk.demo.platform.facebook.FacebookShare.FACEBOOK_VIDEO;
import static cn.sharesdk.demo.platform.littleredbook.LittleredbookShare.LITTLEREDBOOK_IMAGE;
import static cn.sharesdk.demo.platform.littleredbook.LittleredbookShare.LITTLEREDBOOK_VIDEO;
import static cn.sharesdk.demo.platform.douyin.DouyinShare.DOUYIN_VIDEO;
import static cn.sharesdk.demo.platform.douyin.DouyinShare.PHOTO_REQUEST_GALLERY;
import static cn.sharesdk.demo.platform.instagram.InstagramShare.INS_PHOTO;
import static cn.sharesdk.demo.platform.kuaishou.KuaishouShare.KUAISHOU_IMAGE;
import static cn.sharesdk.demo.platform.kuaishou.KuaishouShare.KUAISHOU_VIDEO;
import static cn.sharesdk.demo.platform.meipai.MeipaiShare.MEIPAI_IMAGE;
import static cn.sharesdk.demo.platform.meipai.MeipaiShare.MEIPAI_VIDEO;
import static cn.sharesdk.demo.platform.snapchat.SnapChatShare.SNAP_IMAGE;
import static cn.sharesdk.demo.platform.snapchat.SnapChatShare.SNAP_VIDEO;
import static cn.sharesdk.demo.platform.tencent.qzone.QQZoneShare.QZONE_VIDEO;
import static cn.sharesdk.demo.platform.tiktok.TiktokShare.KT_IMAGE;
import static cn.sharesdk.demo.platform.tiktok.TiktokShare.KT_VIDEO;
import static cn.sharesdk.demo.platform.watermelonvideo.WatermelonvideoShare.WATERM_VIDEO;
import static cn.sharesdk.demo.platform.wechat.favorite.WechatFavoriteShare.WECHAT_FAV_FILE;
import static cn.sharesdk.demo.platform.wework.WeworkShare.WEWORK_SHARE_FILE;
import static cn.sharesdk.demo.platform.wework.WeworkShare.WEWORK_SHARE_IMAGE;
import static cn.sharesdk.demo.platform.wework.WeworkShare.WEWORK_SHARE_VIDEO;
import static cn.sharesdk.demo.platform.oasis.OasisShare.OASIS_PHOTO_REQUEST_GALLERY;
import static cn.sharesdk.demo.platform.oasis.OasisShare.OASIS_SHARE_VIDEO;
import static cn.sharesdk.demo.platform.oasis.OasisShare.OASIS_PHOTO_REQUEST_GALLERY_NET;
import static cn.sharesdk.demo.platform.whatsapp.WhatsAppShare.PHOTO_WHATS_APP;
import static cn.sharesdk.demo.platform.whatsapp.WhatsAppShare.VIDEO_WHATS_APP;

/**
 * Created by yjin on 2017/5/17.
 */

/**
 * 具体类型分享界面
 */

public class SharePlatformTypeActivity extends BaseActivity implements View.OnClickListener, SharePlatformAdapter.OnClickItemListener {
	private ImageView callBack;
	private TextView shareTitle;
	private ShareListItemInEntity entity;
	private RecyclerView recyclerView;
	private SharePlatformAdapter adapter;
	private List<Integer> lists;
	private String name;
	private Context context;

	@Override
	public int getLayoutId() {
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity getLayoutId()");
		return R.layout.share_platform_type_activity;
	}

	@SuppressLint("WrongConstant")
	@Override
	public void initView() {
		recyclerView = (RecyclerView) this.findViewById(R.id.mSharePlatform);
		callBack = (ImageView) this.findViewById(R.id.mReback);
		callBack.setOnClickListener(this);
		shareTitle = (TextView) this.findViewById(R.id.mTitle);
		recyclerView = (RecyclerView) this.findViewById(R.id.mSharePlatform);
		if (entity != null) {
			shareTitle.setText(getString(R.string.share_platform_name) + entity.getName());
		}
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
		recyclerView.setLayoutManager(linearLayoutManager);
		adapter = new SharePlatformAdapter(lists, this);
		adapter.setOnClickItemListener(this);
		recyclerView.setAdapter(adapter);
		context = this;
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity initView()");
	}

	@Override
	public void initData() {
		if (lists == null) {
			lists = new ArrayList<>();
		}
		Intent intent = getIntent();
		if (intent != null) {
			entity = (ShareListItemInEntity) intent.getSerializableExtra("shareEntity");
			name = intent.getStringExtra("name");
		}
		if (entity != null) {
			lists.clear();
			if (!TextUtils.isEmpty(name)) {
				Integer[] plats = PlatformShareConstant.byNamePlatforms(name);
				List<Integer> ls = Arrays.asList(plats);
				lists.addAll(ls);
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
			}
		}
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity initData()");
	}

	protected BasePresenter createPresenter() {
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity createPresenter()");
		return new SharePlatformPresenter();
	}

	@Override
	public void showLoad() {
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity showLoad()");
	}

	@Override
	public void cancelLoad() {
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity cancelLoad()");
	}

	@Override
	public void refreshResult(BaseEntity baseEntity) {
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity refreshResult()");
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.mReback) {
			finish();
		}
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity onClick()");
	}

	@Override
	public void onItemClick(int platformCode) {
		Platform platform = App.getInstance().getPlatformList().get(0);
		if (platform != null) {
			Log.e(MainActivity.TAG, " SharePlatformTypeActivity onItemClick()");

			ShareTypeManager shareManager = new ShareTypeManager(this, platform);
			shareManager.shareShow(platformCode, this);
		}
	}

	private MyPlatformActionListener myPlatformActionListener = null;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case PHOTO_REQUEST_GALLERY:
					Uri uri = data.getData();
					startShareImage(UriUtil.convertUriToPath(this, uri));
					Log.e("QQQ", " 列表的路径： " + UriUtil.convertUriToPath(this, uri));
					break;
				case DOUYIN_VIDEO:
					Uri douyinVideo = data.getData();
					startShareVideo(UriUtil.convertUriToPath(this, douyinVideo));
					break;
				case KT_IMAGE:
					Uri tiktokImage = data.getData();
					tiktokShareImage(UriUtil.convertUriToPath(this, tiktokImage));
					break;
				case KT_VIDEO:
					Uri tiktokVideo = data.getData();
					tiktokShareVideo(UriUtil.convertUriToPath(this, tiktokVideo));
					break;
				case PHOTO_WHATS_APP:
					Uri whatsappImage = data.getData();
					whatsappShareImage(UriUtil.convertUriToPath(this, whatsappImage));
					break;
				case VIDEO_WHATS_APP:
					Uri whatsappVideo = data.getData();
					whatsappShareVideo(UriUtil.convertUriToPath(this, whatsappVideo));
					break;
				case FACEBOOK_VIDEO:
					Uri fbVideoUri = data.getData();
					startShareVideo(fbVideoUri);
					break;
				case WEWORK_SHARE_IMAGE:
					Uri weworkImage = data.getData();
					WewrokShareImage(UriUtil.convertUriToPath(this, weworkImage));
					break;
				case WEWORK_SHARE_VIDEO:
					Uri weworkVideo = data.getData();
					WeworkShareVideo(UriUtil.convertUriToPath(this, weworkVideo));
					break;
				case WEWORK_SHARE_FILE:
					Uri weworkFile = data.getData();
					WewrokShareFile(UriUtil.convertUriToPath(this, weworkFile));
					break;
				case INS_PHOTO:
					Uri insImage = data.getData();
					startShareInsImage(UriUtil.convertUriToPath(this, insImage));
					break;
                case OASIS_PHOTO_REQUEST_GALLERY:  //Oasis
                    Uri oasisImage = data.getData();
                    startShareOasisUri(oasisImage);
                    break;
                case OASIS_SHARE_VIDEO:
                    Uri oasisVideo = data.getData();
                    startShareMedio(oasisVideo);
                    break;
                case OASIS_PHOTO_REQUEST_GALLERY_NET:
                    Uri oasisImgNet = data.getData();
                    startShareOasisUri(UriUtil.convertUriToPath(this, oasisImgNet));
                    break;
				case QZONE_VIDEO:
					Uri qzoneVideo = data.getData();
					QzoneShareVideo(UriUtil.convertUriToPath(this, qzoneVideo));
					break;
                case WECHAT_FAV_FILE:
                    Uri wechatfavFile = data.getData();
                    WechatFavShareFile(UriUtil.convertUriToPath(this, wechatfavFile));
                    break;
				case SNAP_IMAGE:
					shareSnapchatImage(data);
					break;
				case SNAP_VIDEO:
					shareSnapchatVideo(data);
					break;
				case KUAISHOU_IMAGE:
					Uri kuaishouImage = data.getData();
					kuaishouShareImage(UriUtil.convertUriToPath(this, kuaishouImage));
					break;
				case KUAISHOU_VIDEO:
					Uri kuaishouVideo = data.getData();
					kuaishouShareVideo(kuaishouVideo);
					break;

				case LITTLEREDBOOK_IMAGE:
					Uri littleredbookImage = data.getData();
					xiaohongshuShareImage(UriUtil.convertUriToPath(this, littleredbookImage));
					break;
				case LITTLEREDBOOK_VIDEO:
					Uri littleredbookVideo = data.getData();
					xiaohongshuShareVideo(littleredbookVideo);
					break;

				case WATERM_VIDEO:
					Uri watermVideo = data.getData();
					watermShareVideo(watermVideo);
					break;

				case MEIPAI_IMAGE:
					Uri meipaiImage = data.getData();
					meipaiShareImage(UriUtil.convertUriToPath(this, meipaiImage));
					break;
				case MEIPAI_VIDEO:
					Uri meipaiVideo = data.getData();
					meipaiShareVideo(UriUtil.convertUriToPath(this, meipaiVideo));
					break;
			}
		}
	}

    /**
     * oasis视频分享
     */
    private void startShareMedio(Uri path){
        myPlatformActionListener = new MyPlatformActionListener();
        OasisShare oasisShare = new OasisShare(myPlatformActionListener);
        oasisShare.shareVideo(path);
    }

    /**
     * oasis图片uri分享
     */
    private void startShareOasisUri(Uri uri){
        List<Uri> uriList = new ArrayList<>();
        uriList.add(uri);
        myPlatformActionListener = new MyPlatformActionListener();
        OasisShare oasisShare = new OasisShare(myPlatformActionListener);
        oasisShare.shareListUri(uriList);
    }

    /**
     * oasis图片path分享
     */
    private void startShareOasisUri(String path){
        List<String> pathList = new ArrayList<>();
        pathList.add(path);
        myPlatformActionListener = new MyPlatformActionListener();
        OasisShare oasisShare = new OasisShare(myPlatformActionListener);
        oasisShare.shareListPath(pathList);
    }

	/**
	 * ins 图片分享
	 * **/
	private void startShareInsImage(String imagePath) {
		myPlatformActionListener = new MyPlatformActionListener();
		InstagramShare instagramShare = new InstagramShare(myPlatformActionListener);
		instagramShare.shareInsImage(imagePath);
	}

	/**
	 * snapchat本地图片分享
	 * **/
	private void shareSnapchatImage(Intent data) {
		myPlatformActionListener = new MyPlatformActionListener();
		SnapChatShare snapChatShare = new SnapChatShare(myPlatformActionListener);
		snapChatShare.shareSnapchatImageIntent(this, data);
	}

	/**
	 * snapchat本地视频分享
	 * **/
	private void shareSnapchatVideo(Intent data) {
		myPlatformActionListener = new MyPlatformActionListener();
		SnapChatShare snapChatShare = new SnapChatShare(myPlatformActionListener);
		snapChatShare.shareSnapchatVideo(this, data);
	}

	/**
	 * 抖音本地图片分享
	 * **/
	private void startShareImage(String imgPath) {
		myPlatformActionListener = new MyPlatformActionListener();
		DouyinShare douyinShare = new DouyinShare(myPlatformActionListener);
		douyinShare.shareImagePath(this, imgPath);
	}

	/**
	 * 抖音本地视频分享
	 **/
	private void startShareVideo(String videoPath) {
		myPlatformActionListener = new MyPlatformActionListener();
		DouyinShare douyinShare = new DouyinShare(myPlatformActionListener);
		douyinShare.shareVideo(this, new String[]{videoPath});
	}

	/**
	 * tiktok本地图片分享
	 * **/
	private void tiktokShareImage(String imagePath) {
		myPlatformActionListener = new MyPlatformActionListener();
		TiktokShare tiktokShare = new TiktokShare(myPlatformActionListener);
		tiktokShare.shareImagePath(this, new String[]{imagePath});
	}

	/**
	 * tiktok本地视频分享
	 * **/
	private void tiktokShareVideo(String videoPath) {
		myPlatformActionListener = new MyPlatformActionListener();
		TiktokShare tiktokShare = new TiktokShare(myPlatformActionListener);
		tiktokShare.shareVideo(this, new String[]{videoPath});
	}

	/**
	 * whatsapp 分享本地图片
	 **/
	private void whatsappShareImage(String imagePath) {
		myPlatformActionListener = new MyPlatformActionListener();
		WhatsAppShare whatsAppShare = new WhatsAppShare(myPlatformActionListener);
		whatsAppShare.shareImage(imagePath);
	}

	/**
	 * whatsapp 分享本地视频
	 **/
	private void whatsappShareVideo(String videoPath) {
		myPlatformActionListener = new MyPlatformActionListener();
		WhatsAppShare whatsAppShare = new WhatsAppShare(myPlatformActionListener);
		whatsAppShare.shareVideo(videoPath);
	}


	/**
	 * Facebook 本地视频分享
	 **/
	private void startShareVideo(Uri videoUri) {
		myPlatformActionListener = new MyPlatformActionListener();
		FacebookShare facebookShare = new FacebookShare(myPlatformActionListener);
		facebookShare.shareFacebookVideo(videoUri);
	}

	/**
	 * 西瓜视频本地视频分享
	 * **/
	private void watermShareVideo(Uri videoUri) {
		myPlatformActionListener = new MyPlatformActionListener();
		WatermelonvideoShare watermelonvideoShare = new WatermelonvideoShare(myPlatformActionListener);
		watermelonvideoShare.shareVideo(this, videoUri);
	}

	/**
	 * 小红书本地图片分享
	 * **/
	private void xiaohongshuShareImage(String imagePath) {
		myPlatformActionListener = new MyPlatformActionListener();
		LittleredbookShare littleredbookShare = new LittleredbookShare(myPlatformActionListener);
		littleredbookShare.shareImagePath(this, imagePath);
	}

	/**
	 * 小红书本地视频分享
	 * **/
	private void xiaohongshuShareVideo(Uri videoUri) {
		myPlatformActionListener = new MyPlatformActionListener();
		LittleredbookShare littleredbookShare = new LittleredbookShare(myPlatformActionListener);
		littleredbookShare.shareVideo(this, videoUri);
	}

	/**
	 * 美拍图片分享
	 * **/
	private void meipaiShareImage(String imagePath) {
		myPlatformActionListener = new MyPlatformActionListener();
		MeipaiShare meipaiShare = new MeipaiShare(myPlatformActionListener);
		meipaiShare.shareImagePath(this, imagePath);
	}

	/**
	 * 美拍分享视频
	 * **/
	private void meipaiShareVideo(String videoPath) {
		myPlatformActionListener = new MyPlatformActionListener();
		MeipaiShare meipaiShare = new MeipaiShare(myPlatformActionListener);
		meipaiShare.shareVideo(this, videoPath);
	}

	/**
	 * 快手本地图片分享
	 * **/
	private void kuaishouShareImage(String imagePath) {
		myPlatformActionListener = new MyPlatformActionListener();
		KuaishouShare kuaishouShare = new KuaishouShare(myPlatformActionListener);
		kuaishouShare.shareImagePath(this, imagePath);
	}

	/**
	 * 快手本地视频分享
	 * **/
	private void kuaishouShareVideo(Uri videoUri) {
		myPlatformActionListener = new MyPlatformActionListener();
		KuaishouShare kuaishouShare = new KuaishouShare(myPlatformActionListener);
		kuaishouShare.shareVideo(this, videoUri);
	}

	/**
	 * QZONE本地视频分享
	 * **/
	public void QzoneShareVideo(String videoPath) {
		myPlatformActionListener = new MyPlatformActionListener();
		QQZoneShare qzoneShare = new QQZoneShare(myPlatformActionListener);
		qzoneShare.shareVideo(videoPath);
	}

    /**
     * 微信收藏文件分享
     * **/
    public void WechatFavShareFile(String videoPath) {
        myPlatformActionListener = new MyPlatformActionListener();
        WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(myPlatformActionListener);
        wechatFavoriteShare.shareFile(videoPath);
    }

	/**
	 * 企业微信视频分享
	 * */
	private void WeworkShareVideo(String path) {
		myPlatformActionListener = new SharePlatformTypeActivity.MyPlatformActionListener();
		WeworkShare weworkShare = new WeworkShare(myPlatformActionListener);
		weworkShare.shareVideo(path);
	}

	/**
	 * 企业微信文件分享
	 * **/
	 private void WewrokShareFile(String path) {
        myPlatformActionListener = new SharePlatformTypeActivity.MyPlatformActionListener();
        WeworkShare weworkShare = new WeworkShare(myPlatformActionListener);
        weworkShare.shareFile(path);
    }

    /**
	 * 企业微信图片分享
	 * **/
	private void WewrokShareImage(String path) {
		myPlatformActionListener = new SharePlatformTypeActivity.MyPlatformActionListener();
		WeworkShare wewrokShare = new WeworkShare(myPlatformActionListener);
		wewrokShare.shareImage(path);
	}

	class MyPlatformActionListener implements PlatformActionListener {
		@Override
		public void onComplete(final Platform platform, int i, HashMap<String, Object> hashMap) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
						//if (platform.getName().equals("Douyin")) {
							//Toast.makeText(context, "onComplete",  Toast.LENGTH_LONG).show();
						//} else if (context != null) {
							//dialog(context, "Share Complete");
						//} else {
							Toast.makeText(MobSDK.getContext(), "Share Complete", Toast.LENGTH_SHORT).show();
						//}
					} catch (Throwable t) {
						Log.e("QQQ", " onComplete " + t);
					}
				}
			});
		}

		@Override
		public void onError(final Platform platform, int i, final Throwable t) {
			t.printStackTrace();
			final String error = t.toString();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//if (platform.getName().equals("Douyin")) {
						//Toast.makeText(MobSDK.getContext(), "Share Failure" + error, Toast.LENGTH_LONG).show();
					//} else if (context != null) {
						//dialog(MobSDK.getContext(), "Share Failure" + error);
					//} else {
						Toast.makeText(MobSDK.getContext(), "Share Failure" + error, Toast.LENGTH_LONG).show();
					//}

				}
			});
		}

		@Override
		public void onCancel(Platform platform, int i) {
			try {
				//if (platform.getName().equals("Douyin")) {
					//Toast.makeText(MobSDK.getContext(), "Cancel Share", Toast.LENGTH_LONG).show();
				//} else if (context != null) {
					//dialog(MobSDK.getContext(), "Cancel Share");
				//} else {
					Toast.makeText(MobSDK.getContext(), "Cancel Share", Toast.LENGTH_LONG).show();
				//}
			} catch (Throwable t) {
				Log.e("QQQ", " onCancel " + t);
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity onCreate()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity onResume()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity onRestart()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(MainActivity.TAG, " SharePlatformTypeActivity onDestroy()");
	}
}
