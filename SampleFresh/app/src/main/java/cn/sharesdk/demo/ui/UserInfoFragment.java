package cn.sharesdk.demo.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.demo.R;
import cn.sharesdk.demo.activitys.ShowUserInfoActivity;
import cn.sharesdk.demo.adapter.UserInfoAdapter;
import cn.sharesdk.demo.entity.PlatformEntity;
import cn.sharesdk.demo.entity.PlatformMananger;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.entity.ShareInEntityManager;
import cn.sharesdk.demo.platform.PlatformAuthorizeUserInfoManager;
import cn.sharesdk.demo.utils.StrUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by yjin on 2017/5/9.
 */

public class UserInfoFragment extends BaseFragment implements UserInfoAdapter.UserInfoOnItemClickListener,Handler.Callback{
	private View view;
	private RecyclerView userInfo;
	private List<PlatformEntity> lists;
	private UserInfoAdapter adapter;
	private Platform plat;
	private UserInfoFragment userInfoFragment;
	private PlatformAuthorizeUserInfoManager platAuth;

	public void initView(View view){
		userInfo = (RecyclerView)view.findViewById(R.id.mUserInfo);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
		userInfo.setLayoutManager(linearLayoutManager);
		adapter = new UserInfoAdapter(getContext(),lists);
		userInfo.setAdapter(adapter);
		adapter.setAuthorizationOnItemClickListener(this);
		userInfo.setItemAnimator(new DefaultItemAnimator());
	}

	public void initData(){
		if(lists == null){
			lists = new ArrayList<>();
		}
		lists.add(ShareInEntityManager.createNormalInLand(getContext()));
		lists.addAll(PlatformMananger.getInstance(getContext()).getChinaListNormalUserInfo());
		lists.add(ShareInEntityManager.createNormalInternational(getContext()));
		lists.addAll(PlatformMananger.getInstance(getContext()).getNormalListUserInfo());
		userInfoFragment = this;
	}

	@Override
	public int getLayoutId() {
		return R.layout.userinfo_fragment;
	}

	@Override
	public void OnItemClickListener(View view, int position) {
		PlatformEntity formEntity = lists.get(position);
		if(formEntity != null){
			plat = formEntity.getmPlatform();
			String name = plat.getName();
			plat.setPlatformActionListener(new PlatformActionListener() {
				@Override
				public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
					Message msg = new Message();
					msg.what = 1;
					msg.arg2 = i;
					msg.obj = hashMap;
					UIHandler.sendMessage(msg, UserInfoFragment.this);
				}

				@Override
				public void onError(Platform platform, int i, Throwable throwable) {
					Message msg = new Message();
					msg.what = 2;
					msg.arg2 = i;
					msg.obj = throwable;
					UIHandler.sendMessage(msg, UserInfoFragment.this);
				}

				@Override
				public void onCancel(Platform platform, int i) {
					Message msg = new Message();
					msg.what = 3;
					msg.arg2 = i;
					msg.obj = plat;
					UIHandler.sendMessage(msg, UserInfoFragment.this);
				}
			});
			String account = null;
			if ("sinaWeibo".equals(name)) {
				account = PlatformMananger.SDK_SINAWEIBO_UID;
			} else if ("tencentWeibo".equals(name)) {
				account = PlatformMananger.SDK_TENCENTWEIBO_UID;
			}
			if(platAuth == null){
				platAuth = new PlatformAuthorizeUserInfoManager(getActivity());
			}
			platAuth.doUserInfo(plat);
		}
	}

	public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap){
		String msg = ResourcesManager.actionToString(i);
		String text = plat.getName() + " completed at " + msg;
		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
		adapter.notifyItemChanged(adapter.getOnClickCurrentPostion());
		adapter.notifyItemRangeChanged(0, lists.size() - 1);
		adapter.notifyDataSetChanged();
		String userInfo = StrUtils.format("", hashMap);
		if(!TextUtils.isEmpty(userInfo)){
			Intent intent = new Intent(getActivity(), ShowUserInfoActivity.class);
			intent.putExtra("userInfo",userInfo);
			startActivity(intent);
		}
	}

	public void onError(Platform platform, int i, Throwable throwable){
		String msg = ResourcesManager.actionToString(i);
		String text = plat.getName() + " caught error at " + msg;
		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
	}

	public void onCancel(Platform platform, int i){
		String msg = ResourcesManager.actionToString(i);
		String text = plat.getName() + " canceled at " + msg;
		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case 1: {
				onComplete(plat, msg.arg2, (HashMap<String, Object>) msg.obj);
			} break;
			case 2: {
				onError(plat, msg.arg2, (Throwable) msg.obj);
			} break;
			case 3: {
				onCancel(plat, msg.arg2);
			} break;
		}
		return false;
	}
}
