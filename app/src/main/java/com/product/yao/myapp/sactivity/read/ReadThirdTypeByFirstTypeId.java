package com.product.yao.myapp.sactivity.read;

import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by paichufang on 15-12-7.
 */
public class ReadThirdTypeByFirstTypeId {



    private Map<String,List<AVObject>> fToS;
    private Map<String,List<AVObject>> sToT;

    public ReadThirdTypeByFirstTypeId(){
        fToS=new Hashtable<>();
        sToT=new Hashtable<>();
    }
    /**
     * 通过左侧TypeId取数据
     */
    public void getSecondTypebyFirstTypeId(final String LeftClickTypeId,final Handler handler)
    {
        AVQuery.doCloudQueryInBackground("select * from SecondType where firstTypeId='" +
                LeftClickTypeId + "'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (avCloudQueryResult != null) {
                    List<AVObject> avObjects = (List<AVObject>) avCloudQueryResult.getResults();
                    fToS.put(LeftClickTypeId, avObjects);
                    for (final AVObject secondType : fToS.get(LeftClickTypeId)) {
                        AVQuery.doCloudQueryInBackground("select * from ThirdType where " +
                                "firstTypeId='" + LeftClickTypeId +
                                "' and secondTypeId='" + secondType.getString("secondTypeId") + "'", new CloudQueryCallback<AVCloudQueryResult>() {
                            @Override
                            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                if (avCloudQueryResult != null) {
                                    List<AVObject> avObjects = (List<AVObject>) avCloudQueryResult.getResults();

                                    sToT.put(secondType.getString("secondTypeId"), avObjects);
                                }
                                List<Map> list=new ArrayList();
                                list.add(fToS);
                                list.add(sToT);
                                Message message=new Message();
                                message.obj=list;
                                message.what=0x11;
                                handler.sendMessage(message);
                            }
                        });
                    }

                }
            }
        });
    }
}
