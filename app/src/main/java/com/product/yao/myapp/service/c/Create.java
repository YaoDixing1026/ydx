package com.product.yao.myapp.service.c;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.product.yao.myapp.utils.ToastUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by ydx on 15-11-6.
 */
public class Create {

    public static void createobject(final Context context,Object object ){
        String s=object.getClass().getName();
        s=s.substring(s.lastIndexOf(".")+1);
        AVObject avObject=new AVObject(s);
        try {
            Field []fields=object.getClass().getDeclaredFields();
            Method []methods=object.getClass().getDeclaredMethods();
            for(int i=0;i<fields.length;i++){

                for(int j=0;j<methods.length;j++){
                    if(methods[j].getName().contains("get")){
                        if(fields[i].getName().toUpperCase().equals(methods[j].getName().toUpperCase().substring(3,methods[j].getName().length()))){
                            String type = fields[i].getGenericType().toString();
                            if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                                String value = (String) methods[j].invoke(object); // 调用getter方法获取属性值
                                if (value == null) {
                                    avObject.put(fields[i].getName(), "");
                                }else {
                                    avObject.put(fields[i].getName(), methods[j].invoke(object));
                                }
                            }
                            else if (type.equals("class java.lang.Integer")) {
                                Integer value = (Integer) methods[j].invoke(object);
                                if (value == null) {
                                    avObject.put(fields[i].getName(), 0);
                                }else {
                                    avObject.put(fields[i].getName(), methods[j].invoke(object));
                                }
                            }
                            else if (type.equals("class java.lang.Boolean")) {
                                Boolean value = (Boolean) methods[j].invoke(object);
                                if (value == null) {
                                    avObject.put(fields[i].getName(), false);
                                }else {
                                    avObject.put(fields[i].getName(), methods[j].invoke(object));

                                }
                            }
                            else if (type.equals("class java.util.Date")) {

                                Date value = (Date) methods[j].invoke(object);
                                if (value == null) {
                                    avObject.put(fields[i].getName(), new Date());
                                }else {
                                    avObject.put(fields[i].getName(), methods[j].invoke(object));
                                }
                            }
                            else if (type.equals("class java.lang.Float")) {

                                Date value = (Date) methods[j].invoke(object);
                                if (value == null) {
                                    avObject.put(fields[i].getName(), 0);
                                }else {
                                    avObject.put(fields[i].getName(), methods[j].invoke(object));
                                }
                            }
                            else if (type.equals("class java.lang.Double")) {
                                Date value = (Date) methods[j].invoke(object);
                                if (value == null) {
                                    avObject.put(fields[i].getName(), 0);
                                }else {
                                    avObject.put(fields[i].getName(), methods[j].invoke(object));
                                }
                            }
                            else {
//                                Object object1=(Object)methods[j].invoke(object);
//                                if (object1 == null) {
//                                    avObject.put(fields[i].getName(), new Date());
//                                }else {
//                                    avObject.put(fields[i].getName(), methods[j].invoke(object));
//                                }
                            }
                            break;
                        }
                    }else {
                        continue;
                    }

                }

            }

            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e!=null&&e.getMessage()!=null){
                        ToastUtil.showshort(context,e.getMessage());
                    }else {
                        ToastUtil.showshort(context,"success");
                    }

                }
            });
        }catch (Exception e){
            Log.e("Exception:",e.getMessage());
        }

    }

}
