package cn.sharesdk.demo.widget;

import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;

/**
 * 侧栏适配器
 *
 * @author 余勋杰
 */
public abstract class MenuAdapter {
	private ArrayList<SlidingMenuGroup> menus;

	public MenuAdapter(final SlidingMenu menu) {
		menus = new ArrayList<SlidingMenuGroup>();
	}

	private SlidingMenuGroup findGroupById(int id) {
		if (menus == null) {
			return null;
		}

		for (SlidingMenuGroup group : menus) {
			if (group == null) {
				continue;
			}

			if (group.id == id) {
				return group;
			}
		}

		return null;
	}

	public void setGroup(int id, String text) {
		SlidingMenuGroup group = findGroupById(id);
		if (group == null) {
			group = new SlidingMenuGroup();
			group.id = id;
			menus.add(group);
		}
		group.text = text;
	}

	void setGroup(SlidingMenuGroup group) {
		if (group == null) {
			return;
		}

		SlidingMenuGroup groupTmp = findGroupById(group.id);
		if (groupTmp == null) {
			menus.add(group);
			return;
		}

		int index = menus.indexOf(groupTmp);
		menus.remove(index);
		menus.add(index, group);
	}

	public void setItem(int groupId, SlidingMenuItem item) {
		if (item == null) {
			return;
		}

		SlidingMenuGroup group = findGroupById(groupId);
		if (group == null) {
			return;
		}

		group.setItem(item);
	}

	public View getMenuTitle() {
		return null;
	}

	int getGroupCount() {
		return menus == null ? 0 : menus.size();
	}

	SlidingMenuGroup getGroup(int position) {
		return menus.get(position);
	}

	protected String getTitle(int position) {
		return menus.get(position).text;
	}

	public abstract View getGroupView(int position, ViewGroup menu);

	protected SlidingMenuItem getItem(int groupPosition, int position) {
		return menus.get(groupPosition).getItem(position);
	}

	public abstract View getItemView(SlidingMenuItem item, ViewGroup menu);

	public void notifyDataSetChanged(SlidingMenuItem item) {

	}

	public boolean onItemTrigger(SlidingMenuItem item) {
		return false;
	}

	public void onMenuSwitch(boolean menuShown) {

	}

	public SlidingMenuItem getMenuItem(int groupId, int itemId) {
		SlidingMenuGroup group = findGroupById(groupId);
		if (group == null) {
			return null;
		}

		return group.findItemById(itemId);
	}

}
