package com.sparc.BoozeChoose.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.sparc.BoozeChoose.Adapter.DatabaseAdapter;
import com.sparc.BoozeChoose.R;

import java.io.IOException;

public class BoozeChoose extends Activity {

    public DatabaseAdapter myDbHelper;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // set up db

        myDbHelper = new DatabaseAdapter(this);

        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        // button listener for mixers

        Button addMixer = (Button)findViewById(R.id.mixersButton);
        addMixer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","mixers");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        // button listener for liquor

        Button addLiquor = (Button)findViewById(R.id.liquorButton);
        addLiquor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","liquor");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        // button listener for liqueur

        Button addLiqueur = (Button)findViewById(R.id.liqueurButton);
        addLiqueur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","liqueur");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        // button listener for misc

        Button addMisc = (Button)findViewById(R.id.miscButton);
        addMisc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","misc");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

    }
}
