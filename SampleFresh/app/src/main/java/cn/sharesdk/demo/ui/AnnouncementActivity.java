package cn.sharesdk.demo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.mob.tools.utils.BitmapHelper;

import java.util.HashMap;

import cn.sharesdk.demo.R;
import cn.sharesdk.demo.entity.BaseEntity;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.manager.BasePresenter;
import cn.sharesdk.demo.platform.sina.WeiboShare;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

import static cn.sharesdk.demo.utils.CommomDialog.dialog;

public class AnnouncementActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {
	private ImageView callBack;
	private ImageView rightIv;
	private TextView btnNormal;
	private TextView btnLinkCard;
	private Handler handler;
	private Context context;

	@Override
	public int getLayoutId() {
		return R.layout.activity_announcement;
	}

	@Override
	public void initView() {
		callBack = this.findViewById(R.id.mReback);
		callBack.setOnClickListener(this);
		rightIv = this.findViewById(R.id.rightIv);
		rightIv.setOnClickListener(this);
		rightIv.setVisibility(View.VISIBLE);
		btnNormal = findViewById(R.id.btn_normal);
		btnNormal.setOnClickListener(this);
		btnLinkCard = findViewById(R.id.btn_linkcard);
		btnLinkCard.setOnClickListener(this);
		context = this;
	}

	@Override
	public void initData() {
		handler = new Handler(Looper.getMainLooper(), this);
	}

	@Override
	protected BasePresenter createPresenter() {
		return new BasePresenter() {
			@Override
			public void attachView(Object view) {
				super.attachView(view);
			}
		};
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
	public boolean handleMessage(Message msg) {
		String toastMsg = (String) msg.obj;
		showToast(toastMsg);
		return false;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.mReback) {
			finish();
		} else if (v.getId() == R.id.rightIv) {
			onOneKeyShare();
		} else if (v.getId() == R.id.btn_normal) {
			shareText();
		} else if (v.getId() == R.id.btn_linkcard) {
			shareLinkCard();
		}
	}

	private void shareText() {
		WeiboShare sinaWeibo = new WeiboShare(new MyPlatformActionListener());
		sinaWeibo.shareText();
	}

	private void shareLinkCard() {
		WeiboShare sinaWeibo = new WeiboShare(new MyPlatformActionListener());
		sinaWeibo.shareLinkCard();
	}

	private void showToast(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 一键分享
	 */
	private void onOneKeyShare(){
		final cn.sharesdk.onekeyshare.OnekeyShare oks = new cn.sharesdk.onekeyshare.OnekeyShare();
		oks.setActivity(this);
		Bitmap img = BitmapFactory.decodeResource(this.getResources(), R.drawable.linkcard);
		String path = null;
		try {
			path = BitmapHelper.saveBitmap(this, img);
			oks.setImagePath(path);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		final String finalPath = path;
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
			@Override
			public void onShare(Platform platform, Platform.ShareParams shareParams) {
				if(platform.getName().equals("Douyin")){
					shareParams.setShareType(Platform.SHARE_VIDEO);
				}
				if (platform.getName().equals("QQ")) {
					String[] arr = {finalPath};
					oks.setImageArray(arr);
				}
			}
		});

		oks.setCallback(new PlatformActionListener() {
			@Override
			public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
				String msg = ResourcesManager.actionToString(i);
				String text = platform.getName() + " completed at " + msg;
				Message message = new Message();
				message.obj = text;
				handler.sendMessage(message);
			}

			@Override
			public void onError(Platform platform, int i, Throwable throwable) {
				String msg = ResourcesManager.actionToString(i);
				String text = platform.getName() + "caught error at " + msg;
				Message message = new Message();
				message.obj = text;
				handler.sendMessage(message);
			}

			@Override
			public void onCancel(Platform platform, int i) {
				String msg = ResourcesManager.actionToString(i);
				String text = platform.getName() + " canceled at " + msg;
				Message message = new Message();
				message.obj = text;
				handler.sendMessage(message);
			}
		});
		Bitmap logo = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
		String label = this.getString(R.string.app_name);
		View.OnClickListener listener = new View.OnClickListener() {
			public void onClick(View v) {
				String text = "Customer Logo -- ShareSDK ";
				Toast.makeText(AnnouncementActivity.this, text, Toast.LENGTH_SHORT).show();
			}
		};
		oks.setCustomerLogo(logo, label, listener);
		oks.show(this);
	}

	class MyPlatformActionListener implements PlatformActionListener {
		@Override
		public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
			AnnouncementActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (context != null) {
						dialog(context, "Share Complete");
					} else {
						Toast.makeText(MobSDK.getContext(), "Share Complete", Toast.LENGTH_SHORT).show();
					}

				}
			});
		}

		@Override
		public void onError(Platform platform, int i, Throwable throwable) {
			throwable.printStackTrace();
			final String error = throwable.toString();
			AnnouncementActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (context != null) {
						dialog(context, "Share Failure" + error);
					} else {
						Toast.makeText(MobSDK.getContext(), "Share Failure" + error, Toast.LENGTH_SHORT).show();
					}
				}
			});
		}

		@Override
		public void onCancel(Platform platform, int i) {
			if (context != null) {
				dialog(context, "Cancel Share");
			} else {
				Toast.makeText(MobSDK.getContext(), "Cancel Share", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
