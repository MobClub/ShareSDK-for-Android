package cn.sharesdk.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import cn.sharesdk.demo.R;

/**
 * Created by xiangli on 2018/12/21.
 */

public class TagsHomeFragment extends Fragment implements View.OnClickListener {
    private Map<String, Object> data;
    private List<String> tags_id;

    public TagsHomeFragment() {
        // Required empty public constructor
    }

    private Button tagsShop;
    private Button tagsVideo;
    private Button tagsGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tags_item_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tagsShop = (Button) view.findViewById(R.id.tags_shop);
        tagsShop.setOnClickListener(this);
        tagsVideo = (Button) view.findViewById(R.id.tags_video);
        tagsVideo.setOnClickListener(this);
        tagsGame = (Button) view.findViewById(R.id.tags_game);
        tagsGame.setOnClickListener(this);
        tags_id = new ArrayList<String>();
    }


    @Override
    public void onClick(View v) {
        initData();
        switch (v.getId()) {
            case R.id.tags_shop: {
                Log.e("WWW", " 购物~~~~~ ");
                readMap("0");
                Intent intent = new Intent();
                intent.setClass(getActivity(), TagsItemInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("tags_id", (ArrayList<String>) tags_id);
                bundle.putString("likeType", "0");
                intent.putExtras(bundle);
                startActivity(intent);
            } break;
            case R.id.tags_video: {
                Log.e("WWW", " 视频~~~~~ ");
                readMap("1");
                Intent intent = new Intent();
                intent.setClass(getActivity(), TagsItemInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("tags_id", (ArrayList<String>) tags_id);
                bundle.putString("likeType", "1");
                intent.putExtras(bundle);
                startActivity(intent);
            } break;
            case R.id.tags_game: {
                Log.e("WWW", " 游戏~~~~~ ");
                readMap("2");
                Intent intent = new Intent();
                intent.setClass(getActivity(), TagsItemInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("tags_id", (ArrayList<String>) tags_id);
                bundle.putString("likeType", "2");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            default:
                break;
        }
    }

    private void readMap(String categroy) {
        Bundle bundle = getActivity().getIntent().getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get("serMap");

        String usertags = (String) serializableHashMap.getMap().get("userTags");
        JSONArray jsonArray = null;
        try {
            if (usertags != null) {
                JSONObject jsonObject = JSONObject.parseObject(usertags);
                jsonArray = JSONObject.parseArray(String.valueOf(jsonObject.get("list")));
            }
        } catch (NullPointerException t) {
            Log.e("SDK+", "TagsHomeFragment server is not response " + t.getMessage());
        }

        try {
            tags_id.clear();
            Map<String,Object> mapId = new HashMap<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                mapId = (Map<String, Object>) data.get(categroy);
                for (String key : mapId.keySet()) {
                    if (key.equals(object.get("id"))) {
                        tags_id.add(key);
                        String[] array = (String[]) mapId.get(key);
                        for (int m = 0; m < array.length; m++) {
                            Log.e("SDK+ ", " TagsHomeFragment " + array[m]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("SDK+", e.getMessage());
        }

    }

    private void initData() {
        data = new HashMap<String, Object>();
        //shop
        HashMap<String, Object> shopMap = new HashMap<>();
        shopMap.put("83", new String[]{"潮流搭配","潮人搭配，惊艳这个秋冬"});
        shopMap.put("156", new String[]{"男士冬日搭配","怎么穿都不过时"});
        shopMap.put("362", new String[]{"真爱无敌","真爱无敌，把商场搬回家"});
        shopMap.put("781", new String[]{"潮流搭配","潮人搭配，惊艳这个秋冬"});
        shopMap.put("782", new String[]{"gap短袖t恤","纯棉大码横条纹"});
        shopMap.put("783", new String[]{"维秘pink系列","完美身型"});
        shopMap.put("784", new String[]{"雪地靴女式","UGG新款雪地靴"});
        shopMap.put("785", new String[]{"女性长款羽绒服","2018冬季新款"});
        shopMap.put("807", new String[]{"男童加绒卫衣","圆领休闲宽松"});
        data.put("0", shopMap);

        //video
        HashMap<String, Object> videoMap = new HashMap<>();
        videoMap.put("110", new String[]{"大帅哥","一代军阀的成长史"});
        videoMap.put("113", new String[]{"海王","这其实是一部环保宣传片"});
        videoMap.put("131", new String[]{"神奇体验","多媒体艺术美轮美奂"});
        videoMap.put("160", new String[]{"4K体验","你，就在画面里"});
        videoMap.put("205", new String[]{"风味人间","全球视野下的中国美食之旅"});
        videoMap.put("255", new String[]{"神秘海洋","领略海底3D世界"});
        videoMap.put("276", new String[]{"黄宗泽说普通话","整个tvb都怕了他"});
        videoMap.put("318", new String[]{"新闻24小时","传媒快报"});
        videoMap.put("349", new String[]{"双红会一促即发","鹅还有机会战胜渣叔吗"});
        videoMap.put("456", new String[]{"吐槽大会","看我们超越小姐姐吐槽了什么"});
        videoMap.put("539", new String[]{"权力的游戏","我兰尼斯特，有债必还"});
        videoMap.put("566", new String[]{"万万没想到","啦啦啦啦啦"});
        videoMap.put("619", new String[]{"火星情报局","反目成仇？"});
        videoMap.put("819", new String[]{"宋小宝小品合集","你看我帅得秃噜皮了都"});
        videoMap.put("828", new String[]{"CCTV","央视新闻24小时咨询"});
        data.put("1", videoMap);

        //game
        HashMap<String, Object> gameMap = new HashMap<>();
        gameMap.put("130", new String[]{"女神联盟", "激情与狂野"});
        gameMap.put("202", new String[]{"少年三国志", "即时对战策略"});
        gameMap.put("219", new String[]{"土豆打败僵尸", "土豆与僵尸的战场"});
        gameMap.put("227", new String[]{"街头大怪头", "街机游戏的战斗机"});
        gameMap.put("239", new String[]{"极品飞车","手机上的速度狂飙"});
        gameMap.put("281", new String[]{"权利的游戏","一场冒险的旅行"});
        gameMap.put("294", new String[]{"黑暗房间","秘密房间里的秘密"});
        gameMap.put("477", new String[]{"上古神器","打造极品装备"});
        gameMap.put("300", new String[]{"暴力摩托","随身体验台式机的经典"});
        gameMap.put("313", new String[]{"汤姆猫跑酷","画面美轮美奂"});
        gameMap.put("354", new String[]{"真实赛车3","更多赛道加持"});
        gameMap.put("357", new String[]{"极速赛艇","从未有过如此真实"});
        gameMap.put("572", new String[]{"越野传奇","前方高能请慎入"});
        gameMap.put("584", new String[]{"无敌争战","未来科技的真实战斗"});
        gameMap.put("705", new String[]{"逃脱计划","年度最烧脑的逃脱游戏"});
        gameMap.put("848", new String[]{"球球大作战","最受欢迎的竞技游戏"});
        data.put("2", gameMap);
    }

}
