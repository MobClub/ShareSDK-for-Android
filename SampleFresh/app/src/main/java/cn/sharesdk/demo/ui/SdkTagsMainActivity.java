package cn.sharesdk.demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mob.MobSDK;

import java.util.HashMap;

import cn.sharesdk.demo.R;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class SdkTagsMainActivity extends Activity implements View.OnClickListener, PlatformActionListener {

    private RelativeLayout loginWechat;
    private RelativeLayout loginSina;
    private Button login_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdk_tags_main);
        initView();
    }

    private void initView() {
        loginWechat = findViewById(R.id.login_wechat);
        loginSina = findViewById(R.id.login_sina);
        loginWechat.setOnClickListener(this);
        loginSina.setOnClickListener(this);

        login_test = findViewById(R.id.login_test);
        login_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_wechat: {
                WeChat();
            } break;
            case R.id.login_sina: {
                Sina();
            } break;
            case R.id.login_test:
                Intent intent = new Intent();
                intent.setClass(this, TagsItemActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void Sina() {
        ShareSDK.setEnableAuthTag(true);
        Platform plat = ShareSDK.getPlatform("SinaWeibo");
        plat.removeAccount(true);
        plat.setPlatformActionListener(this);
        if (plat.isClientValid()) {

        }
        if (plat.isAuthValid()) {

        }
        plat.showUser(null);
    }

    private void WeChat() {
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        ShareSDK.setEnableAuthTag(true);
        plat.removeAccount(true);
        plat.setPlatformActionListener(this);
        if (plat.isClientValid()) {

        }
        if (plat.isAuthValid()) {

        }
        plat.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        hashMap.put("userTags", platform.getDb().get("userTags")); //SDK+ tags
        Log.e("SDK+", " SdkTagsMainActivity platform: " + platform +
                " i: " + i + " hashMap " + hashMap);
        Intent inent = new Intent();
        SerializableHashMap serMap = new SerializableHashMap();
        serMap.setMap(hashMap);
        Bundle bundle = new Bundle();
        bundle.putSerializable("serMap", serMap);
        inent.putExtras(bundle);
        inent.setClass(this, TagsItemActivity.class);
        startActivity(inent);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e("SDK+", " SdkTagsMainActivity onError platform: " + platform +
                " i: " + i + " throwable " + throwable.getMessage());
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.e("SDK+", " SdkTagsMainActivity onCancel platform: " + platform +
                " i: " + i);
    }
}
