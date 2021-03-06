package com.product.yao.myapp.sactivity.create;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.entity.Product;
import com.product.yao.myapp.entity.ProductPhoto;
import com.product.yao.myapp.entity.ThirdType;
import com.product.yao.myapp.service.c.Create;
import com.product.yao.myapp.utils.MyDialog;
import com.product.yao.myapp.utils.MyUtils;
import com.product.yao.myapp.utils.ToastUtil;
import com.product.yao.myapp.utils.WHUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by paichufang on 15-11-17.
 */
public class ChooseUploadActivity extends BaseActivity{
    private String id;
    private String photoType;
    private Button productPhotoTypeBtn;
    private String productPhotoType;
    private static final String [] PRODUCTPHOTOTYPE=new String[]{
            "封面","展示","listThumb"
    };


    private Uri currentPhotoUri;
    private List<String>photoNativePaths;
    private List<Bitmap> bitmaps;
    private List<ImageView> deleteImages;
    private List<ProgressBar>circleProgressList;
    private List<FrameLayout>frameLayouts;
    private RelativeLayout mImageLayout;
    private LinearLayout Layout1;
    private LinearLayout Layout2;
    private LinearLayout imageLayout1;
    private LinearLayout imageLayout2;
    private RelativeLayout fatherLayout;
    private int deviceWidth;
    private Context mContext;
    private EditText addContentEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_choose_picture);
        mContext=this;
        photoNativePaths=new ArrayList<>();
        circleProgressList=new ArrayList<>();
        deleteImages=new ArrayList<>();
        frameLayouts=new ArrayList<>();
        bitmaps=new ArrayList<>();
        deviceWidth=MyUtils.getDeviceScreenWidth(this);
        final ChooseUploadActivity chooseUploadActivity=new ChooseUploadActivity();
        final String className=this.getPackageName()+"."+this.getLocalClassName();
        id=getIntent().getStringExtra("id");
        photoType=getIntent().getStringExtra("photoType");
        if(photoType!=null) {
            if (photoType.equals("product")) {
                if(id!=null) {
                    getProductById();
                }else {
                    try {
                        product=AVObject.parseAVObject(getIntent().getStringExtra("product"));
                        handler.sendEmptyMessage(0x11);
                    }catch (Exception e){

                    }

                }
            }else {
                getThirdType();
            }
        }
        productPhotoTypeBtn=(Button)findViewById(R.id.product_photo_type);
        productPhotoTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.singleChooseDialog(ChooseUploadActivity.this, handler, PRODUCTPHOTOTYPE, 0x111);
            }
        });
        findViewById(R.id.upload_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseUploadActivity.this, PictureUploadActivity.class)
                        .putExtra("action", 200)
                        .putExtra("className", className));
            }
        });
        findViewById(R.id.upload_pai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseUploadActivity.this, PictureUploadActivity.class)
                        .putExtra("action", 100)
                        .putExtra("className", className));
            }
        });
        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0x12);
            }
        });





        mImageLayout=(RelativeLayout)findViewById(R.id.image_layout);
        Layout1=(LinearLayout)findViewById(R.id.layout1);
        Layout2=(LinearLayout)findViewById(R.id.layout2);
        imageLayout1=(LinearLayout)findViewById(R.id.image_layout1);
        imageLayout2=(LinearLayout)findViewById(R.id.image_layout2);
        int width=(deviceWidth-MyUtils.dip2px(mContext,15)*4)/3;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                width,
                width);
        params.leftMargin=MyUtils.dip2px(mContext, 15);


    }
    private AVObject product;
    private boolean isGetProduct;
    private void getProductById(){
        AVQuery.doCloudQueryInBackground("select * from Product where productId='" + id + "'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null) {
                    if (avCloudQueryResult != null && avCloudQueryResult.getResults().size() > 0) {
                        product = avCloudQueryResult.getResults().get(0);
                        handler.sendEmptyMessage(0x11);
                    }

                }else {
                    ToastUtil.showshort(ChooseUploadActivity.this,e.getMessage());
                }

            }
        });
    }
    private void upLoadPhoto(Bitmap bitmap){
        String path = MyUtils.compressFile(bitmap);
        if(photoType!=null){
            if(photoType.equals("product")){
                try {
                    String  ss=path.substring(path.lastIndexOf("/") + 1, path.length());
                    ToastUtil.showshort(ChooseUploadActivity.this, ss);
                    final AVFile avFile=AVFile.withAbsoluteLocalPath(path.substring(path.lastIndexOf("/")+1,path.length()),path);
                    avFile.getName();

                    avFile.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Message message=new Message();
                                message.obj=avFile;
                                message.what=0x21;
                                handler.sendMessage(message);

                            } else {
                                ToastUtil.showshort(ChooseUploadActivity.this, e.getMessage());
                            }
                        }
                    });
                }catch (Exception e){

                    ToastUtil.showshort(ChooseUploadActivity.this,e.getMessage());
                }
            }
            else if(photoType.equals("thirdType")){
                try {
                    String  ss=path.substring(path.lastIndexOf("/") + 1, path.length());
                    ToastUtil.showshort(ChooseUploadActivity.this,ss);
                    final AVFile avFile=AVFile.withAbsoluteLocalPath(path.substring(path.lastIndexOf("/")+1,path.length()),path);
                    avFile.getName();
                    avFile.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Message message=new Message();
                                message.what=0121;
                                message.obj=avFile;
                                handler.sendMessage(message);
                            } else {
                                ToastUtil.showshort(ChooseUploadActivity.this, e.getMessage());
                            }
                        }
                    });
                }catch (Exception e){

                    ToastUtil.showshort(ChooseUploadActivity.this,e.getMessage());
                }
            }
        }


    }
    private boolean uploadPhotoDone;
    private boolean getThirdObj;
    private AVObject thirdObject;
    private void getThirdType(){
        AVQuery.doCloudQueryInBackground("select * from ThirdType where thirdTypeId='" + id + "'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if(e==null){
                    if(avCloudQueryResult!=null&&!avCloudQueryResult.getResults().isEmpty()){
                     thirdObject=   avCloudQueryResult.getResults().get(0);
                        handler.sendEmptyMessage(0x22);
                    }
                }else {
                    ToastUtil.showshort(ChooseUploadActivity.this,e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        byte [] bitmapBytes=intent.getByteArrayExtra("bitmap");
        if(bitmapBytes!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            bitmaps.add(bitmap);
            displayImage(bitmap);
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    isGetProduct=true;
                    break;
                case 0x12:
                   for(int i=0;i<bitmaps.size();i++){
                       upLoadPhoto(bitmaps.get(i));
                   }

                    break;
                case 0x21:
                    uploadPhotoDone=true;
                    if(isGetProduct&&uploadPhotoDone) {
                        AVFile avFile = (AVFile) msg.obj;
                        if(productPhotoType.equals("展示")) {
                            String url = avFile.getUrl();
                            List<String> productPhotoUrl=product.getList("photoShowUrl");
                            if(productPhotoUrl==null){
                                productPhotoUrl=new ArrayList<>();
                            }
                            productPhotoUrl.add(url);
                            product.put("photoShowUrl", productPhotoUrl);
                            product.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        ToastUtil.showshort(ChooseUploadActivity.this, "success");
                                    } else {
                                        ToastUtil.showshort(ChooseUploadActivity.this, e.getMessage());
                                    }
                                }
                            });
                        }else if(productPhotoType.equals("封面")){
                            String url = avFile.getUrl();
                            List<String> productPhotoUrl=product.getList("photoCoverUrl");
                            if(productPhotoUrl==null){
                                productPhotoUrl=new ArrayList<>();
                            }
                            productPhotoUrl.add(url);
                            product.put("photoCoverUrl", productPhotoUrl);
                            product.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        ToastUtil.showshort(ChooseUploadActivity.this, "success");
                                    } else {
                                        ToastUtil.showshort(ChooseUploadActivity.this, e.getMessage());
                                    }
                                }
                            });
                        }

                        else {
                            String url = avFile.getThumbnailUrl(
                                    false,
                                    WHUtil.getDeviceScreenWidth(ChooseUploadActivity.this) * 1 /2 - WHUtil.dip2px(ChooseUploadActivity.this, 20),
                                    WHUtil.getDeviceScreenWidth(ChooseUploadActivity.this) * 1 / 2 - WHUtil.dip2px(ChooseUploadActivity.this, 20));
                            product.put("thumbUrl", url);
                            product.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        ToastUtil.showshort(ChooseUploadActivity.this, "success");
                                    } else {
                                        ToastUtil.showshort(ChooseUploadActivity.this, e.getMessage());
                                    }
                                }
                            });
                        }
                        isGetProduct=false;
                        uploadPhotoDone=false;
                    }else {
                        ToastUtil.showshort(ChooseUploadActivity.this,"please commit upload later or check network");
                    }
                    break;
                case 0x22:
                    getThirdObj=true;
                    break;
                case 0x111:
                    int pos=(int)msg.obj;
                    productPhotoType=PRODUCTPHOTOTYPE[pos];
                    break;
                case 0121:
                    uploadPhotoDone=true;
                    if(getThirdObj&&uploadPhotoDone) {
                        AVFile avFile = (AVFile) msg.obj;
                     String url=   avFile.getThumbnailUrl(
                                false,
                                WHUtil.getDeviceScreenWidth(ChooseUploadActivity.this)*1/4-WHUtil.dip2px(ChooseUploadActivity.this,20),
                                WHUtil.getDeviceScreenWidth(ChooseUploadActivity.this)*1/4-WHUtil.dip2px(ChooseUploadActivity.this,20));
                        thirdObject.put("photoUrl", url);
                        thirdObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    ToastUtil.showshort(ChooseUploadActivity.this, "success");
                                } else {
                                    ToastUtil.showshort(ChooseUploadActivity.this, e.getMessage());
                                }
                            }
                        });
                        getThirdObj=false;
                        uploadPhotoDone=false;
                    }else {
                        ToastUtil.showshort(ChooseUploadActivity.this,"please commit upload later or check network");
                    }
                    break;
                case 0x123:
                    deleteImage();
                    break;
            }
        }
    };
    private File file;
    private int pos=0;
    private void displayImage(Bitmap bitmap){
        ImageView imageView=new ImageView(ChooseUploadActivity.this);
        imageView.setId(pos);
        int width=(deviceWidth-MyUtils.dip2px(mContext,15)*4)/3;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                width,
                width);
        FrameLayout.LayoutParams params2=new FrameLayout.LayoutParams(
                width,
                width);
        FrameLayout.LayoutParams params1=new FrameLayout.LayoutParams(
                width/3,
                width/3);
        FrameLayout.LayoutParams paramsDelete=new FrameLayout.LayoutParams(
                width/5,
                width/5);
        paramsDelete.gravity= Gravity.RIGHT|Gravity.TOP;
        FrameLayout frameLayout1=new FrameLayout(mContext);
        frameLayout1.setLayoutParams(params);
        frameLayout1.addView(imageView);
        frameLayouts.add(frameLayout1);
        ImageView deleteImage=new ImageView(mContext);
        deleteImage.setImageResource(R.mipmap.tab_del);
        deleteImage.setLayoutParams(paramsDelete);
        deleteImages.add(deleteImage);
        handler.sendEmptyMessage(0x123);
        ProgressBar progressBar=new ProgressBar(mContext);
        params1.gravity= Gravity.CENTER;
        progressBar.setLayoutParams(params1);
        progressBar.setVisibility(View.GONE);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress));
        circleProgressList.add(progressBar);
        frameLayout1.addView(progressBar);
        frameLayout1.addView(deleteImage);
        params.leftMargin=MyUtils.dip2px(mContext,15);
        imageView.setLayoutParams(params2);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(bitmap);

        imageLayout1.setVisibility(View.VISIBLE);

        if(pos>=0&&pos<3){
            imageLayout1.addView(frameLayout1);
            if (pos==2){
                Layout2.setVisibility(View.VISIBLE);
                imageLayout2.setVisibility(View.VISIBLE);
            }
        }
        else if(pos>=3&&pos<=5){
            imageLayout2.addView(frameLayout1);

            if(pos==5){
            }
        }
        pos++;
    }

    private void deleteImage(){
        for(int i=0;i<deleteImages.size();i++){
            final  int position=i;
            deleteImages.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position<=2){
                        imageLayout1.removeView(frameLayouts.get(position));
                        bitmaps.remove(position);
                        circleProgressList.remove(position);
                        deleteImages.remove(position);
                        frameLayouts.remove(position);
                        if(pos>3){
                            imageLayout2.removeView(frameLayouts.get(2));
                            imageLayout1.addView(frameLayouts.get(2));

                        }
                        if(pos==3){
                            Layout2.setVisibility(View.GONE);
                        }
                        pos--;

                    }else {
                        imageLayout2.removeView(frameLayouts.get(position));
                        bitmaps.remove(position);
                        circleProgressList.remove(position);
                        deleteImages.remove(position);
                        frameLayouts.remove(position);
                        if(pos==6){
                        }
                        pos--;
                    }
                    deleteImage();
                }
            });
        }
    }
}
