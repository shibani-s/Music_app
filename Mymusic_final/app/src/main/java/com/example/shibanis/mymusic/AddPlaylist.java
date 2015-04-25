package com.example.shibanis.mymusic;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shibanis.mymusic.JSONParser;

public class AddPlaylist extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    Spinner inputPrice;


    // url to create new product
    private static String url_create_product = "http://647ab108.ngrok.com/create_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addplaylist);
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Edit Text
        inputName = (EditText) findViewById(R.id.inputName);
        inputPrice = (Spinner) findViewById(R.id.planets_spinner);


        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                String name = inputName.getText().toString();
                Log.d("myname",name);
                String price = String.valueOf(inputPrice.getSelectedItem());


                SQLiteDatabase db = openOrCreateDatabase("se_project", MODE_PRIVATE, null);
                //db.execSQL("DELETE FROM playlist");
                db.execSQL("INSERT INTO playlist VALUES('"+name+"','"+price+"');");

                Cursor c = db.rawQuery("SELECT * FROM playlist", null);

                c.moveToFirst();
                //Toast.makeText(getApplicationContext(),c.getString(0) + "",Toast.LENGTH_LONG).show();
                StringBuffer buffer = new StringBuffer();
                int i = 0;
                while(c.moveToNext()) {
                    buffer.append("name : " + c.getString(0));
                    buffer.append("tag : " + c.getString(1)+"\n");
                }
                Toast.makeText(getApplicationContext(), (String) buffer.toString(),
                        Toast.LENGTH_LONG).show();
                Intent k = new Intent(getApplicationContext(), DisplayPlaylists.class);

                startActivity(k);
            }
        });
    }

    /**
     * Background Async Task to Create new product
     * */

}

