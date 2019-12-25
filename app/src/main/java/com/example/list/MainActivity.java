package com.example.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    ListView listToDo;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor listCursor;
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listToDo = (ListView) findViewById(android.R.id.list);
        listToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = databaseHelper.getReadableDatabase();

        listCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);

        String[] headers = new String[] {DatabaseHelper.COLUMN_TEXT};

        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, listCursor,
                headers, new int[]{android.R.id.text1},0);
        listToDo.setAdapter(cursorAdapter);
    }

    public void addMain(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        listCursor.close();
    }
}
