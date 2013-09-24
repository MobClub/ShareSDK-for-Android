/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;

/**
 * 演示获取用户资料
 * <p>
 * 启动页面时传递一个int类型的字段type，用于标记获取自己的资料（type = 0）
 *还是别人的资料（type = 1）。如果尝试获取别人的资料，示例代码会获取不同
 *平台Share SDK的官方帐号的资料。
 * <p>
 * 如果资料获取成功，会通过{@link JsonPage}展示
 */
public class GetInforPage extends Activity implements Callback,
		OnClickListener, PlatformActionListener {
	private TitleLayout llTitle;
	private Handler handler;
	private PlatAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int type = getIntent().getIntExtra("type", 0);
		handler = new Handler(this);
		setContentView(R.layout.page_get_user_info);
		llTitle = (TitleLayout) findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(type == 0 ? R.string.demo_get_my_info
				: R.string.demo_get_other_info);

		ListView lvPlats = (ListView) findViewById(R.id.lvPlats);
		lvPlats.setSelector(new ColorDrawable());
		adapter = new PlatAdapter(this);
		adapter.setType(type);
		lvPlats.setAdapter(adapter);
	}

	/** 具体获取资料的演示代码 */
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
		handler.sendMessage(msg);

		Message msg2 = new Message();
		msg2.what = 1;
		JsonUtils ju = new JsonUtils();
		String json = ju.fromHashMap(res);
		msg2.obj = ju.format(json);
		handler.sendMessage(msg2);
	}

	public void onError(Platform palt, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = palt;
		handler.sendMessage(msg);
	}

	public void onCancel(Platform plat, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = plat;
		handler.sendMessage(msg);
	}

	/** 处理操作结果 */
	public boolean handleMessage(Message msg) {
		switch(msg.what) {
			case 1: {
				Intent i = new Intent(this, JsonPage.class);
				i.putExtra("title", llTitle.getTvTitle().getText().toString());
				i.putExtra("data", String.valueOf(msg.obj));
				startActivity(i);
			}
			break;
			default: {
				Platform plat = (Platform) msg.obj;
				String text = MainActivity.actionToString(msg.arg2);
				switch (msg.arg1) {
					case 1: {
						// 成功
						text = plat.getName() + " completed at " + text;
					}
					break;
					case 2: {
						// 失败
						text = plat.getName() + " caught error at " + text;
					}
					break;
					case 3: {
						// 取消
						text = plat.getName() + " canceled at " + text;
					}
					break;
				}

				Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			}
			break;
		}
		return false;
	}

	private static class PlatAdapter extends BaseAdapter implements OnClickListener {
		private GetInforPage page;
		private ArrayList<Platform> platforms;
		// 要获取资料的对象：0：自己的资料；1：他人的资料
		private int type;

		public PlatAdapter(GetInforPage page) {
			this.page = page;
		}

		public void setType(int type) {
			this.type = type;

			// 获取平台列表
			Platform[] tmp = ShareSDK.getPlatformList(page);
			platforms = new ArrayList<Platform>();

			if (type == 0) {
				for (Platform p : tmp) {
					String name = p.getName();
					if ("Wechat".equals(name) || "WechatMoments".equals(name)
							|| "QQ".equals(name) || "Email".equals(name)
							|| "ShortMessage".equals(name) || "GooglePlus".equals(name)
							|| "Pinterest".equals(name)) {
						continue;
					}
					platforms.add(p);
				}
			}
			else {
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
				convertView = View.inflate(page, R.layout.button_list_item, null);
			}

			Platform plat = getItem(position);
			Button btn = (Button) convertView.findViewById(R.id.btn);
			btn.setOnClickListener(this);
			int resId = type == 0 ? R.string.get_user_info_format : R.string.get_other_info_format;
			btn.setText(page.getString(resId, getName(plat)));
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

			int resId = cn.sharesdk.framework.utils.R.getStringRes(page, plat.getName());
			return page.getString(resId);
		}

		public void onClick(View v) {
			Platform plat = (Platform) v.getTag();
			String name = plat.getName();
			plat.setPlatformActionListener(page);
			String account = null;
			if ("SinaWeibo".equals(name)) {
				account = MainAdapter.SDK_SINAWEIBO_UID;
			}
			else if ("TencentWeibo".equals(name)) {
				account = MainAdapter.SDK_TENCENTWEIBO_UID;
			}
			plat.showUser(type == 0 ? null : account);
		}

	}

}
