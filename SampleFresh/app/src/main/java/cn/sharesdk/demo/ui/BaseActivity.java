package cn.sharesdk.demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.sharesdk.demo.App;
import cn.sharesdk.demo.manager.BasePresenter;
import cn.sharesdk.demo.manager.ViewHandlerCall;

/**
 * Created by yjin on 2017/5/9.
 */

/**
 * 基类，抽象的救赎
 */
public abstract class BaseActivity extends AppCompatActivity implements ViewHandlerCall {
	protected BasePresenter presenter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		App app = new App();
		app.setCurrentActivity(this);
		initData();
		initView();
		presenter = createPresenter();
		presenter.attachView(this);
	}

	//界面加载类
	public abstract int getLayoutId();
	
	//界面加载类
	public abstract void initView();
	
	//数据初始化完成操作
	public abstract void initData();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.detailView();
	}

	//必须实现的中间处理类
	protected abstract BasePresenter createPresenter();
}
