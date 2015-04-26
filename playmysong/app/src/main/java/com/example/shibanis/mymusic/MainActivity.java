package com.example.shibanis.mymusic;





import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.database.sqlite.*;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {
    final String PREFS_NAME = "MyPrefsFile";
    private static String url_create_product = "http://6b55cca7.ngrok.com/createproduct.php";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "Name";
    private static final String TAG_NAME = "Tag";
    private static final String TAG_VALUE = "Value";

    private static String url_get_product = "http://6b55cca7.ngrok.com/gettags.php";
    JSONArray products = null;
    // Array of strings...

  //  String[] Tasks = {"Artists", "Albums", "Allsongs",};
  ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        Set Play = new HashSet();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time7", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");
            SQLiteDatabase db = openOrCreateDatabase("se_project", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS song(sid BIGINT,title VARCHAR,album VARCHAR, artist VARCHAR,year VARCHAR);");
            db.execSQL("CREATE TABLE IF NOT EXISTS tagged(sid BIGINT,tag VARCHAR,value VARCHAR, FOREIGN KEY (sid) references song(sid));");
            db.execSQL("CREATE TABLE IF NOT EXISTS playlist(pl_name VARCHAR,sid BIGINT, FOREIGN KEY (sid) references song(sid));");

            //db.execSQL("DELETE FROM playlist");
            //db.execSQL("INSERT INTO playlist VALUES('Driving','Happy');");
            //db.execSQL("INSERT INTO playlist VALUES('Driving','Fast');");
          //  db.execSQL("INSERT INTO tagged VALUES('Driving','Fast','90');");
            // first time task
            new UpdateDB().execute();
            Play.add("My_playlists");
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time7", false).commit();
        }
       else {
            SQLiteDatabase db = openOrCreateDatabase("se_project", MODE_PRIVATE, null);
            Cursor c = db.rawQuery("SELECT * FROM playlist", null);

            c.moveToFirst();
            //Toast.makeText(getApplicationContext(),c.getString(0) + "",Toast.LENGTH_LONG).show();
            StringBuffer buffer = new StringBuffer();
            int i = 0;
            while (c.moveToNext()) {
                buffer.append("name : " + c.getString(0));
               // Play.add(c.getString(0));
                buffer.append("tag : " + c.getString(1) + "\n");
            }
            Play.add("My_playlists");
            Toast.makeText(getApplicationContext(), (String) buffer.toString(),
                    Toast.LENGTH_LONG).show();
        }
        ArrayList<String> Tasks = new ArrayList<String>(Play);
        Tasks.add("Artists");
        Tasks.add("Albums");
        Tasks.add("Allsongs");
        Tasks.add("Year");


        adapter = new ArrayAdapter<String>(this,
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
                if(product.equals("Allsongs")) {
                    Intent i = new Intent(getApplicationContext(), DisplayAllSongs.class);
                    // sending data to new activity

                    i.putExtra("product", product);
                    startActivity(i);
                }
                else if(product.equals("Albums")) {
                    Intent i = new Intent(getApplicationContext(), DisplayAlbum.class);
                    // sending data to new activity
                    i.putExtra("product", product);
                    startActivity(i);
                }
                else if(product.equals("Artists")) {
                    Intent i = new Intent(getApplicationContext(), DisplayArtists.class);
                    // sending data to new activity
                    i.putExtra("product", product);
                    startActivity(i);
                }
                else if(product.equals("Year")) {
                    Intent i = new Intent(getApplicationContext(), DisplayYear.class);
                    // sending data to new activity

                    i.putExtra("product", product);
                    startActivity(i);
                }
                else if(product.equals("My_playlists")) {
                    Intent i = new Intent(getApplicationContext(), DisplayPlaylists.class);
                    // sending data to new activity

                    i.putExtra("product", product);
                    startActivity(i);
                }
            }
        });


    }
    class UpdateDB extends AsyncTask<String, String, Void> {
        // List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        // public UpdateDB(List<NameValuePair> p)

        @Override
        protected Void doInBackground(String... hi) {

            System.out.println("hi hey hello");
            JSONParser jsonParser = new JSONParser();
            ContentResolver musicResolver = getContentResolver();
            Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
            int i=1;
            if(musicCursor!=null && musicCursor.moveToFirst()) {
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
                    String thisArtist = musicCursor.getString(artistColumn);
                    String thisAlbum = musicCursor.getString(albumColumn);
                    String thisYear = musicCursor.getString(yearColumn);
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("Name", thisTitle));
                    params.add(new BasicNameValuePair("Album", thisAlbum));
                    params.add(new BasicNameValuePair("Artist", thisArtist));
                    params.add(new BasicNameValuePair("Year", thisYear));
                       SQLiteDatabase db = openOrCreateDatabase("se_project", MODE_PRIVATE, null);
                    //db.execSQL("DELETE FROM playlist");
                    Log.d("myid",String.valueOf(thisId));
                     db.execSQL("INSERT INTO song VALUES('"+thisId+"','"+thisTitle+"','"+thisAlbum+"','"+thisArtist+"','"+thisYear+"');");
                    i++;
                    // new UpdateDB(params).execute();
                       JSONObject json = jsonParser.makeHttpRequest(url_create_product,"POST", params);

                }
                while (musicCursor.moveToNext());
                Log.d("done", "after loop");
            }

            if(musicCursor!=null && musicCursor.moveToFirst()) {

               int  titleColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.TITLE);
                int idColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media._ID);
                int artistColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.ARTIST);
                int albumColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.ALBUM);
                int yearColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.YEAR);
                SQLiteDatabase db = openOrCreateDatabase("se_project", MODE_PRIVATE, null);
                do {
                    //   JSONParser jsonParser = new JSONParser();

                    long thisId = musicCursor.getLong(idColumn);
                    String thisTitle = musicCursor.getString(titleColumn);

                    String thisArtist = musicCursor.getString(artistColumn);
                    String thisAlbum = musicCursor.getString(albumColumn);
                    String thisYear = musicCursor.getString(yearColumn);
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("Name", thisTitle));
                    params.add(new BasicNameValuePair("Album", thisAlbum));
                    params.add(new BasicNameValuePair("Artist", thisArtist));
                    params.add(new BasicNameValuePair("Year", thisYear));

                    // new UpdateDB(params).execute();
                    JSONObject json = jsonParser.makeHttpRequest(url_get_product, "POST", params);
                    // JSONObject json = jsonParser.makeHttpRequest(url_all_products,"POST", params);
                    //   Log.d("All Products: ", json.toString());
                    //  Log.d("Debug", json.toString());
                    int success = 0;
                    try {
                        success = json.getInt("success");
                        if (success == 1) {
                            products = json.getJSONArray(TAG_PRODUCTS);
                            for (int j = 0; j < products.length(); j++) {
                                JSONObject c = products.getJSONObject(j);

                                // Storing each json item in variable
                                String id = c.getString(TAG_PID);
                                String name = c.getString(TAG_NAME);
                                String value = c.getString(TAG_VALUE);

                                Cursor ci = db.rawQuery("SELECT * FROM song WHERE title='" + thisTitle + "'", null);
                                Set Play = new HashSet();
                                ci.moveToFirst();
                               Log.d("nameoftag",name+value);
                                db.execSQL("INSERT INTO tagged VALUES('" + ci.getLong(0) + "','" + name + "','" + value + "');");
                                //Toast.makeText(getApplicationContext(),c.getString(0) + "",Toast.LENGTH_LONG).show();

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    i++;

                }
                while (musicCursor.moveToNext());


            }


            return null;

        }


        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}

