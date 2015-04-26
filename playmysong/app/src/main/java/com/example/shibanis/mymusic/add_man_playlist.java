package com.example.shibanis.mymusic;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rohan on 26/4/15.
 */
public class add_man_playlist extends Activity {
    Set Task = new HashSet();
    String str;

    @Override
    public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.displayallsongs);
            Bundle extras= getIntent().getExtras();
            str=extras.getString("product");
            ArrayList<String> Tasks = new ArrayList<String>(Task);
            //   Tasks = Task.toArray(new ArrayList<String>);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.activity_listview, Tasks);

            ListView listView = (ListView) findViewById(R.id.song_list);
            listView.setAdapter(adapter);



            // listening to single list item on click
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // selected item
                    String song_name = ((TextView) view).getText().toString();

                    SQLiteDatabase db = openOrCreateDatabase("se_project", MODE_PRIVATE, null);
                    Cursor c = db.rawQuery("SELECT sid FROM song where title="+song_name+");", null);
                    if(c.moveToFirst()) {
                        db.execSQL("");
                    }
                    // Launching new Activity on selecting single List Item

                    // sending data to new activity

                }

            });
        }

}
