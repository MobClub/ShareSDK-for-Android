package cn.sharesdk.demo.ui;

import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
	private View view = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(getLayoutId(), null);
			initData();
			initView(view);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	public abstract void initView(View mView);

	public abstract void initData();

	public abstract int getLayoutId();


}
