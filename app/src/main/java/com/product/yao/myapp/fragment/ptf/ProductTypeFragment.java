package com.product.yao.myapp.fragment.ptf;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.product.yao.myapp.R;
import com.product.yao.myapp.adapter.PTFLeftAdapter;
import com.product.yao.myapp.base.BaseFragment;

import java.util.List;

/**
 * Created by paichufang on 15-11-6.
 */
public class ProductTypeFragment extends BaseFragment{
    private View view;
    private ListView leftType;
    private boolean isFirstOpen=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_product_type,null);
        return view;
    }

    private List<AVObject> leftTypes;
    @Override
    public void onResume() {
        super.onResume();

        //异步查询左侧type
        AVQuery.doCloudQueryInBackground("select * from FirstType", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult result, AVException cqlException) {
                if (cqlException == null) {
                    List<AVObject> avObjects = (List<AVObject>) result.getResults(); //// 这里是你查询到的结果
                    leftTypes = avObjects;
                    handler.sendEmptyMessage(01);
                } else {

                }
            }

        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstOpen=true;
    }

    private RelativeLayout rightFragmentLayout;
    private FragmentManager fragmentManager;
    private void initView(){
        //左侧TypeList
        leftType=(ListView)view.findViewById(R.id.list_left_type);
        PTFLeftAdapter adapter = new PTFLeftAdapter(leftTypes, getActivity());
        leftType.setAdapter(adapter);
        leftType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (leftTypes != null) {
                    String firstTypeId = leftTypes.get(position).getString("firstTypeId");
                    initRightContent(firstTypeId);
                }

            }
        });
        if(leftTypes!=null){
            initRightContent(leftTypes.get(0).getString("firstTypeId"));
        }

    }
    private void initRightContent(String firstTypeId){
        rightFragmentLayout=(RelativeLayout)view.findViewById(R.id.fragment_right_content);
        ProductRightFragment fragment=new ProductRightFragment();
        Bundle bundle=new Bundle();
        bundle.putString("clickTypeId", firstTypeId);
        fragment.setArguments(bundle);
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(rightFragmentLayout.getId(),fragment)
                .show(fragment)
                .commit();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 01:
                    if(isFirstOpen){
                        initView();
                        isFirstOpen=false;
                    }

                    break;
            }
        }
    };
}
