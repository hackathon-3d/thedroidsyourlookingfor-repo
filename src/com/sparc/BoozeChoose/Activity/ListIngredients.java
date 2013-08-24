package com.sparc.BoozeChoose.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import com.sparc.BoozeChoose.Adapter.IngredientAdapter;
import com.sparc.BoozeChoose.Model.Ingredient;
import com.sparc.BoozeChoose.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Whitney Champion
 * Date: 8/23/13
 * Time: 11:01 PM
 * Description:
 */
public class ListIngredients extends BoozeChoose {


    public Ingredient[] ingredientData;
    public IngredientAdapter adapter;
    public ListView ingredients;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_list);

        // get intent extras

        Bundle extras = getIntent().getExtras();
        String type = "";
        if (getIntent().getStringExtra("type") != null) {
            type = extras.getString("type");
        }

        // populate ingredients for each type

        List<Ingredient> ingredients = getIngredients(type);
        if (!(ingredients.size() < 1)) {

            ingredientData = ingredients.toArray(new Ingredient[ingredients.size()]);

            adapter = new IngredientAdapter(this, ListIngredients.this, R.layout.ingredient_list_item, ingredientData);

            this.ingredients = (ListView) findViewById(R.id.ingredientListView);

            this.ingredients.setAdapter(adapter);

        }

        Button closeButton = (Button)findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ListIngredients.this,BoozeChoose.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }

    // get list of ingredients from database
    public List<Ingredient> getIngredients(String type) {
        ArrayList<Ingredient> result = new ArrayList<Ingredient>();
        SQLiteDatabase db = myDbHelper.getWritableDatabase();

        Cursor myCursor = db.query(type, new String[] {"_id","name"}, null, null, null, null, null);

        try{
            if (myCursor.moveToFirst()){
                do{
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(myCursor.getString((myCursor.getColumnIndex("name"))));
                    ingredient.set_id(myCursor.getString((myCursor.getColumnIndex("_id"))));
                    result.add(ingredient);
                }while(myCursor.moveToNext());
            }
        }finally{
            myCursor.close();
        }
        db.close();
        return result;
    }



}