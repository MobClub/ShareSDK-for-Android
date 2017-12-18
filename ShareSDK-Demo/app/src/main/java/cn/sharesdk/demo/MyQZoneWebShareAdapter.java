package cn.sharesdk.demo;

import android.view.Window;
import cn.sharesdk.tencent.qzone.QZoneWebShareAdapter;

//#if def{lang} == cn
/** 一个用于演示{@link QZoneWebShareAdapter}的例子。 */
//#elif def{lang} == en
/** a example of {@link QZoneWebShareAdapter} */
//#endif
public class MyQZoneWebShareAdapter extends QZoneWebShareAdapter {
	
	public void onCreate() {
		//#if def{lang} == cn
		// 设置页面以Dialog的方式展示
		//#elif def{lang} == en
		// display the page in dialog mode
		//#endif
		getActivity().setTheme(android.R.style.Theme_Dialog);
		getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate();
		
		//#if def{lang} == cn
		// 修改页面标题
		//#elif def{lang} == en
		// change page title
		//#endif
		getTitleLayout().getTvTitle().setText(R.string.qzone_customer_share_style);
		
		//#if def{lang} == cn
		// 下面的代码可以拦截webview加载的页面地址，但是添加后，分享操作将可能无法正确执行
//		getWebBody().setWebViewClient(new WebViewClient() {
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				System.out.println("=========== " + url);
//				return super.shouldOverrideUrlLoading(view, url);
//			}
//		});
		//#endif

	}
	
}
