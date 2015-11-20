package com.product.yao.myapp.sactivity.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.entity.Product;
import com.product.yao.myapp.service.c.Create;
import com.product.yao.myapp.utils.MyDialog;
import com.product.yao.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ydx on 15-11-13.
 */
public class CreateProductActivity extends BaseActivity implements View.OnClickListener{
    private Context context;
    private EditText productNameEdit;
    private EditText productPrice;
    private EditText productOnSalePrice;
    private EditText productCount;
    private EditText productDescription;
    private EditText productDate;
    private EditText productLife;
    private Button firstTypeBtn;
    private Button secondTypeBtn;
    private Button thirdTypeBtn;
    private Button productStatesBtn;
    private Button commit;

    private AVObject firstType;
    private AVObject secondType;
    private AVObject thirdType;

    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_create_product);
        context=this;
        product=new Product();
        firstType=new AVObject();
        secondType=new AVObject();
        thirdType=new AVObject();
        initView();
        getFirstType();
    }
    private void initView(){
        productNameEdit=(EditText)findViewById(R.id.edit_product_name);
        productPrice=(EditText)findViewById(R.id.edit_product_price);
        productOnSalePrice=(EditText)findViewById(R.id.edit_product_onsale);
        productCount=(EditText)findViewById(R.id.edit_count);
        productDescription=(EditText)findViewById(R.id.edit_product_description);
        productDate=(EditText)findViewById(R.id.edit_product_date);
        productLife=(EditText)findViewById(R.id.edit_product_life);

        firstTypeBtn=(Button)findViewById(R.id.choose_first_type_btn);
        secondTypeBtn=(Button)findViewById(R.id.choose_second_type_btn);
        thirdTypeBtn=(Button)findViewById(R.id.choose_third_type_btn);
        firstTypeBtn.setOnClickListener(this);
        secondTypeBtn.setOnClickListener(this);
        thirdTypeBtn.setOnClickListener(this);

        productStatesBtn=(Button)findViewById(R.id.choose_product_status_btn);

        commit=(Button)findViewById(R.id.commit);
        productStatesBtn.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_first_type_btn:
                MyDialog.singleChooseDialog(context, handler, itemsFirstType, 0x123);
                break;
            case R.id.choose_second_type_btn:
                MyDialog.singleChooseDialog(context, handler, itemsSecondType, 0x124);
                break;
            case R.id.choose_third_type_btn:
                MyDialog.singleChooseDialog(context, handler, itemsThirdType, 0x125);
                break;
            case R.id.choose_product_status_btn:
                MyDialog.productStatusDialog(context,handler,0x126);
                break;
            case R.id.up_load_product_photo_btn:
                startActivity(new Intent(CreateProductActivity.this,ChooseUploadActivity.class));
                break;
            case R.id.commit:
                //valid
               if( validInfo()){
                 final   String id=UUID.randomUUID().toString();
                   product.put("productId", id);
                   product.saveInBackground(new SaveCallback() {
                       @Override
                       public void done(AVException e) {
                           if(e==null){
                               ToastUtil.showshort(CreateProductActivity.this,"success");
                               CreateProductActivity
                                       .this
                                       .startActivity(new Intent(CreateProductActivity
                                               .this, ChooseUploadActivity
                                               .class)
                                               .putExtra("productId",id));
                               product=new Product();
                           }else {
                               ToastUtil.showshort(CreateProductActivity.this,e.getMessage());
                           }
                       }
                   });
               }

                break;
        }
    }

    /**
     * 取一级类别
     */
    private AVCloudQueryResult resultFirstType;
    private String []itemsFirstType;
    public void getFirstType(){
        AVQuery.doCloudQueryInBackground("select * from FirstType", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (avCloudQueryResult != null && avCloudQueryResult.getResults().size() > 0) {
                    resultFirstType = avCloudQueryResult;
                    itemsFirstType = new String[avCloudQueryResult.getResults().size()];
                    for (int i = 0; i < avCloudQueryResult.getResults().size(); i++) {
                        itemsFirstType[i] = avCloudQueryResult.getResults().get(i).getString("firstTypeName");
                    }
                }
            }
        });
    }
    /**
     * 取二级类别
     */
    private AVCloudQueryResult resultSecondType;
    private String []itemsSecondType;
    public void getSecondType(){
        AVQuery.doCloudQueryInBackground("select * from SecondType", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (avCloudQueryResult != null && avCloudQueryResult.getResults().size() > 0) {
                    resultSecondType = avCloudQueryResult;
                    List<String> list=new ArrayList<String>();
                    for(int j=0;j<avCloudQueryResult.getResults().size();j++){
                       AVObject avObject=(AVObject) avCloudQueryResult.getResults().get(j).get("firstType");
                        if(avObject.getObjectId().equals(firstType.getObjectId())){
                            list.add(avCloudQueryResult.getResults().get(j).getString("secondTypeName"));
                        }
                    }

                    itemsSecondType = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        itemsSecondType[i] = list.get(i);
                    }
                }
            }
        });
    }
    /**
     * 取三级类别
     */
    private AVCloudQueryResult resultThirdType;
    private String []itemsThirdType;
    public void getThirdType(){
        AVQuery.doCloudQueryInBackground("select * from ThirdType", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (avCloudQueryResult != null && avCloudQueryResult.getResults().size() > 0) {
                    resultThirdType = avCloudQueryResult;
                    List<String> list=new ArrayList<String>();
                    for(int j=0;j<avCloudQueryResult.getResults().size();j++){
                        AVObject avObject=(AVObject) avCloudQueryResult.getResults().get(j).get("secondType");
                        if(avObject.getObjectId().equals(secondType.getObjectId())){
                            list.add(avCloudQueryResult.getResults().get(j).getString("thirdTypeName"));
                        }
                    }

                    itemsThirdType = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        itemsThirdType[i] = list.get(i);
                    }
                }
            }
        });
    }
    private void upLoadPhoto(){

    }
    /**
     *验证输入内容
     * @return
     */
    private boolean validInfo(){
        //product name
        if(productNameEdit.getText().toString().equals("")){
            ToastUtil.showshort(this,"请输入商品名");
            return false;
        }else {
            if(productNameEdit.getText().length()>25){
                ToastUtil.showshort(this,"商品名要小于25个字");
                return false;
            }else {
                product.put("productName", productNameEdit.getText().toString());
            }
        }
        //product price
        if(productPrice.getText().toString().equals("")){
            ToastUtil.showshort(this,"请输入商品价格");
            return false;
        }else {
            if(productPrice.getText().toString().contains(".")&&productPrice.getText().toString().length()<3){
                ToastUtil.showshort(this, "请输入正确的商品价格");
                return false;
            }else if(productPrice.getText().toString().contains(".")&&productPrice.getText().toString().length()-productPrice.getText().toString().indexOf(".")>2){
                ToastUtil.showshort(this,"商品价格小数点后最多2位");
                return false;
            }else {
                product.put("productPrice", Double.valueOf(productPrice.getText().toString()));
            }
        }
        //product onSale price
        if(productOnSalePrice.getText().toString().equals("")){
            ToastUtil.showshort(this,"请输入促销价格");
            return false;
        }else {
            if(productOnSalePrice.getText().toString().contains(".")&&productPrice.getText().toString().length()<3){
                ToastUtil.showshort(this,"请输入正确的促销价格");
                return false;
            }else if(productOnSalePrice.getText().toString().contains(".")&&productOnSalePrice.getText().toString().length()-productPrice.getText().toString().indexOf(".")>2){
                ToastUtil.showshort(this,"促销价格小数点后最多2位");
                return false;
            }else {
                product.put("productOnSalePrice", Double.valueOf(productOnSalePrice.getText().toString()));
            }
        }
        //product count
        if(productCount.getText().toString().equals("")){
            ToastUtil.showshort(this,"请输入商品数量");
            return false;
        }else {
            if(Integer.valueOf(productCount.getText().toString())==0){
                ToastUtil.showshort(this,"商品数量不能为0");
                return false;
            }else {
                product.put("productCount", Integer.valueOf(productCount.getText().toString()));
            }
        }
        //product description
        product.put("productDescription", productDescription.getText().toString());
        //product date
        product.put("productDate",productDate.getText().toString());
        //product life
        product.put("productLife",productLife.getText().toString());
        if(product.get("firstType")==null){
            ToastUtil.showshort(this,"请选择一级分类");
            return false;
        }
        if(product.get("secondType")==null){
            ToastUtil.showshort(this,"请选择二级分类");
            return false;
        }
        if(product.get("thirdType")==null){
            ToastUtil.showshort(this,"请选择三级分类");
            return false;
        }

        return true;
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:
                    firstTypeBtn.setText(itemsFirstType[Integer.valueOf(msg.obj+"")]);
                    firstType=resultFirstType.getResults().get(Integer.valueOf(msg.obj+""));
                    product.put("firstType",firstType);
                    getSecondType();
                    break;
                case 0x124:
                    secondTypeBtn.setText(itemsSecondType[Integer.valueOf(msg.obj + "")]);
                    secondType=resultSecondType.getResults().get(Integer.valueOf(msg.obj+""));
                    product.put("secondType",secondType);
                    getThirdType();
                    break;
                case 0x125:
                    thirdTypeBtn.setText(itemsThirdType[Integer.valueOf(msg.obj+"")]);
                    thirdType=resultThirdType.getResults().get(Integer.valueOf(msg.obj+""));
                    product.put("thirdType",thirdType);
                    break;
                case 0x126:
                    product.put("productStatus", (boolean)msg.obj);
                    break;
            }
        }
    };
}
