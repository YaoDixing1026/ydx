package com.product.yao.myapp.sactivity.create;

import android.os.Bundle;
import android.view.View;

import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.entity.MyRole;
import com.product.yao.myapp.entity.MyUser;
import com.product.yao.myapp.service.c.Create;

import java.util.UUID;

/**
 * Created by paichufang on 15-11-6.
 */
public class CreateUserActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_user);
        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUser user = new MyUser();
                MyRole role = new MyRole();
                role.setRole(0);
                user.setMyRole(role);
                user.setNickName("admin");
                user.setUserId(UUID.randomUUID().toString());
                Create.createobject(CreateUserActivity.this, user);
            }
        });

    }
}
