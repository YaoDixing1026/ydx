package com.product.yao.myapp.sactivity.create;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.utils.MyUtils;
import com.product.yao.myapp.utils.ToastUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class PictureUploadActivity extends BaseActivity {
    private String token;
    private String userID;
    protected static final String TAG = PictureUploadActivity.class.getSimpleName();

    static final int CAPTURE_REQUEST_CODE = 100;
    static final int PICK_REQUEST_CODE = 200;

    private List<String> fileNameInService = new ArrayList<String>();
    private Uri currentPhotoUri;
    private int action;
    private File file = null;
    private File pic = null;
    private String productName;
    private String productId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_loading);
        action = getIntent().getIntExtra("action", CAPTURE_REQUEST_CODE);
        productId=getIntent().getStringExtra("productId");
        productName=getIntent().getStringExtra("productName");
        if (action == PICK_REQUEST_CODE) {
            //打开照片选择页面，选择完之后带参数打开本页面
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");//相片类型  
            startActivityForResult(intent, PICK_REQUEST_CODE);
        } else {
            //打开拍照页面，选择完之后带参数打开本页面，先调用onActivityResult方法
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            final String timestamp = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
            currentPhotoUri = MyUtils.getOutputMediaFileUri(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE, timestamp);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri);
            startActivityForResult(intent, CAPTURE_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_REQUEST_CODE) {
//                Toast.makeText(getApplicationContext(), R.string.image_please_wait, Toast.LENGTH_LONG).show();
                LinearLayout loading = (LinearLayout) findViewById(R.id.progressBar);
                loading.setVisibility(View.VISIBLE);
                String s=currentPhotoUri.getPath();

            } else if (requestCode == PICK_REQUEST_CODE) {
//                Toast.makeText(getApplicationContext(), R.string.image_please_wait, Toast.LENGTH_LONG).show();

                currentPhotoUri = data.getData();
                if(currentPhotoUri.getPath()==null){
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap  photo = (Bitmap) bundle.get("data"); //get bitmap
//                        upload2Server(photo);
                    }else{
                        Log.i("bundle != null","bundle != null");
                    }
                }else{
                    Log.i("currentPhotoUri", currentPhotoUri.getPath().toString());
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(currentPhotoUri, projection, null, null, null);
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String s=cursor.getString(columnIndex);

                    ToastUtil.showshort(PictureUploadActivity.this,"success");
                    Intent processingActivity = new Intent(getApplicationContext(),
                            ChooseUploadActivity.class)
                            .putExtra("path", s)
                            .putExtra("productName", productName)
                            .putExtra("productId",productId);
                    processingActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(processingActivity);
//                    upload2Server(s);
                    cursor.close();
                }

            }
        } else {
            Intent processingActivity = new Intent(getApplicationContext(),
                    ChooseUploadActivity.class);
            processingActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(processingActivity);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();

    }

}

