package cn.sharesdk.demo.utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mob.MobSDK;
import com.mob.OperationCallback;
import com.mob.PrivacyPolicy;

import java.util.ArrayList;

import cn.sharesdk.demo.ActivityHook;
import cn.sharesdk.demo.R;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.loopshare.LoopSharePasswordListener;

import static cn.sharesdk.demo.MainActivity.TAG;

public class PrivacyDialog extends Activity implements View.OnClickListener {

    private Button btnOk;
    private Button btnCancel;
    private TextView showContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityHook.hookOrientation(this);//hook，绕过检查
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_dialog);
        initView();
    }

    private void initView() {
        btnOk = findViewById(R.id.ok);
        btnOk.setOnClickListener(this);

        btnCancel = findViewById(R.id.cancel);
        btnCancel.setOnClickListener(this);

        showContent = findViewById(R.id.show_content);

        queryPrivacy();
    }

    private void queryPrivacy() {

        // 异步方法
        MobSDK.getPrivacyPolicyAsync(MobSDK.POLICY_TYPE_URL, new PrivacyPolicy.OnPolicyListener()
        {
            @Override
            public void onComplete(PrivacyPolicy data) {
                if (data != null) {
                    // 富文本内容
                    String text = data.getContent();
                    if (showContent != null) {

                        showContent.setText(MobSDK.getContext().getResources().getString(R.string.privacy_content) + "\n"  +
                                MobSDK.getContext().getResources().getString(R.string.privacy_details) +   " " + Html.fromHtml(text));
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // 请求失败
                Log.e(TAG, "隐私协议查询结果：失败 " + t);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok: {
                setResult(1);
                submitPrivacyGrantResult(true);
                finish();
            } break;
            case R.id.cancel: {
                setResult(0);
                submitPrivacyGrantResult(false);
                finish();
            } break;
            default:
                break;
        }
    }


    private void submitPrivacyGrantResult(boolean granted) {
        MobSDK.submitPolicyGrantResult(granted, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void data) {
                Log.e(TAG, "隐私协议授权结果提交：成功 " + data);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "隐私协议授权结果提交：失败: " + t);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
