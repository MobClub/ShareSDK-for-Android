package cn.sharesdk.demo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mob.MobSDK;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import cn.sharesdk.PassWordShow;
import cn.sharesdk.demo.ui.VideoPassWordShow;
import cn.sharesdk.demo.utils.PrivacyDialog;
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
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.loopshare.LoopSharePasswordListener;
import cn.sharesdk.framework.loopshare.watermark.ReadQrImageListener;

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
	public static final String TAG = "ShareSDK";

	private static final int PRIVACY_CODE = 0x11;

	public int getLayoutId() {
		return R.layout.activity_main;
	}

	private static final String[] temp = {""};

	@Override
	public void initView() {
		horizontalScrollView = this.findViewById(R.id.mHorizontal);
		container = this.findViewById(R.id.mContainer);
		viewPager = this.findViewById(R.id.mViewPager);
		addScrollView(titles);
		pagerAdapter = new PagerAdapter(getSupportFragmentManager());
		pagerAdapter.setTitles(titles);
		pagerAdapter.setFragments(fragmentList);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(this);
		viewPager.setOffscreenPageLimit(3);
		Log.e(TAG, " initView() ");


		Intent intent = new Intent();
		intent.setClass(this, PrivacyDialog.class);
		startActivityForResult(intent, PRIVACY_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PRIVACY_CODE) {
			if (resultCode == 1){
//				checkPermissions();
			}else if (resultCode == 0){
				finish();
			}
		}
	}

	/**
	 * 识别相册第一张二维码的图片
	 * **/
	private void paraseQRImage() {

		ShareSDK.getFirstQrImage(this, new ReadQrImageListener() {

			@Override
			public void onSucessed(String s) {
				if (temp[0].equals("")) {
					Intent intent = new Intent();
					intent.setClass(MobSDK.getContext(), VideoPassWordShow.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					temp[0] = s;
					Log.d("ShareSDK", "onSucessed 视频二维码解析正常，跳转 " + s);
				} else if (!temp[0].equals("")) {
					if (!temp[0].equals(s)) {
						Intent intent = new Intent();
						intent.setClass(MobSDK.getContext(), VideoPassWordShow.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						temp[0] = s;
						Log.d("ShareSDK", "onSucessed 视频二维码解析正常，跳转 " + s);
					}
				}

			}

			@Override
			public void onFailed(Throwable throwable, int i) {
				Log.d("ShareSDK", "onFailed 视频二维码解析失败: " + throwable);
			}


		});
	}

	/**
	 * 读取剪切板的淘口令并且解析
	 * **/
	private void parasQuickPassWord() {
		ShareSDK.readPassWord(true, new LoopSharePasswordListener() {
			@Override
			public void onResult(Object var1) {
				Log.d("ShareSDK", " onResult " + var1);
				if (var1 != null) {
					try {
						Intent intent = new Intent();
						intent.setClass(MobSDK.getContext(), PassWordShow.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						Log.d("ShareSDK", " 执行跳转的逻辑 ");
					} catch (Throwable t) {
						Log.d("ShareSDK", " 跳转失败 " + t);
					}
				}
			}

			@Override
			public void onError(Throwable var1) {
				Log.d("ShareSDK", " onError " + var1);
			}
		});
	}

	/* 检查使用权限 */
	protected void checkPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			try {
				PackageManager pm = getPackageManager();
				PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
				ArrayList<String> list = new ArrayList<String>();
				for (String p : pi.requestedPermissions) {
					if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
						list.add(p);
					}
				}
				if (list.size() > 0) {
					String[] permissions = list.toArray(new String[list.size()]);
					if (permissions != null) {
						requestPermissions(permissions, 1);
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private void addScrollView(String[] titles) {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		final int count = titles.length;
		for (int i = 0; i < count; i++) {
			final String title = titles[i];
			final View view = layoutInflater.inflate(R.layout.horizontal_item_layout, null);
			final LinearLayout linearLayout = view.findViewById(R.id.itemLayout);
			final TextView titeTxt = view.findViewById(R.id.titleName);
			final ImageView titleImg = view.findViewById(R.id.titleImg);
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
		Log.e(TAG, " onCreate() ");
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
		Log.e(TAG, " initData() ");
	}

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
		Log.e(TAG, " initResources() ");
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
		Log.e(TAG, " showLoad() ");
	}

	@Override
	public void cancelLoad() {
		Log.e(TAG, " cancelLoad() ");
	}

	@Override
	public void refreshResult(BaseEntity baseEntity) {
		Log.e(TAG, " refreshResult() ");
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		Log.e(TAG, " onPageScrolled() ");
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
		Log.e(TAG, " onPageScrollStateChanged() ");
	}

	@Override
	protected void onStart() {
		super.onStart();
		manager.startListen();
		Log.e(TAG, " onStart() ");
	}

	@Override
	protected void onResume() {
		super.onResume();
		parasQuickPassWord();
		paraseQRImage();
		Log.e(TAG, " onStart() ");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, " onStart() ");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, " onStart() ");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		manager.stopListen();
		Log.e(TAG, " onDestroy() ");
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
