package cn.sharesdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mob.MobSDK;
import com.shizhefei.view.largeimage.LargeImageView;

import java.util.HashMap;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class ShareMobLinkActivity extends Activity implements View.OnClickListener{

    public final static String LINK_URL = "http://m.93lj.com/sharelink/";
    public final static String LINK_TEXT = "loopShare 重磅上线！一键实现分享闭环！错过它，就错过了全世界~";

    private LargeImageView imageShow;
    private Button btnShowlink;
    private Button btn_back;
    private Button btnCustomShowlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_demo);
        initView();
        try {
            String str = ShareSDK.getCustomDataFromLoopShare().toString();
            Log.e("QQQ", " 测试场景还原数据 " + str);
        } catch (Throwable t) {
            Log.e("QQQ", " 你个逗比 " + t);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        imageShow = findViewById(R.id.imageView);
        imageShow.setImage(R.mipmap.sharelink);

        btnShowlink = findViewById(R.id.btn_showlink);
        btnShowlink.setOnClickListener(this);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        btnCustomShowlink = findViewById(R.id.btn_custome_showlink);
        btnCustomShowlink.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_showlink:
            {
                shareWebPager();
            } break;
            case R.id.btn_back:
            {
                finish();
            } break;
            case R.id.btn_custome_showlink:
            {
                shareCustomUrl();
            } break;
            default:
                break;
        }
    }

    /**
     * 自定义场景还原参数分享
     * **/
    private void shareCustomUrl() {
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        boolean clientBool = platform.isClientValid();
        if (clientBool) {
            Platform.ShareParams shareParams = new  Platform.ShareParams();
            shareParams.setText(LINK_TEXT);
            shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
            shareParams.setUrl("https://f.moblink.mob.com/share/link/indexAnd.html");
            HashMap<String, Object> mobIdMap = new HashMap<String, Object>();
            mobIdMap.put("path", "pathTest");

            HashMap<String, Object> paramasTest = new HashMap<String, Object>();
            paramasTest.put("key1", "value1");
            paramasTest.put("key2", "value2");
            paramasTest.put("key3", "value3");
            paramasTest.put("key4", "value4");
            mobIdMap.put("params", paramasTest);

            shareParams.setLoopshareCustomParams(mobIdMap);

            shareParams.setShareType(Platform.SHARE_WEBPAGE);
            platform.share(shareParams);
        } else {
            Toast.makeText(MobSDK.getContext(), "请先安装QQ客户端", Toast.LENGTH_LONG).show();
        }
    }

    private void shareWebPager(){
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        boolean clientBool = platform.isClientValid();
        if (clientBool) {
            Platform.ShareParams shareParams = new  Platform.ShareParams();
            shareParams.setText(LINK_TEXT);
            shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
            shareParams.setTitleUrl("https://f.moblink.mob.com/share/link/indexAnd.html");
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
            platform.share(shareParams);
        } else {
            Toast.makeText(MobSDK.getContext(), "请先安装QQ客户端", Toast.LENGTH_LONG).show();
        }
    }

}
