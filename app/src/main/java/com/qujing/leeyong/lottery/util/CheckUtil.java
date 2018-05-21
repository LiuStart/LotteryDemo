package com.qujing.leeyong.lottery.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/4/17.
 */

public class CheckUtil {
    //网络是否连接
    public static boolean isNetworkAvailable(Context context) {
        boolean flag = false;
        ConnectivityManager systemService = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (systemService.getActiveNetworkInfo() != null) {
            flag = systemService.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }
    public static void setNet(final Context mContext) {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,
                AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle("网络提示信息");
        builder.setMessage("数据连接不可用，如果继续，请先设置！");
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
				/*Intent intent = null;
				if (android.os.Build.VERSION.SDK_INT > 10) {
					intent = new Intent(
							android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				} else {
					intent = new Intent();
					ComponentName component = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}*/
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                mContext.startActivity(intent);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(mContext, "没有网络将无法获取数据！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }
}
