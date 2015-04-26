package com.example.shibanis.mymusic;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.*;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shibanis.mymusic.JSONParser;

import static com.example.shibanis.mymusic.R.id.planets_spinner;

public class AddPlaylist extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    EditText inputName;
    MultiSpinner inputPrice;


    // url to create new product
    private static String url_create_product = "http://647ab108.ngrok.com/create_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addplaylist);

        MultiSpinner spinner = (MultiSpinner) findViewById(planets_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Edit Text
        inputName = (EditText) findViewById(R.id.inputName);
        //inputPrice = (Spinner) findViewById(R.id.planets_spinner);
        //final MultiSpinner inputPrice = new MultiSpinner(getApplicationContext());//findViewById(R.id.planets_spinner));

        inputPrice = (MultiSpinner) findViewById(planets_spinner);
        final List<String> items = new ArrayList<>(3);
        items.add("Fast");
        items.add("Slow");
        items.add("Happy");
        items.add("Romantic");
        items.add("Instrumental");
        items.add("Sad");
        inputPrice.setItems(items, getString(R.string.app_name), null);


        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

        //int tag_count=0;
        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SQLiteDatabase db = openOrCreateDatabase("se_project", MODE_PRIVATE, null);
                //db.execSQL("DELETE FROM playlist");
                String name = inputName.getText().toString();
                Log.d("myname",name);
                String query = "INSERT INTO playlist VALUES('" + name+ "',";

                boolean[] sel = inputPrice.getSelected();
                for(int j=0;j<sel.length;j++)
                {
                    if(sel[j])
                    {
                        String q = query + "'" + items.get(j) + "');";
                        db.execSQL(q);
                    }
                    //Toast.makeText(getApplicationContext(),""+sel[j],Toast.LENGTH_LONG).show();
                }

                Cursor c = db.rawQuery("SELECT * FROM playlist", null);

                c.moveToFirst();
                //Toast.makeText(getApplicationContext(),c.getString(0) + "",Toast.LENGTH_LONG).show();
                StringBuffer buffer = new StringBuffer();
                int i = 0;
                while(c.moveToNext()) {
                    buffer.append("Name : " + c.getString(0) + " ");
                    buffer.append("Tag : " + c.getString(1)+"\n");
                }
                Toast.makeText(getApplicationContext(), (String) buffer.toString(),
                        Toast.LENGTH_LONG).show();

                // Intent i = new Intent(getApplicationContext(), MainActivity.class);

                //startActivity(i);
            }
        });
    }

    /**
     * Background Async Task to Create new product
     * */

}



