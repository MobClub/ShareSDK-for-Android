package cn.sharesdk.demo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.demo.App;
import cn.sharesdk.demo.MainActivity;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.activitys.SharePlatformTypeActivity;
import cn.sharesdk.demo.adapter.ShareRecylerViewAdapter;
import cn.sharesdk.demo.entity.PlatformMananger;
import cn.sharesdk.demo.entity.ShareInEntityManager;
import cn.sharesdk.demo.entity.ShareListItemInEntity;
import cn.sharesdk.demo.manager.platform.PlatformShareConstant;
import cn.sharesdk.demo.sensor.OnSharkeShotListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by yjin on 2017/5/9.
 */

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

	public void initView(View view) {
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
		linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
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
		listView.setAdapter(adapter);
		listView.setItemAnimator(new DefaultItemAnimator());
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

	@Override
	public void onSharkClick() {
		if (sernorLayout.getVisibility() == View.VISIBLE) {
			sernorLayout.setVisibility(View.GONE);
		} else {
			sernorLayout.setVisibility(View.VISIBLE);
			thumbLayout.setVisibility(View.GONE);
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
		}
	}
}
