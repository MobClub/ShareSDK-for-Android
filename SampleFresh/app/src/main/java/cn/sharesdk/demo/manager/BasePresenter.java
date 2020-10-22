package cn.sharesdk.demo.manager;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import cn.sharesdk.demo.entity.OutBaseEntity;

/**
 * Created by yjin on 2017/5/10.
 */

/**
 *
 * 抽象Presenter类，提供给Activity与具体业务处理的管理类。
 */
public abstract class BasePresenter<T> {
	protected Reference<T> viewRefer;

	public void attachView(T view){
		viewRefer = new WeakReference<T>(view);
	}

	public T getView(){
		return viewRefer.get();
	}

	public void detailView(){
		if(viewRefer != null){
			viewRefer.clear();
			viewRefer = null;
		}
	}

	public void requestData(OutBaseEntity baseEntity){

	}
}
