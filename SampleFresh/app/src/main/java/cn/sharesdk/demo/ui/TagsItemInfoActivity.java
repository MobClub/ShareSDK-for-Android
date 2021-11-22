package cn.sharesdk.demo.ui;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cn.sharesdk.demo.R;
import cn.sharesdk.demo.adapter.TagsGridViewAdapter;

public class TagsItemInfoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private TextView txtTopbar;
    private TextView subhead;
    private TextView describe;
    private List<Integer> mDatas;
    private GridView mGridView;
    private TagsGridViewAdapter adapter;

    private List<String> tags_id = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_item_info);
        initView();

        Intent intent = getIntent();
        Bundle bundleExtra = intent.getExtras();
        String likeType = bundleExtra.getString("likeType");
        if (likeType.equals("0")) {
            txtTopbar.setText(R.string.tags_like_type_shop);
            if (subhead != null) {
                subhead.setText(R.string.tags_subhead_shop);
            }
            if (describe != null) {
                describe.setText(R.string.tags_describe_shop);
            }
        }
        if (likeType.equals("1")) {
            txtTopbar.setText(R.string.tags_like_type_video);
            if (subhead != null) {
                subhead.setText(R.string.tags_subhead_video);
            }
            if (describe != null) {
                describe.setText(R.string.tags_describe_video);
            }
        }
        if (likeType.equals("2")) {
            txtTopbar.setText(R.string.tags_like_type_game);
            if (subhead != null) {
                subhead.setText(R.string.tags_subhead_game);
            }
            if (describe != null) {
                describe.setText(R.string.tags_describe_game);
            }
        }

        tags_id = bundleExtra.getStringArrayList("tags_id");
        for (int i=0; i<tags_id.size(); i++) {
            Log.e("SDK+", " TagsItemInfoActivity  tags_id.size(): " + tags_id.size() + " tagsId " + tags_id.get(i));
        }

        adapter = new TagsGridViewAdapter(this, tags_id);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
    }

    private void initView() {
        mGridView = findViewById(R.id.gv_test);
        txtTopbar = findViewById(R.id.txt_topbar);
        subhead = findViewById(R.id.subhead);
        describe = findViewById(R.id.describe);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
