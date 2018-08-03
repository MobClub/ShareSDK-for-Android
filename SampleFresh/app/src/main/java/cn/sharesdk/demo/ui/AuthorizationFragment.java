package cn.sharesdk.demo.ui;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.demo.R;
import cn.sharesdk.demo.adapter.AuthorizationAdapter;
import cn.sharesdk.demo.entity.PlatformEntity;
import cn.sharesdk.demo.entity.PlatformMananger;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.entity.ShareInEntityManager;
import cn.sharesdk.demo.platform.PlatformAuthorizeUserInfoManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by yjin on 2017/5/9.
 */

public class AuthorizationFragment extends BaseFragment implements AuthorizationAdapter.AuthorizationOnItemClickListener, PlatformActionListener {
	private View view;
	private RecyclerView recyclerView;
	private AuthorizationAdapter adapter;
	private List<PlatformEntity> lists;
	private Platform plat;
	private int curentPostion = 0;
	TextView textView = null;
	private PlatformAuthorizeUserInfoManager platAuth;

	public void initView(View view) {
		recyclerView = (RecyclerView) view.findViewById(R.id.mAuthorization);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
		recyclerView.setLayoutManager(linearLayoutManager);
		adapter = new AuthorizationAdapter(getContext(), lists);
		recyclerView.setAdapter(adapter);
		adapter.setAuthorizationOnItemClickListener(this);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
	}

	public void initData() {
		if (lists == null) {
			lists = new ArrayList<>();
		}
		lists.add(ShareInEntityManager.createNormalInLand(getContext()));
		lists.addAll(PlatformMananger.getInstance(getContext()).getChinaListNormal());
		lists.add(ShareInEntityManager.createNormalInternational(getContext()));
		lists.addAll(PlatformMananger.getInstance(getContext()).getNormalList());
		lists.add(ShareInEntityManager.createNormalSystem(getContext()));
		lists.addAll(PlatformMananger.getInstance(getContext()).getSystemListNormal());
	}

	@Override
	public int getLayoutId() {
		return R.layout.authorization_fragment;
	}

	@Override
	public void OnItemClickListener(View view, int position) {
		PlatformEntity entity = lists.get(position);
		curentPostion = position;
		if (view instanceof TextView) {
			textView = (TextView) view;
		}
		if (entity != null) {
			plat = entity.getmPlatform();
		}

		if (plat.isAuthValid()) {
			plat.removeAccount(true);
			textView.setText(getActivity().getString(R.string.authorization_txt));
			return;
		}
		//这里开启一下SSO，防止OneKeyShare分享时调用了oks.disableSSOWhenAuthorize();把SSO关闭了
		//plat.SSOSetting(false);
		//plat.setPlatformActionListener(this);
		if (platAuth == null) {
			platAuth = new PlatformAuthorizeUserInfoManager(getActivity());
		}
		platAuth.doAuthorize(plat);
	}

	@Override
	public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
		if (textView != null) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					textView.setText(getActivity().getString(R.string.authorization_txt_delete));
				}
			});
		}
		String msg = ResourcesManager.actionToString(i);
		String text = plat.getName() + " completed at " + msg;
		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
		adapter.notifyItemChanged(curentPostion);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onError(Platform platform, int i, Throwable throwable) {
		String msg = ResourcesManager.actionToString(i);
		String text = plat.getName() + " caught error at " + msg;
		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCancel(Platform platform, int i) {
		String msg = ResourcesManager.actionToString(i);
		String text = plat.getName() + " canceled at " + msg;
		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
	}
}
