package com.example.shiyanercontent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText,editText2;
    private Button insertBtn;
    private Button deleteBtn;
    private  Button queryBtn;
    private Button updateBtn;
    private Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bangID();
    }

    private void bangID() {
        insertBtn = findViewById(R.id.zengjia);
        deleteBtn = findViewById(R.id.shanchu);
        queryBtn = findViewById(R.id.chaxun);
        updateBtn = findViewById(R.id.genggai);
        clearBtn = findViewById(R.id.chongzhi);
        editText = findViewById(R.id.et);
        editText2 = findViewById(R.id.et2);

        insertBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.zengjia:

                Date date = new Date();
                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        //配置时间格式
                String simpleDate = simpleDateFormat.format(date);

                String title = editText.getText().toString();
                String content = editText2.getText().toString();
                Uri uri1 = Uri.parse("content://com.example.shiyanerdanciben.contentprodiver/Note");
                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("content",content);
                values.put("date", simpleDate);
                getContentResolver().insert(uri1,values);                //将值传入数据库中
                break;
            case R.id.shanchu:
                String title1 = editText.getText().toString();
                Uri uri2 = Uri.parse("content://com.example.shiyanerdanciben.contentprodiver/Note");
                getContentResolver().delete(uri2,"title=?",new String[]{title1});
                break;
            case R.id.chaxun:
                Uri uri = Uri.parse("content://com.example.shiyanerdanciben.contentprodiver/Note");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                cursor.moveToFirst();
                if(editText.getText().toString().equals("")==false&&editText2.getText().toString().equals("")==true){
                    do{
                        String title2 = cursor.getString(cursor.getColumnIndex("title"));
                        if(title2.equals(editText.getText().toString())){
                            editText2.setText(cursor.getString(cursor.getColumnIndex("content")));
                        }
                        else {
                            editText2.setText("库中无本单词");
                        }
                    }while(cursor.moveToNext());
                    cursor.close();
                    break;
                }
                else if(editText.getText().toString().equals("")==true&&editText2.getText().toString().equals("")==false){
                    do{
                        String content2 = cursor.getString(cursor.getColumnIndex("content"));
                        if(content2.equals(editText2.getText().toString())){
                            editText.setText(cursor.getString(cursor.getColumnIndex("title")));
                        }
                        else {
                            editText.setText("库中无本单词");
                        }
                    }while(cursor.moveToNext());
                    cursor.close();
                    break;
                }

            case R.id.genggai:
                Uri uri3 =Uri.parse("content://com.example.shiyanerdanciben.contentprodiver/Note");
                Cursor cursor1 = getContentResolver().query(uri3,null,null,null,null);
                cursor1.moveToFirst();
                do{

                    String title3 = cursor1.getString(cursor1.getColumnIndex("title"));
                    ContentValues values1 = new ContentValues();
                    values1.put("content",editText2.getText().toString());

                    String content3 = cursor1.getString(cursor1.getColumnIndex("content"));
                    ContentValues values2 = new ContentValues();
                    values2.put("title",editText.getText().toString());

                    if(title3.equals(editText.getText().toString())) {
                        getContentResolver().update(uri3,values1,"title=?",new String[]{title3});
                    }
                    else if(content3.equals(editText2.getText().toString())){
                        getContentResolver().update(uri3,values2,"content=?",new String[]{content3});
                    }

                }while(cursor1.moveToNext());
                cursor1.close();
                break;
            case R.id.chongzhi:
                editText.setText("");
                editText2.setText("");
                break;
            default:
        }

    }
}