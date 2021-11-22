package cn.sharesdk.demo.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.mob.tools.network.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import cn.sharesdk.demo.App;
import cn.sharesdk.demo.MainActivity;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.ShareMobLinkActivity;
import cn.sharesdk.demo.activitys.SharePlatformTypeActivity;
import cn.sharesdk.demo.adapter.ShareRecylerViewAdapter;
import cn.sharesdk.demo.entity.PlatformMananger;
import cn.sharesdk.demo.entity.ShareInEntityManager;
import cn.sharesdk.demo.entity.ShareListItemInEntity;
import cn.sharesdk.demo.eventbus.MessageWrap;
import cn.sharesdk.demo.manager.platform.PlatformShareConstant;
import cn.sharesdk.demo.sensor.OnSharkeShotListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.loopshare.LoopSharePasswordListener;
import cn.sharesdk.framework.loopshare.watermark.WaterMarkListener;
import cn.sharesdk.framework.utils.SSDKLog;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;



public class ShareFragment extends BaseFragment implements CallBackShotImageView, ShareRecylerViewAdapter.ShotOnClickListener, OnSharkeShotListener {
	private RecyclerView listView;
	private ShareRecylerViewAdapter adapter;
	private List<ShareListItemInEntity> lists;
	private ThumbnailLayout thumbLayout;
	private String imageUri = "";
	private BitmapFactory.Options options;
	private Bitmap bitmap;
	private ImageView iconImg;
	private ContentLoadingProgressBar progress;
	private TextView shareImage;
	private LinearLayout sernorLayout;
	private TextView announcementTv;
	private String testVideo;

	public void initView(View view) {
		announcementTv = (TextView) view.findViewById(R.id.announcement);
		announcementTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToAnnouncement();
			}
		});
		thumbLayout = (ThumbnailLayout) view.findViewById(R.id.thumbLayout);
		thumbLayout.setAutoHide(true);//设置自动消失。
		iconImg = (ImageView) view.findViewById(R.id.thumb);
		progress = (ContentLoadingProgressBar) view.findViewById(R.id.startShow);
		sernorLayout = (LinearLayout) view.findViewById(R.id.mSernor);
		progress.setVisibility(View.GONE);
		shareImage = (TextView) view.findViewById(R.id.shareFriends);
		shareImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OneKeyShare(imageUri);
			}
		});
		listView = (RecyclerView) view.findViewById(R.id.mListView);
		listView.setHasFixedSize(true);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
		listView.setLayoutManager(linearLayoutManager);
		adapter = new ShareRecylerViewAdapter(getContext(), lists);
		adapter.setOnItemListener(new ShareRecylerViewAdapter.ListOnItemListener() {
			@Override
			public void onClick(View view, int position) {
				ShareListItemInEntity inEntity = lists.get(position);
				if (view.getId() == R.id.onMainLayout) {
					Intent intent = new Intent(getActivity(), SharePlatformTypeActivity.class);
					PlatformShareConstant.getInstance().setPlatform(lists.get(position).getPlatform());
					App.getInstance().setPlatformList(lists.get(position).getPlatform());
					intent.putExtra("name", lists.get(position).getPlatName());
					inEntity.setPlatform(null);
					intent.putExtra("shareEntity", inEntity);
					startActivity(intent);
				}
				lists.get(position).setPlatform(App.getInstance().getPlatformList().get(0));
			}
		});
		adapter.setOnShotListener(this);
		adapter.setActivity(getActivity());
		listView.setAdapter(adapter);
		listView.setItemAnimator(new DefaultItemAnimator());

		Log.e(MainActivity.TAG, "ShareFragment  initView()");

		Thread thread = new Thread() {
			@Override
			public void run() {
				String videoUrl = "http://www.mob.com/video/ShareSDK.mp4";
				try {
					testVideo = new NetworkHelper().downloadCache(getContext(), videoUrl, "videos", true, null);
					Log.e("ShareSDK", "======> " + testVideo);
				} catch (Throwable e) {
					Log.e("ShareSDK", "视频下载catch======> " + e);
				}
			}
		};
		thread.start();

	}

	public void initData() {
		MainActivity activity = (MainActivity) getActivity();
		if (activity != null) {
			activity.setCallBackImage(this);
			activity.setOnSharkeShotListener(this);
		}
		if (lists == null) {
			lists = new ArrayList<>();
		}
		lists.add(ShareInEntityManager.createYanShi(getContext()));
		lists.add(ShareInEntityManager.createDirect(getContext()));
		lists.add(ShareInEntityManager.createInLand(getContext()));
		lists.addAll(PlatformMananger.getInstance(getContext()).getChinaList());
		lists.add(ShareInEntityManager.createInternational(getContext()));
		lists.addAll(PlatformMananger.getInstance(getContext()).getList());
		lists.add(ShareInEntityManager.createSystem(getContext()));
		lists.addAll(PlatformMananger.getInstance(getContext()).getSystemList());

		Log.e(MainActivity.TAG, "ShareFragment  initData()");
	}

	private void screenshot() {
		// 获取屏幕
		View dView = getActivity().getWindow().getDecorView();
		dView.setDrawingCacheEnabled(true);
		dView.buildDrawingCache();
		Bitmap bmp = dView.getDrawingCache();
		if (bmp != null) {
			try {
				// 获取内置SD卡路径
				String sdCardPath = Environment.getExternalStorageDirectory().getPath();
				// 图片文件路径
				String filePath = sdCardPath + File.separator + "screenshot.png";
				File file = new File(filePath);
				FileOutputStream os = new FileOutputStream(file);
				bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
				os.flush();
				os.close();
				setImageView(filePath, bmp);
			} catch (Exception e) {

			}
		}
	}

	@Override
	public int getLayoutId() {
		Log.e(MainActivity.TAG, "ShareFragment  getLayoutId()");
		return R.layout.share_framgnent;
	}

	private void setImageView(final String file, final Bitmap mBitmap) {
		imageUri = file;
		if (thumbLayout != null && (thumbLayout.getVisibility() == View.GONE)) {
			thumbLayout.setVisibility(View.VISIBLE);
		}
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (mBitmap == null) {
					options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					bitmap = BitmapFactory.decodeFile(file, options);
					if (bitmap != null) {
						iconImg.setImageBitmap(bitmap);
						progress.setVisibility(View.GONE);
					}
				} else {
					iconImg.setImageBitmap(mBitmap);
					progress.setVisibility(View.GONE);
				}
			}
		});
	}

	@Override
	public void onRefresh(String url) {
		setImageView(url, null);
		Log.e(MainActivity.TAG, "ShareFragment  onRefresh()");
	}

	@Override
	public void onClick() {
		if (thumbLayout.getVisibility() == View.VISIBLE) {
			thumbLayout.setVisibility(View.GONE);
		} else {
			thumbLayout.setVisibility(View.VISIBLE);
			sernorLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 合成视频
	 **/
	private void makeVideo(String videoPath, String mobId) {
		Log.e("ShareSDK", " makeVideo videoPath: " + videoPath);

		final Dialog dialog = new LoadingDialog(getContext(), R.style.DialogTheme);
		dialog.show();

		ShareSDK.makeVideoWaterMark(mobId, "微视频",
				"截屏保存，打开[微视频]APP观看完整视频", videoPath,
				new WaterMarkListener() {
					@Override
					public void onStart() {
						Log.e("ShareSDK", " onStart ");
					}

					@Override
					public void onProgress(int progress) {
						Log.e("ShareSDK", " onProgress ");
					}

					@Override
					public void onCancel() {
						Log.e("ShareSDK", " onCancel ");
					}

					@Override
					public void onEnd(int result) {
						Log.e("ShareSDK", " onEnd ");
						EventBus.getDefault().post(new MessageWrap(1));
						dialog.dismiss();
						Toast.makeText(getActivity(), "视频已经保存到相册，请去相册分享", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onFailed(String s, int i) {
						Log.e("ShareSDK", " onFailed " + s);
					}
				});
	}

	@Override
	public void onSharkClick() {
		Log.e("ShareSDK", " 摇一摇分享执行了 ");
		if (!TextUtils.isEmpty(testVideo)) {
			ShareSDK.makeVideoWaterMark("二维码内容", "测试标题", "测试内容",
	 				testVideo, new WaterMarkListener() {

						@Override
						public void onStart() {
							Log.e("ShareSDK", "onStart");
						}

						@Override
						public void onProgress(int i) {
							Log.e("ShareSDK", "onProgress: " + i);
						}

						@Override
						public void onCancel() {
							Log.e("ShareSDK", "onCancel");
						}

						@Override
						public void onEnd(int i) {
							Log.e("ShareSDK", "onEnd: " + i);
						}

						@Override
						public void onFailed(String s, int i) {
							Log.e("ShareSDK", "onFailed: " + s + " i: " + i);
						}
					});

		} else {
			Toast.makeText(getContext(), "视频没有下载完成，请重启应用重新下载", Toast.LENGTH_LONG).show();
		}
	}

	private void OneKeyShare(String imageUrl) {
		if ( ! TextUtils.isEmpty(imageUrl)) {
			OnekeyShare oks = new OnekeyShare();
			oks.setTitle(getString(R.string.app_name));
			oks.setText(getString(R.string.shot_screen_share_title));
			oks.setImagePath(imageUrl);
			oks.show(getContext());
		}
	}

	@Override
	public void onShark() {
		if (sernorLayout.getVisibility() == View.VISIBLE) {
			screenshot();
			thumbLayout.setVisibility(View.VISIBLE);
			sernorLayout.setVisibility(View.GONE);

			Log.e(MainActivity.TAG, "ShareFragment  onShark()");
		}
	}

	private void goToAnnouncement() {
		getContext().startActivity(new Intent(getContext(), ShareMobLinkActivity.class));
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
