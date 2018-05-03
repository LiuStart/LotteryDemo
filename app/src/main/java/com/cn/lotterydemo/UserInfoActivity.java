package com.cn.lotterydemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.lotterydemo.util.BitMapUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/4/28.
 */

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView imageView;
    private TextView tv_nicheng;
    private TextView tv_xingbie;
    private SharedPreferences USER;
    private String Name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfoactivity);
        USER=getSharedPreferences("USER", Context.MODE_PRIVATE);
        imageView=findViewById(R.id.set_userhead);
        imageView.setOnClickListener(this);
        tv_nicheng=findViewById(R.id.nicheng);
        tv_xingbie=findViewById(R.id.xingbie);
        RelativeLayout setxingbie=findViewById(R.id.setxingbie);
        setxingbie.setOnClickListener(this);
        RelativeLayout setnicheng=findViewById(R.id.setnicheng);
        setnicheng.setOnClickListener(this);
        Button loginout=findViewById(R.id.bt_loginout);
        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                USER.edit().remove(Name+"NICHENG").commit();
                USER.edit().remove(Name+"XINGBIE").commit();
                USER.edit().remove(Name+"HEAD").commit();
                USER.edit().remove("NAME").commit();
                Toast.makeText(UserInfoActivity.this, "用户已注销！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        initData();
    }

    private void initData() {
        Name=USER.getString("NAME","");
        String nicheng=USER.getString(Name+"NICHENG","");
        String xingbie=USER.getString(Name+"XINGBIE","");
       final String headPath=USER.getString(Name+"HEAD","");
        if(TextUtils.isEmpty(nicheng)){
            tv_nicheng.setText("设置昵称");
        }else {
            tv_nicheng.setText(nicheng);
        }
        if(TextUtils.isEmpty(xingbie)){
            tv_xingbie.setText("保密");
        }else {
            tv_xingbie.setText(xingbie);
        }
        if(TextUtils.isEmpty(headPath)){

        }else {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(BitMapUtil.loadBitmap(headPath,imageView));
                }
            });
         //   imageView.setImageBitmap(BitmapFactory.decodeFile(headPath));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_userhead:
                getImage();
                break;
            case R.id.setxingbie:
                getxingbie();
                break;
            case R.id.setnicheng:
                login(tv_nicheng.getText().toString());
                break;
        }
    }

    private void getxingbie() {
        AlertDialog.Builder build =new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.xingbie, null);
        final  CheckBox man=view.findViewById(R.id.man);
        final  CheckBox women=view.findViewById(R.id.woman);
        final CheckBox noman=view.findViewById(R.id.noman);
        man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(man.isChecked()){
                    women.setChecked(false);
                    noman.setChecked(false);
                }
            }
        });
        women.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (women.isChecked()) {
                    man.setChecked(false);
                    noman.setChecked(false);
                }
            }
        });
        noman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (noman.isChecked()) {
                    man.setChecked(false);
                    women.setChecked(false);
                }
            }
        });
        build.setView(view);
        build.setTitle("选择性别");
        build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(man.isChecked()){
                    tv_xingbie.setText("男");
                    USER.edit().putString(Name+"XINGBIE","男").commit();
                } else if (women.isChecked()) {
                    tv_xingbie.setText("女");
                    USER.edit().putString(Name+"XINGBIE","女").commit();
                } else if (noman.isChecked()) {
                    tv_xingbie.setText("保密");
                    USER.edit().putString(Name+"XINGBIE","保密").commit();
                }
            }
        });
        build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        build.create();
        build.show();

    }

    private void getImage() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //这里要传一个整形的常量RESULT_LOAD_IMAGE到startActivityForResult()方法。
        startActivityForResult(intent, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            //查询我们需要的数据
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            USER.edit().putString(USER.getString("NAME","")+"HEAD", picturePath).commit();
            Toast.makeText(UserInfoActivity.this, "设置成功", Toast.LENGTH_SHORT).show();

        }
    }
    private void login(String name) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final EditText username = view.findViewById(R.id.username);
        if (!TextUtils.isEmpty(name)) {
            username.setText(name);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("填写信息");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getText())) {
                    Toast.makeText(UserInfoActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    USER.edit().putString("USERNAME", username.getText().toString().trim()).commit();
                    tv_nicheng.setText( username.getText().toString().trim());
                    USER.edit().putString((USER.getString("NAME","")+"NICHENG"),username.getText().toString()).commit();
                    Toast.makeText(UserInfoActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
    }
}
