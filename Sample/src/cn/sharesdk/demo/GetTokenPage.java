/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 mob.com. All rights reserved.
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

/** page to show how to authorize and get accesstoken by sharesdk */
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

	/** display authorize result by toast */
	public boolean handleMessage(Message msg) {
		Platform plat = (Platform) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: {
				// success
				text = plat.getName() + " get token: " + plat.getDb().getToken();
			}
			break;
			case 2: {
				// failed
				text = plat.getName() + " caught error";
			}
			break;
			case 3: {
				// canceled
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

			// request platform list
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

			int resId = com.mob.tools.utils.R.getStringRes(page.activity, plat.getName());
			return page.activity.getString(resId);
		}

		public void onClick(View v) {
			Platform plat = (Platform) v.getTag();
			plat.setPlatformActionListener(page);
			plat.authorize();
		}

	}

}
