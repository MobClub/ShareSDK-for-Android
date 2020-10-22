package cn.sharesdk.demo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.demo.R;

/**
 * Created by xiangli on 2018/12/24.
 */

public class TagsGridViewAdapter extends BaseAdapter{
    private Context context;
    private List<String> list;
    private LayoutInflater layoutInflater;
    private HashMap<String, Object> idTags;

    public TagsGridViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        initData();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void initData() {
        idTags = new HashMap<>();
        //shop
        idTags.put("83", new String[]{"潮流搭配","潮人搭配，惊艳这个秋冬"});
        idTags.put("156", new String[]{"男士冬日搭配","怎么穿都不过时"});
        idTags.put("362", new String[]{"真爱无敌","真爱无敌，把商场搬回家"});
        idTags.put("781", new String[]{"乔丹男鞋运动鞋","跑步透气休闲轻便"});
        idTags.put("782", new String[]{"gap短袖t恤","纯棉大码横条纹"});
        idTags.put("783", new String[]{"维秘pink系列","完美身型"});
        idTags.put("784", new String[]{"雪地靴女式","UGG新款雪地靴"});
        idTags.put("785", new String[]{"女性长款羽绒服","2018冬季新款"});
        idTags.put("807", new String[]{"男童加绒卫衣","圆领休闲宽松"});

        //video
        idTags.put("110", new String[]{"大帅哥","一代军阀的成长史"});
        idTags.put("113", new String[]{"海王","这其实是一部环保宣传片"});
        idTags.put("131", new String[]{"神奇体验","多媒体艺术美轮美奂"});
        idTags.put("160", new String[]{"4K体验","你，就在画面里"});
        idTags.put("205", new String[]{"风味人间","全球视野下的中国美食之旅"});
        idTags.put("255", new String[]{"神秘海洋","领略海底3D世界"});
        idTags.put("276", new String[]{"黄宗泽说普通话","整个tvb都怕了他"});
        idTags.put("318", new String[]{"新闻24小时","传媒快报"});
        idTags.put("349", new String[]{"双红会一促即发","鹅还有机会战胜渣叔吗"});
        idTags.put("456", new String[]{"吐槽大会","看我们超越小姐姐吐槽了什么"});
        idTags.put("539", new String[]{"权力的游戏","我兰尼斯特，有债必还"});
        idTags.put("566", new String[]{"万万没想到","啦啦啦啦啦"});
        idTags.put("619", new String[]{"火星情报局","反目成仇？"});
        idTags.put("819", new String[]{"宋小宝小品合集","你看我帅得秃噜皮了都"});
        idTags.put("828", new String[]{"CCTV","央视新闻24小时咨询"});

        //game
        idTags.put("130", new String[]{"女神联盟", "激情与狂野"});
        idTags.put("202", new String[]{"少年三国志", "即时对战策略"});
        idTags.put("219", new String[]{"土豆打败僵尸", "土豆与僵尸的战场"});
        idTags.put("227", new String[]{"街头大怪头", "街机游戏的战斗机"});
        idTags.put("239", new String[]{"极品飞车","手机上的速度狂飙"});
        idTags.put("281", new String[]{"权利的游戏","一场冒险的旅行"});
        idTags.put("294", new String[]{"黑暗房间","秘密房间里的秘密"});
        idTags.put("477", new String[]{"上古神器","打造极品装备"});
        idTags.put("300", new String[]{"暴力摩托","随身体验台式机的经典"});
        idTags.put("313", new String[]{"汤姆猫跑酷","画面美轮美奂"});
        idTags.put("354", new String[]{"真实赛车3","更多赛道加持"});
        idTags.put("357", new String[]{"极速赛艇","从未有过如此真实"});
        idTags.put("572", new String[]{"越野传奇","前方高能请慎入"});
        idTags.put("584", new String[]{"无敌争战","未来科技的真实战斗"});
        idTags.put("705", new String[]{"逃脱计划","年度最烧脑的逃脱游戏"});
        idTags.put("848", new String[]{"球球大作战","最受欢迎的竞技游戏"});
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.grid_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item);
            viewHolder.item_tv_title = (TextView) convertView.findViewById(R.id.item_tv_title);
            viewHolder.item_tv_content = (TextView) convertView.findViewById(R.id.item_tv_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position < list.size()) {
            String tags = list.get(position);
            for (String key : idTags.keySet()) {
                if (key.equals(tags)) {
                    String[] array = (String[]) idTags.get(key);
                        viewHolder.item_tv_title.setText(array[0]);
                        viewHolder.item_tv_content.setText(array[1]);
                    viewHolder.imageView.setBackgroundResource(R.drawable.cloth);
                }
            }

        } else {
            Log.e("TagsGridViewAdapter", " position > list.size() ");
        }

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView item_tv_title;
        TextView item_tv_content;
    }
}
