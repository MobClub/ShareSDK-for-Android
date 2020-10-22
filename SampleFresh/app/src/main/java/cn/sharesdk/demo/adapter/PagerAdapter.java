package cn.sharesdk.demo.adapter;

import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import cn.sharesdk.demo.ui.BaseFragment;

/**
 * Created by yjin on 2017/5/10.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
	private String[] titles;
	private List<BaseFragment> fragments;

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public BaseFragment getItem(int position) {
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		BaseFragment fragment = null;
		try {
			fragment = (BaseFragment) super.instantiateItem(container, position);
		} catch (Exception e) {

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
