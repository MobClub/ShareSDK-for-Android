package cn.sharesdk.demo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.mob.MobSDK;

public class PrivacyDialogUtils {
	public static final String KEY_PRIVACY = "privacy";
	private SharedPreferences sharedPreferences;
	private AlertDialog downloadDialog;


	public void showPrivacyDialogIfNeed(Activity activity) {
		if (downloadDialog != null && downloadDialog.isShowing()) {
			return;
		}
		sharedPreferences = activity.getSharedPreferences("privacy", Context.MODE_PRIVATE);
		if (sharedPreferences.getBoolean(KEY_PRIVACY, false)) {
			return;
		}
		DisplayMetrics displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels;
		LinearLayout linearLayout = new LinearLayout(activity);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		WebView webView = new WebView(activity);
		int width = screenWidth - 2 * 30;
		LinearLayout.LayoutParams webViewParams = new LinearLayout.LayoutParams(width, (int) (1.1f * width));
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setDomStorageEnabled(true);
		webSettings.setAllowFileAccess(false);
		webSettings.setSavePassword(false);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				try {
					handler.proceed();
				} catch (Throwable e) {
				}
			}
		});
		linearLayout.addView(webView, webViewParams);
		try {
			AlertDialog.Builder b = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Light_Dialog);
			b.setTitle("ShareSDK隐私政策");
			b.setCancelable(false);
			b.setView(linearLayout);

			b.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					MobSDK.submitPolicyGrantResult(true);
					sharedPreferences.edit().putBoolean(KEY_PRIVACY, true).apply();
				}
			});
			b.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			webView.loadUrl("file:///android_asset/www/privacy.html");
			downloadDialog = b.create();
			downloadDialog.show();
		} catch (Throwable e) {
		}
	}

}
