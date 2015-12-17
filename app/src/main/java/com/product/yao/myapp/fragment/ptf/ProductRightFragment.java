package com.product.yao.myapp.fragment.ptf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVObject;
import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseFragment;
import com.product.yao.myapp.cactivity.product.ProductListActivity;
import com.product.yao.myapp.myview.ActivityBottomItemView;
import com.product.yao.myapp.myview.typerightcoutent.TypeFragmentOneLayoutH;
import com.product.yao.myapp.myview.typerightcoutent.TypeFragmentRightContent;
import com.product.yao.myapp.sactivity.read.ReadThirdTypeByFirstTypeId;
import com.product.yao.myapp.utils.WHUtil;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;

/**
 * Created by paichufang on 15-11-7.
 */
public class ProductRightFragment extends BaseFragment{

    private String LeftClickTypeId;
    private Map<String,List<AVObject>> fToS;
    private Map<String,List<AVObject>> sToT;
    private ImageLoader imageLoader;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader=ImageLoaderFactory.create(mContext);
        LeftClickTypeId= this.getArguments().getString("clickTypeId");
        fToS=new Hashtable<>();
        sToT=new Hashtable<>();
        getSecondTypebyFirstTypeId();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=LayoutInflater.from(mContext).inflate(R.layout.fragment_product_right_content,null);
        return view;
    }
    private View view;
    private LinearLayout content;//father layout
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * 通过左侧TypeId取数据
     */
    private void getSecondTypebyFirstTypeId()
    {
        ReadThirdTypeByFirstTypeId readThirdTypeByFirstTypeId =new ReadThirdTypeByFirstTypeId();

        readThirdTypeByFirstTypeId.getSecondTypebyFirstTypeId(LeftClickTypeId, handler);
    }


    private void initView(){
        content=(LinearLayout)view.findViewById(R.id.item);
        final int oneWidth=WHUtil.getDeviceScreenWidth(mContext)*1/4;
       new Thread(new Runnable() {
           @Override
           public void run() {
               for(int i=0;i<fToS.get(LeftClickTypeId).size();i++){
                   AVObject secondType=fToS.get(LeftClickTypeId).get(i);
                   String secondId=secondType.getString("secondTypeId");
                   TypeFragmentRightContent tfrc=new TypeFragmentRightContent(mContext);
                   tfrc.setLeftText(secondType.getString("secondTypeName"), 0, 0, 0);
                   tfrc.setRightText("bb", 0, 0, 0);
                   int count=0;
                   int size=sToT.get(secondId).size();
                   int mod=size %3;
                   if(size>=3) {
                       for (int j = 0; j < size / 3; j++) {

                           TypeFragmentOneLayoutH hLayout = new TypeFragmentOneLayoutH(mContext);
                           hLayout.setLayoutPadding(0, WHUtil.dip2px(mContext, 5), 0, 0);
                           for (int k = 0; k < 3; k++) {
                               if (count < 12&& count<size) {
                                   loadView(secondId,oneWidth,hLayout,count);
                                   count++;
                               } else {
                                   break;
                               }
                           }
                           tfrc.addContent(hLayout);

                       }
                   }

                   if(size>3&&mod!=0){
                       TypeFragmentOneLayoutH hLayout = new TypeFragmentOneLayoutH(mContext);
                       hLayout.setLayoutPadding(0, WHUtil.dip2px(mContext, 5), 0, 0);
                       for (int j = 0; j < mod ; j++) {
                           loadView(secondId,oneWidth,hLayout,count);
                           count++;
                       }
                       tfrc.addContent(hLayout);
                   }

                   if(size<3) {
                       TypeFragmentOneLayoutH hLayout = new TypeFragmentOneLayoutH(mContext);
                       hLayout.setLayoutPadding(0, WHUtil.dip2px(mContext, 5), 0, 0);
                       for (int j = 0; j < size ; j++) {
                           loadView(secondId,oneWidth,hLayout,count);
                           count++;
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
    private void loadView(String secondId,int oneWidth,TypeFragmentOneLayoutH hLayout,int count){
        AVObject thirdType = sToT.get(secondId).get(count);
        ActivityBottomItemView v = new ActivityBottomItemView(mContext);
        v.setFatherGravity(Gravity.CENTER_HORIZONTAL);
        if(thirdType.getString("photoUrl")!=null) {
            v.setImageViewByUrl(imageLoader, thirdType.getString("photoUrl"));
        }else {
            v.setImageViewByUrl(imageLoader,"http://pic2.ooopic.com/01/28/96/31b1OOOPIC95.jpg");
        }
        v.setImageViewWH(oneWidth-WHUtil.dip2px(mContext,20),oneWidth-WHUtil.dip2px(mContext,20));
        v.setText(thirdType.getString("thirdTypeName"), 0, 0, 0);
        v.setFatherWH(oneWidth, oneWidth);
        productOnclick(v,thirdType.getString("thirdTypeId"));
        hLayout.addItem(v);
    }
    private void productOnclick(View view,final String id){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("objectId",id);
                startActivity(new Intent(mContext, ProductListActivity.class)
                        .putExtras(bundle));
            }
        });
    }


    int count=0;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    count++;
                    List<Map> list=(List)msg.obj;
                    fToS=list.get(0);
                    if(count==fToS.get(LeftClickTypeId).size()) {
                        sToT=list.get(1);
                        initView();
                    }

                    break;
                case 0x123:
                   View  view= (TypeFragmentRightContent)msg.obj;
                    content.addView(view);
                    break;
            }
        }
    };

}
