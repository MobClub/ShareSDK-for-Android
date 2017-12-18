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
 * If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 * 
 * Copyright (c) 2013 mob.com. All rights reserved.
 */
//#endif

package cn.sharesdk.demo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import cn.sharesdk.demo.widget.SlidingMenu;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.TitleLayout;

import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

//#if def{lang} == cn
/** 
 * 授权和取消授权演示页面
 * <p>
 * 由于UI显示的需要授权过的平台显示账户的名称，
 *因此此页面事实上展示的是“获取用户资料”和“取消
 *授权”两个功能。如果想看纯粹的“授权”操作，请参
 *考{@link GetTokenPage}页面的相关代码。
 */
//#elif def{lang} == en
/** page to show how to authorize and get user info */
//#endif
public class AuthPage extends SlidingMenuPage implements
		OnClickListener, PlatformActionListener {
	private View pageView;
	private TitleLayout llTitle;
	private AuthAdapter adapter;

	public AuthPage(SlidingMenu menu) {
		super(menu);
		pageView = getPage();
		
		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_auth);
		
		ListView lvPlats = (ListView) pageView.findViewById(R.id.lvPlats);
		lvPlats.setSelector(new ColorDrawable());
		adapter = new AuthAdapter(this);
		lvPlats.setLayoutAnimation(InLayoutAnim.getAnimationController());
		lvPlats.setAdapter(adapter);
		lvPlats.setOnItemClickListener(adapter);
	}
	
	protected View initPage() {
		return LayoutInflater.from(getContext()).inflate(R.layout.page_auth, null);
	}
	
	public void onClick(View v) {
		if (v.equals(llTitle.getBtnBack())) {
			if (isMenuShown()) {
				hideMenu();
			} else {
				showMenu();
			}
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

	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();
		
		Message msg = new Message();
		msg.arg1 = 2;
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

	//#if def{lang} == cn
	/** 
	 * 处理操作结果
	 * <p>
	 * 如果获取到用户的名称，则显示名称；否则如果已经授权，则显示
	 *平台名称
	 */
	//#elif def{lang} == en
	/** handling user info */
	//#endif
	public boolean handleMessage(Message msg) {
		Platform plat = (Platform) msg.obj;
		String text = MainActivity.actionToString(msg.arg2);
		boolean isCallBackMsg = false;
		switch (msg.arg1) {
			case 1: {
				//#if def{lang} == cn
				// 成功
				//#elif def{lang} == en
				// success
				//#endif
				text = plat.getName() + " completed at " + text;
				isCallBackMsg=true;
				Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
			} break;
			case 2: {
				//#if def{lang} == cn
				// 失败
				//#elif def{lang} == en
				// failed
				//#endif
				text = plat.getName() + " caught error at " + text;
				isCallBackMsg=true;
				Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
			} break;
			case 3: {
				//#if def{lang} == cn
				// 取消
				//#elif def{lang} == en
				// canceled
				//#endif
				text = plat.getName() + " canceled at " + text;
				isCallBackMsg=true;
				Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
			} break;
		}
		
		adapter.notifyDataSetChanged();
		if(isCallBackMsg){
			new AlertDialog.Builder(getContext()).setMessage(text).create().show();
		}
		return false;
	}

	private static class AuthAdapter extends BaseAdapter implements OnItemClickListener {
		private AuthPage page;
		private ArrayList<Platform> platforms;
		
		public AuthAdapter(AuthPage page) {
			this.page = page;
			
			//#if def{lang} == cn
			// 获取平台列表
			//#elif def{lang} == en
			// quests platform list
			//#endif
			Platform[] tmp = ShareSDK.getPlatformList();
			platforms = new ArrayList<Platform>();
			if (tmp == null) {
				return;
			}
			
			for (Platform p : tmp) {
				String name = p.getName();
				if ((p instanceof CustomPlatform) 
						|| !DemoUtils.canGetUserInfo(name)) {
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
				convertView = View.inflate(page.getContext(), R.layout.auth_page_item, null);
			}
			
			int count = getCount();
			View llItem = convertView.findViewById(R.id.llItem);
			int dp10 = ResHelper.dipToPx(parent.getContext(), 10);
			if (count == 1) {
				llItem.setBackgroundResource(R.drawable.list_item_single_normal);
				llItem.setPadding(0, 0, 0, 0);
				convertView.setPadding(dp10, dp10, dp10, dp10);
			} else if (position == 0) {
				llItem.setBackgroundResource(R.drawable.list_item_first_normal);
				llItem.setPadding(0, 0, 0, 0);
				convertView.setPadding(dp10, dp10, dp10, 0);
			} else if (position == count - 1) {
				llItem.setBackgroundResource(R.drawable.list_item_last_normal);
				llItem.setPadding(0, 0, 0, 0);
				convertView.setPadding(dp10, 0, dp10, dp10);
			} else {
				llItem.setBackgroundResource(R.drawable.list_item_middle_normal);
				llItem.setPadding(0, 0, 0, 0);
				convertView.setPadding(dp10, 0, dp10, 0);
			}
			
			Platform plat = getItem(position);
			ImageView ivLogo = (ImageView) convertView.findViewById(R.id.ivLogo);
			Bitmap logo = getIcon(plat);
			if (logo != null && !logo.isRecycled()) {
				ivLogo.setImageBitmap(logo);
			}
			CheckedTextView ctvName = (CheckedTextView) convertView.findViewById(R.id.ctvName);
			ctvName.setChecked(plat.isAuthValid());
			if (plat.isAuthValid()) {
				String userName = plat.getDb().get("nickname");
				if (userName == null || userName.length() <= 0 || "null".equals(userName)) {
					userName = getName(plat);
				}
				ctvName.setText(userName);
			} else {
				ctvName.setText(R.string.not_yet_authorized);
			}
			return convertView;
		}
		
		private Bitmap getIcon(Platform plat) {
			if (plat == null) {
				return null;
			}
			
			String name = plat.getName();
			if (name == null) {
				return null;
			}
			
			String resName = "ssdk_oks_classic_" + plat.getName();
			int resId = ResHelper.getBitmapRes(page.getContext(), resName.toLowerCase());
			return BitmapFactory.decodeResource(page.getResources(), resId);
		}
		
		private String getName(Platform plat) {
			if (plat == null) {
				return "";
			}
			
			String name = plat.getName();
			if (name == null) {
				return "";
			}
			
			int resId = ResHelper.getStringRes(page.getContext(), "ssdk_" + plat.getName());
			return page.getContext().getString(resId);
		}

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Platform plat = getItem(position);
			CheckedTextView ctvName = (CheckedTextView) view.findViewById(R.id.ctvName);
			if (plat == null) {
				ctvName.setChecked(false);
				ctvName.setText(R.string.not_yet_authorized);
				return;
			}
			
			if (plat.isAuthValid()) {
				plat.removeAccount(true);
				ctvName.setChecked(false);
				ctvName.setText(R.string.not_yet_authorized);
				return;
			}
			//这里开启一下SSO，防止OneKeyShare分享时调用了oks.disableSSOWhenAuthorize();把SSO关闭了
			plat.SSOSetting(!CustomShareFieldsPage.getBoolean("enableSSO", true));
//			plat.SSOSetting(true);
			plat.setPlatformActionListener(page);
			plat.authorize();			
		}
		
	}
	
}
