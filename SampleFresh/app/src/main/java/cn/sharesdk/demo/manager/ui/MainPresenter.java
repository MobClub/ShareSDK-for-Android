package cn.sharesdk.demo.manager.ui;

import cn.sharesdk.demo.MainActivity;
import cn.sharesdk.demo.entity.OutBaseEntity;
import cn.sharesdk.demo.manager.BasePresenter;
import cn.sharesdk.demo.ui.BaseActivity;


public class MainPresenter extends BasePresenter<BaseActivity> {

	private MainActivity activity;
	@Override
	public void attachView(BaseActivity view) {
		super.attachView(view);
		activity = (MainActivity) getView();
	}

	@Override
	public void requestData(OutBaseEntity baseEntity) {
		super.requestData(baseEntity);
	}

	@Override
	public void detailView() {
		super.detailView();
		activity.cancelLoad();
	}
}
