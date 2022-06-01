package cn.sharesdk.demo.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import androidx.annotation.NonNull;
import cn.sharesdk.demo.R;


public class CommomDialog extends Dialog implements View.OnClickListener{

    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private Context context;
    private String contentStr;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;

    public CommomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CommomDialog(@NonNull Context context, int themeResId, String content) {
        super(context, themeResId);
        this.context = context;
        this.contentStr = content;
    }

    public CommomDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.context = context;
        this.contentStr = content;
        this.listener = listener;
    }

    protected CommomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }


    public CommomDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public CommomDialog setPositiveButton(String name){
        this.positiveName = name;
        return this;
    }

    public CommomDialog setNegativeButton(String name){
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();

    }

    private void initView(){
        titleTxt = (TextView)findViewById(R.id.title);
        contentTxt = (TextView)findViewById(R.id.tv_content);
        submitTxt = (TextView)findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = (TextView)findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

        if (!TextUtils.isEmpty(contentStr)) {
            contentTxt.setText(contentStr);
        }

        if(!TextUtils.isEmpty(positiveName)){
            submitTxt.setText(positiveName);
        }
        if(!TextUtils.isEmpty(negativeName)){
            cancelTxt.setText(negativeName);
        }
        if(!TextUtils.isEmpty(title)){
            titleTxt.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if(listener != null){
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if(listener != null){
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }

    public static void dialog(Context context, String contentStr) {
        try {
            if (!((Activity)context).isFinishing()){
                CommomDialog dialog = new CommomDialog(context, R.style.mydialog, contentStr,
                        new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                }
                            }
                        });
                dialog.setTitle("Tips").show();
            } else if(MobSDK.getContext() != null) {
                CommomDialog dialog = new CommomDialog(MobSDK.getContext(), R.style.mydialog, contentStr,
                        new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                }
                            }
                        });
                dialog.setTitle("Tips").show();
            } else {
                Toast.makeText(context, contentStr,  Toast.LENGTH_LONG).show();
            }
        } catch (Throwable t) {
            Toast.makeText(context, contentStr,  Toast.LENGTH_LONG).show();
            Log.e("QQQ", " Don't worry about just the pop-up hanging ===> " + t);
        }

    }


}
