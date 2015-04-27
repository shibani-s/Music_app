package com.example.shibanis.mymusic;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rohan on 26/4/15.
 */
public class add_man_playlist extends Activity {
    Set Task = new HashSet();
    String str;    private ArrayList<Song> songList;


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

            ListView listView = (ListView) findViewById(R.id.song_list1);

            songList = new ArrayList<Song>();

            getSongList();

            // new UpdateDB().execute();


            Collections.sort(songList, new Comparator<Song>() {
                public int compare(Song a, Song b) {
                    return a.getTitle().compareTo(b.getTitle());
                }
            });
            SongAdapter songAdt = new SongAdapter(this, songList);
            listView.setAdapter(songAdt);



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


    int i=0;
    private ArrayList<String> sl = new ArrayList<String>();


    public void getSongList() {
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
                sl.add(thisTitle);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum = musicCursor.getString(albumColumn);
                String thisYear = musicCursor.getString(yearColumn);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Name", thisTitle));
                params.add(new BasicNameValuePair("Album", thisAlbum));
                params.add(new BasicNameValuePair("Artist", thisArtist));
                params.add(new BasicNameValuePair("Year", thisYear ));
                //    new UpdateDB(params).execute();


                songList.add(new Song(thisId, thisTitle, thisArtist, thisYear));
                i++;

            }
            while (musicCursor.moveToNext());

        }
    }
    public void songPicked(View view){
        String song_name = view.getTag().toString();

        SQLiteDatabase db = openOrCreateDatabase("se_project", MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT sid FROM song where title="+song_name+";", null);
        if(c.moveToFirst()) {
            db.execSQL("INSERT INTO playlist values("+str+","+song_name+") ");
        }
       }

}
