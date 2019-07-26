package cn.sharesdk.demo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mob.MobSDK;
import cn.sharesdk.demo.utils.ScreenShotListenManager;
import java.util.ArrayList;
import java.util.List;
import cn.sharesdk.demo.adapter.PagerAdapter;
import cn.sharesdk.demo.entity.BaseEntity;
import cn.sharesdk.demo.entity.PlatformMananger;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.manager.BasePresenter;
import cn.sharesdk.demo.manager.ui.MainPresenter;
import cn.sharesdk.demo.sensor.OnSharkeShotListener;
import cn.sharesdk.demo.sensor.ShakeListener;
import cn.sharesdk.demo.ui.AuthorizationFragment;
import cn.sharesdk.demo.ui.BaseActivity;
import cn.sharesdk.demo.ui.BaseFragment;
import cn.sharesdk.demo.ui.CallBackShotImageView;
import cn.sharesdk.demo.ui.ShareFragment;
import cn.sharesdk.demo.ui.UserInfoFragment;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
	private HorizontalScrollView horizontalScrollView;
	private ViewGroup container;
	private ViewPager viewPager;
	private int curClassIndex = 0;
	private final static String COLOR_SELECTED = "#FF7800";
	private final static String COLOR_UN_SELECTED = "#464646";
	private final static String COLOR_IMG_UN_SELECTED = "#eef0f3";
	private List<BaseFragment> fragmentList;
	private ScreenShotListenManager manager;
	private ShakeListener shakeListener;
	private CallBackShotImageView callBackImage;
	private OnSharkeShotListener onSharkeShotListener;

	private PagerAdapter pagerAdapter;
	private int scrollX = 0;
	private String[] titles = {MobSDK.getContext().getString(R.string.share_title_share_txt),
			MobSDK.getContext().getString(R.string.share_title_authorizon_txt),
			MobSDK.getContext().getString(R.string.share_title_userinfo_txt)};

	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void initView() {
		horizontalScrollView = (HorizontalScrollView) this.findViewById(R.id.mHorizontal);
		container = (LinearLayout) this.findViewById(R.id.mContainer);
		viewPager = (ViewPager) this.findViewById(R.id.mViewPager);
		addScrollView(titles);
		pagerAdapter = new PagerAdapter(getSupportFragmentManager());
		pagerAdapter.setTitles(titles);
		pagerAdapter.setFragments(fragmentList);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(this);
		viewPager.setOffscreenPageLimit(3);
		getPermission(this);
	}

	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE};

	public static void getPermission(Activity activity) {
		int permission = ActivityCompat.checkSelfPermission(activity,
				Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE);
		}
	}

	private void addScrollView(String[] titles) {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		final int count = titles.length;
		for (int i = 0; i < count; i++) {
			final String title = titles[i];
			final View view = layoutInflater.inflate(R.layout.horizontal_item_layout, null);
			final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
			final TextView titeTxt = (TextView) view.findViewById(R.id.titleName);
			final ImageView titleImg = (ImageView) view.findViewById(R.id.titleImg);
			titeTxt.setText(title);
			if (curClassIndex == i) {
				titeTxt.setTextColor(Color.parseColor(COLOR_SELECTED));
				titleImg.setBackgroundColor(Color.parseColor(COLOR_SELECTED));
			} else {
				titeTxt.setTextColor(Color.parseColor(COLOR_UN_SELECTED));
				titleImg.setBackgroundColor(Color.parseColor(COLOR_IMG_UN_SELECTED));
			}
			final int index = 1;
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					View view = null;
					for (int i = 0; i < container.getChildCount(); i++) {
						view = container.getChildAt(i);
						if (v == view) {
							changePagePostion(i);
							viewPager.setCurrentItem(i);
						}
					}
				}
			});
			container.addView(view);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initScreenShotListener();
	}

	@Override
	public void initData() {
		initResources();
		if (fragmentList == null) {
			fragmentList = new ArrayList<>();
		}
		ShareFragment shareFragment = new ShareFragment();
		fragmentList.add(shareFragment);
		AuthorizationFragment authrizationFragment = new AuthorizationFragment();
		fragmentList.add(authrizationFragment);
		UserInfoFragment userInfoFragment = new UserInfoFragment();
		fragmentList.add(userInfoFragment);
		shakeListener = new ShakeListener(this);
		shakeListener.setOnShakeListener(new UOnShakeListener());
	}

/*	*//**
	 * link场景还原的监听处理
	 * **//*
	@Override
	public Class<? extends Activity> willRestoreScene(Scene scene) {
		Log.e("QQQ", " willRestoreScene ");
		return ShareMobLinkActivity.class;
	}

	@Override
	public void completeRestore(Scene scene) {
		Log.e("QQQ", " completeRestore ");
	}

	@Override
	public void notFoundScene(Scene scene) {
		Log.e("QQQ", " notFoundScene ");
	}*/

	private class UOnShakeListener implements ShakeListener.OnShakeListener {
		@Override
		public void onShake() {
			onSharkeShotListener.onShark();
		}
	}

	public OnSharkeShotListener getOnSharkeShotListener() {
		return onSharkeShotListener;
	}

	public void setOnSharkeShotListener(OnSharkeShotListener onSharkeShotListener) {
		this.onSharkeShotListener = onSharkeShotListener;
	}

	public void initResources() {
		resourceManager();
		platformManager();
	}

	private void resourceManager() {
		ResourcesManager.getInstace(this);
	}

	private void platformManager() {
		PlatformMananger.getInstance(this);
	}

	@Override
	protected BasePresenter createPresenter() {
		return new MainPresenter();
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
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		changePagePostion(position);
	}

	private void changePagePostion(int position) {
		//首先设置当前的Item为正常状态
		View preView = container.getChildAt(curClassIndex);
		((TextView) (preView.findViewById(R.id.titleName))).setTextColor(Color.parseColor(COLOR_UN_SELECTED));
		((ImageView) (preView.findViewById(R.id.titleImg))).setBackgroundColor(Color.parseColor(COLOR_IMG_UN_SELECTED));
		curClassIndex = position;
		//设置当前为选中状态
		View currentItem = container.getChildAt(curClassIndex);
		((ImageView) (currentItem.findViewById(R.id.titleImg))).setBackgroundColor(Color.parseColor(COLOR_SELECTED));
		((TextView) (currentItem.findViewById(R.id.titleName))).setTextColor(Color.parseColor(COLOR_SELECTED));
		//这边移动的距离 是经过计算粗略得出来的
		scrollX = currentItem.getLeft() - 300;
		horizontalScrollView.post(new Runnable() {
			@Override
			public void run() {
				horizontalScrollView.scrollTo(scrollX, 0);
			}
		});
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	protected void onStart() {
		super.onStart();
		manager.startListen();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		manager.stopListen();
	}

	private void initScreenShotListener() {
		manager = ScreenShotListenManager.newInstance(this);
		manager.setListener(
				new ScreenShotListenManager.OnScreenShotListener() {
					public void onShot(String imagePath) {
						callBackImage.onRefresh(imagePath);
					}
				}
		);
	}

	public CallBackShotImageView getCallBackImage() {
		return callBackImage;
	}

	public void setCallBackImage(CallBackShotImageView callBackImage) {
		this.callBackImage = callBackImage;
	}
}
