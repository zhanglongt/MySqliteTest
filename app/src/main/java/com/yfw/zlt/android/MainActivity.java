package com.yfw.zlt.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    // 帐号和密码
    private EditText edname;
    private EditText edpassword;

    private Button btregister;
    private Button btlogin;
    // 创建SQLite数据库
    public static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edname = (EditText) findViewById(R.id.edname);
        edpassword = (EditText) findViewById(R.id.edpassword);
        btregister = (Button) findViewById(R.id.btregister);
        btlogin = (Button) findViewById(R.id.btlogin);
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/test.dbs", null);
        Log.i("ii","gg:"+this.getFilesDir().toString() + "/test.dbs");
        // 跳转到注册界面
        btregister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });
        btlogin.setOnClickListener(new LoginListener());

    }

    class LoginListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String name = edname.getText().toString();
            String password = edpassword.getText().toString();
            if (name.equals("") || password.equals("")) {
                // 弹出消息框
                new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                        .setMessage("帐号或密码不能空").setPositiveButton("确定", null)
                        .show();
            } else {
                isUserinfo(name, password);
            }
        }
    }
    // 判断输入的用户是否正确
    public Boolean isUserinfo(String name, String pwd) {
        try{
            String str="select * from tb_user where name=? and password=?";
            Cursor cursor = db.rawQuery(str, new String []{name,pwd});
            if(cursor.getCount()<=0){
                new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                        .setMessage("帐号或密码错误！").setPositiveButton("确定", null)
                        .show();
                return false;
            }else{
                new AlertDialog.Builder(MainActivity.this).setTitle("正确")
                        .setMessage("成功登录").setPositiveButton("确定", null)
                        .show();
                return true;
            }

        }catch(SQLiteException e){
            createDb();
        }
        return false;
    }
    // 创建数据库和用户表
    public void createDb() {
        db.execSQL("create table tb_user( name varchar(30) primary key,password varchar(30))");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}