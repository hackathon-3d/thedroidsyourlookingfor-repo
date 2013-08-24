package com.sparc.BoozeChoose.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
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

        Button mixersText = (Button)findViewById(R.id.mixersText);
        mixersText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","mixers");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

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

        Button liquorText = (Button)findViewById(R.id.liquorText);
        liquorText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","liquor");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

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

        Button liqueurText = (Button)findViewById(R.id.liqueurText);
        liqueurText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","liqueur");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

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

        Button miscText = (Button)findViewById(R.id.miscText);
        miscText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","misc");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

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
                if (result.size()<1) {
                    Toast.makeText(BoozeChoose.this,"No drinks were found containing those ingredients", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(BoozeChoose.this,ListDrinks.class);
                    for (int x=0; x<result.size(); x++) {
                        i.putExtra("drinks_"+x,new Drink(result.get(x).getId(),result.get(x).getName(),result.get(x).getIngredients()));
                    }
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
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

        List<String> ingredientsString = new ArrayList<String>(0);
        for (int x=0; x<ingredients.size(); x++) {
            ingredientsString.add(x,ingredients.get(x).getName());
        }

        String listIngredients = "";
        for (String ingredient:ingredientsString) {
            if (ingredientsString.size()==1) {
                listIngredients = " ingredients LIKE '%" + ingredient + "%'";
            } else if (ingredientsString.indexOf(ingredient) == ingredientsString.size()-1) {
                listIngredients = " ingredients LIKE '%" + ingredient + "%' AND " + listIngredients;
            } else {
                listIngredients = " ingredients LIKE '%" + ingredient + "%'" + listIngredients;
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
