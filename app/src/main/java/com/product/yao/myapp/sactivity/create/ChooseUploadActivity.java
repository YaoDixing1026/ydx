package com.product.yao.myapp.sactivity.create;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import com.product.yao.myapp.entity.Product;
import com.product.yao.myapp.entity.ProductPhoto;
import com.product.yao.myapp.entity.ThirdType;
import com.product.yao.myapp.service.c.Create;
import com.product.yao.myapp.utils.MyDialog;
import com.product.yao.myapp.utils.ToastUtil;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * Created by paichufang on 15-11-17.
 */
public class ChooseUploadActivity extends BaseActivity{
    private String id;
    private String photoType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_choose_picture);
        final ChooseUploadActivity chooseUploadActivity=new ChooseUploadActivity();
        final String className=this.getPackageName()+"."+this.getLocalClassName();
        id=getIntent().getStringExtra("id");
        photoType=getIntent().getStringExtra("photoType");
        getProductById();
        getThirdType();
        findViewById(R.id.upload_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseUploadActivity.this, PictureUploadActivity.class)
                        .putExtra("action", 200)
                        .putExtra("className", className));
            }
        });
        findViewById(R.id.upload_pai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseUploadActivity.this, PictureUploadActivity.class)
                        .putExtra("action", 100)
                        .putExtra("className",className));
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
    private void upLoadPhoto(String path){
        if(photoType!=null){
            if(photoType.equals("product")){
                try {
                    String  ss=path.substring(path.lastIndexOf("/") + 1, path.length());
                    ToastUtil.showshort(ChooseUploadActivity.this,ss);
                    final AVFile avFile=AVFile.withAbsoluteLocalPath(path.substring(path.lastIndexOf("/")+1,path.length()),path);
                    avFile.getName();
                    avFile.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                ProductPhoto productPhoto=new ProductPhoto();
                                productPhoto.setProductId(id);
                                productPhoto.setProductPhotoFile(avFile);
                                productPhoto.setProductPhotoName(product.getString("productName"));
                                productPhoto.setProductPhotoType("展示");
                                Create.createobject(ChooseUploadActivity.this,productPhoto);
                            } else {
                                ToastUtil.showshort(ChooseUploadActivity.this, e.getMessage());
                            }
                        }
                    });
                }catch (Exception e){

                    ToastUtil.showshort(ChooseUploadActivity.this,e.getMessage());
                }
            }
            else if(photoType.equals("thirdType")){
                try {
                    String  ss=path.substring(path.lastIndexOf("/") + 1, path.length());
                    ToastUtil.showshort(ChooseUploadActivity.this,ss);
                    final AVFile avFile=AVFile.withAbsoluteLocalPath(path.substring(path.lastIndexOf("/")+1,path.length()),path);
                    avFile.getName();
                    avFile.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Message message=new Message();
                                message.what=0121;
                                message.obj=avFile;
                                handler.sendMessage(message);
                            } else {
                                ToastUtil.showshort(ChooseUploadActivity.this, e.getMessage());
                            }
                        }
                    });
                }catch (Exception e){

                    ToastUtil.showshort(ChooseUploadActivity.this,e.getMessage());
                }
            }
        }


    }
    private boolean uploadPhotoDone;
    private boolean getThirdObj;
    private AVObject thirdObject;
    private void getThirdType(){
        AVQuery.doCloudQueryInBackground("select * from ThirdType where thirdTypeId='" + id + "'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if(e==null){
                    if(avCloudQueryResult!=null&&!avCloudQueryResult.getResults().isEmpty()){
                     thirdObject=   avCloudQueryResult.getResults().get(0);
                        handler.sendEmptyMessage(0x22);
                    }
                }else {
                    ToastUtil.showshort(ChooseUploadActivity.this,e.getMessage());
                }
            }
        });
    }

    String path;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        path=intent.getStringExtra("path");
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:

                    break;
                case 0x12:
                    upLoadPhoto(path);
                    break;
                case 0x22:
                    getThirdObj=true;
                    break;
                case 0121:
                    uploadPhotoDone=true;
                    if(getThirdObj&&uploadPhotoDone) {
                        AVFile avFile = (AVFile) msg.obj;
                        thirdObject.put("photo", avFile);
                        thirdObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    ToastUtil.showshort(ChooseUploadActivity.this,"success");
                                } else {
                                    ToastUtil.showshort(ChooseUploadActivity.this, e.getMessage());
                                }
                            }
                        });
                        getThirdObj=false;
                        uploadPhotoDone=false;
                    }else {
                        ToastUtil.showshort(ChooseUploadActivity.this,"please commit upload later or check network");
                    }
                    break;
            }
        }
    };


}
