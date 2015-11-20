package com.product.yao.myapp.sactivity.create;

import android.os.Bundle;
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
import com.product.yao.myapp.utils.ToastUtil;

import java.util.UUID;

/**
 * Created by paichufang on 15-11-6.
 */
public class CreateFirstTypeAcitvity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_create_first_type);
        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText=(EditText)findViewById(R.id.edit_first_type);
                String name= editText.getText().toString();
                CheckFirstType(name);

            }
        });
    }
    private void CheckFirstType(final String name){
        AVQuery.doCloudQueryInBackground("select * from FirstType where firstTypeName= '" + name + "'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e != null) {
                    ToastUtil.showshort(CreateFirstTypeAcitvity.this, e.getMessage());
                    if (e.getCode() == 101) {
                        FirstType firstType = new FirstType();
                        firstType.put("firstTypeId", UUID.randomUUID().toString());
                        firstType.put("firstTypeName", name);
                        save(firstType);
//                        Create.createobject(CreateFirstTypeAcitvity.this, firstType);
                    }
                } else {
                    if (avCloudQueryResult != null && avCloudQueryResult.getResults() != null) {
                        if (avCloudQueryResult.getResults().size() > 0) {
                            ToastUtil.showshort(CreateFirstTypeAcitvity.this, "FirstType重复");
                        } else {
                            FirstType firstType = new FirstType();
                            firstType.put("firstTypeId", UUID.randomUUID().toString());
                            firstType.put("firstTypeName", name);
                            save(firstType);
//                            Create.createobject(CreateFirstTypeAcitvity.this, firstType);
                        }
                    }
                }
            }
        });
    }
    private void save(FirstType firstType){
        firstType.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    ToastUtil.showshort(CreateFirstTypeAcitvity.this,"success");
                }else {
                    ToastUtil.showshort(CreateFirstTypeAcitvity.this,e.getMessage());
                }
            }
        });
    }
}
