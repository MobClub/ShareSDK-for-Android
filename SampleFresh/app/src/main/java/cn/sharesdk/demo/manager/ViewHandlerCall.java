package cn.sharesdk.demo.manager;

import cn.sharesdk.demo.entity.BaseEntity;


public interface ViewHandlerCall {
	public void showLoad();

	public void cancelLoad();

	public void refreshResult(BaseEntity baseEntity);
}
