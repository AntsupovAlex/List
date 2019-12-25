package com.example.list;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    EditText textBox;
    Button saveButton;
    Button deleteButton;

    DatabaseHelper sqlhelper;
    SQLiteDatabase database;
    Cursor listCursor;
    long listID = 0;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        textBox = (EditText) findViewById(R.id.enter_edit_text);
        deleteButton = (Button) findViewById(R.id.enter_delete_text);
        saveButton = (Button)findViewById(R.id.enter_add_text);

        sqlhelper = new DatabaseHelper(this);
        database = sqlhelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            listID = extras.getLong("id");
        }

        //if  0 when add
        if (listID>0) {
            //получаем элемент по id из бд
            listCursor = database.rawQuery("select * from "+ DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(listID)});

            listCursor.moveToFirst();
            textBox.setText(listCursor.getString(1));
            listCursor.close();
        } else {
            //скрывает кнопку удаления
            deleteButton.setVisibility(View.GONE);
        }
    }

    public void save(View view) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TEXT, textBox.getText().toString());

        if (listID>0) {
            database.update(DatabaseHelper.TABLE,cv,DatabaseHelper.COLUMN_ID + "=" + String.valueOf(listID),null);
        } else {
            database.insert(DatabaseHelper.TABLE,null,cv);
        }

        goHome();
    }

    public void delete(View view) {
        database.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(listID)});
        goHome();
    }

    private void goHome() {
        database.close();

        //переход на главную активити
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
