/*
 * Copyright (c) Mob.COM $year.14-10-27 下午4:01
 *
 * @author Milk <jecelyin@gmail.com>
 */

package cn.sharesdk.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

import cn.sharesdk.demo.widget.SlidingMenu;
import cn.sharesdk.framework.TitleLayout;

/**
 * @author Jecelyin <jecelyin@gmail.com>
 * @since 2014.10.27
 */
public class CustomShareFieldsPage extends SlidingMenuPage implements View.OnClickListener, AdapterView.OnItemClickListener {
	private final TitleLayout llTitle;
	private final static String[] KEYS = {
			"enableSSO(1,0)"
			,"theme(classic,skyblue)"
			,"title"
			,"titleUrl"
			,"text"
			,"imagePath"
			,"imageUrl"
			,"url"
			,"filePath"
			,"comment"
			,"site"
			,"siteUrl"
			,"venueName"
			,"venueDescription"
	};
	private final static HashMap<String, String> storeMap = new HashMap<String, String>(KEYS.length);
	private final CustomFieldsAdapter adapter;

	public static String getString(String key, String def) {
		if(storeMap.containsKey(key))
			return storeMap.get(key);
		return def;
	}

	public static boolean getBoolean(String key, boolean def) {
		if(storeMap.containsKey(key))
		{
			String val = storeMap.get(key).trim();
			return "1".equals(val) || "yes".equals(val) || "true".equals(val);
		}
		return def;
	}

	private static void setString(String key, String value) {
		storeMap.put(key, value);
	}

	public CustomShareFieldsPage(SlidingMenu menu) {
		super(menu);
		View pageView = getPage();
		llTitle = (TitleLayout) pageView.findViewById(R.id.llTitle);
		llTitle.getBtnBack().setOnClickListener(this);
		llTitle.getTvTitle().setText(R.string.sm_item_custom_fields);

		adapter = new CustomFieldsAdapter(getContext().getApplicationContext());
		ListView listView = (ListView)pageView.findViewById(R.id.listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	protected View initPage() {
		View v = View.inflate(getContext(), R.layout.page_custom_fields, null);
		((ListView) v.findViewById(R.id.listView)).setLayoutAnimation(InLayoutAnim.getAnimationController());
		return v;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(llTitle.getBtnBack())) {
			if (isMenuShown()) {
				hideMenu();
			}
			else {
				showMenu();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final String key = KEYS[position];
		String value = (String) adapter.getItem(position);

		AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
		alert.setMessage(key + " :");

		// Set an EditText view to get user input
		final EditText editText = new EditText(getContext());
		editText.setText(value);
		alert.setView(editText);

		alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String newValue = editText.getText().toString().trim();
				setString(key.replaceAll("\\(.+?\\)", ""), newValue);
				dialog.dismiss();
				adapter.notifyDataSetChanged();
			}
		});

		alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alert.show();
	}

	private static class CustomFieldsAdapter extends BaseAdapter {

		private final Context context;

		public CustomFieldsAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return KEYS.length;
		}

		@Override
		public Object getItem(int position) {
			return getString(KEYS[position].replaceAll("\\(.+?\\)",""), "");
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView keyTextView, valueTextView;
			if(convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.page_custom_fields_item, null);
				keyTextView = (TextView) convertView.findViewById(R.id.keyTextView);
				valueTextView = (TextView) convertView.findViewById(R.id.valueTextView);
				convertView.setTag(R.id.keyTextView, keyTextView);
				convertView.setTag(R.id.valueTextView, valueTextView);
			}else{
				keyTextView = (TextView) convertView.getTag(R.id.keyTextView);
				valueTextView = (TextView) convertView.getTag(R.id.valueTextView);
			}

			keyTextView.setText(KEYS[position]);
			valueTextView.setText((String)getItem(position));

			return convertView;
		}
	}
}
