package cn.sharesdk.demo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import cn.sharesdk.demo.R;

/**
 * Created by xiangli on 2018/12/21.
 */

public class TagsMyFragment extends Fragment {
    private ImageView head;        //user avatar
    private TextView nickName;     //user nickname
    private ImageView tagsGender;  //user gender
    private ImageView peoplePor;   //human portrait
    private TextView age;          //age
    private TextView edu;          //education background
    private TextView likeBuy;
    private TextView likeMoive;
    private TextView likeGame;

    private List<String> tags_id;
    private HashMap<String, Object> dataMap;
    private Map<String, Object> type;

    public TagsMyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tags_item_my, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        head = (ImageView) view.findViewById(R.id.head);
        nickName = (TextView) view.findViewById(R.id.nick_name);
        tagsGender = (ImageView) view.findViewById(R.id.tags_gender);
        peoplePor = (ImageView) view.findViewById(R.id.people_por);
        age = (TextView) view.findViewById(R.id.tags_age);
        edu = (TextView) view.findViewById(R.id.tags_edu);
        likeBuy = (TextView) view.findViewById(R.id.tv_like_buy);
        likeMoive = (TextView) view.findViewById(R.id.tv_like_moive);
        likeGame = (TextView) view.findViewById(R.id.tv_like_game);
        tags_id = new ArrayList<String>();
        initData();
        readData();
    }

    private void readData() {
        Bundle bundle = getActivity().getIntent().getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get("serMap");

        try {
            //head
            String headPor = (String) serializableHashMap.getMap().get("headimgurl");
            String avatar_hd = (String) serializableHashMap.getMap().get("avatar_hd");
            if ((!TextUtils.isEmpty(headPor)) && (head != null)) {
                //peoplePor.set
                Glide.with(getActivity()).load(headPor).into(head);
            } else if (!(TextUtils.isEmpty(avatar_hd)) && (head != null)) {
                Glide.with(getActivity()).load(avatar_hd).into(head);
            }

            //nickName
            String nicknameStr = (String) serializableHashMap.getMap().get("nickname");
            String screen_name = (String) serializableHashMap.getMap().get("screen_name");
            if ((!TextUtils.isEmpty(nicknameStr)) && (nickName != null)) {
                nickName.setText(nicknameStr);
            } else if ((!TextUtils.isEmpty(screen_name)) && (nickName != null)){
                nickName.setText(screen_name);
            }

            //sex
            String genderSina = (String) serializableHashMap.getMap().get("gender");
            int sexInter = -1;
            try {
                sexInter = (int) serializableHashMap.getMap().get("sex");
            } catch (NullPointerException t) {

            }

            if (!TextUtils.isEmpty(genderSina)) {
                if (((tagsGender != null)) && (peoplePor != null)) {
                    if (genderSina.equals("m")) {
                        tagsGender.setBackground(getResources().getDrawable(R.drawable.gender_man));
                        peoplePor.setBackground(getResources().getDrawable(R.drawable.male));
                    } else {
                        tagsGender.setBackground(getResources().getDrawable(R.drawable.gender_woman));
                        peoplePor.setBackground(getResources().getDrawable(R.drawable.female));
                    }
                }
            } else if (((tagsGender != null)) && (peoplePor != null)) {
                if (sexInter == 1) { //男
                    tagsGender.setBackground(getResources().getDrawable(R.drawable.gender_man));
                    peoplePor.setBackground(getResources().getDrawable(R.drawable.male));
                } else {
                    tagsGender.setBackground(getResources().getDrawable(R.drawable.gender_woman));
                    peoplePor.setBackground(getResources().getDrawable(R.drawable.female));
                }
            }
        } catch (Exception e) {
            //Log.e("TagsMyFragment ", " " + e.getMessage());
        }


        String usertags = (String) serializableHashMap.getMap().get("userTags");
        Log.e("SDK+", "TagsMyFragment 个人信息页面，得到的数据 ===》 " + usertags);
        JSONArray jsonArray = null;
        try {
            if (usertags != null) {
                JSONObject jsonObject = JSONObject.parseObject(usertags);
                jsonArray = JSONObject.parseArray(String.valueOf(jsonObject.get("list")));
            }

            for (int m = 0; m < jsonArray.size(); m++) {
                JSONObject object = (JSONObject) jsonArray.get(m);
                compare(object);
            }

        } catch (NullPointerException t) {
            Log.e("TagsMyFragment", " server is not response " + t.getMessage());
        }
       /* try {
            tags_id.clear();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                for (String key : dataMap.keySet()) {
                    if (key.equals(object.get("id"))) {
                        tags_id.add(key);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("WWW", e.getMessage());
        }*/
    }

    private void compare(JSONObject obj) {
        String type = (String) obj.get("type");

        //年龄
        if (type.equals("agebin")) {
            findValue("agebin", obj, age);
        }

        //学历
        if (type.equals("edu")) {
            findValue("edu", obj, edu);
        }

        //购物
        if (type.equals("A120")) {
            findValue("A120", obj, likeBuy);
        } else if (type.equals("A115")) {
            findValue("A115", obj, likeBuy);
        } else if (type.equals("A117")) {
            findValue("A117", obj, likeBuy);
        } else if (type.equals("A116")) {
            findValue("A116", obj, likeBuy);
        } else if (type.equals("A123")) {
            findValue("A123", obj, likeBuy);
        } else if (type.equals("A118")) {
            findValue("A118", obj, likeBuy);
        } else if (type.equals("A119")) {
            findValue("A119", obj, likeBuy);
        } else if (type.equals("A121")) {
            findValue("A121", obj, likeBuy);
        } else if (type.equals("A122")) {
            findValue("A122", obj, likeBuy);
        }

        //视频
        if (type.equals("A194")) {
            findValue("A194", obj, likeMoive);
        } else if (type.equals("A191")) {
            findValue("A191", obj, likeMoive);
        } else if (type.equals("A193")) {
            findValue("A193", obj, likeMoive);
        } else if (type.equals("A195")) {
            findValue("A195", obj, likeMoive);
        } else if (type.equals("A189")) {
            findValue("A189", obj, likeMoive);
        } else if (type.equals("A190")) {
            findValue("A190", obj, likeMoive);
        } else if (type.equals("A192")) {
            findValue("A192", obj, likeMoive);
        } else if (type.equals("A196")) {
            findValue("A196", obj, likeMoive);
        } else if (type.equals("A188")) {
            findValue("A188", obj, likeMoive);
        }

        //游戏
        if (type.equals("A235")) {
            findValue("A235", obj, likeGame);
        } else if (type.equals("A233")) {
            findValue("A233", obj, likeGame);
        } else if (type.equals("A229")) {
            findValue("A229", obj, likeGame);
        } else if (type.equals("A231")) {
            findValue("A231", obj, likeGame);
        } else if (type.equals("A237")) {
            findValue("A237", obj, likeGame);
        } else if (type.equals("A243")) {
            findValue("A243", obj, likeGame);
        } else if (type.equals("A239")) {
            findValue("A239", obj, likeGame);
        } else if (type.equals("A238")) {
            findValue("A238", obj, likeGame);
        } else if (type.equals("A230")) {
            findValue("A230", obj, likeGame);
        } else if (type.equals("A241")) {
            findValue("A241", obj, likeGame);
        } else if (type.equals("A242")) {
            findValue("A242", obj, likeGame);
        } else if (type.equals("A244")) {
            findValue("A244", obj, likeGame);
        } else if (type.equals("A232")) {
            findValue("A232", obj, likeGame);
        } else if (type.equals("A234")) {
            findValue("A234", obj, likeGame);
        } else if (type.equals("A236")) {
            findValue("A236", obj, likeGame);
        } else if (type.equals("A240")) {
            findValue("A240", obj, likeGame);
        }

    }

    private void findValue(String typeStr, JSONObject obj, TextView tv) {
        HashMap<String, Object> map = (HashMap<String, Object>) type.get(typeStr);
        String value = (String) map.get(obj.get("id"));
        if ((!TextUtils.isEmpty(value) && tv != null)) {
            tv.setText(value);
        } else {
            tv.setText(getResources().getString(R.string.tags_unknown));
        }
    }

    private void initData() {
        type = new HashMap<String, Object>();

        //gender
        HashMap<String, Object> genderMap = new HashMap<>();
        genderMap.put("0", "男");
        genderMap.put("1", "女");
        type.put("gender", genderMap);

        //age
        HashMap<String, Object> agebinMap = new HashMap<>();
        agebinMap.put("5", "45岁以上");
        agebinMap.put("6", "35-44岁");
        agebinMap.put("7", "25-34岁");
        agebinMap.put("8", "18-24岁");
        agebinMap.put("9", "18岁以下");
        type.put("agebin", agebinMap);

        //edu
        HashMap<String, Object> eduMap = new HashMap<>();
        eduMap.put("6", "高中及以下");
        eduMap.put("7", "大专");
        eduMap.put("8", "本科");
        eduMap.put("9", "硕士及以上");
        type.put("edu", eduMap);

        //income
        HashMap<String, Object> incomeMap = new HashMap<>();
        incomeMap.put("3", "小于3k");
        incomeMap.put("4", "3-5k");
        incomeMap.put("5", "5-10k");
        incomeMap.put("6", "10-20k");
        incomeMap.put("7", "20k以上");
        type.put("income", incomeMap);

        //影视
        HashMap<String, Object> videoMapsix = new HashMap<>();
        videoMapsix.put("1", "3d");
        videoMapsix.put("26", "tvb");
        videoMapsix.put("73", "传媒");
        videoMapsix.put("104", "点播");
        videoMapsix.put("110", "电视剧");
        videoMapsix.put("113", "电影");
        videoMapsix.put("131", "多媒体");
        videoMapsix.put("160", "高清视频");
        videoMapsix.put("205", "纪录片");
        videoMapsix.put("307", "欧美");
        videoMapsix.put("456", "脱口秀");
        videoMapsix.put("539", "影视");
        videoMapsix.put("566", "原创");
        videoMapsix.put("619", "综艺");
        videoMapsix.put("819", "小品");
        type.put("A194", videoMapsix);

        //短视频
        HashMap<String, Object> shortVideo = new HashMap<>();
        shortVideo.put("17", "mv");
        shortVideo.put("127", "短视频");
        type.put("A191", shortVideo);

        //视频辅助
        HashMap<String, Object> videoAssisted = new HashMap<>();
        videoAssisted.put("47", "播放器");
        videoAssisted.put("394", "看片");
        videoAssisted.put("395", "视频播放器");
        videoAssisted.put("396", "视频分享");
        videoAssisted.put("397", "视频剪辑");
        videoAssisted.put("399", "视频课程");
        videoAssisted.put("421", "特效");
        videoAssisted.put("685", "剪辑");
        type.put("A193", videoAssisted);

        //直播
        HashMap<String, Object> liveVideo = new HashMap<>();
        liveVideo.put("48", "播客");
        liveVideo.put("112", "电视直播");
        liveVideo.put("428", "体育直播");
        liveVideo.put("551", "游戏直播");
        liveVideo.put("581", "在线视频");
        liveVideo.put("595", "真人视频");
        liveVideo.put("599", "直播");
        liveVideo.put("716", "主播");
        type.put("A195", liveVideo);

        //电视盒子
        HashMap<String, Object> tvBox = new HashMap<>();
        tvBox.put("109", "电视盒子");
        type.put("A189", liveVideo);

        //电视台
        HashMap<String, Object> tvStation = new HashMap<>();
        tvStation.put("111", "电视台");
        tvStation.put("401", "视频新闻");
        tvStation.put("747", "电视节目");
        tvStation.put("810", "卫视");
        tvStation.put("828", "央视");
        type.put("A190", liveVideo);

        //监控
        HashMap<String, Object> monitoring = new HashMap<>();
        monitoring.put("267", "录屏录像");
        monitoring.put("268", "录音");
        monitoring.put("684", "监控");
        type.put("A192", monitoring);

        //视频
        HashMap<String, Object> video = new HashMap<>();
        video.put("393", "视频");
        type.put("A196", video);

        //弹幕
        HashMap<String, Object> bulletScreen = new HashMap<>();
        bulletScreen.put("648", "弹幕");
        type.put("A188", bulletScreen);


        /** 游戏 **/
        //飞行射击
        HashMap<String, Object> flightShooting = new HashMap<>();
        flightShooting.put("4", "cf");
        flightShooting.put("103", "第一人称射击游戏");
        flightShooting.put("124", "动作射击");
        flightShooting.put("149", "飞行");
        flightShooting.put("150", "飞行模拟");
        flightShooting.put("151", "飞行射击");
        flightShooting.put("241", "狙击");
        flightShooting.put("249", "空战");
        flightShooting.put("338", "枪械");
        flightShooting.put("339", "抢滩登陆");
        flightShooting.put("376", "射击");
        flightShooting.put("600", "直升机");
        flightShooting.put("652", "第一人称");
        flightShooting.put("662", "反恐");
        flightShooting.put("721", "fps");
        flightShooting.put("728", "tps");
        type.put("A235", flightShooting);

        //动作冒险
        HashMap<String, Object> actionAdventure = new HashMap<>();
        actionAdventure.put("7", "dnf");
        actionAdventure.put("58", "minecraft");
        actionAdventure.put("87", "大冒险");
        actionAdventure.put("122", "动作游戏");
        actionAdventure.put("123", "动作冒险");
        actionAdventure.put("130", "对战");
        actionAdventure.put("164", "格斗");
        actionAdventure.put("195", "火柴人");
        actionAdventure.put("202", "即时战斗");
        actionAdventure.put("219", "僵尸");
        actionAdventure.put("227", "街机");
        actionAdventure.put("281", "冒险");
        actionAdventure.put("293", "迷宫");
        actionAdventure.put("294", "密室");
        actionAdventure.put("347", "忍者");
        actionAdventure.put("379", "神庙");
        actionAdventure.put("477", "武器装备");
        actionAdventure.put("584", "战斗");
        actionAdventure.put("705", "逃脱");
        actionAdventure.put("806", "逃亡");
        type.put("A233", actionAdventure);

        //moba
        HashMap<String, Object> moba = new HashMap<>();
        moba.put("8", "dota");
        moba.put("15", "lol");
        moba.put("302", "魔兽");
        moba.put("629", "moba");
        type.put("A229", moba);

        //moba
        HashMap<String, Object> singleGame = new HashMap<>();
        singleGame.put("11", "gameloft");
        singleGame.put("94", "单机");
        type.put("A231", singleGame);

        //角色扮演
        HashMap<String, Object> rolePlay = new HashMap<>();
        rolePlay.put("16", "mmo");
        rolePlay.put("191", "回合制");
        rolePlay.put("223", "角色扮演");
        rolePlay.put("301", "魔幻");
        rolePlay.put("328", "骑士");
        rolePlay.put("481", "西游");
        rolePlay.put("632", "暗黑");
        rolePlay.put("659", "恶魔");
        rolePlay.put("677", "横版");
        rolePlay.put("691", "精灵");
        rolePlay.put("720", "mmorpg");
        rolePlay.put("725", "rpg");
        rolePlay.put("814", "西方魔幻");
        type.put("A237", rolePlay);

        //休闲益智
        HashMap<String, Object> casualPuzzle = new HashMap<>();
        casualPuzzle.put("32", "宝石消除");
        casualPuzzle.put("52", "捕鱼");
        casualPuzzle.put("75", "闯关");
        casualPuzzle.put("137", "儿童游戏");
        casualPuzzle.put("226", "接龙");
        casualPuzzle.put("261", "连连看");
        casualPuzzle.put("314", "泡泡龙");
        casualPuzzle.put("340", "切水果");
        casualPuzzle.put("360", "三消");
        casualPuzzle.put("423", "变声");
        casualPuzzle.put("431", "跳跃躲避");
        casualPuzzle.put("437", "停车游戏");
        casualPuzzle.put("493", "消除游戏");
        casualPuzzle.put("509", "休闲");
        casualPuzzle.put("510", "休闲益智");
        casualPuzzle.put("525", "益智");
        casualPuzzle.put("591", "找茬");
        casualPuzzle.put("640", "成语");
        casualPuzzle.put("641", "城堡");
        casualPuzzle.put("663", "方块");
        casualPuzzle.put("706", "团队协作");
        casualPuzzle.put("811", "文字游戏");
        casualPuzzle.put("818", "消除");
        casualPuzzle.put("824", "休闲游戏");
        casualPuzzle.put("831", "益智解谜");
        casualPuzzle.put("849", "祖玛消除");
        type.put("A243", casualPuzzle);

        //棋牌桌游
        HashMap<String, Object> chessBoard = new HashMap<>();
        chessBoard.put("50", "博雅");
        chessBoard.put("97", "德州扑克");
        chessBoard.put("125", "斗地主");
        chessBoard.put("126", "斗牛");
        chessBoard.put("243", "卡牌");
        chessBoard.put("277", "麻将");
        chessBoard.put("322", "扑克");
        chessBoard.put("330", "棋牌");
        chessBoard.put("331", "棋盘");
        chessBoard.put("585", "战棋");
        chessBoard.put("602", "纸牌");
        chessBoard.put("614", "桌游");
        chessBoard.put("847", "桌球");
        type.put("A239", chessBoard);

        //经营策略
        HashMap<String, Object> operatingStrategy = new HashMap<>();
        operatingStrategy.put("59", "策略");
        operatingStrategy.put("60", "策略塔防");
        operatingStrategy.put("74", "传统塔防");
        operatingStrategy.put("86", "大富翁");
        operatingStrategy.put("102", "帝国");
        operatingStrategy.put("214", "建造游戏");
        operatingStrategy.put("237", "经营策略");
        operatingStrategy.put("297", "模拟经营");
        operatingStrategy.put("299", "模拟养成");
        operatingStrategy.put("305", "农场");
        operatingStrategy.put("358", "三国");
        operatingStrategy.put("416", "塔防");
        operatingStrategy.put("708", "养成");
        operatingStrategy.put("726", "rts");
        operatingStrategy.put("727", "slg");
        operatingStrategy.put("772", "经营养成");
        type.put("A238", operatingStrategy);

        //游戏
        HashMap<String, Object> game = new HashMap<>();
        game.put("88", "大型游戏");
        game.put("200", "极限运动");
        game.put("271", "萝莉");
        game.put("298", "模拟器");
        game.put("424", "腾讯游戏");
        game.put("463", "网络游戏");
        game.put("466", "网易游戏");
        game.put("548", "游戏");
        game.put("549", "游戏攻略");
        game.put("552", "游戏助手");
        game.put("643", "传说");
        game.put("710", "宇宙");
        game.put("715", "重力");
        game.put("800", "竖版");
        game.put("801", "水墨风格");
        game.put("833", "游戏机");
        type.put("A230", game);

        //体育竞速
        HashMap<String, Object> sportsRacing = new HashMap<>();
        sportsRacing.put("239", "竞速");
        sportsRacing.put("300", "摩托");
        sportsRacing.put("313", "跑酷");
        sportsRacing.put("354", "赛车");
        sportsRacing.put("357", "赛艇");
        sportsRacing.put("572", "越野");
        sportsRacing.put("848", "竞技");
        type.put("A241", sportsRacing);

        //小游戏
        HashMap<String, Object> miniGames = new HashMap<>();
        miniGames.put("496", "小游戏");
        type.put("A242", miniGames);

        //游戏音乐
        HashMap<String, Object> gameMusic = new HashMap<>();
        gameMusic.put("513", "炫舞");
        gameMusic.put("530", "音乐节奏");
        gameMusic.put("533", "音乐游戏");
        type.put("A244", gameMusic);

        //弹珠
        HashMap<String, Object> hoodle = new HashMap<>();
        hoodle.put("742", "弹珠");
        type.put("A232", hoodle);

        //放置类
        HashMap<String, Object> place = new HashMap<>();
        place.put("754", "放置类");
        type.put("A234", place);

        //换装
        HashMap<String, Object> reloading = new HashMap<>();
        reloading.put("765", "换装");
        type.put("A236", reloading);

        //沙盒
        HashMap<String, Object> sandbox = new HashMap<>();
        sandbox.put("793", "沙盒");
        type.put("A240", sandbox);


        /** 购物 **/
        //商品
        HashMap<String, Object> commodity = new HashMap<>();
        commodity.put("30", "包包");
        commodity.put("235", "进口");
        commodity.put("265", "零食");
        commodity.put("365", "商品");
        commodity.put("371", "奢侈品");
        commodity.put("383", "生鲜");
        commodity.put("391", "食品");
        commodity.put("392", "市场营销");
        commodity.put("692", "精品");
        commodity.put("704", "水果");
        type.put("A120", commodity);

        //电商
        HashMap<String, Object> eCommerce = new HashMap<>();
        eCommerce.put("40", "比价");
        eCommerce.put("82", "促销");
        eCommerce.put("108", "电商");
        eCommerce.put("145", "返利");
        eCommerce.put("385", "省钱");
        eCommerce.put("420", "特卖");
        eCommerce.put("545", "优惠");
        eCommerce.put("624", "夺宝");
        eCommerce.put("634", "包邮");
        eCommerce.put("649", "导购");
        type.put("A115", eCommerce);

        //购物模式
        HashMap<String, Object> shoppingPatterns = new HashMap<>();
        shoppingPatterns.put("54", "采购");
        shoppingPatterns.put("90", "代购");
        shoppingPatterns.put("92", "代理");
        shoppingPatterns.put("179", "海淘");
        shoppingPatterns.put("465", "网上购物");
        shoppingPatterns.put("809", "微商");
        type.put("A117", shoppingPatterns);

        //服装鞋包
        HashMap<String, Object> csp = new HashMap<>();
        csp.put("83", "搭配");
        csp.put("156", "服装搭配");
        csp.put("781", "男鞋");
        csp.put("782", "男装");
        csp.put("783", "内衣");
        csp.put("784", "女鞋");
        csp.put("785", "女装");
        csp.put("807", "童装");
        type.put("A116", csp);

        //购物
        HashMap<String, Object> shopping = new HashMap<>();
        shopping.put("138", "二手");
        shopping.put("172", "购物");
        shopping.put("700", "扫码");
        type.put("A123", shopping);

        //母婴
        HashMap<String, Object> fransnana = new HashMap<>();
        fransnana.put("178", "孩子");
        fransnana.put("276", "妈咪");
        fransnana.put("303", "母婴");
        fransnana.put("553", "幼儿");
        fransnana.put("764", "怀孕");
        fransnana.put("838", "孕期");
        type.put("A118", fransnana);

        //商城
        HashMap<String, Object> shoppingMall = new HashMap<>();
        shoppingMall.put("254", "快递");
        shoppingMall.put("362", "商城");
        shoppingMall.put("364", "商家商铺");
        shoppingMall.put("515", "亚马逊");
        shoppingMall.put("813", "物流");
        type.put("A119", shoppingMall);

        //数码
        HashMap<String, Object> ncode = new HashMap<>();
        ncode.put("410", "数码");
        ncode.put("746", "电脑");
        ncode.put("748", "电玩");
        ncode.put("750", "电子产品");
        ncode.put("797", "手机配件");
        type.put("A121", ncode);

        //团购
        HashMap<String, Object> groupPurchase = new HashMap<>();
        groupPurchase.put("455", "团购");
        groupPurchase.put("546", "优惠券");
        groupPurchase.put("594", "折扣");
        type.put("A122", groupPurchase);
    }

}
