package com.example.shibanis.mymusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by shibanis on 4/9/2015.
 */

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FirstPage extends Activity {

    // Array of strings...
    String[] Tasks = {"sleeping", "driving", "cleaning", "studying"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, Tasks);

        ListView listView = (ListView) findViewById(R.id.country_list);
        listView.setAdapter(adapter);



        // listening to single list item on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item 
                String product = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), Song.class);
                // sending data to new activity
                i.putExtra("product", product);
                startActivity(i);

            }
        });
    }
}

