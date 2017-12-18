//#if def{lang} == cn
/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 * 
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */
//#elif def{lang} == en
/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version.
 *  If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 * 
 * Copyright (c) 2013 mob.com. All rights reserved.
 */
//#endif

package cn.sharesdk.demo;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.drawable.ColorDrawable;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;

import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

//#if def{lang} == cn
/** 
 * 演示获取用户资料
 * <p>
 * 启动页面时传递一个int类型的字段type，用于标记获取自己的资料（type = 0）
 *还是别人的资料（type = 1）。如果尝试获取别人的资料，示例代码会获取不同
 *平台ShareSDK的官方帐号的资料。
 * <p>
 * 如果资料获取成功，会通过{@link JsonPage}展示
 */
//#elif def{lang} == en
/** page shows how to request your or other user's info */
//#endif
public class GetInforPage extends FakeActivity implements Callback, 
		OnClickListener, PlatformActionListener {
	private int type;
	private TitleLayout llTitle;
	private PlatAdapter adapter;
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void onCreate() {
		activity.setContentView(R.layout.page_get_user_info);
		llTitle = (TitleLayout) activity.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(type == 0 ? R.string.demo_get_my_info 
				: R.string.demo_get_other_info);
		
		ListView lvPlats = (ListView) activity.findViewById(R.id.lvPlats);
		lvPlats.setSelector(new ColorDrawable());
		adapter = new PlatAdapter(this);
		adapter.setType(type);
		lvPlats.setLayoutAnimation(InLayoutAnim.getAnimationController());
		lvPlats.setAdapter(adapter);
	}
	
	public void onClick(View v) {
		if (v.equals(llTitle.getBtnBack())) {
			finish();
		}
	}

	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
		
		Message msg2 = new Message();
		msg2.what = 1;
		msg2.obj = res;
		UIHandler.sendMessage(msg2, this);
	}

	public void onError(Platform palt, int action, Throwable t) {
		t.printStackTrace();
		
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = palt;
		UIHandler.sendMessage(msg, this);
	}

	public void onCancel(Platform plat, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	//#if def{lang} == cn
	/** 处理操作结果 */
	//#elif def{lang} == en
	/** handling request result */
	//#endif
	@SuppressWarnings("unchecked")
	public boolean handleMessage(Message msg) {
		switch(msg.what) {
			case 1: {
				JsonPage page = new JsonPage();
				String title = llTitle.getTvTitle().getText().toString();
				page.setData(title, (HashMap<String, Object>) msg.obj);
				page.show(activity, null);
			} break;
			default: {
				Platform plat = (Platform) msg.obj;
				String text = MainActivity.actionToString(msg.arg2);
				switch (msg.arg1) {
					case 1: {
						//#if def{lang} == cn
						// 成功
						//#elif def{lang} == en
						// success
						//#endif
						text = plat.getName() + " completed at " + text;
					} break;
					case 2: {
						//#if def{lang} == cn
						// 失败
						//#elif def{lang} == en
						// failed
						//#endif
						text = plat.getName() + " caught error at " + text;
					} break;
					case 3: {
						//#if def{lang} == cn
						// 取消
						//#elif def{lang} == en
						// canceled
						//#endif
						text = plat.getName() + " canceled at " + text;
					} break;
				}
				
				Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
			} break;
		}
		return false;
	}

	private static class PlatAdapter extends BaseAdapter implements OnClickListener {
		private GetInforPage page;
		private ArrayList<Platform> platforms;
		//#if def{lang} == cn
		// 要获取资料的对象：0：自己的资料；1：他人的资料
		//#elif def{lang} == en
		// target to request info: 0, myself; 1, other people
		//#endif
		private int type;

		public PlatAdapter(GetInforPage page) {
			this.page = page;
		}
		
		public void setType(int type) {
			this.type = type;
			
			//#if def{lang} == cn
			// 获取平台列表
			//#elif def{lang} == en
			// request platform list
			//#endif
			Platform[] tmp = ShareSDK.getPlatformList();
			platforms = new ArrayList<Platform>();
			if (tmp == null) {
				return;
			}
			
			if (type == 0) {
				for (Platform p : tmp) {
					String name = p.getName();
					if ((p instanceof CustomPlatform) 
							|| !DemoUtils.canGetUserInfo(name)) {
						continue;
					}
					platforms.add(p);
				}
			} else {
				for (Platform p : tmp) {
					String name = p.getName();
					if ("SinaWeibo".equals(name) || "TencentWeibo".equals(name)) {
						platforms.add(p);
					}
					continue;
				}
			}
		}
		
		public int getCount() {
			return platforms == null ? 0 : platforms.size();
		}

		public Platform getItem(int position) {
			return platforms.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(page.activity, R.layout.button_list_item, null);
			}
			
			Platform plat = getItem(position);
			Button btn = (Button) convertView.findViewById(R.id.btn);
			btn.setOnClickListener(this);
			int resId = type == 0 ? R.string.get_user_info_format : R.string.get_other_info_format;
			btn.setText(page.activity.getString(resId, getName(plat)));
			btn.setTag(plat);
			
			return convertView;
		}
		
		private String getName(Platform plat) {
			if (plat == null) {
				return "";
			}
			
			String name = plat.getName();
			if (name == null) {
				return "";
			}
			
			int resId = ResHelper.getStringRes(page.activity, "ssdk_" + plat.getName());
			return page.activity.getString(resId);
		}
		
		public void onClick(View v) {
			Platform plat = (Platform) v.getTag();
			String name = plat.getName();
			plat.setPlatformActionListener(page);
			String account = null;
			if ("SinaWeibo".equals(name)) {
				account = MainAdapter.SDK_SINAWEIBO_UID;
			} else if ("TencentWeibo".equals(name)) {
				account = MainAdapter.SDK_TENCENTWEIBO_UID;
			}
			plat.showUser(type == 0 ? null : account);
		}
		
	}
	
}
