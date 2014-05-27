package cn.sharesdk.demo;

import android.view.Window;
import cn.sharesdk.tencent.qzone.QZoneWebShareAdapter;

/** a example of {@link QZoneWebShareAdapter} */
public class MyQZoneWebShareAdapter extends QZoneWebShareAdapter {

	public void onCreate() {
		// display the page in dialog mode
		getActivity().setTheme(android.R.style.Theme_Dialog);
		getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate();

		// change page title
		getTitleLayout().getTvTitle().setText(R.string.qzone_customer_share_style);


	}

}
