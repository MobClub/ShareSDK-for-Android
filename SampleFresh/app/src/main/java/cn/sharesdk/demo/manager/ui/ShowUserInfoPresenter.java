package cn.sharesdk.demo.manager.ui;

import cn.sharesdk.demo.activitys.ShowUserInfoActivity;
import cn.sharesdk.demo.manager.BasePresenter;

/**
 * Created by yjin on 2017/5/19.
 */

public class ShowUserInfoPresenter extends BasePresenter<ShowUserInfoActivity> {
	private ShowUserInfoActivity activity;

	@Override
	public void attachView(ShowUserInfoActivity view) {
		super.attachView(view);
		this.activity = view;
	}
}
