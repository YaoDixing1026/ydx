package com.product.yao.myapp.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {

	public static void show(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}

	public static void show(Context context, int info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}
	
	public static void showshort(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}

	public static void showshort(Context context, int info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}
	public static void showshortCenter(Context context, String info) {
		Toast toast = Toast.makeText(context, info,
				Toast.LENGTH_SHORT);
		//可以控制toast显示的位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
