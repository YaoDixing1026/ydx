package com.product.yao.myapp.sactivity.create;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.myview.TitleBar;
import com.product.yao.myapp.myview.cropper.CropImageView;
import com.sina.weibo.sdk.api.share.Base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;

/**
 * Created by paichufang on 15-12-22.
 */
public class CutImageActivity extends BaseActivity {
    private TextView ttt;
    private ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_image);
        imageLoader= ImageLoaderFactory.create(this);
        final CropImageView cropImageView=(CropImageView)findViewById(R.id.CropImageView);
        cropImageView.setAspectRatio(1, 1);
        cropImageView.setFixedAspectRatio(true);
        TitleBar titleLayout=(TitleBar)findViewById(R.id.cut_title);
        titleLayout.setOnBtnClickListener(new TitleBar.onBtnClickListener() {
            @Override
            public void backClick() {
                finish();
            }

            @Override
            public void moreClick() {
                Intent intent = new Intent();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                cropImageView.getCroppedImage().compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
                byte[] bitmapByte = byteArrayOutputStream.toByteArray();
                intent.putExtra("bitmap", bitmapByte);
                intent.setClassName(CutImageActivity.this.getPackageName(), getIntent().getStringExtra("className"));
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                /*关闭流*/
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void titleClick() {

            }
        });
        titleLayout.setTitleText("裁剪图片");
        titleLayout.setMoreVisible(false);
        titleLayout.setMoreText("确定");
        titleLayout.setMoreTextVisiable(true);

        String path=getIntent().getStringExtra("path");
        Bitmap bitmap= BitmapFactory.decodeFile(path);
        cropImageView.setImageBitmap(bitmap);
    }
}
