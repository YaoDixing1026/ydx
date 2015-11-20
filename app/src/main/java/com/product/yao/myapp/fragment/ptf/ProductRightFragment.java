package com.product.yao.myapp.fragment.ptf;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseFragment;
import com.product.yao.myapp.entity.FirstType;
import com.product.yao.myapp.myview.ActivityBottomItemView;
import com.product.yao.myapp.myview.typerightcoutent.TypeFragmentOneLayoutH;
import com.product.yao.myapp.myview.typerightcoutent.TypeFragmentRightContent;
import com.product.yao.myapp.utils.WHUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paichufang on 15-11-7.
 */
public class ProductRightFragment extends BaseFragment{

    private String LeftClickTypeId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeftClickTypeId= this.getArguments().getString("clickTypeId");

        needSecondType=new ArrayList<>();
        needThirdType=new ArrayList<>();
        needProduct=new ArrayList<>();

        getSecondTypebyFirstTypeId();
        getThirdTypebySecondTypeId();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_product_right_content,null);
        return view;
    }
    private View view;
    private LinearLayout content;//father layout
    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 通过左侧TypeId取数据
     */
    private void getSecondTypebyFirstTypeId(){
        AVQuery.doCloudQueryInBackground("select * from SecondType", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if(avCloudQueryResult!=null) {
                    List<AVObject> avObjects = (List<AVObject>) avCloudQueryResult.getResults();
                    for (AVObject avObject : avObjects) {
                        String fistTypeJson = avObject.get("firstType").toString();
                        try {
                            JSONObject jsonObject = new JSONObject(fistTypeJson);
                            String id = jsonObject.getString("firstTypeId");
                            if (id != null && LeftClickTypeId != null) {
                                if (id.equals(LeftClickTypeId)) {
                                    Message message = new Message();
                                    message.what = 01;
                                    message.obj = avObject;
                                    handler.sendMessage(message);
                                }
                            }

                        } catch (Exception e1) {

                        }
                    }
                }

            }
        });
    }

    /**
     * 取thirdType
     */
    private void getThirdTypebySecondTypeId(){
        AVQuery.doCloudQueryInBackground("select * from ThirdType", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (avCloudQueryResult != null) {
                    List<AVObject> avObjects = (List<AVObject>) avCloudQueryResult.getResults();
                    if (avObjects != null) {
                        for (AVObject avObject : avObjects) {
                            String secondTypeJson = avObject.get("secondType").toString();
                            try {
                                JSONObject jsonObject1 = new JSONObject(secondTypeJson);
                                String secondId = jsonObject1.getString("secondTypeId");

                                if (needSecondType != null) {
                                    for (AVObject object : needSecondType) {
                                        if (object.getString("secondTypeId").equals(secondId)) {
                                            Message message = new Message();
                                            message.what = 02;
                                            message.obj = avObject;
                                            handler.sendMessage(message);
                                        }
                                    }
                                }

                            } catch (Exception e1) {

                            }
                        }
                    }

                }
            }
        });
    }

    private void initView(){
        content=(LinearLayout)view.findViewById(R.id.item);
        final int oneWidth=WHUtil.getDeviceScreenWidth(getActivity())*1/4;
       new Thread(new Runnable() {
           @Override
           public void run() {
               for(int i=0;i<3;i++){
                   TypeFragmentRightContent tfrc=new TypeFragmentRightContent(getActivity());
                   tfrc.setLeftText("aa", 0, 0, 0);
                   tfrc.setRightText("bb", 0, 0, 0);
                   for(int j=0;j<3;j++){
                       TypeFragmentOneLayoutH hLayout=new TypeFragmentOneLayoutH(getActivity());
                       hLayout.setLayoutPadding(0,WHUtil.dip2px(getActivity(),5),0,0);
                       for (int k=0;k<3;k++){
                           ActivityBottomItemView v=new ActivityBottomItemView(getActivity());
                           v.setFatherGravity(Gravity.CENTER);
                           v.setImageView(R.mipmap.tab_home);
                           v.setText("aaa", 0, 0, 0);
                           v.setFatherWH(oneWidth, WHUtil.dip2px(getActivity(),40));
                           hLayout.addItem(v);
                       }
                       tfrc.addContent(hLayout);
                   }
                   Message message=new Message();
                   message.obj=tfrc;
                   message.what=0x123;
                   handler.sendMessage(message);
               }
           }
       }).start();

    }
    private List<AVObject> needSecondType;
    private List<AVObject> needThirdType;
    private List<AVObject> needProduct;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:
                    content.addView((TypeFragmentRightContent)msg.obj);
                    break;
                //get a secondType Avobject
                case 01:
                    needSecondType.add((AVObject)msg.obj);
                    Log.i("second",((AVObject) msg.obj).getString("secondTypeId"));
                    break;
                //get a ThirdType Avobject
                case 02:
                    needThirdType.add((AVObject)msg.obj);
                    break;
                //get a product Avobject
                case 03:

                    break;
            }
        }
    };
}
