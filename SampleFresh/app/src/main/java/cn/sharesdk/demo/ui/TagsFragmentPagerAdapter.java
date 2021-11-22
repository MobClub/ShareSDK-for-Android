package cn.sharesdk.demo.ui;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class TagsFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 2;
    private TagsHomeFragment homeFragment = null;
    private TagsMyFragment myFragment = null;

    public TagsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        homeFragment = new TagsHomeFragment();
        myFragment = new TagsMyFragment();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case TagsItemActivity.PAGE_ONE:
                fragment = homeFragment;
                break;
            case TagsItemActivity.PAGE_FOUR:
                fragment = myFragment;
                break;
        }
        return fragment;
    }
}
