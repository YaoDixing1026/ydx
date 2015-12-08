package com.product.yao.myapp.sactivity.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.entity.ProductPhoto;
import com.product.yao.myapp.sactivity.read.ReadProduct;
import com.product.yao.myapp.service.c.Create;
import com.product.yao.myapp.utils.MyDialog;
import com.product.yao.myapp.utils.ToastUtil;
import com.product.yao.myapp.utils.WHUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by paichufang on 15-11-17.
 */
public class ProductPhotoUploadActivity extends BaseActivity{
    private String id;
    private String photoType;
    private ReadProduct readProduct;
    private Button firstBtn;
    private Button secondBtn;
    private Button thirdBtn;
    private Context mContext;
    private Button chooseProduct;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_create_product_photo);
        mContext=this;
        intent=new Intent(mContext,ChooseUploadActivity.class);
        final ProductPhotoUploadActivity chooseUploadActivity=new ProductPhotoUploadActivity();
        final String className=this.getPackageName()+"."+this.getLocalClassName();
        readProduct =new ReadProduct(handler);
        firstBtn=(Button) findViewById(R.id.choose_first);
        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readProduct.getFirstType();
            }
        });
        secondBtn=(Button)findViewById(R.id.choose_second);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstTypeId==null||firstTypeId.equals("")){
                    ToastUtil.showshort(mContext,"请选择一级分类");
                }else {
                    readProduct.getSecondType(firstTypeId);
                }
            }
        });
        thirdBtn=(Button)findViewById(R.id.choose_third);
        thirdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstTypeId==null||firstTypeId.equals("")){
                    ToastUtil.showshort(mContext,"请选择一级分类");
                }else if (secondTypeId==null||secondTypeId.equals("")){
                    ToastUtil.showshort(mContext,"请选择二级分类");
                } else {
                    readProduct.getThirdType(firstTypeId,secondTypeId);
                }

            }
        });
        chooseProduct=(Button)findViewById(R.id.choose_product);
        chooseProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readProduct.getProductListByThirdId(thirdTypeId);
            }
        });
        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("photoType", "product");
                startActivity(intent);
            }
        });
    }


    String path;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        path=intent.getStringExtra("path");
    }
    private String[] firstResult;
    private AVCloudQueryResult firstAVList;
    private String[] secondResult;
    private AVCloudQueryResult secondAVList;
    private String[] thirdResult;
    private AVCloudQueryResult thirdAVList;
    private AVCloudQueryResult resultProduct;
    private String []itemsProduct;
    private String firstTypeId;
    private String secondTypeId;
    private String thirdTypeId;
    private String productId;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x41:
                   List list1=(List) msg.obj;
                    firstResult=(String[])list1.get(0);
                    firstAVList=(AVCloudQueryResult)list1.get(1);
                    MyDialog.singleChooseDialog(ProductPhotoUploadActivity.this,handler,firstResult,0x123);
                    break;
                case 0x42:
                    List list2=(List) msg.obj;
                    secondResult=(String[])list2.get(0);
                    secondAVList=(AVCloudQueryResult)list2.get(1);
                    MyDialog.singleChooseDialog(ProductPhotoUploadActivity.this,handler,secondResult,0x124);
                    break;
                case 0x43:
                    List list3=(List) msg.obj;
                    thirdResult=(String[])list3.get(0);
                    thirdAVList=(AVCloudQueryResult)list3.get(1);
                    MyDialog.singleChooseDialog(ProductPhotoUploadActivity.this,handler,thirdResult,0x125);
                    break;
                case 0x44:
                    List list4=(List) msg.obj;
                    itemsProduct=(String[])list4.get(0);
                    resultProduct=(AVCloudQueryResult)list4.get(1);
                    MyDialog.singleChooseDialog(ProductPhotoUploadActivity.this,handler,itemsProduct,0x126);
                    break;
                case 0x123:
                    int position1=(int)msg.obj;
                    String firstName=  firstAVList.getResults().get(position1).getString("firstTypeName");
                    firstTypeId=firstAVList.getResults().get(position1).getString("firstTypeId");
                    firstBtn.setText(firstName);
                    break;
                case 0x124:
                    int position2=(int)msg.obj;
                    String secondName=  secondAVList.getResults().get(position2).getString("secondTypeName");
                    secondTypeId=secondAVList.getResults().get(position2).getString("secondTypeId");
                    secondBtn.setText(secondName);
                    break;
                case 0x125:
                    int position3=(int)msg.obj;
                    String thirdName=  thirdAVList.getResults().get(position3).getString("thirdTypeName");
                    thirdTypeId=thirdAVList.getResults().get(position3).getString("thirdTypeId");
                    thirdBtn.setText(thirdName);
                    break;
                case 0x126:
                    int position4=(int)msg.obj;
                    String productName=  resultProduct.getResults().get(position4).getString("productName");
                    intent.putExtra("product",resultProduct.getResults().get(position4).toString());
                    chooseProduct.setText(productName);
                    break;

            }
        }
    };


}
