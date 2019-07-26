package cn.sharesdk.demo.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
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

import cn.sharesdk.demo.App;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.UriUtil;
import cn.sharesdk.demo.adapter.SharePlatformAdapter;
import cn.sharesdk.demo.entity.BaseEntity;
import cn.sharesdk.demo.entity.ShareListItemInEntity;
import cn.sharesdk.demo.manager.BasePresenter;
import cn.sharesdk.demo.manager.platform.PlatformShareConstant;
import cn.sharesdk.demo.manager.share.ShareTypeManager;
import cn.sharesdk.demo.manager.ui.SharePlatformPresenter;
import cn.sharesdk.demo.platform.douyin.DouyinShare;
import cn.sharesdk.demo.platform.wework.WeworkShare;
import cn.sharesdk.demo.ui.BaseActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import static cn.sharesdk.demo.platform.douyin.DouyinShare.DOUYIN_VIDEO;
import static cn.sharesdk.demo.platform.douyin.DouyinShare.PHOTO_REQUEST_GALLERY;
import static cn.sharesdk.demo.platform.wework.WeworkShare.WEWORK_SHARE_FILE;
import static cn.sharesdk.demo.platform.wework.WeworkShare.WEWORK_SHARE_IMAGE;
import static cn.sharesdk.demo.platform.wework.WeworkShare.WEWORK_SHARE_VIDEO;
import static cn.sharesdk.demo.utils.CommomDialog.dialog;

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
		return R.layout.share_platform_type_activity;
	}

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
	}

	protected BasePresenter createPresenter() {
		return new SharePlatformPresenter();
	}

	@Override
	public void showLoad() {

	}

	@Override
	public void cancelLoad() {

	}

	@Override
	public void refreshResult(BaseEntity baseEntity) {

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.mReback) {
			finish();
		}
	}

	@Override
	public void onItemClick(int platformCode) {
		Platform platform = App.getInstance().getPlatformList().get(0);
		if (platform != null) {
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
					startShareImage(UriUtil.convertUriToPath(this,uri));
					Log.e("QQQ", " 列表的路径： " + UriUtil.convertUriToPath(this,uri));
					break;
				case DOUYIN_VIDEO:
					Uri douyinVideo = data.getData();
					startShareVideo(UriUtil.convertUriToPath(this, douyinVideo));
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
			}
		}
	}

	/**
	 * 抖音本地图片分享
	 * **/
	private void startShareImage(String imagePath) {
		myPlatformActionListener = new MyPlatformActionListener();
		DouyinShare douyinShare = new DouyinShare(myPlatformActionListener);
		douyinShare.shareImagePath(this, imagePath);
	}

	/**
	 * 抖音本地视频分享
	 * **/
	private void startShareVideo(String videoPath) {
		myPlatformActionListener = new MyPlatformActionListener();
		DouyinShare douyinShare = new DouyinShare(myPlatformActionListener);
		douyinShare.shareVideo(this, videoPath);
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

}
