package com.sparc.BoozeChoose.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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
    private Cursor myCursor;

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
                startActivityForResult(i,1);
            }
        });

        // button listener for liquor

        Button addLiquor = (Button)findViewById(R.id.liquorButton);
        addLiquor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","liquor");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(i,0);
            }
        });

        // button listener for liqueur

        Button addLiqueur = (Button)findViewById(R.id.liqueurButton);
        addLiqueur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","liqueur");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(i,0);
            }
        });

        // button listener for misc

        Button addMisc = (Button)findViewById(R.id.miscButton);
        addMisc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(BoozeChoose.this,ListIngredients.class);
                i.putExtra("type","misc");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(i,0);
            }
        });

        // button listener for booze

        Button chooseBooze = (Button)findViewById(R.id.boozeButton);
        chooseBooze.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (myIngredients.size() > 0) {
                    List<Drink> result = chooseMyBooze(myIngredients);

                    Intent i = new Intent(BoozeChoose.this, ListDrinks.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    for (int x=0; x<result.size(); x++) {
                        i.putExtra("drinks", new Drink(result.get(x).getId(),result.get(x).getName(),result.get(x).getIngredients()));
                    }
                    startActivity(i);

                } else {
                    Toast.makeText(BoozeChoose.this, "Please choose some ingredients", Toast.LENGTH_SHORT).show();
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


    private ArrayList<Drink> chooseMyBooze(List<Ingredient> myIngredients) {

        ArrayList<Drink> result = new ArrayList<Drink>();
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        String ingredients = "";

        for (int x=0; x<myIngredients.size(); x++) {
            if (x == myIngredients.size() -1) {
                ingredients = ingredients + "'%" + myIngredients.get(x).getName() + "%'";
            } else {
                ingredients = ingredients + "'%" + myIngredients.get(x).getName() + "%' AND ingredients LIKE ";
            }
        }

        myCursor = db.query("drinks", new String[] {"_id","name","ingredients"}, "ingredients LIKE " + ingredients, null, null, null, null);

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
        myIngredients.clear();
        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (adapter != null) {
            ingredientData =  myIngredients.toArray(new Ingredient[myIngredients.size()]);
            adapter.notifyDataSetChanged();
        }
    }
}
