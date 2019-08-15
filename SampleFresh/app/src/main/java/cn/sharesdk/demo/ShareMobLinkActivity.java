package cn.sharesdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mob.MobSDK;
import com.shizhefei.view.largeimage.LargeImageView;

import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class ShareMobLinkActivity extends Activity implements View.OnClickListener{

    public final static String LINK_URL = "http://m.93lj.com/sharelink/";
    //public final static String LINK_URL = "http://m.93lj.com/sharelink?mobid=ziqMNf";
    public final static String LINK_TEXT = "loopShare 重磅上线！一键实现分享闭环！错过它，就错过了全世界~";

    private LargeImageView imageShow;
    private Button btnShowlink;
    private Button btn_back;

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
        //String str = ShareSDK.getCustomDataFromLoopShare().toString();
        //Log.e("QQQ", " 测试场景还原数据 " + str);
    }

    private void initView() {
        imageShow = (LargeImageView) findViewById(R.id.imageView);
        imageShow.setImage(R.mipmap.sharelink);

        btnShowlink = (Button) findViewById(R.id.btn_showlink);
        btnShowlink.setOnClickListener(this);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
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
            default:
                break;
        }
    }

    public void shareWebPager(){
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        boolean clientBool = platform.isClientValid();
        if (clientBool) {
            Platform.ShareParams shareParams = new  Platform.ShareParams();
            shareParams.setText(LINK_TEXT);
            shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
            shareParams.setTitleUrl(LINK_URL);
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
            platform.share(shareParams);
        } else {
            Toast.makeText(MobSDK.getContext(), "请先安装QQ客户端", Toast.LENGTH_LONG).show();
        }
    }

}
