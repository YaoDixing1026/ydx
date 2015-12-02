package com.product.yao.myapp.sactivity.create;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.entity.FirstType;
import com.product.yao.myapp.entity.ThirdType;
import com.product.yao.myapp.utils.MyDialog;
import com.product.yao.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by paichufang on 15-11-10.
 */
public class CreateThirdTypeActivity extends BaseActivity{
    private ThirdType thirdtype;
    private Button firstButton;
    private Button secondButton;
    private Button uploadButton;
    private String thirdtypeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_create_third_type);
        firstButton=(Button)findViewById(R.id.choose_first_type_btn);
        secondButton=(Button)findViewById(R.id.choose_second_type_btn);
        uploadButton=(Button)findViewById(R.id.upload_third_type_photo);
        thirdtype=new ThirdType();
        getFirstType();
        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edit_first_type);
                String name = editText.getText().toString();

                thirdtype.put("thirdTypeName", name);
                if (name == null || name.equals("")) {
                    ToastUtil.showshort(CreateThirdTypeActivity.this, "请输入ThirdType");
                    return;
                } else if (thirdtype.get("firstTypeId") == null) {
                    ToastUtil.showshort(CreateThirdTypeActivity.this, "请选择FirstType");
                    return;
                } else if (thirdtype.get("secondTypeId") == null) {
                    ToastUtil.showshort(CreateThirdTypeActivity.this, "请选择SecondType");
                    return;
                } else {
                    thirdtypeId=UUID.randomUUID().toString();
                    thirdtype.put("thirdTypeId",thirdtypeId );
                    thirdtype.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                ToastUtil.showshort(CreateThirdTypeActivity.this, "success");
                                thirdtype = new ThirdType();
                                thirdtype.put("firstTypeId", firstTypeId);
                                thirdtype.put("secondTypeId", secondTypeId);
                            } else {
                                ToastUtil.showshort(CreateThirdTypeActivity.this, e.getMessage());
                            }
                        }
                    });
//                    Create.createobject(CreateThirdTypeActivity.this, thirdtype);

                }

            }
        });
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.singleChooseDialog(CreateThirdTypeActivity.this, handler, items, 0x123);
            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.singleChooseDialog(CreateThirdTypeActivity.this, handler, items1, 0x124);
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thirdtypeId!=null){
                    uploadIntent=new Intent(CreateThirdTypeActivity.this,ChooseUploadActivity.class);
                    uploadIntent.putExtra("id",thirdtypeId);
                    uploadIntent.putExtra("photoType","thirdType");
                    startActivity(uploadIntent);
                }else {
                    ToastUtil.showshort(CreateThirdTypeActivity.this,"please create third type first");
                }
            }
        });
    }
    private Intent uploadIntent;
    private AVCloudQueryResult result;
    String items[];
    public void getFirstType(){
        AVQuery.doCloudQueryInBackground("select * from FirstType", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (avCloudQueryResult != null && avCloudQueryResult.getResults().size() > 0) {
                    result = avCloudQueryResult;
                    items = new String[avCloudQueryResult.getResults().size()];
                    for (int i = 0; i < avCloudQueryResult.getResults().size(); i++) {
                        items[i] = avCloudQueryResult.getResults().get(i).getString("firstTypeName");
                    }

                }
            }
        });
    }

    private AVCloudQueryResult result1;
    String items1[];
    public void getSecondType(){
        AVQuery.doCloudQueryInBackground("select * from SecondType where firstTypeId='"+firstTypeId+"'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (avCloudQueryResult != null && avCloudQueryResult.getResults().size() > 0) {
                    result1 = avCloudQueryResult;
//                    items1 = new String[avCloudQueryResult.getResults().size()];
                    List<String>list=new ArrayList<String>();
                    for (int i = 0; i < avCloudQueryResult.getResults().size(); i++) {
                        Log.i("getsecondtype:",result1.getResults().get(i).get("firstTypeId").toString());
                        list.add(avCloudQueryResult.getResults().get(i).getString("secondTypeName"));

                    }
                    items1=new String [list.size()];
                    for(int i=0;i<list.size();i++){
                        items1[i]=list.get(i);
                    }
                }
            }
        });
    }
    String firstTypeId;
    String secondTypeId;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:
                    firstTypeId=result.getResults().get(Integer.valueOf(msg.obj.toString())).getString("firstTypeId");
                    thirdtype.put("firstTypeId", result.getResults().get(Integer.valueOf(msg.obj.toString())).getString("firstTypeId"));
                    firstButton.setText(result.getResults().get(Integer.valueOf(msg.obj.toString())).getString("firstTypeName"));
                    getSecondType();
                    break;
                case 0x124:
                    thirdtype.put("secondTypeId", result1.getResults().get(Integer.valueOf(msg.obj.toString())).getString("secondTypeId"));
                    secondButton.setText(result1.getResults().get(Integer.valueOf(msg.obj.toString())).getString("secondTypeName"));
                    secondTypeId=result1.getResults().get(Integer.valueOf(msg.obj.toString())).getString("secondTypeId");
                    break;
            }
        }
    };

}
