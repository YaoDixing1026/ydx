package com.product.yao.myapp.sactivity.create;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.utils.MyDialog;
import com.product.yao.myapp.utils.ToastUtil;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by paichufang on 15-11-17.
 */
public class ChooseUploadActivity extends BaseActivity{
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_choose_picture);

         id=getIntent().getStringExtra("productId");

        getProductById();


        findViewById(R.id.upload_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseUploadActivity.this, PictureUploadActivity.class)
                        .putExtra("action", 200)
                        .putExtra("productId",id)
                        .putExtra("productName", product.getString("productName")));
            }
        });
        findViewById(R.id.upload_pai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseUploadActivity.this, PictureUploadActivity.class)
                        .putExtra("action", 100)
                        .putExtra("productId", id)
                        .putExtra("productName", product.getString("productName")));
            }
        });
        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0x12);
            }
        });
    }
    private AVObject product;
    private void getProductById(){
        AVQuery.doCloudQueryInBackground("select * from Product where productId='" + id + "'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null) {
                    if (avCloudQueryResult != null && avCloudQueryResult.getResults().size() > 0) {
                        product = avCloudQueryResult.getResults().get(0);
                        handler.sendEmptyMessage(0x11);
                    }

                }else {
                    ToastUtil.showshort(ChooseUploadActivity.this,e.getMessage());
                }

            }
        });
    }
    private void upLoadPhoto(String name,String path,Hashtable map){
        AVFile avFile=new AVFile(name,path,map);
          avFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {

                } else {
                    ToastUtil.showshort(ChooseUploadActivity.this, e.getMessage());
                }
            }
        });
    }
    String path;
    String productId;
    String productName;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        path=intent.getStringExtra("path");
        productId=intent.getStringExtra("productId");
        productName=intent.getStringExtra("productName");
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:

                    break;
                case 0x12:
                    Hashtable<String,String> map=new Hashtable<>();
                    map.put("productId",productId);
                    upLoadPhoto(productName, path,map);
                    break;
            }
        }
    };
}
