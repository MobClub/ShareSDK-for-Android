package cn.sharesdk.demo.widget;

import java.util.ArrayList;

/**
 * 菜单组（控件内部使用）
 *
 * @author 余勋杰
 */
final class SlidingMenuGroup {
	int id;
	String text;
	private ArrayList<SlidingMenuItem> items;

	SlidingMenuGroup() {
		items = new ArrayList<SlidingMenuItem>();
	}

	void setItem(SlidingMenuItem item) {
		if (item == null) {
			return;
		}

		SlidingMenuItem itemTmp = findItemById(item.id);
		item.group = id;
		if (itemTmp == null) {
			items.add(item);
		} else {
			int index = items.indexOf(itemTmp);
			items.remove(index);
			items.add(index, item);
		}
	}

	SlidingMenuItem findItemById(int id) {
		if (items == null) {
			return null;
		}

		for (SlidingMenuItem item : items) {
			if (item == null) {
				continue;
			}

			if (item.id == id) {
				return item;
			}
		}

		return null;
	}

	int getCount() {
		return items == null ? 0 : items.size();
	}

	SlidingMenuItem getItem(int position) {
		return items.get(position);
	}

}
