package cn.sharesdk.demo.adapter;

import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import cn.sharesdk.demo.ui.BaseFragment;


public class PagerAdapter extends FragmentStatePagerAdapter {
	private String[] titles;
	private List<BaseFragment> fragments;

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@NonNull
	@Override
	public BaseFragment getItem(int position) {
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		BaseFragment fragment = null;
		try {
			fragment = (BaseFragment) super.instantiateItem(container, position);
		} catch (Throwable e) {
			Log.e("PagerAdapter","instantiateItem fail");
		}
		return super.instantiateItem(container, position);
	}

	public List<BaseFragment> getFragments() {
		return fragments;
	}

	public void setFragments(List<BaseFragment> fragments) {
		this.fragments = fragments;
	}
}
