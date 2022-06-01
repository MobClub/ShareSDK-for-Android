package cn.sharesdk.demo;

import android.app.Application;
import android.util.Log;

import com.mob.MobSDK;
import com.mob.tools.utils.Hashon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.ui.BaseActivity;
import cn.sharesdk.demo.utils.PrivacyDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.loopshare.LoopShareResultListener;


public class App extends Application {
	private BaseActivity currentActivity;
	public List<Platform> platformList = new ArrayList<>();
	public  static App app = null;

	@Override
	public void onCreate() {
		super.onCreate();
		MobSDK.submitPolicyGrantResult(true,null);
		//loopshare init and set Listener
		ShareSDK.prepareLoopShare(new LoopShareResultListener() {
			@Override
			public void onResult(Object var1) {
				String test = new Hashon().fromHashMap((HashMap<String, Object>) var1);
				Log.e("WWW", "LoopShareResultListener onResult " + test);
			}

			@Override
			public void onError(Throwable t) {
				Log.e("WWW", "LoopShareResultListener onError " + t);
			}
		});
		Log.e("WWW", " ShareSDK.prepareLoopShare() successed ");

		this.app = this;
		ResourcesManager.getInstace(MobSDK.getContext());
	}

	public static App getInstance(){
		return app;
	}

	public List<Platform> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(Platform platform) {
		if(platformList.size() > 0){
			platformList.clear();
		}
		if(platform != null){
			platformList.add(platform);
		}
	}

	public void setCurrentActivity(BaseActivity activity){
		this.currentActivity = activity;
	}
}
