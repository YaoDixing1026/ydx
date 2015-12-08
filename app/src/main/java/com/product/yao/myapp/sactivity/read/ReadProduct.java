package com.product.yao.myapp.sactivity.read;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.product.yao.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paichufang on 15-12-7.
 */
public class ReadProduct {
    private Handler handler;
    public ReadProduct(Handler handler){
        this.handler=handler;
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
                List list=new ArrayList();
                list.add(itemsFirstType);
                list.add(resultFirstType);
                Message message=new Message();
                message.obj=list;
                message.what=0x41;
                handler.sendMessage(message);
            }
        });
    }
    /**
     * 取二级类别
     */
    private AVCloudQueryResult resultSecondType;
    private String []itemsSecondType;
    public void getSecondType(String firstId){
        AVQuery.doCloudQueryInBackground("select * from SecondType where firstTypeId='"+firstId+"'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (avCloudQueryResult != null && avCloudQueryResult.getResults().size() > 0) {
                    resultSecondType = avCloudQueryResult;
                    List<String> list=new ArrayList<String>();
                    for(int j=0;j<avCloudQueryResult.getResults().size();j++){
                        list.add(avCloudQueryResult.getResults().get(j).getString("secondTypeName"));
                    }

                    itemsSecondType = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        itemsSecondType[i] = list.get(i);
                    }
                }
                List list=new ArrayList();
                list.add(itemsSecondType);
                list.add(resultSecondType);
                Message message=new Message();
                message.obj=list;
                message.what=0x42;
                handler.sendMessage(message);
            }
        });
    }
    /**
     * 取三级类别
     */
    private AVCloudQueryResult resultThirdType;
    private String []itemsThirdType;
    public void getThirdType(String firstId,String secondId){
        AVQuery.doCloudQueryInBackground("select * from ThirdType where firstTypeId='"
                        +firstId+"' and secondTypeId='"
                        +secondId+"'",
                new CloudQueryCallback<AVCloudQueryResult>() {
                    @Override
                    public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                        if (avCloudQueryResult != null && avCloudQueryResult.getResults().size() > 0) {
                            resultThirdType = avCloudQueryResult;
                            List<String> list=new ArrayList<String>();
                            for(int j=0;j<avCloudQueryResult.getResults().size();j++){
                                list.add(avCloudQueryResult.getResults().get(j).getString("thirdTypeName"));
                            }

                            itemsThirdType = new String[list.size()];
                            for (int i = 0; i < list.size(); i++) {
                                itemsThirdType[i] = list.get(i);
                            }
                        }
//                        else {
//                            itemsThirdType = new String[0];
//                            thirdTypeBtn.setText("");
//                            product.put("thirdTypeId",null);
//                        }
                        List list=new ArrayList();
                        list.add(itemsThirdType);
                        list.add(resultThirdType);
                        Message message=new Message();
                        message.obj=list;
                        message.what=0x43;
                        handler.sendMessage(message);
                    }
                });
    }
    private AVCloudQueryResult resultProduct;
    private String []itemsProduct;
    public void getProductListByThirdId(String thirdTypeId){
        AVQuery.doCloudQueryInBackground("select * from Product where thirdTypeId='" + thirdTypeId + "'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if(avCloudQueryResult!=null&&avCloudQueryResult.getResults().size()>0){
                    resultProduct = avCloudQueryResult;
                    List<String> list=new ArrayList<String>();
                    for(int j=0;j<avCloudQueryResult.getResults().size();j++){
                        list.add(avCloudQueryResult.getResults().get(j).getString("productName"));
                    }

                    itemsProduct = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        itemsProduct[i] = list.get(i);
                    }
                }
                List list=new ArrayList();
                list.add(itemsProduct);
                list.add(resultProduct);
                Message message=new Message();
                message.obj=list;
                message.what=0x44;
                handler.sendMessage(message);

            }
        });
    }
}
