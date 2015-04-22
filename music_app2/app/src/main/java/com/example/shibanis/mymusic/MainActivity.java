package com.example.shibanis.mymusic;





import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends Activity {

    // Array of strings...
    String[] Tasks = {"artists", "albums", "allsongs",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, Tasks);

        ListView listView = (ListView) findViewById(R.id.song_list);
        listView.setAdapter(adapter);



        // listening to single list item on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                String product = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                if(product.equals("allsongs")) {
                    Intent i = new Intent(getApplicationContext(), DisplayAllSongs.class);
                    // sending data to new activity

                    i.putExtra("product", product);
                    startActivity(i);
                }
                else if(product.equals("albums")) {
                    Intent i = new Intent(getApplicationContext(), DisplayAlbum.class);
                    // sending data to new activity

                    i.putExtra("product", product);
                    startActivity(i);
                }
                else if(product.equals("artists")) {
                    Intent i = new Intent(getApplicationContext(), DisplayArtists.class);
                    // sending data to new activity

                    i.putExtra("product", product);
                    startActivity(i);
                }
            }
        });
    }
}

