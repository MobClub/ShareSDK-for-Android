/*
 * Offical Website:http://www.mob.com
 * Support QQ: 4006852216
 * Offical Wechat Account:ShareSDK   (We will inform you our updated news at the first time by Wechat, if we release a new version. If you get any problem, you can also contact us with Wechat, we will reply you within 24 hours.)
 *
 * Copyright (c) 2013 mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare.theme.skyblue;

import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.PlatformListFakeActivity;

import static cn.sharesdk.framework.utils.R.getLayoutRes;
import static cn.sharesdk.framework.utils.R.getStringRes;

public class PlatformListPage extends PlatformListFakeActivity implements View.OnClickListener {
	private PlatformGridViewAdapter gridViewAdapter;

	public void onCreate() {
		super.onCreate();
		activity.setContentView(getLayoutRes(activity, "skyblue_share_platform_list"));

		initView();
	}

	private void initView() {
		View backImageView = findViewByResName("backImageView");
		backImageView.setTag(android.R.string.cancel);
		backImageView.setOnClickListener(this);

		View okImageView = findViewByResName("okImageView");
		okImageView.setTag(android.R.string.ok);
		okImageView.setOnClickListener(this);

		gridViewAdapter = new PlatformGridViewAdapter(activity);
		gridViewAdapter.setCustomerLogos(customerLogos);

		GridView gridView = (GridView) findViewByResName("gridView");
		gridView.setAdapter(gridViewAdapter);

		new AsyncTask<Void, Void, Platform[]>() {

			@Override
			protected Platform[] doInBackground(Void... params) {
				return ShareSDK.getPlatformList();
			}

			@Override
			protected void onPostExecute(Platform[] platforms) {
				gridViewAdapter.setData(platforms, hiddenPlatforms);
			}
		}.execute();
	}

	public void onClick(View v) {
		Object tag = v.getTag();
		if(tag == null || !(tag instanceof Integer))
			return;

		switch ((Integer)tag) {
			case android.R.string.cancel:
				setCanceled(true);
				finish();
				break;
			case android.R.string.ok:
				onShareButtonClick(v);
				break;
		}
	}

	private void onShareButtonClick(View v) {
		if(gridViewAdapter == null || "locked".equals(v.getTag()))
			return;

		List<Object> checkedPlatforms = gridViewAdapter.getCheckedItems();
		if(checkedPlatforms.size() == 0){
			Toast.makeText(activity, getStringRes(activity, "select_one_plat_at_least"), Toast.LENGTH_SHORT).show();
			return;
		}

		v.setTag("locked");
		onShareButtonClick(v, checkedPlatforms);
	}

}
