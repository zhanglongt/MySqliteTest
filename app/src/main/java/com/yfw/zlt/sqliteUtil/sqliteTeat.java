package com.yfw.zlt.sqliteUtil;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yfw.zlt.android.R;

public class sqliteTeat extends Activity implements View.OnClickListener{
    Button ru,xun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_teat);
        ru= (Button) findViewById(R.id.ru);
        xun= (Button) findViewById(R.id.xun);
        ru.setOnClickListener(this);
        xun.setOnClickListener(this);

    }
    private void mInsert(){
        //插入
        DatabaseUtil dbUtil = new DatabaseUtil(this);
        dbUtil.open();
        dbUtil.createStudent("123","123");
        dbUtil.close();
    }

    private void mQuery(){
        //查询
        DatabaseUtil dbUtil = new DatabaseUtil(this);
        dbUtil.open();
        Cursor cursor = dbUtil.fetchAllStudents();
        if(cursor != null){
            while(cursor.moveToNext()){
                Log.i("Student","id:"+cursor.getString(0)+";Student Name: " + cursor.getString(1) +
                        " ;Grade: " + cursor.getString(2));
            }
        }
        dbUtil.close();
    }

    private void mDelete(){
        DatabaseUtil dbUtil=new DatabaseUtil(this);
        dbUtil.open();
        boolean b = dbUtil.deleteStudent(1);
        if(b){
            
            Toast.makeText(sqliteTeat.this,"删除成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ru:
                mInsert();
               // mDelete();
                break;
            case R.id.xun:
                mQuery();
                break;
        }
    }
}
