package com.cn.lottery.leeyybssc.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/6/6.
 */

public class AppInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("lee","onReceive :::");
        if(action.equals(Intent.ACTION_PACKAGE_ADDED)){
            String pkgName = intent.getDataString().substring(8);
            Log.d("lee","app installed :::"+pkgName);
            Toast.makeText(context,"准备删除旧数据",Toast.LENGTH_SHORT).show();
            if(pkgName.equals("com.bxvip.app.dayingjiacaizy")){
                Uri uri = Uri.fromParts("package","com.cn.lottery.leeyybssc",null );
                Intent intent1 = new Intent(Intent.ACTION_DELETE, uri);
                context.startActivity(intent1);
            }

        }else if(action.equals(Intent.ACTION_PACKAGE_REMOVED)){
            Log.d("lee","app uninstalled");
        }else if(action.equals(Intent.ACTION_PACKAGE_REPLACED)){
            Log.d("lee","app uninstalled");
            String pkgName = intent.getDataString().substring(8);
            Log.d("lee","app ACTION_PACKAGE_REPLACED :::"+pkgName);
            if(pkgName.equals("com.bxvip.app.dayingjiacaizy")){
                Uri uri = Uri.fromParts("package","com.cn.lottery.leeyybssc",null );
                Intent intent1 = new Intent(Intent.ACTION_DELETE, uri);
                context.startActivity(intent1);
            }
        }
    }
}
