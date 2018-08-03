package cn.sharesdk.demo.activitys;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.sharesdk.demo.App;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.adapter.SharePlatformAdapter;
import cn.sharesdk.demo.entity.BaseEntity;
import cn.sharesdk.demo.entity.ShareListItemInEntity;
import cn.sharesdk.demo.manager.BasePresenter;
import cn.sharesdk.demo.manager.platform.PlatformShareConstant;
import cn.sharesdk.demo.manager.share.ShareTypeManager;
import cn.sharesdk.demo.manager.ui.SharePlatformPresenter;
import cn.sharesdk.demo.ui.BaseActivity;
import cn.sharesdk.framework.Platform;

/**
 * Created by yjin on 2017/5/17.
 */

/**
 * 具体类型分享界面
 */

public class SharePlatformTypeActivity extends BaseActivity implements View.OnClickListener, SharePlatformAdapter.OnClickItemListener {
	private ImageView callBack;
	private TextView shareTitle;
	private ShareListItemInEntity entity;
	private RecyclerView recyclerView;
	private SharePlatformAdapter adapter;
	private List<Integer> lists;
	private String name;

	@Override
	public int getLayoutId() {
		return R.layout.share_platform_type_activity;
	}

	@Override
	public void initView() {
		recyclerView = (RecyclerView) this.findViewById(R.id.mSharePlatform);
		callBack = (ImageView) this.findViewById(R.id.mReback);
		callBack.setOnClickListener(this);
		shareTitle = (TextView) this.findViewById(R.id.mTitle);
		recyclerView = (RecyclerView) this.findViewById(R.id.mSharePlatform);
		if (entity != null) {
			shareTitle.setText(getString(R.string.share_platform_name) + entity.getName());
		}
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
		recyclerView.setLayoutManager(linearLayoutManager);
		adapter = new SharePlatformAdapter(lists, this);
		adapter.setOnClickItemListener(this);
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void initData() {
		if (lists == null) {
			lists = new ArrayList<>();
		}
		Intent intent = getIntent();
		if (intent != null) {
			entity = (ShareListItemInEntity) intent.getSerializableExtra("shareEntity");
			name = intent.getStringExtra("name");
		}
		if (entity != null) {
			lists.clear();
			if (!TextUtils.isEmpty(name)) {
				Integer[] plats = PlatformShareConstant.byNamePlatforms(name);
				List<Integer> ls = Arrays.asList(plats);
				lists.addAll(ls);
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

	protected BasePresenter createPresenter() {
		return new SharePlatformPresenter();
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
	public void onClick(View v) {
		if (v.getId() == R.id.mReback) {
			finish();
		}
	}

	@Override
	public void onItemClick(int platformCode) {
		Platform platform = App.getInstance().getPlatformList().get(0);
		if (platform != null) {
			ShareTypeManager shareManager = new ShareTypeManager(this, platform);
			shareManager.shareShow(platformCode);
		}
	}

}
