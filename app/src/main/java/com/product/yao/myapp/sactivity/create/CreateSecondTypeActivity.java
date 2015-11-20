package com.product.yao.myapp.sactivity.create;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.entity.SecondType;
import com.product.yao.myapp.utils.MyDialog;
import com.product.yao.myapp.utils.ToastUtil;

import java.util.UUID;

/**
 * Created by paichufang on 15-11-10.
 */
public class CreateSecondTypeActivity extends BaseActivity{

    private SecondType secondtype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_create_second_type);
        secondtype=new SecondType();
        getFirstType();
        //点击commit
        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edit_first_type);
                String name = editText.getText().toString();
                secondtype.put("secondTypeId", UUID.randomUUID().toString());
                secondtype.put("secondTypeName",name);
                if (name == null || name.equals("")) {
                    ToastUtil.showshort(CreateSecondTypeActivity.this, "请输入SecondType");
                    return;
                } else if (secondtype.get("firstType") == null) {
                    ToastUtil.showshort(CreateSecondTypeActivity.this, "请选择FirstType");
                    return;
                } else {
//                    Create.createobject(CreateSecondTypeActivity.this, secondtype);
                    secondtype.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if(e==null){
                                ToastUtil.showshort(CreateSecondTypeActivity.this,"success");
                            }else {
                                ToastUtil.showshort(CreateSecondTypeActivity.this,e.getMessage());
                            }

                        }
                    });
                }

            }
        });
        findViewById(R.id.choose_first_type_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.singleChooseDialog(CreateSecondTypeActivity.this, handler, items, 0x123);
            }
        });
    }
    private AVCloudQueryResult result;
    String items[];
    public void getFirstType(){
        AVQuery.doCloudQueryInBackground("select * from FirstType", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if(avCloudQueryResult!=null&&avCloudQueryResult.getResults().size()>0){
                    result=avCloudQueryResult;
                    items=new String[avCloudQueryResult.getResults().size()];
                    for(int i=0;i<avCloudQueryResult.getResults().size();i++){
                        items[i]=avCloudQueryResult.getResults().get(i).getString("firstTypeName");
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
                secondtype.put("firstType",result.getResults().get(Integer.valueOf(msg.obj.toString())));
                break;
        }
    }
};
}
