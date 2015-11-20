package com.product.yao.myapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * Created by paichufang on 15-11-10.
 */
public class MyDialog {

    private static int singleSelectedId; // 单选ＩＤ

    /**
     *
     * @param mContext context
     * @return which u choose
     */
    public static void singleChooseDialog(final Context mContext,final Handler handler,final String items[],final int what){

        singleSelectedId = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("单选Dialog");
        ScrollView scrollView=new ScrollView(mContext);
        ScrollView.LayoutParams params=new ScrollView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,150);
        scrollView.setLayoutParams(params);
        builder.setView(scrollView);
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                singleSelectedId = which;
                Toast.makeText(mContext, "你选择的ID为：" + which, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if (singleSelectedId >= 0) {
                    Toast.makeText(mContext, "你选择的ID为："+singleSelectedId, Toast.LENGTH_SHORT).show();
                    Message message=new Message();
                    message.what=what;
                    message.obj=singleSelectedId;
                    handler.sendMessage(message);
                    dialog.dismiss();
                } else {
                    singleSelectedId = 0;
                    // 业务逻辑
                    Toast.makeText(mContext, "请选择一个", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }
    public static void productStatusDialog(final Context context,final Handler handler,final int what){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("上架状态");
        String items[]={
          "已上架","已下架"
        };
        singleSelectedId = -1;
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                singleSelectedId=which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Message message=new Message();
                if(singleSelectedId==0){
                    message.obj=true;
                    message.what=what;
                }else if(singleSelectedId==1){
                    message.obj=false;
                    message.what=what;
                }
                handler.sendMessage(message);
            }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
        .create().show();
    }
}
