package com.sparc.BoozeChoose.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.sparc.BoozeChoose.Adapter.DatabaseAdapter;
import com.sparc.BoozeChoose.Adapter.IngredientAdapter;
import com.sparc.BoozeChoose.Model.Drink;
import com.sparc.BoozeChoose.Model.Ingredient;
import com.sparc.BoozeChoose.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoozeChoose extends Activity {

    public static ListView ingredientsList;
    private IngredientAdapter adapter;
    private Ingredient[] ingredientData;
    public static List<Ingredient> myIngredients = new ArrayList<Ingredient>(0);

    public DatabaseAdapter myDbHelper;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ingredientsList = (ListView) findViewById(R.id.ingredientsList);

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


        // button listener for booze button

        Button boozeButton = (Button)findViewById(R.id.boozeButton);
        boozeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Drink> result = chooseMyBooze(myIngredients);
                Intent i = new Intent(BoozeChoose.this,ListDrinks.class);
                for (int x=0; x<result.size(); x++) {
                    i.putExtra("drinks_"+x, new Drink(result.get(x).getId(),result.get(x).getName(),result.get(x).getIngredients()));
                }
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        // populate user's ingredients

        if (!(myIngredients.size() < 1)) {

            ingredientData = myIngredients.toArray(new Ingredient[myIngredients.size()]);

            adapter = new IngredientAdapter(this, BoozeChoose.this, R.layout.ingredient_list_item, ingredientData);

            ingredientsList = (ListView) findViewById(R.id.ingredientsList);

            ingredientsList.setAdapter(adapter);

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (!(myIngredients.size() < 1)) {

            ingredientData = myIngredients.toArray(new Ingredient[myIngredients.size()]);

            adapter = new IngredientAdapter(this, BoozeChoose.this, R.layout.ingredient_list_item, ingredientData);

            ingredientsList = (ListView) findViewById(R.id.ingredientsList);

            if (ingredientsList!=null){
                ingredientsList.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

        }
    }

    private List<Drink> chooseMyBooze(List<Ingredient> ingredients) {

        ArrayList<Drink> result = new ArrayList<Drink>();
        SQLiteDatabase db = myDbHelper.getWritableDatabase();

        List<String> ingredientsStrings = new ArrayList<String>(0);
        for (int x=0; x<ingredients.size(); x++) {
            ingredientsStrings.add(x,ingredients.get(x).getName());
        }

        String listIngredients = "";
        for (int x=0; x< ingredientsStrings.size(); x++) {
            if (x == ingredientsStrings.size()-1) {
                listIngredients = " ingredients LIKE '%" + ingredientsStrings.get(x);
            } else {
                listIngredients = " ingredients LIKE '%" + ingredientsStrings.get(x) + "% AND " + listIngredients;
            }
        }

        Cursor myCursor = db.query("drinks", new String[] {"_id","name","ingredients"}, listIngredients, null, null, null, null);

        try{
            if (myCursor.moveToFirst()){
                do{
                    Drink drink = new Drink();
                    drink.setName(myCursor.getString((myCursor.getColumnIndex("name"))));
                    drink.setId(myCursor.getString((myCursor.getColumnIndex("_id"))));
                    drink.setIngredients(myCursor.getString((myCursor.getColumnIndex("ingredients"))));
                    result.add(drink);
                }while(myCursor.moveToNext());
            }
        }finally{
            myCursor.close();
        }
        db.close();
        return result;
    }

}
