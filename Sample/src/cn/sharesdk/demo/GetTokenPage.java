/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package cn.sharesdk.demo;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.CustomPlatform;

import com.mob.tools.FakeActivity;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;

import com.mob.tools.utils.UIHandler;

import cn.sharesdk.onekeyshare.ShareCore;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/** 演示授权并获取获取AccessToken */
public class GetTokenPage extends FakeActivity implements Callback,
		OnClickListener, PlatformActionListener {
	private TitleLayout llTitle;
	private AuthAdapter adapter;

	public void onCreate() {
		activity.setContentView(R.layout.page_get_access_token);
		llTitle = (TitleLayout) activity.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.demo_get_access_token);

		ListView lvPlats = (ListView) activity.findViewById(R.id.lvPlats);
		lvPlats.setSelector(new ColorDrawable());
		lvPlats.setLayoutAnimation(InLayoutAnim.getAnimationController());
		adapter = new AuthAdapter(this);
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
	}

	public void onCancel(Platform plat, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	/** 通过Toast显示操作结果 */
	public boolean handleMessage(Message msg) {
		Platform plat = (Platform) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: {
				// 成功
				text = plat.getName() + " get token: " + plat.getDb().getToken();
			}
			break;
			case 2: {
				// 失败
				text = plat.getName() + " caught error";
			}
			break;
			case 3: {
				// 取消
				text = plat.getName() + " authorization canceled";
			}
			break;
		}

		Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
		return false;
	}

	private static class AuthAdapter extends BaseAdapter implements OnClickListener {
		private GetTokenPage page;
		private ArrayList<Platform> platforms;

		public AuthAdapter(GetTokenPage page) {
			this.page = page;

			// 获取平台列表
			Platform[] tmp = ShareSDK.getPlatformList();
			platforms = new ArrayList<Platform>();
			if (tmp == null) {
				return;
			}

			for (Platform p : tmp) {
				String name = p.getName();
				if ((p instanceof CustomPlatform)
						|| !ShareCore.canAuthorize(p.getContext(), name)) {
					continue;
				}
				platforms.add(p);
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
			btn.setText(page.activity.getString(R.string.get_token_format, getName(plat)));
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

			int resId = com.mob.tools.utils.R.getStringRes(page.activity, "ssdk_" + plat.getName());
			return page.activity.getString(resId);
		}

		public void onClick(View v) {
			Platform plat = (Platform) v.getTag();
			plat.setPlatformActionListener(page);
			plat.removeAccount(true);
			plat.authorize();
		}

	}

}
