package com.product.yao.myapp.sactivity.create;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_create_third_type);
        thirdtype=new ThirdType();
        getFirstType();
        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edit_first_type);
                String name = editText.getText().toString();
                thirdtype.put("thirdTypeId", (UUID.randomUUID().toString()));
                thirdtype.put("thirdTypeName",name);
                if (name == null || name.equals("")) {
                    ToastUtil.showshort(CreateThirdTypeActivity.this, "请输入ThirdType");
                    return;
                } else if (thirdtype.get("firstType") == null) {
                    ToastUtil.showshort(CreateThirdTypeActivity.this, "请选择FirstType");
                    return;
                }else if (thirdtype.get("secondType") == null) {
                    ToastUtil.showshort(CreateThirdTypeActivity.this, "请选择SecondType");
                    return;
                }
                else {
                    thirdtype.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if(e==null){
                                ToastUtil.showshort(CreateThirdTypeActivity.this,"success");
                            }else {
                                ToastUtil.showshort(CreateThirdTypeActivity.this,e.getMessage());
                            }
                        }
                    });
//                    Create.createobject(CreateThirdTypeActivity.this, thirdtype);

                }

            }
        });
        findViewById(R.id.choose_first_type_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.singleChooseDialog(CreateThirdTypeActivity.this, handler, items, 0x123);
            }
        });
        findViewById(R.id.choose_second_type_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.singleChooseDialog(CreateThirdTypeActivity.this, handler, items1, 0x124);
            }
        });
    }
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
        AVQuery.doCloudQueryInBackground("select * from SecondType", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (avCloudQueryResult != null && avCloudQueryResult.getResults().size() > 0) {
                    result1 = avCloudQueryResult;
//                    items1 = new String[avCloudQueryResult.getResults().size()];
                    List<String>list=new ArrayList<String>();
                    for (int i = 0; i < avCloudQueryResult.getResults().size(); i++) {
                        Log.i("getsecondtype:",result1.getResults().get(i).get("firstType").toString());
                        try {
                           FirstType  firstType1=(FirstType)result1.getResults().get(i).get("firstType");

                            if(firstType1.getObjectId()
                                    .equals(result.getResults().get(i).getObjectId())){
                                list.add(avCloudQueryResult.getResults().get(i).getString("secondTypeName"));
                            }
                        }catch (Exception e1){

                        }

                    }
                    items1=new String [list.size()];
                    for(int i=0;i<list.size();i++){
                        items1[i]=list.get(i);
                    }
                }
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:
                    thirdtype.put("firstType", result.getResults().get(Integer.valueOf(msg.obj.toString())));
                    getSecondType();
                    break;
                case 0x124:
                    thirdtype.put("secondType",result1.getResults().get(Integer.valueOf(msg.obj.toString())));
                    break;
            }
        }
    };

}
