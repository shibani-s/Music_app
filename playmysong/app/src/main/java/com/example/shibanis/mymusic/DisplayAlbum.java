package com.example.shibanis.mymusic;





import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shibanis on 4/9/2015.
 */

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class DisplayAlbum extends Activity {

    // Array of strings...

    Set Task = new HashSet();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayalbum);
        getalbumlist();
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
                String product = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item

                    Intent i = new Intent(getApplicationContext(), DisplayAlbumSongs.class);
                    // sending data to new activity

                    i.putExtra("product", product);
                    startActivity(i);
                }

        });
    }
    public void getalbumlist() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int albumColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ALBUM);
            int yearColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.YEAR);


            //add songs to list
            do {
                //   JSONParser jsonParser = new JSONParser();
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);


                String thisAlbum = musicCursor.getString(albumColumn);





                //    new UpdateDB(params).execute();


                Task.add(thisAlbum);


            }
            while (musicCursor.moveToNext());



        }
    }

}


